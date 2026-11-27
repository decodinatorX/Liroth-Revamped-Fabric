package com.decodinator.liroth.core.blockentities;

import java.util.Map;


import com.decodinator.liroth.client.screens.LirothSplitterScreenHandler;
import com.decodinator.liroth.core.blocks.LirothSplitterBlock;
import com.decodinator.liroth.core.recipetypes.AbstractSplitterRecipe;
import com.decodinator.liroth.core.registry.LirothBlockEntities;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class LirothSplitterBlockEntity extends BlockEntity implements MenuProvider, Container, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private NonNullList<ItemStack> inventory =
            NonNullList.withSize(5, ItemStack.EMPTY);
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal = getCookTime();
    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> LirothSplitterBlockEntity.this.burnTime;
                case 1 -> LirothSplitterBlockEntity.this.fuelTime;
                case 2 -> LirothSplitterBlockEntity.this.cookTime;
                case 3 -> LirothSplitterBlockEntity.this.cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    LirothSplitterBlockEntity.this.burnTime = value;
                    break;
                }
                case 1: {
                    LirothSplitterBlockEntity.this.fuelTime = value;
                    break;
                }
                case 2: {
                    LirothSplitterBlockEntity.this.cookTime = value;
                    break;
                }
                case 3: {
                    LirothSplitterBlockEntity.this.cookTimeTotal = value;
                    break;
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    private final Object2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<RecipeInput, ? extends AbstractSplitterRecipe> quickCheck;

    public static final int totalTime = 50;

    public LirothSplitterBlockEntity(BlockPos pos, BlockState state, RecipeType<? extends AbstractSplitterRecipe> recipeThingy) {
        super(LirothBlockEntities.LIROTH_SPLITTER, pos, state);
        this.quickCheck = RecipeManager.createCheck(recipeThingy);
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("container.liroth_splitter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NonNull Inventory inv, @NonNull Player player) {
        return new LirothSplitterScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("BurnTime", this.burnTime);
        output.putInt("FuelTime", this.fuelTime);
        output.putInt("CookTime", this.cookTime);
        output.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(output, this.inventory);
        output.store("RecipesUsed", RECIPES_USED_CODEC, this.recipesUsed);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.inventory);
        this.burnTime = input.getIntOr("BurnTime", 0);
        this.fuelTime = input.getIntOr("FuelTime", 0);
        this.cookTime = input.getIntOr("CookTime", 0);
        this.cookTimeTotal = input.getIntOr("CookTimeTotal", getCookTime());
        if (this.burnTime > 0 && this.fuelTime <= 0) {
            this.fuelTime = Math.max(this.burnTime, this.getFuelTime(this.inventory.get(1)));
        }
        this.recipesUsed.clear();
        this.recipesUsed.putAll(input.read("RecipesUsed", RECIPES_USED_CODEC).orElse(Map.of()));
    }

    public static void playSound(Level world, BlockPos pos, SoundEvent sound) {
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void serverTick(ServerLevel p_155014_, BlockPos p_155015_, BlockState p_155016_, LirothSplitterBlockEntity p_155017_) {
        boolean flag = p_155017_.isBurning();
        boolean flag1 = false;
        if (p_155017_.isBurning()) {
            --p_155017_.burnTime;
        }

        ItemStack fuel = p_155017_.inventory.get(1);
        ItemStack input = p_155017_.inventory.get(0);
        boolean hasInput = !input.isEmpty();
        boolean hasFuel = !fuel.isEmpty();
        if (p_155017_.isBurning() || hasFuel && hasInput) {
            RecipeHolder<? extends AbstractSplitterRecipe> recipe;
            if (hasInput) {
                recipe = p_155017_.quickCheck.getRecipeFor(new SingleRecipeInput(input), p_155014_).orElse(null);
            } else {
                recipe = null;
            }

            if (recipe != null && p_155017_.cookTimeTotal != recipe.value().getCookingTime()) {
                p_155017_.cookTime = 0;
                p_155017_.cookTimeTotal = Math.max(1, recipe.value().getCookingTime());
            }

            int i = p_155017_.getMaxStackSize();
            if (!p_155017_.isBurning() && p_155017_.canBurn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                p_155017_.burnTime = p_155017_.getFuelTime(fuel);
                p_155017_.fuelTime = p_155017_.burnTime;
                if (p_155017_.isBurning()) {
                    flag1 = true;
                    ItemStackTemplate remainder = fuel.getCraftingRemainder();
                    fuel.shrink(1);
                    if (remainder != null) {
                        if (fuel.isEmpty()) {
                            p_155017_.inventory.set(1, remainder.create());
                        } else {
                            Containers.dropItemStack(p_155014_, p_155015_.getX(), p_155015_.getY(), p_155015_.getZ(), remainder.create());
                        }
                    } else if (fuel.isEmpty()) {
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
            p_155016_ = p_155016_.setValue(LirothSplitterBlock.LIT, p_155017_.isBurning());
            p_155014_.setBlock(p_155015_, p_155016_, 3);
        }

        if (flag1) {
            setChanged(p_155014_, p_155015_, p_155016_);
        }

    }

    private boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractSplitterRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, LirothSplitterBlockEntity furnace) {
        if (recipe == null || inventory.getFirst().isEmpty()) {
            return false;
        }

        AbstractSplitterRecipe splitterRecipe = recipe.value();
        ItemStack result = splitterRecipe.assemble(furnace, registryAccess);
        return !result.isEmpty()
                && canFitOutput(inventory.get(2), result, maxStackSize)
                && canFitOutput(inventory.get(3), splitterRecipe.getBonusItem().getStack(), maxStackSize)
                && canFitOutput(inventory.get(4), splitterRecipe.getBonusItem2().getStack(), maxStackSize);
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

    private boolean burn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractSplitterRecipe> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_, LirothSplitterBlockEntity entity) {
        if (this.canBurn(registryAccess, p_155027_, p_155028_, p_155029_, entity)) {
            ItemStack itemstack = p_155028_.getFirst();
            AbstractSplitterRecipe splitterRecipe = p_155027_.value();
            addOutput(p_155028_, 2, splitterRecipe.assemble(this, registryAccess));
            assert level != null;
            addOutput(p_155028_, 3, splitterRecipe.createBonus(level.getRandom()));
            addOutput(p_155028_, 4, splitterRecipe.createBonus2(level.getRandom()));

            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !p_155028_.get(1).isEmpty() && p_155028_.get(1).is(Items.BUCKET)) {
                p_155028_.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
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

    private static int getCookTime() {
        return (200);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    protected int getFuelTime(ItemStack p_58343_) {
        if (p_58343_.isEmpty()) {
            return 0;
        } else {
            if (this.level instanceof ServerLevel serverLevel) {
                return ResolvableInt.getFromItem(
                        p_58343_, DataComponents.COOKING_FUEL, CookingFuel::burnTime, getFuelLootContext(serverLevel, p_58343_), 0);
            }
            return 0;
        }
    }

    private LootContext getFuelLootContext(ServerLevel serverLevel, ItemStack fuel) {
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.BLOCK_STATE, this.getBlockState())
                .withParameter(LootContextParams.BLOCK_ENTITY, this)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.getBlockPos()))
                .withParameter(LootContextParams.CONTAINER, this)
                .create(LootContextParamSets.CONTAINER_PROCESS);
        return new LootContext.Builder(params).create(java.util.Optional.empty());
    }

    @Override
    public boolean canPlaceItem(int slot, @NonNull ItemStack stack) {
        if (slot == 2 || slot == 3 || slot == 4) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.inventory.get(1);
            return stack.has(DataComponents.COOKING_FUEL) || stack.is(Items.BUCKET) && !itemStack.is(Items.BUCKET);
        }
        return true;
    }

    @Override
    public void clearContent() {
        this.inventory.clear();

    }

    @Override
    public int getContainerSize() {
        return 5;
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
    public @NonNull ItemStack getItem(int p_18941_) {
        return this.inventory.get(p_18941_);

    }

    @Override
    public @NonNull ItemStack removeItem(int p_18942_, int p_18943_) {
        return ContainerHelper.removeItem(this.inventory, p_18942_, p_18943_);
    }

    @Override
    public @NonNull ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.inventory, p_18951_);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        ItemStack itemstack = this.inventory.get(p_18944_);
        boolean bl = !p_18945_.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, p_18945_);
        this.inventory.set(p_18944_, p_18945_);
        if (p_18945_.getCount() > this.getMaxStackSize()) {
            p_18945_.setCount(this.getMaxStackSize());
        }

        if (p_18944_ == 0 && !bl) {
            this.cookTimeTotal = getTotalCookTime();
            this.cookTime = 0;
            this.setChanged();
        }
    }

    private static int getTotalCookTime() {
        return 200;
    }

    @Override
    public boolean stillValid(@NonNull Player p_18946_) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_18946_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void drops() {
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, this.inventory);
        }
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (this.level instanceof ServerLevel) {
            Containers.dropContents(this.level, pos, this.inventory);
        }
    }

    public int @NonNull [] getSlotsForFace(@NonNull Direction p_58363_) {
        if (p_58363_ == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return p_58363_ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    public boolean canPlaceItemThroughFace(int p_58336_, @NonNull ItemStack p_58337_, @Nullable Direction p_58338_) {
        return this.canPlaceItem(p_58336_, p_58337_);
    }

    public boolean canTakeItemThroughFace(int p_58392_, @NonNull ItemStack p_58393_, @NonNull Direction p_58394_) {
        if (p_58394_ == Direction.DOWN && p_58392_ == 1) {
            return p_58393_.is(Items.WATER_BUCKET) || p_58393_.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> p_58345_) {
        if (p_58345_ != null) {
            this.recipesUsed.addTo(p_58345_.id(), 1);
        }

    }

}