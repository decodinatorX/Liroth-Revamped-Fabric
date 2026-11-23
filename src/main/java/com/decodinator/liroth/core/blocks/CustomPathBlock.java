package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.blocks.basis.FabricToolModifiedBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CustomPathBlock extends DirtPathBlock implements FabricToolModifiedBlock {
    private final Supplier<Block> dirtBlock;
    private final Supplier<Block> farmland;

    public CustomPathBlock(BlockBehaviour.Properties settings, Supplier<Block> dirtBlock, Supplier<Block> farmland) {
        super(settings);
        this.dirtBlock = dirtBlock;
        this.farmland = farmland;
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext p_153131_) {
        return !this.defaultBlockState().canSurvive(p_153131_.getLevel(), p_153131_.getClickedPos())
                ? Block.pushEntitiesUp(this.defaultBlockState(), dirtBlock.get().defaultBlockState(), p_153131_.getLevel(), p_153131_.getClickedPos())
                : super.getStateForPlacement(p_153131_);
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, ItemStack tool) {
        if (tool.is(net.minecraft.tags.ItemTags.HOES)) {
            return farmland.get().defaultBlockState();
        }
        return null;
    }
}
