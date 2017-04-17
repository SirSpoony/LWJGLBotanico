package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemBuilding;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/25/2016.
 */
public class CPacketBuildingInteraction extends AutoPacketAdapter implements IServerHandler {

  public static final byte CLICK = 0;
  public static final byte CREATE = 1;
  public static final byte DESTROY = 2;

  public long x;
  public long y;
  public byte type;

  @Override
  public void onReceive(BotanicoServer server, RemoteEntityPlayer player) {
    OmniPosition position = new OmniPosition(PositionType.GAME, x, y);
    if (type == CREATE) {
      ItemSlot cursorSlot = player.getCursor();
      if (!(cursorSlot.getStack().getItem() instanceof ItemBuilding)) {
        System.err.println("Received building interaction but there is no building in the cursor");
        return;
      }

      Building newBuilding = ((ItemBuilding) cursorSlot.getStack().getItem()).getBuilding();

      if (newBuilding.canCreate(player.getPlane(), position)) {
        player.getPlane().setBuilding(position, newBuilding);
        player.inventory.getStack(EntityPlayer.SLOT_CURSOR).increaseCount(-1);
      }
    } else if (type == DESTROY) {
      player.getPlane().breakBuildingAndDrop(position, player);
    } else if (type == CLICK) {
      if (player.getPlane().getBuilding(position) == null) {
        System.err.println(
            "Received building interaction with null building, server and client possibly out of sync.");
      } else {
        player.getPlane().getBuilding(position).onClick(player.getPlane(), player, position);
      }
    }
  }
}
