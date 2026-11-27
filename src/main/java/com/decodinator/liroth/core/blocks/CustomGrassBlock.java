package com.decodinator.liroth.core.blocks;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.decodinator.liroth.core.blocks.basis.CustomSpreadableBlock;
import com.decodinator.liroth.core.blocks.basis.FabricToolModifiedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CustomGrassBlock extends CustomSpreadableBlock implements BonemealableBlock {
    public Supplier<Block> tilled;
    public Supplier<Block> pathed;
    public CustomGrassBlock(BlockBehaviour.Properties p_53685_, Block theDirt, Supplier<Block> theTilled, Supplier<Block> thePathed) {
        super(p_53685_);
        dirtBlock = theDirt;
        tilled = theTilled;
        pathed = thePathed;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        Block drop = SilkTouchLoot.hasSilkTouch(params) ? this : dirtBlock;
        return List.of(new ItemStack(drop));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return level.getBlockState(pos.above()).isAir() && level.isInsideBuildHeight(pos.above());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        BlockPos above = pos.above();
        Optional<Holder.Reference<PlacedFeature>> grassFeature = level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE).get(VegetationPlacements.GRASS_BONEMEAL);

        label46:
        for (int attempt = 0; attempt < 128; attempt++) {
            BlockPos testPos = above;

            for (int i = 0; i < attempt / 16; i++) {
                testPos = testPos.offset(random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1);
                if (!level.getBlockState(testPos.below()).is(this) || level.getBlockState(testPos).isCollisionShapeFullBlock(level, testPos)) {
                    continue label46;
                }
            }

            BlockState testState = level.getBlockState(testPos);
            if (testState.is(Blocks.SHORT_GRASS) && random.nextFloat() < 0.1F) {
                BonemealableBlock grass = (BonemealableBlock) Blocks.SHORT_GRASS;
                if (grass.isValidBonemealTarget(level, testPos, testState, source)) {
                    grass.performBonemeal(level, random, testPos, testState, source);
                }
            }

            if (testState.isAir() && !level.isOutsideBuildHeight(testPos)) {
                if (random.nextFloat() < 0.125F) {
                    List<Feature> features = level.getBiome(testPos).value().getGenerationSettings().getBoneMealFeatures();
                    if (features.isEmpty()) {
                        continue;
                    }

                    Util.getRandom(features, random).place(level, level.getChunkSource().getGenerator(), random, testPos);
                } else {
                    if (grassFeature.isEmpty()) {
                        continue;
                    }

                    grassFeature.get().value().place(level, level.getChunkSource().getGenerator(), random, testPos);
                }
            }
        }
    }

    @Override
    public BonemealableBlock.Type getType() {
        return BonemealableBlock.Type.NEIGHBOR_SPREADER;
    }
}
