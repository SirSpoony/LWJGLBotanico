package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.dialog.DialogInventoryPlayer;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/25/2016.
 */
public class CPacketChangeDialog extends AutoPacketAdapter implements IServerHandler {

  public static final byte OPEN_PLAYER_INVENTORY = 0;
  public static final byte CLOSE_DIALOG = 1;

  public byte operation;

  public CPacketChangeDialog setOperation(byte operation) {
    this.operation = operation;
    return this;
  }

  @Override
  public void onReceive(BotanicoServer server, RemoteEntityPlayer player) {
    if (operation == OPEN_PLAYER_INVENTORY) {
      DialogInventoryPlayer invdialog = new DialogInventoryPlayer();
      player.openDialog(invdialog);
    } else if (operation == CLOSE_DIALOG) {
      player.currentDialog.onClose(player);
      player.currentDialog = null;
    }
  }
}
