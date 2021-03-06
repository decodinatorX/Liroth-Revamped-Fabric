package com.decodinator.liroth.core.blocks.entity;

import com.decodinator.liroth.Liroth;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LirothSplitterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final World world;
    private final PropertyDelegate propertyDelegate;
	private PlayerEntity entity;

    public LirothSplitterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(4));
    }

    public LirothSplitterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Liroth.LIROTH_SPLITTER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 5);
        this.inventory = inventory;
        this.world = playerInventory.player.world;
		this.entity = entity;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        
        // Our Slots
        this.addProperties(propertyDelegate);
        this.addSlot(new Slot(inventory, 0, 52, 16));
        this.addSlot(new Slot(inventory, 1, 52, 52));
        this.addSlot(new Slot(inventory, 2, 112, 8));
        this.addSlot(new Slot(inventory, 3, 112, 34));
        this.addSlot(new Slot(inventory, 4, 112, 60));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + (i + 1) * 9, 0 + 8 + l * 18, 0 + 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 0 + 8 + i * 18, 0 + 142));
        }
    }
    
	@Environment(EnvType.CLIENT)
	public int getCookProgress(int pixels) {
		int time = propertyDelegate.get(2);
		int timeTotal = propertyDelegate.get(3);
		return timeTotal != 0 && time != 0 ? time * pixels / timeTotal : 0;
	}
	
	@Environment(EnvType.CLIENT)
    public int getFuelProgress() {
        int i = this.propertyDelegate.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.propertyDelegate.get(0) * 13 / i;
    }
	
	@Environment(EnvType.CLIENT)
    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }
}