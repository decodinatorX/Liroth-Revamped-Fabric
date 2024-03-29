package com.decodinator.liroth.core.blocks.entity;

import java.util.List;
import java.util.Map;


import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.LirothItems;
import com.decodinator.liroth.core.blocks.LirothSplitterBlock;
import com.decodinator.liroth.core.helpers.AbstractSplitterRecipe;
import com.decodinator.liroth.core.helpers.SplitterRecipe;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
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
    private final RecipeManager.CachedCheck<Container, ? extends AbstractSplitterRecipe> quickCheck;

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
        super(Liroth.LIROTH_SPLITTER_BLOCK_ENTITY, pos, state);
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
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("CookTime", this.cookTime);
        nbt.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(nbt, this.inventory);
        CompoundTag compoundtag = new CompoundTag();
        nbt.put("RecipesUsed", compoundtag);
     }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
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

        ItemStack itemstack = p_155017_.inventory.get(1);
        boolean flag2 = !p_155017_.inventory.get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (p_155017_.isBurning() || flag3 && flag2) {
           Recipe<?> recipe;
           if (flag2) {
              recipe = p_155017_.quickCheck.getRecipeFor(p_155017_, p_155014_).orElse(null);
           } else {
              recipe = null;
           }

           int i = p_155017_.getMaxStackSize();
           if (!p_155017_.isBurning() && p_155017_.canBurn(recipe, p_155017_.inventory, i)) {
              p_155017_.burnTime = p_155017_.getFuelTime(itemstack);
              p_155017_.fuelTime = p_155017_.burnTime;
              if (p_155017_.isBurning()) {
                 flag1 = true;
                 if (itemstack.getItem().hasCraftingRemainingItem())
                    p_155017_.inventory.set(1, itemstack.getItem().getCraftingRemainingItem().getDefaultInstance());
                 else
                 if (flag3) {
                    Item item = itemstack.getItem();
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                       p_155017_.inventory.set(1, itemstack.getItem().getCraftingRemainingItem().getDefaultInstance());
                    }
                 }
              }
           }

           if (p_155017_.isBurning() && p_155017_.canBurn(recipe, p_155017_.inventory, i)) {
              ++p_155017_.cookTime;
              if (p_155017_.cookTime == p_155017_.cookTimeTotal) {
                 p_155017_.cookTime = 0;
                 p_155017_.cookTimeTotal = getTotalCookTime(p_155014_, p_155017_);
                 if (p_155017_.burn(recipe, p_155017_.inventory, i)) {
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
    
    private boolean canBurn(@Nullable Recipe<?> p_155006_, NonNullList<ItemStack> p_155007_, int p_155008_) {
        if (!p_155007_.get(0).isEmpty() && p_155006_ != null) {
           ItemStack itemstack = ((Recipe<WorldlyContainer>) p_155006_).assemble(this, this.level.registryAccess());
           if (itemstack.isEmpty()) {
              return false;
           } else {
              ItemStack itemstack1 = p_155007_.get(2);
              ItemStack itemstack2 = p_155007_.get(3);
              ItemStack itemstack3 = p_155007_.get(4);
               ItemStack itemStackSame1 = (ItemStack)p_155007_.get(2);
               ItemStack itemStackSame2 = (ItemStack)p_155007_.get(3);
               ItemStack itemStackSame3 = (ItemStack)p_155007_.get(4);
               if (itemstack1.isEmpty()) {
                 return true;
              } else if (!itemstack1.isSameItem(itemStackSame1, itemstack)) {
                 return false;
              } else if (itemstack1.getCount() + itemstack.getCount() <= p_155008_ && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                 return true;
              }
              if (itemstack2.isEmpty()) {
                 return true;
              } else if (!itemstack2.isSameItem(itemStackSame2, itemstack)) {
                 return false;
              } else if (itemstack2.getCount() + itemstack.getCount() <= p_155008_ && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                 return true;
              }
              if (itemstack3.isEmpty()) {
                 return true;
              } else if (!itemstack3.isSameItem(itemStackSame3, itemstack)) {
                 return false;
              } else if (itemstack3.getCount() + itemstack.getCount() <= p_155008_ && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                 return true;
              } else {
                 return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize() && itemstack2.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize() && itemstack3.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
              }
           }
        } else {
           return false;
        }
     }

     private boolean burn(@Nullable Recipe<?> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_) {
        if (p_155027_ != null && this.canBurn(p_155027_, p_155028_, p_155029_)) {
           ItemStack itemstack = p_155028_.get(0);
           ItemStack itemstack1 = ((SplitterRecipe) p_155027_).assemble(this);
           ItemStack itemstack2 = p_155028_.get(2);
           ItemStack itemstack3 = p_155028_.get(3);
           ItemStack itemstack4 = p_155028_.get(4);
           AbstractSplitterRecipe fuckOff = ((SplitterRecipe) p_155027_);
           if (itemstack2.isEmpty()) {
              p_155028_.set(2, itemstack1.copy());
           } else if (itemstack2.is(itemstack1.getItem())) {
              itemstack2.grow(itemstack1.getCount());
           }
           if (itemstack3.isEmpty()) {
               p_155028_.set(3, fuckOff.createBonus(level.random));
            } else if (itemstack3.is(fuckOff.getBonusItem().bonus.getItem())) {
               itemstack3.grow(itemstack1.getCount());
            }
           if (itemstack4.isEmpty()) {
               p_155028_.set(4, fuckOff.createBonus2(level.random));
            } else if (itemstack4.is(fuckOff.getBonusItem2().bonus.getItem())) {
               itemstack4.grow(itemstack1.getCount());
            }

           if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !p_155028_.get(1).isEmpty() && p_155028_.get(1).is(Items.BUCKET)) {
              p_155028_.set(1, new ItemStack(Items.WATER_BUCKET));
           }

           itemstack.shrink(1);
           return true;
        } else {
           return false;
        }
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
          boolean bl = !p_18945_.isEmpty() && ItemStack.isSameItemSameTags(itemstack, p_18945_);
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
	     
	     public void setRecipeUsed(@Nullable Recipe<?> p_58345_) {
	         if (p_58345_ != null) {
	            ResourceLocation resourcelocation = p_58345_.getId();
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
	         List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(p_155004_.serverLevel(), p_155004_.position());
	         p_155004_.awardRecipes(list);
	         this.recipesUsed.clear();
	      }

	      public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel p_154996_, Vec3 p_154997_) {
	         List<Recipe<?>> list = Lists.newArrayList();

	         for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
	            p_154996_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
	               list.add(p_155023_);
	               createExperience(p_154996_, p_154997_, entry.getIntValue(), ((AbstractSplitterRecipe)p_155023_).getExperience());
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