package me.spoony.botanico.common.net.client;

import com.google.common.collect.Range;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Item;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.server.RemoteClient;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/24/2016.
 */
public class CPacketInventorySlotClick extends AutoPacketAdapter implements IServerHandler
{
    public int slotpos;
    public int dialogID;
    public byte clickType;

    @Override
    public void onReceive(BotanicoServer server, RemoteClient client) {
        RemoteEntityPlayer player = client.getPlayer();

        ItemSlot clickedslot;
        ItemSlot cursorslot;

        if (dialogID == Dialog.PLAYER_INV_ID)
        {
            if (clickType == 2 || clickType == 3) { //shiftclick
                clickedslot = player.inventory.getSlot(slotpos);
                if (clickedslot.getStack() == null) return;

                if (!Range.closed(7, 41).contains(slotpos)) { // if slot is hotbar
                    ItemStack leftover = player.inventory.addItem(clickedslot.getStack(), Range.closed(7, 41));
                    clickedslot.setStack(leftover);
                } else {
                    ItemStack leftover = player.inventory.addItem(clickedslot.getStack(), Range.closed(0,6));
                    clickedslot.setStack(leftover);
                }
            } else {
                clickedslot = player.inventory.getSlot(slotpos);
                cursorslot = player.inventory.getSlot(EntityPlayer.SLOT_CURSOR);

                ItemSlot.exchange(clickedslot, cursorslot, clickType);
            }
        } else if (player.currentDialog != null)
        {
            if (clickType == 2 || clickType == 3) {
                clickedslot = player.currentDialog.inventory.getSlot(slotpos);

                ItemStack leftover = player.inventory.addItem(clickedslot.getStack(), Range.closed(0, 41));
                clickedslot.setStack(leftover);
            } else {
                clickedslot = player.currentDialog.inventory.getSlot(slotpos);
                cursorslot = player.inventory.getSlot(EntityPlayer.SLOT_CURSOR);

                ItemSlot.exchange(clickedslot, cursorslot, clickType);
            }
        } else {
            System.err.println("[ServerPacketHandler] Received unknown Inventory Slot Click");
            return;
        }

        player.updateQueuedPlayerInventory(); //update modified player inv slots
        if (player.currentDialog != null) player.currentDialog.viewers.updateDialogAll(); //update entire dialog for all viewers
    }
}
