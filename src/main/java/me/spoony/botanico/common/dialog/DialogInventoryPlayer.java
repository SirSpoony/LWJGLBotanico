package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogInventoryPlayer extends Dialog {

  public DialogInventoryPlayer() {
    super(Dialog.PLAYER_INV_CRAFTING_ID);
    this.inventory = new Inventory(16, Dialog.PLAYER_INV_CRAFTING_ID);
    for (int i = 4; i < 16; i++) {
      this.inventory.getSlot(i).setMode(ItemSlotMode.TAKE_ONLY);
    }
  }

  public void checkCrafting() {
    for (int i = 0; i < 4; i++) {
      if (inventory.getSlot(i).isEmpty()) {
        continue;
      }

      ItemStack stack = inventory.getSlot(i).getStack();
      if (stack.getItem() == Items.WHEAT_SEEDS) {
        inventory.setStack(4, new ItemStack(Items.DIRT));
      }
    }
  }

  @Override
  public void onClose(EntityPlayer player) {
    super.onClose(player);

    if (player instanceof RemoteEntityPlayer) {
      RemoteEntityPlayer remoteEntityPlayer = (RemoteEntityPlayer) player;
      for (int i = 0; i < 4; i++) {
        remoteEntityPlayer.giveItemStack(inventory.getStack(i));
        inventory.setStack(i, null);
      }
    }
  }

  @Override
  public void onItemSlotInteraction() {
    checkCrafting();
  }
}
