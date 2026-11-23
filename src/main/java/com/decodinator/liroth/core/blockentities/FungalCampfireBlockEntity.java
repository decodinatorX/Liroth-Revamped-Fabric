package com.decodinator.liroth.core.blockentities;

import java.util.Optional;

import com.decodinator.liroth.core.blocks.FungalCampfireBlock;

import com.decodinator.liroth.core.registry.LirothBlockEntities;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FungalCampfireBlockEntity extends BlockEntity implements Clearable {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private final int[] cookingProgress = new int[4];
	private final int[] cookingTime = new int[4];
	private final RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);

	public FungalCampfireBlockEntity(BlockPos p_155301_, BlockState p_155302_) {
		super(LirothBlockEntities.FUNGAL_CAMPFIRE, p_155301_, p_155302_);
	}

	public static void cookTick(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, FungalCampfireBlockEntity p_155310_) {
		boolean flag = false;
		for(int i = 0; i < p_155310_.items.size(); ++i) {
			ItemStack itemstack = p_155310_.items.get(i);
			if (!itemstack.isEmpty()) {
				flag = true;
                if (p_155310_.cookingProgress[i] >= p_155310_.cookingTime[i]) {
					SingleRecipeInput container = new SingleRecipeInput(itemstack);
					ItemStack itemstack1 = p_155310_.quickCheck.getRecipeFor(container, p_155307_).map((p_270054_) -> p_270054_.value().assemble(container, p_155307_.registryAccess())).orElse(itemstack);
					Containers.dropItemStack(p_155307_, p_155308_.getX(), p_155308_.getY(), p_155308_.getZ(), itemstack1);
					p_155310_.items.set(i, ItemStack.EMPTY);
					p_155307_.sendBlockUpdated(p_155308_, p_155309_, p_155309_, 3);
					p_155307_.gameEvent(GameEvent.BLOCK_CHANGE, p_155308_, GameEvent.Context.of(p_155309_));
				}
			}
		}

		if (flag) {
			setChanged(p_155307_, p_155308_, p_155309_);
		}
	}

	public static void cooldownTick(Level p_155314_, BlockPos p_155315_, BlockState p_155316_, FungalCampfireBlockEntity p_155317_) {
		boolean flag = false;
		for(int i = 0; i < p_155317_.items.size(); ++i) {
			if (p_155317_.cookingProgress[i] > 0) {
				flag = true;
				p_155317_.cookingProgress[i] = Mth.clamp(p_155317_.cookingProgress[i] - 2, 0, p_155317_.cookingTime[i]);
			}
		}

		if (flag) {
			setChanged(p_155314_, p_155315_, p_155316_);
		}
	}

	public static void particleTick(Level p_155319_, BlockPos p_155320_, BlockState p_155321_, FungalCampfireBlockEntity p_155322_) {
		RandomSource randomsource = p_155319_.random;
		if (randomsource.nextFloat() < 0.11F) {
			for(int i = 0; i < randomsource.nextInt(2) + 2; ++i) {
				FungalCampfireBlock.makeParticles(p_155319_, p_155320_, p_155321_.getValue(FungalCampfireBlock.SIGNAL_FIRE), false);
			}
		}

		int l = p_155321_.getValue(FungalCampfireBlock.FACING).get2DDataValue();
		for(int j = 0; j < p_155322_.items.size(); ++j) {
			if (!p_155322_.items.get(j).isEmpty() && randomsource.nextFloat() < 0.2F) {
				Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
                double d0 = (double)p_155320_.getX() + 0.5D - (double)((float)direction.getStepX() * 0.3125F) + (double)((float)direction.getClockWise().getStepX() * 0.3125F);
				double d1 = (double)p_155320_.getY() + 0.5D;
				double d2 = (double)p_155320_.getZ() + 0.5D - (double)((float)direction.getStepZ() * 0.3125F) + (double)((float)direction.getClockWise().getStepZ() * 0.3125F);

				for(int k = 0; k < 4; ++k) {
					p_155319_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
				}
			}
		}
	}

	public NonNullList<ItemStack> getItems() {
		return this.items;
	}

	protected void saveAdditional(CompoundTag p_187486_, HolderLookup.Provider registries) {
		super.saveAdditional(p_187486_, registries);
		ContainerHelper.saveAllItems(p_187486_, this.items, registries);
		p_187486_.putIntArray("CookingTimes", this.cookingProgress);
		p_187486_.putIntArray("CookingTotalTimes", this.cookingTime);
	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag compoundtag = new CompoundTag();
		ContainerHelper.saveAllItems(compoundtag, this.items, true, registries);
		return compoundtag;
	}

	public Optional<RecipeHolder<CampfireCookingRecipe>> getCookableRecipe(ItemStack stack) {
		return this.items.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), this.level);
	}

	public boolean placeFood(@Nullable Entity p_238285_, ItemStack p_238286_, int p_238287_) {
		for(int i = 0; i < this.items.size(); ++i) {
			ItemStack itemstack = this.items.get(i);
			if (itemstack.isEmpty()) {
				this.cookingTime[i] = p_238287_;
				this.cookingProgress[i] = 0;
				this.items.set(i, p_238286_.split(1));
                assert this.level != null;
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(p_238285_, this.getBlockState()));
				this.markUpdated();
				return true;
			}
		}
		return false;
	}

	private void markUpdated() {
		this.setChanged();
        assert this.getLevel() != null;
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public void clearContent() {
		this.items.clear();
	}

	public void dowse() {
		if (this.level != null) {
			this.markUpdated();
		}
	}
}