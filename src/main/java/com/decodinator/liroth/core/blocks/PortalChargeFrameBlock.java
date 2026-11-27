package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothPortalBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import java.util.function.Supplier;

public class PortalChargeFrameBlock extends Block {
    public static final BooleanProperty CHARGED = BlockStateProperties.LIT;

    private final Item chargeItem;
    private final Supplier<Block> regularFrame;
    private final Supplier<LirothPortalBlock> portal;

    public PortalChargeFrameBlock(Item chargeItem, Supplier<Block> regularFrame, Supplier<LirothPortalBlock> portal) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
        this.chargeItem = chargeItem;
        this.regularFrame = regularFrame;
        this.portal = portal;
        registerDefaultState(stateDefinition.any().setValue(CHARGED, false));
    }

    public Item getChargeItem() {
        return chargeItem;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(CHARGED) || !stack.is(chargeItem)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockState chargedState = state.setValue(CHARGED, true);
        level.setBlock(pos, chargedState, Block.UPDATE_ALL);
        if (!LirothPortalBuilders.tryCreatePortal(level, pos, chargedState, regularFrame.get(), portal.get())) {
            level.setBlock(pos, state, Block.UPDATE_ALL);
            player.sendSystemMessage(Component.translatable("message.liroth.portal.invalid_frame"));
            return InteractionResult.FAIL;
        }

        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.playSound(null, pos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
        LirothPortalBuilders.removePortalForFrame(level, pos, portal.get());
    }
}