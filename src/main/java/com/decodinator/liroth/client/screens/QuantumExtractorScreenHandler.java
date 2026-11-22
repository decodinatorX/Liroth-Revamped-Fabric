package com.decodinator.liroth.client.screens;

import com.decodinator.liroth.core.registry.LirothScreenHandlers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class QuantumExtractorScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final ContainerData propertyDelegate;

    public QuantumExtractorScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(4), new SimpleContainerData(4));
    }

    public QuantumExtractorScreenHandler(int syncId, Inventory playerInventory, Container simpleContainer, ContainerData data) {
        super(LirothScreenHandlers.QUANTUM_EXTRACTOR_SCREEN_HANDLER, syncId);
        checkContainerSize(simpleContainer, 4);
        this.inventory = simpleContainer;
        inventory.startOpen(playerInventory.player);
        this.propertyDelegate = data;
        
        // Our Slots
        this.addSlot(new Slot(inventory, 0, 56, 13));
        this.addSlot(new Slot(inventory, 1, 56, 49));
        this.addSlot(new Slot(inventory, 2, 113, 18));
        this.addSlot(new Slot(inventory, 3, 113, 45));

        for(int i = 0; i < 3; ++i) {
           for(int j = 0; j < 9; ++j) {
              this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
           }
        }

        for(int k = 0; k < 9; ++k) {
           this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(data);
     }

    @Override
    public boolean stillValid(Player p_38974_) {
        return this.inventory.stillValid(p_38974_);
     }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.getMaxStackSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getMaxStackSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getMaxStackSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
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