package me.spoony.botanico.common.net;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/27/2016.
 */
public class SPacketTileChange extends AutoPacketAdapter implements IClientHandler {
    public long x;
    public long y;

    @NotTransferable
    public transient Tile tile;

    private int tileid;

    @Override
    public void preEncode() {
        if (tile == null) {
            tileid = -1;
        } else {
            tileid = tile.getID();
        }
    }

    @Override
    public void postDecode() {
        if (tileid == -1) {
            this.tile = null;
        } else {
            this.tile = Tile.REGISTRY.get(tileid);
        }
    }


    @Override
    public void onReceive(BotanicoClient client) {
        client.getLocalLevel().receiveTileUpdate(new TilePosition(x, y), tile);
    }
}
