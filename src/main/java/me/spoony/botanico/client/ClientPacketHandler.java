package me.spoony.botanico.client;

import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.net.client.CPacketInventoryButtonClick;
import me.spoony.botanico.common.net.client.CPacketInventorySlotClick;
import me.spoony.botanico.common.net.client.CPacketUseItem;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientPacketHandler {

  BotanicoClient client;

  public ClientPacketHandler(BotanicoClient client) {
    this.client = client;
  }

  public void sendDialogButtonPress(int buttonid) {
    CPacketInventoryButtonClick pibc = new CPacketInventoryButtonClick();
    pibc.buttonID = buttonid;
    pibc.dialogID = client.gameView.getDialog().id;
    client.sendPacket(pibc);
  }

  public void sendItemSlotExchange(ItemSlot slot, byte type) {
    CPacketInventorySlotClick pic = new CPacketInventorySlotClick();
    pic.inventoryID = slot.getInventory().id;
    pic.slotpos = slot.getSlotIndex();
    pic.clickType = type;
    client.sendPacket(pic);
  }

  public void sendUseItem(TilePosition position) {
    CPacketUseItem put = new CPacketUseItem();
    put.x = position.x;
    put.y = position.y;
    client.sendPacket(put);
  }
}
