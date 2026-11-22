package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.betterx.bclib.behaviours.interfaces.BehaviourSapling;

public class CustomSapling extends SaplingBlock implements BehaviourSapling {
	private static final VoxelShape SHAPE = box(4, 0, 4, 12, 14, 12);

	public CustomSapling(TreeGrower treeGrower) {
		super(treeGrower, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING));
	}

		@Override
		public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			return SHAPE;
		}
	}