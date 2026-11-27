package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.core.blocks.LirothPortalBlock;
import com.decodinator.liroth.core.blocks.PortalChargeFrameBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;

// New Portal method as CPAPI suddenly drops support post 1.21.1

public final class LirothPortalBuilders {
	private static final int PORTAL_SIZE = 3;
	private static final FramePlane[] FRAME_PLANES = {
			new FramePlane(Direction.Axis.X, Direction.Axis.Z, true),
			new FramePlane(Direction.Axis.X, Direction.Axis.Y, false),
			new FramePlane(Direction.Axis.Z, Direction.Axis.Y, false)
	};

	private LirothPortalBuilders() {
	}

	// Portal creation junk, such as whether or not its 3x3 as well as calls to the functions for the portal creation and lightning strike
	public static boolean tryCreatePortal(Level level, BlockPos chargePos, BlockState chargedFrameState, Block regularFrame, LirothPortalBlock portal) {
		Block chargeFrame = chargedFrameState.getBlock();
		for (FramePlane plane : FRAME_PLANES) {
			for (int width = -3; width <= 3; width++) {
				for (int height = -3; height <= 3; height++) {
					BlockPos portalOrigin = offset(chargePos, plane, width, height);
					if (isValidFrame(level, portalOrigin, chargePos, chargeFrame, regularFrame, plane)) {
						buildPortal(level, portalOrigin, regularFrame, portal, plane);
						strikePortalCenter(level, portalOrigin, plane);
						return true;
					}
				}
			}
		}
		return false;
	}


	// CRACK-A-TOWA!!! Lightning strike the center of the portal on creation
	private static void strikePortalCenter(Level level, BlockPos origin, FramePlane plane) {
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		BlockPos center = offset(origin, plane, PORTAL_SIZE / 2, PORTAL_SIZE / 2);
		LightningBolt lightning = EntityTypes.LIGHTNING_BOLT.create(serverLevel, EntitySpawnReason.TRIGGERED);
		if (lightning != null) {
			lightning.setVisualOnly(true);
			lightning.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
			serverLevel.addFreshEntity(lightning);
		}
	}

	// If I break the block frame, break the portal, easy as that shit
	public static void removePortalForFrame(Level level, BlockPos framePos, LirothPortalBlock portal) {
		for (FramePlane plane : FRAME_PLANES) {
			for (int width = -1; width <= PORTAL_SIZE; width++) {
				for (int height = -1; height <= PORTAL_SIZE; height++) {
					if ((width >= 0 && width < PORTAL_SIZE && height >= 0 && height < PORTAL_SIZE)
							|| isCorner(width, height)) {
						continue;
					}

					BlockPos origin = offset(framePos, plane, -width, -height);
					if (!hasPortalInterior(level, origin, portal, plane)) {
						continue;
					}

					for (int portalWidth = 0; portalWidth < PORTAL_SIZE; portalWidth++) {
						for (int portalHeight = 0; portalHeight < PORTAL_SIZE; portalHeight++) {
							BlockPos portalPos = offset(origin, plane, portalWidth, portalHeight);
							if (level.getBlockState(portalPos).is(portal)) {
								level.destroyBlock(portalPos, false);
							}
						}
					}
				}
			}
		}
	}

	// make a portal or find a pre-existing portal in either-or dimension to act as the exiting portal
	public static BlockPos findOrCreateExit(ServerLevel level, BlockPos target, LirothPortalBlock portal) {
		BlockPos existing = findExistingPortal(level, target, portal);
		if (existing != null) {
			return existing;
		}

		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, target.getX(), target.getZ());
		BlockPos origin = new BlockPos(target.getX() - 1, y, target.getZ() - 1);
		Block frame = portal.getPortalFrame();
		for (int x = -1; x <= PORTAL_SIZE; x++) {
			for (int z = -1; z <= PORTAL_SIZE; z++) {
				if (x == -1 || x == PORTAL_SIZE || z == -1 || z == PORTAL_SIZE) {
					level.setBlockAndUpdate(origin.offset(x, 0, z), frame.defaultBlockState());
				}
			}
		}
		for (int x = 0; x < PORTAL_SIZE; x++) {
			for (int z = 0; z < PORTAL_SIZE; z++) {
				BlockPos portalPos = origin.offset(x, 0, z);
				if (!level.getBlockState(portalPos.below()).isSolid()) {
					level.setBlockAndUpdate(portalPos.below(), frame.defaultBlockState());
				}
				level.setBlockAndUpdate(portalPos, portal.defaultBlockState());
			}
		}
		return origin;
	}

	// should be explanatory as is
	private static boolean isValidFrame(Level level, BlockPos origin, BlockPos chargePos, Block chargeFrame, Block regularFrame, FramePlane plane) {
		int chargedFrames = 0;
		for (int width = -1; width <= PORTAL_SIZE; width++) {
			for (int height = -1; height <= PORTAL_SIZE; height++) {
				if (width >= 0 && width < PORTAL_SIZE && height >= 0 && height < PORTAL_SIZE) {
					continue;
				}
				if (isCorner(width, height)) {
					continue;
				}
				BlockPos framePos = offset(origin, plane, width, height);
				BlockState state = level.getBlockState(framePos);
				if (state.is(chargeFrame)) {
					if (framePos.equals(chargePos)) {
						if (!state.getValue(PortalChargeFrameBlock.CHARGED)) {
							return false;
						}
						chargedFrames++;
					}
				} else if (!state.is(regularFrame)) {
					return false;
				}
			}
		}
		return chargedFrames == 1;
	}

	// same for this one lmao
	private static boolean isCorner(int width, int height) {
		return (width == -1 || width == PORTAL_SIZE)
				&& (height == -1 || height == PORTAL_SIZE);
	}

	// this one I made so that it gets rid of any shit above the portal blocks itself cause it was happening way too often
	private static void buildPortal(Level level, BlockPos origin, Block regularFrame, LirothPortalBlock portal, FramePlane plane) {
		for (int width = 0; width < PORTAL_SIZE; width++) {
			for (int height = 0; height < PORTAL_SIZE; height++) {
				BlockPos pos = offset(origin, plane, width, height);
				if (plane.horizontal && !level.getBlockState(pos.below()).isSolid()) {
					level.setBlockAndUpdate(pos.below(), regularFrame.defaultBlockState());
				}
				level.setBlockAndUpdate(pos, portal.defaultBlockState());
				if (plane.horizontal) {
					level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
					level.setBlockAndUpdate(pos.above(2), Blocks.AIR.defaultBlockState());
				}
			}
		}
	}

	private static BlockPos offset(BlockPos origin, FramePlane plane, int width, int height) {
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		switch (plane.widthAxis) {
			case X -> x += width;
			case Y -> y += width;
			case Z -> z += width;
		}
		switch (plane.heightAxis) {
			case X -> x += height;
			case Y -> y += height;
			case Z -> z += height;
		}
		return new BlockPos(x, y, z);
	}

	private record FramePlane(Direction.Axis widthAxis, Direction.Axis heightAxis, boolean horizontal) {
	}

	private static BlockPos findExistingPortal(ServerLevel level, BlockPos target, LirothPortalBlock portal) {
		int minY = Math.max(level.getMinY(), target.getY() - 16);
		int maxY = Math.min(level.getMaxY() - 3, target.getY() + 16);
		for (int x = target.getX() - 16; x <= target.getX() + 16; x++) {
			for (int z = target.getZ() - 16; z <= target.getZ() + 16; z++) {
				for (int y = minY; y <= maxY; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if (!level.getBlockState(pos).is(portal)) {
						continue;
					}
					for (int dx = -2; dx <= 0; dx++) {
						for (int dz = -2; dz <= 0; dz++) {
							BlockPos origin = pos.offset(dx, 0, dz);
							if (isPortalInterior(level, origin, portal)) {
								return origin;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private static boolean isPortalInterior(ServerLevel level, BlockPos origin, LirothPortalBlock portal) {
		for (int x = 0; x < PORTAL_SIZE; x++) {
			for (int z = 0; z < PORTAL_SIZE; z++) {
				if (!level.getBlockState(origin.offset(x, 0, z)).is(portal)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean hasPortalInterior(Level level, BlockPos origin, LirothPortalBlock portal, FramePlane plane) {
		for (int width = 0; width < PORTAL_SIZE; width++) {
			for (int height = 0; height < PORTAL_SIZE; height++) {
				if (!level.getBlockState(offset(origin, plane, width, height)).is(portal)) {
					return false;
				}
			}
		}
		return true;
	}
}