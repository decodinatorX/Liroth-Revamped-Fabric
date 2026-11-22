package com.decodinator.liroth.core.blockentities;

import java.util.Collection;
import java.util.Map;


import com.decodinator.liroth.client.screens.LirothSplitterScreenHandler;
import com.decodinator.liroth.core.blocks.LirothSplitterBlock;
import com.decodinator.liroth.core.recipetypes.AbstractSplitterRecipe;
import com.decodinator.liroth.core.registry.LirothBlockEntities;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LirothSplitterBlockEntity extends BlockEntity implements MenuProvider, Container, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    private final RecipeType<? extends AbstractSplitterRecipe> recipeType;
    private NonNullList<ItemStack> inventory =
            NonNullList.withSize(5, ItemStack.EMPTY);
    FriendlyByteBuf extraData;
    private static final Map<Item, Integer> AVAILABLE_FUELS = Maps.newHashMap();
    private int timer;
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal = this.getCookTime();
    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return LirothSplitterBlockEntity.this.burnTime;
                }
                case 1: {
                    return LirothSplitterBlockEntity.this.fuelTime;
                }
                case 2: {
                    return LirothSplitterBlockEntity.this.cookTime;
                }
                case 3: {
                    return LirothSplitterBlockEntity.this.cookTimeTotal;
                }
            }
            return 0;
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

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<RecipeInput, ? extends AbstractSplitterRecipe> quickCheck;

    protected int processTime;
    protected int totalProcessTime;
    private int ticks;

    // uses for both tile entity and jei recipe viewer
    public static final int totalTime = 50;

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.processTime;
            case 1:
                return this.totalProcessTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.processTime = value;
                break;
            case 1:
                this.totalProcessTime = value;
                break;
        }
    }

    public int getFieldCount() {
        return 2;
    }

    public LirothSplitterBlockEntity(BlockPos pos, BlockState state, RecipeType<? extends AbstractSplitterRecipe> recipeThingy) {
        super(LirothBlockEntities.LIROTH_SPLITTER, pos, state);
        this.quickCheck = RecipeManager.createCheck((RecipeType)recipeThingy);
        this.recipeType = recipeThingy;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.liroth_splitter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new LirothSplitterScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("CookTime", this.cookTime);
        nbt.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(nbt, this.inventory, registries);
        CompoundTag compoundtag = new CompoundTag();
        nbt.put("RecipesUsed", compoundtag);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory, registries);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        CompoundTag compoundtag = nbt.getCompound("RecipesUsed");
    }

    public void forceUpdateAllStates() {
        BlockState state = level.getBlockState(worldPosition);
        if (state.getValue(LirothSplitterBlock.LIT) != burnTime > 0) {
            level.setBlock(worldPosition, state.setValue(LirothSplitterBlock.LIT, burnTime > 0), 3);
        }
    }

    public static void playSound(Level world, BlockPos pos, SoundEvent sound) {
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public static void serverTick(Level p_155014_, BlockPos p_155015_, BlockState p_155016_, LirothSplitterBlockEntity p_155017_) {
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

            int i = p_155017_.getMaxStackSize();
            if (!p_155017_.isBurning() && p_155017_.canBurn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                p_155017_.burnTime = p_155017_.getFuelTime(fuel);
                p_155017_.fuelTime = p_155017_.burnTime;
                if (p_155017_.isBurning()) {
                    flag1 = true;
                    if (fuel.getItem().hasCraftingRemainingItem()) {
                        p_155017_.inventory.set(1, fuel);
                    } else if (hasFuel) {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            p_155017_.inventory.set(1, fuel);
                        }
                    }
                }
            }

            if (p_155017_.isBurning() && p_155017_.canBurn(p_155014_.registryAccess(), recipe, p_155017_.inventory, i, p_155017_)) {
                ++p_155017_.cookTime;
                if (p_155017_.cookTime == p_155017_.cookTimeTotal) {
                    p_155017_.cookTime = 0;
                    p_155017_.cookTimeTotal = getTotalCookTime(p_155014_, p_155017_);
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
            p_155016_ = p_155016_.setValue(LirothSplitterBlock.LIT, Boolean.valueOf(p_155017_.isBurning()));
            p_155014_.setBlock(p_155015_, p_155016_, 3);
        }

        if (flag1) {
            setChanged(p_155014_, p_155015_, p_155016_);
        }

    }

    private boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractSplitterRecipe> recipe, NonNullList<ItemStack> inventory, int maxStackSize, LirothSplitterBlockEntity furnace) {
        if (recipe == null || inventory.get(0).isEmpty()) {
            return false;
        }

        AbstractSplitterRecipe splitterRecipe = recipe.value();
        ItemStack result = splitterRecipe.assemble(furnace, registryAccess);
        return !result.isEmpty()
                && canFitOutput(inventory.get(2), result, maxStackSize)
                && canFitOutput(inventory.get(3), splitterRecipe.getBonusItem().bonus, maxStackSize)
                && canFitOutput(inventory.get(4), splitterRecipe.getBonusItem2().bonus, maxStackSize);
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
        if (p_155027_ != null && this.canBurn(registryAccess, p_155027_, p_155028_, p_155029_, entity)) {
            ItemStack itemstack = p_155028_.get(0);
            AbstractSplitterRecipe splitterRecipe = p_155027_.value();
            addOutput(p_155028_, 2, splitterRecipe.assemble(this, registryAccess));
            addOutput(p_155028_, 3, splitterRecipe.createBonus(level.random));
            addOutput(p_155028_, 4, splitterRecipe.createBonus2(level.random));

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

    private static void craftItem(LirothSplitterBlockEntity entity) {

        entity.removeItem(0, 1);

        entity.setItem(2, new ItemStack(LirothItems.LIROTH_DUST_ANSALUM, entity.getItem(2).getCount() + 1));
        entity.setItem(3, new ItemStack(LirothItems.LIROTH_DUST_LUX, entity.getItem(3).getCount() + 1));
        entity.setItem(4, new ItemStack(LirothItems.LIROTH_DUST_SALEM, entity.getItem(4).getCount() + 1));

        entity.ticks = 0;
    }

    private static boolean hasRecipe(LirothSplitterBlockEntity entity) {
        boolean hasItemInFirstSlot = entity.getItem(0).getItem() == LirothBlocks.LIROTH_GEM.ingot;

        return hasItemInFirstSlot;
    }

    private static boolean hasNotReachedStackLimit(LirothSplitterBlockEntity entity) {
        return entity.getItem(2).getCount() < entity.getItem(2).getCount() ||
                entity.getItem(3).getCount() < entity.getItem(3).getCount() ||
                entity.getItem(4).getCount() < entity.getItem(4).getCount();
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
            Item item = p_58343_.getItem();
            return getFabricFuel(p_58343_);
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 2 || slot == 3 || slot == 4) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.inventory.get(1);
            return AbstractFurnaceBlockEntity.isFuel(stack) || stack.is(Items.BUCKET) && !itemStack.is(Items.BUCKET);
        }
        return true;
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return AVAILABLE_FUELS.containsKey(stack.getItem());
    }

    public static Map<Item, Integer> availableFuels() {
        return AVAILABLE_FUELS;
    }

    private static boolean tickReached100(LirothSplitterBlockEntity entity) {
        return entity.ticks <= getCookTime();
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
    public ItemStack getItem(int p_18941_) {
        return this.inventory.get(p_18941_);

    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return ContainerHelper.removeItem(this.inventory, p_18942_, p_18943_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
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
            this.cookTimeTotal = getTotalCookTime(this.level, this);
            this.cookTime = 0;
            this.setChanged();
        }
    }

    private static int getTotalCookTime(Level p_222693_, LirothSplitterBlockEntity p_222694_) {
        return 200;
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_18946_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void drops() {
        SimpleContainer inventoryDrop = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            inventory.set(i, inventory.get(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventoryDrop);
    }

    public int[] getSlotsForFace(Direction p_58363_) {
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
        if (p_58394_ == Direction.DOWN && p_58392_ == 1) {
            return p_58393_.is(Items.WATER_BUCKET) || p_58393_.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    public void setRecipeUsed(@Nullable RecipeHolder<?> p_58345_) {
        if (p_58345_ != null) {
            ResourceLocation resourcelocation = p_58345_.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player p_58396_) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer p_155004_) {
        Collection<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(p_155004_.serverLevel(), p_155004_.position());
        p_155004_.awardRecipes(list);
        this.recipesUsed.clear();
    }

    public Collection<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel p_154996_, Vec3 p_154997_) {
        Collection<RecipeHolder<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            p_154996_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
                list.add(p_155023_);
                createExperience(p_154996_, p_154997_, entry.getIntValue(), ((AbstractSplitterRecipe)p_155023_.value()).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel p_154999_, Vec3 p_155000_, int p_155001_, float p_155002_) {
        int i = Mth.floor((float)p_155001_ * p_155002_);
        float f = Mth.frac((float)p_155001_ * p_155002_);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrb.award(p_154999_, p_155000_, i);
    }

    public void fillStackedContents(StackedContents p_58342_) {
        for(ItemStack itemstack : this.inventory) {
            p_58342_.accountStack(itemstack);
        }

    }
    private static int getFabricFuel(ItemStack stack) {
        Integer ticks = FuelRegistry.INSTANCE.get(stack.getItem());
        return ticks == null ? 0 : ticks;
    }
}