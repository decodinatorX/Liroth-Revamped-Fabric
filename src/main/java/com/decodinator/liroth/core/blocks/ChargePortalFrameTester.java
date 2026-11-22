package com.decodinator.liroth.core.blocks;

import net.kyrptonaught.customportalapi.portal.frame.FlatPortalAreaHelper;
import net.kyrptonaught.customportalapi.portal.frame.PortalFrameTester;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.BlockUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class ChargePortalFrameTester extends FlatPortalAreaHelper {
    private final Block regularFrame;
    private Block chargeFrame;

    public ChargePortalFrameTester(Block regularFrame) {
        this.regularFrame = regularFrame;
    }

    @Override
    public ChargePortalFrameTester init(LevelAccessor world, BlockPos pos, Direction.Axis axis, Block... foundations) {
        chargeFrame = foundations[0];
        Block[] frames = Arrays.copyOf(foundations, foundations.length + 1);
        frames[foundations.length] = regularFrame;
        super.init(world, pos, axis, frames);
        return this;
    }

    @Override
    public Optional<PortalFrameTester> getNewPortal(LevelAccessor world, BlockPos pos, Direction.Axis axis, Block... foundations) {
        ChargePortalFrameTester tester = new ChargePortalFrameTester(regularFrame).init(world, pos, axis, foundations);
        return tester.isValidFrame() && tester.foundPortalBlocks == 0 ? Optional.of(tester) : Optional.empty();
    }

    @Override
    public Optional<PortalFrameTester> getOrEmpty(LevelAccessor world, BlockPos pos, Predicate<PortalFrameTester> predicate, Direction.Axis axis, Block... foundations) {
        return Optional.<PortalFrameTester>of(new ChargePortalFrameTester(regularFrame).init(world, pos, axis, foundations)).filter(predicate);
    }

    @Override
    public boolean isValidFrame() {
        if (!super.isValidFrame() || xSize != 3 || zSize != 3) {
            return false;
        }

        int chargedFrames = 0;
        for (int x = -1; x <= xSize; x++) {
            for (int z = -1; z <= zSize; z++) {
                if (x != -1 && x != xSize && z != -1 && z != zSize) {
                    continue;
                }
                BlockPos framePos = lowerCorner.offset(x, 0, z);
                BlockState frameState = world.getBlockState(framePos);
                if (frameState.is(chargeFrame)) {
                    if (!frameState.getValue(PortalChargeFrameBlock.CHARGED)) {
                        return false;
                    }
                    chargedFrames++;
                }
            }
        }
        return chargedFrames == 1;
    }

    @Override
    public void lightPortal(Block frameBlock) {
        super.lightPortal(frameBlock);
        if (world instanceof Level level && !level.isClientSide) {
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightning.setVisualOnly(true);
            lightning.setPos(lowerCorner.getX() + 1.5, lowerCorner.getY(), lowerCorner.getZ() + 1.5);
            level.addFreshEntity(lightning);
        }
    }

    @Override
    public void createPortal(Level level, BlockPos pos, BlockState chargeFrameState, Direction.Axis axis) {
        BlockState chargedFrame = chargeFrameState.setValue(PortalChargeFrameBlock.CHARGED, true);
        BlockState regularFrameState = regularFrame.defaultBlockState();
        for (int x = -1; x <= 3; x++) {
            for (int z = -1; z <= 3; z++) {
                if (x >= 0 && x <= 2 && z >= 0 && z <= 2) {
                    continue;
                }
                BlockPos framePos = pos.offset(x, 0, z);
                BlockState frameState = x == -1 && z == 1 ? chargedFrame : regularFrameState;
                level.setBlockAndUpdate(framePos, frameState);
            }
        }

        for (int x = -1; x <= 3; x++) {
            for (int z = -1; z <= 3; z++) {
                if (x < 0 || x > 2 || z < 0 || z > 2) {
                    clearReplaceablePortalSpace(level, pos.offset(x, 0, z).above());
                }
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                BlockPos portalPos = pos.offset(x, 0, z);
                BlockPos landingPos = portalPos.below();
                if (!level.getBlockState(landingPos).isSolid()) {
                    level.setBlockAndUpdate(landingPos, regularFrameState);
                }
                clearPortalSpace(level, portalPos.above());
                clearPortalSpace(level, portalPos.above(2));
            }
        }

        lowerCorner = pos;
        xSize = zSize = 3;
        world = level;
        foundPortalBlocks = 9;
        lightPortal(chargeFrameState.getBlock());
    }

    @Override
    public BlockPos doesPortalFitAt(Level level, BlockPos attemptPos, Direction.Axis axis) {
        WorldBorder border = level.getWorldBorder();
        BlockUtil.FoundRectangle rectangle = BlockUtil.getLargestRectangleAround(attemptPos.above(), Axis.X, 5, Axis.Z, 5, pos -> border.isWithinBounds(pos) && level.getBlockState(pos).isSolid() && !level.getBlockState(pos.above()).isSolid() && !level.getBlockState(pos.above()).liquid() && !level.getBlockState(pos.above(2)).isSolid() && !level.getBlockState(pos.above(2)).liquid());
        if (rectangle.axis1Size < 5 || rectangle.axis2Size < 5) {
            return null;
        }

        int surfaceHeight = rectangle.minCorner.getY() + 1;
        for (int x = 0; x < rectangle.axis1Size; x++) {
            for (int z = 0; z < rectangle.axis2Size; z++) {
                BlockPos floorPos = rectangle.minCorner.offset(x, 0, z);
                if (level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, floorPos.getX(), floorPos.getZ()) != surfaceHeight) {
                    return null;
                }
            }
        }
        return rectangle.minCorner;
    }

    private static void clearReplaceablePortalSpace(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.isAir() && !state.isSolid()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 16);
        }
    }

    private static void clearPortalSpace(Level level, BlockPos pos) {
        if (!level.getBlockState(pos).isAir()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 16);
        }
    }
}