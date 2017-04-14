package me.spoony.botanico.server.net;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.eventbus.Subscribe;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.dialog.DialogInventoryPlayer;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.net.*;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/20/2016.
 */
public class ServerPacketHandler {
    BotanicoServer server;

    public ServerPacketHandler(BotanicoServer server) {
        this.server = server;
    }

    public void sendBuildingChange(TilePosition position, ServerPlane plane, Building building) {
        SPacketBuildingChange pbc = new SPacketBuildingChange();
        pbc.x = position.x;
        pbc.y = position.y;
        pbc.building = building;
        server.sendPacketToAll(pbc, client -> client.getPlayer().getPlane() == plane);
    }

    public void sendTileChange(TilePosition position, ServerPlane plane, Tile tile) {
        SPacketTileChange pbc = new SPacketTileChange();
        pbc.x = position.x;
        pbc.y = position.y;
        pbc.tile = tile;
        server.sendPacketToAll(pbc, client -> client.getPlayer().getPlane() == plane);
    }

    public void sendBuildingDataChange(TilePosition position, ServerPlane plane, byte data) {
        SPacketBuildingData pbd = new SPacketBuildingData();
        pbd.x = position.x;
        pbd.y = position.y;
        pbd.data = data;
        server.sendPacketToAll(pbd, client -> client.getPlayer().getPlane() == plane);
    }

    public void sendChunk(Chunk chunk, RemoteEntityPlayer player) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(chunk);

        SPacketChunk packet = new SPacketChunk();
        packet.buildings = chunk.buildings;
        packet.tiles = chunk.tiles;
        packet.buildingdata = chunk.buildingData;
        packet.x = chunk.position.x;
        packet.y = chunk.position.y;

        player.remoteClient.sendPacket(packet);
    }

    public void sendEntityState(Entity entity) {
        SPacketEntityState pes = new SPacketEntityState();
        pes.eid = entity.eid;
        pes.state = entity.getState();
        server.sendPacketToAll(pes, client -> client.getPlayer().getPlane() == entity.getPlane());
    }

    public void sendTeleport(RemoteEntityPlayer player) {
        SPacketTeleport teleport = new SPacketTeleport();
        teleport.x = player.getPosition().x;
        teleport.y = player.getPosition().y;
        teleport.plane = player.getPlane().getID();
        player.remoteClient.sendPacket(teleport);
    }
}
