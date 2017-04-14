package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.server.BotanicoServer;

/**
 * Created by Colten on 12/30/2016.
 */
public class BuildingCave extends Building {
    public BuildingCave(int id) {
        super(id);
        this.name = "cave";
        this.textureName = "building/cave.png";
        this.hardness = Float.MAX_VALUE;
        this.alwaysBehindCharacter = true;
        this.collisionBounds = new DoubleRectangle(0,0,41/16f,35/16f);
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                new IntRectangle((d == 1 ? 64 : 0), 0, 64, 64), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
    }

    @Override
    public boolean onClick(IPlane plane, EntityPlayer player, TilePosition position) {
        if (plane.isLocal()) return true;
        if (player == null) return true;
        ServerPlane serverPlane = (ServerPlane) plane;
        ItemStack heldStack = ((RemoteEntityPlayer)player).getHeldSlot().getStack();

        if (heldStack != null && heldStack.getItem() == Items.ROPE) {
            if (plane.getBuildingData(position) == 1) return true;
            serverPlane.setBuildingData(position, (byte)1);
            serverPlane.getLevel().getUnderworld().setBuilding(position, Buildings.CAVE_ROPE);
            heldStack.increaseCount(-1);
            ((RemoteEntityPlayer)player).updatePlayerInventorySlot(EntityPlayer.SLOT_CURSOR);
            return true;
        }

        if (serverPlane.getBuildingData(position) == (byte) 1) {
            ((RemoteEntityPlayer)player).teleport(position.toGamePosition(), BotanicoServer.getCurrentInstance().level.getUnderworld());
            return true;
        } else {
            ((RemoteEntityPlayer) player).sendMessage("You need rope first!");
        }

        return true;
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.ROCK;
    }
}
