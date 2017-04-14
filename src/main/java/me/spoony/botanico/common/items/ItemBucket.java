package me.spoony.botanico.common.items;

import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/29/2016.
 */
public class ItemBucket extends Item {
    public ItemBucket(int id, IntRectangle textureBounds, String name, int maxStackSize) {
        super(id, textureBounds, name, maxStackSize);
    }

    @Override
    public void onUse(IPlane level, EntityPlayer player, ItemSlot cursor, TilePosition position) {
        if (!(level instanceof ServerPlane)) return;
        ServerPlane sl = (ServerPlane) level;
        RemoteEntityPlayer sp = (RemoteEntityPlayer) player;

        Tile clickedTile = sl.getTile(position);
        if (clickedTile == Tiles.WATER || clickedTile == Tiles.DEEP_WATER) {
            cursor.getStack().setItem(Items.WATER_BUCKET);
            sp.updatePlayerInventorySlot(EntityPlayer.SLOT_CURSOR);
        }
    }
}
