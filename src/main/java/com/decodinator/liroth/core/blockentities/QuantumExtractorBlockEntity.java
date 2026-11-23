package com.decodinator.liroth.core.blockentities;

import com.decodinator.liroth.client.screens.QuantumExtractorScreenHandler;
import com.decodinator.liroth.core.recipetypes.AbstractQuantumRecipe;
import com.decodinator.liroth.core.registry.LirothBlockEntities;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import com.decodinator.liroth.core.blocks.QuantumExtractorBlock;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QuantumExtractorBlockEntity extends BlockEntity implements MenuProvider, Container, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal = this.getCookTime();
    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> QuantumExtractorBlockEntity.this.burnTime;
                case 1 -> QuantumExtractorBlockEntity.this.fuelTime;
                case 2 -> QuantumExtractorBlockEntity.this.cookTime;
                case 3 -> QuantumExtractorBlockEntity.this.cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    QuantumExtractorBlockEntity.this.burnTime = value;
                    break;
                }
                case 1: {
                    QuantumExtractorBlockEntity.this.fuelTime = value;
                    break;
                }
                case 2: {
                    QuantumExtractorBlockEntity.this.cookTime = value;
                    break;
                }
                case 3: {
                    QuantumExtractorBlockEntity.this.cookTimeTotal = value;
                    break;
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<RecipeInput, ? extends AbstractQuantumRecipe> quickCheck;


    private int ticks;

    public QuantumExtractorBlockEntity(BlockPos pos, BlockState state, RecipeType<? extends AbstractQuantumRecipe> recipeThingy) {
        super(LirothBlockEntities.QUANTUM_EXTRACTOR, pos, state);
        this.quickCheck = RecipeManager.createCheck(recipeThingy);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.quantum_extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new QuantumExtractorScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void loadAdditional(CompoundTag p_155025_, HolderLookup.Provider registries) {
        super.loadAdditional(p_155025_, registries);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155025_, this.inventory, registries);
        this.burnTime = p_155025_.getInt("BurnTime");
        this.cookTime = p_155025_.getInt("CookTime");
        this.cookTimeTotal = p_155025_.getInt("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        CompoundTag compoundtag = p_155025_.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(s), compoundtag.getInt(s));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag p_187452_, HolderLookup.Provider registries) {
        super.saveAdditional(p_187452_, registries);
        p_187452_.putInt("BurnTime", this.burnTime);
        p_187452_.putInt("CookTime", this.cookTime);
        p_187452_.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(p_187452_, this.inventory, registries);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
    }

    public static void playSound(Level world, BlockPos pos, SoundEvent sound) {
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void serverTick(Level p_155014_, BlockPos p_155015_, BlockState p_155016_, QuantumExtractorBlockEntity p_155017_) {
        boolean flag = p_155017_.isBurning();
        boolean flag1 = false;
        if (p_155017_.isBurning()) {
            --p_155017_.burnTime;
        }

        ItemStack itemstack = p_155017_.inventory.get(1);
        boolean flag2 = !p_155017_.inventory.get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (p_155017_.isBurning() || flag3 && flag2) {
            RecipeHolder<?extends AbstractQuantumRecipe> recipe;
            if (flag2) {
                recipe = p_155017_.quickCheck.getRecipeFor(new SingleRecipeInput(p_155017_.inventory.getFirst()), p_155014_).orElse(null);
            } else {
                recipe = null;
            }

            int i = p_155017_.getMaxStackSize();
            if (recipe != null && p_155017_.cookTimeTotal != recipe.value().getCookingTime()) {
                p_155017_.cookTime = 0;
                p_155017_.cookTimeTotal = Math.max(1, recipe.value().getCookingTime());
            }
            if (!p_155017_.isBurning() && p_155017_.canBurn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                p_155017_.burnTime = p_155017_.getFuelTime(itemstack);
                p_155017_.fuelTime = p_155017_.burnTime;
                if (p_155017_.isBurning()) {
                    flag1 = true;
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        p_155017_.inventory.set(1, ItemStack.EMPTY);
                    }
                }
            }

            if (p_155017_.isBurning() && p_155017_.canBurn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                ++p_155017_.cookTime;
                if (p_155017_.cookTime == p_155017_.cookTimeTotal) {
                    p_155017_.cookTime = 0;
                    p_155017_.cookTimeTotal = Math.max(1, recipe.value().getCookingTime());
                    if (p_155017_.burn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                        p_155017_.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                p_155017_.cookTime = 0;
            }
        } else if (!p_155017_.isBurning() && p_155017_.cookTime > 0) {
            p_155017_.cookTime = Mth.clamp(p_155017_.cookTime - 2, 0, p_155017_.cookTimeTotal);
        }

        if (flag != p_155017_.isBurning()) {
            flag1 = true;
            p_155016_ = p_155016_.setValue(QuantumExtractorBlock.LIT, p_155017_.isBurning());
            p_155014_.setBlock(p_155015_, p_155016_, 3);
        }

        if (flag1) {
            setChanged(p_155014_, p_155015_, p_155016_);
        }
    }

    private boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractQuantumRecipe> recipe, NonNullList<ItemStack> inv, int maxStackSize, QuantumExtractorBlockEntity entity) {
        if (recipe == null || inv.getFirst().isEmpty()) {
            return false;
        }

        AbstractQuantumRecipe quantumRecipe = recipe.value();
        ItemStack result = quantumRecipe.assemble(entity, registryAccess);
        ItemStack bonus = quantumRecipe.getBonusItem().bonus;
        return !result.isEmpty()
                && canFitOutput(inv.get(2), result, maxStackSize)
                && (bonus.isEmpty() || quantumRecipe.getBonusItem().percent <= 0
                || canFitOutput(inv.get(3), bonus, maxStackSize));
    }

    private boolean burn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractQuantumRecipe> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_, QuantumExtractorBlockEntity entity) {
        if (this.canBurn(registryAccess, p_155027_, p_155028_, p_155029_, entity)) {
            ItemStack itemstack = p_155028_.getFirst();
            AbstractQuantumRecipe quantumRecipe = p_155027_.value();
            addOutput(p_155028_, 2, quantumRecipe.assemble(this, registryAccess));
            assert level != null;
            addOutput(p_155028_, 3, quantumRecipe.createBonus(level.random));

            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private static boolean canFitOutput(ItemStack existing, ItemStack output, int maxStackSize) {
        if (output.isEmpty()) {
            return true;
        }
        if (existing.isEmpty()) {
            return output.getCount() <= Math.min(maxStackSize, output.getMaxStackSize());
        }
        return ItemStack.isSameItemSameComponents(existing, output)
                && existing.getCount() + output.getCount() <= Math.min(maxStackSize, existing.getMaxStackSize());
    }

    private static void addOutput(NonNullList<ItemStack> inventory, int slot, ItemStack output) {
        if (output.isEmpty()) {
            return;
        }
        ItemStack existing = inventory.get(slot);
        if (existing.isEmpty()) {
            inventory.set(slot, output.copy());
        } else {
            existing.grow(output.getCount());
        }
    }

    private int getCookTime() {
        return (200);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else if (fuel.is(Blocks.REDSTONE_BLOCK.asItem())) {
            return 600;
        } else {
            return 0;
        }
    }

    @Override
    public boolean canPlaceItem(int p_58389_, ItemStack p_58390_) {
        if (p_58389_ == 2 || p_58389_ == 3) {
            return false;
        } else if (p_58389_ != 1) {
            return true;
        } else {
            return this.getFuelTime(p_58390_) > 0;
        }
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int p_18941_) {
        return this.inventory.get(p_18941_);
    }

    @Override
    public @NotNull ItemStack removeItem(int p_18942_, int p_18943_) {
        return ContainerHelper.removeItem(this.inventory, p_18942_, p_18943_);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.inventory, p_18951_);
    }

    @Override
    public void setItem(int p_58333_, ItemStack p_58334_) {
        ItemStack itemstack = this.inventory.get(p_58333_);
        boolean bl = !p_58334_.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, p_58334_);
        this.inventory.set(p_58333_, p_58334_);
        if (p_58334_.getCount() > this.getMaxStackSize()) {
            p_58334_.setCount(this.getMaxStackSize());
        }

        if (p_58333_ == 0 && !bl) {
            this.cookTimeTotal = getTotalCookTime(this.level, this);
            this.cookTime = 0;
            this.setChanged();
        }
    }

    private static int getTotalCookTime(Level p_222693_, QuantumExtractorBlockEntity p_222694_) {
        return 200;
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_18946_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void drops() {
        SimpleContainer inventoryDrop = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            inventoryDrop.setItem(i, inventory.get(i).copy());
            inventory.set(i, ItemStack.EMPTY);
        }

        Containers.dropContents(this.level, this.worldPosition, inventoryDrop);
    }

    public int @NotNull [] getSlotsForFace(Direction p_58363_) {
        if (p_58363_ == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return p_58363_ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    public boolean canPlaceItemThroughFace(int p_58336_, ItemStack p_58337_, @Nullable Direction p_58338_) {
        return this.canPlaceItem(p_58336_, p_58337_);
    }

    public boolean canTakeItemThroughFace(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
        return p_58394_ != Direction.DOWN || p_58392_ == 2 || p_58392_ == 3;
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> p_58345_) {
        if (p_58345_ != null) {
            ResourceLocation resourcelocation = p_58345_.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }
}