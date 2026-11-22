package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.Liroth;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ItemInteractionResult;
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

public class PortalChargeFrameBlock extends Block {
    public static final BooleanProperty CHARGED = BlockStateProperties.LIT;

    private final Item chargeItem;
    private final String ignitionSource;

    public PortalChargeFrameBlock(Item chargeItem, String ignitionSource) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
        this.chargeItem = chargeItem;
        this.ignitionSource = ignitionSource;
        registerDefaultState(stateDefinition.any().setValue(CHARGED, false));
    }

    public Item getChargeItem() {
        return chargeItem;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(CHARGED) || !stack.is(chargeItem)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        BlockState chargedState = state.setValue(CHARGED, true);
        level.setBlock(pos, chargedState, Block.UPDATE_ALL);

        boolean activated = false;
        PortalIgnitionSource source = PortalIgnitionSource.CustomSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, ignitionSource)
        );
        for (int x = -3; x <= 3 && !activated; x++) {
            for (int z = -3; z <= 3 && !activated; z++) {
                if (x == 0 && z == 0) {
                    continue;
                }
                activated = PortalPlacer.attemptPortalLight(level, pos.offset(x, 0, z), pos, source
                );
            }
        }

        if (!activated) {
            level.setBlock(pos, state, Block.UPDATE_ALL);
            return ItemInteractionResult.FAIL;
        }

        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.playSound(null, pos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }
}