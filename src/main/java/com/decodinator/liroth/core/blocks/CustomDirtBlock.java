package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.blocks.basis.FabricToolModifiedBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.blocks.BaseBlock;

import java.util.function.Supplier;

public class CustomDirtBlock extends BaseBlock implements FabricToolModifiedBlock {
    private final Supplier<Block> farmland;
    private final Supplier<Block> path;

    public CustomDirtBlock(BlockBehaviour.Properties properties, Supplier<Block> farmland, Supplier<Block> path) {
        super(properties);
        this.farmland = farmland;
        this.path = path;
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, ItemStack tool) {
        if (tool.is(net.minecraft.tags.ItemTags.HOES)) {
            return farmland.get().defaultBlockState();
        }
        if (tool.is(net.minecraft.tags.ItemTags.SHOVELS)) {
            return path.get().defaultBlockState();
        }
        return null;
    }
}
