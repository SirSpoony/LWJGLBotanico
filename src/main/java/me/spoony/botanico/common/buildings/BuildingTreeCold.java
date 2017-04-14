package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.client.ClientPlane;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingTreeCold extends Building {
    public BuildingTreeCold(int id) {
        super(id);
        this.name = "tree_cold";
        this.textureName = "building/tree_cold.png";
        this.hardness = 10;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        rg.sprite(position.toGamePosition().add(-1, 0), rg.getResourceManager().getTexture(textureName),
                new IntRectangle(0, 0, 46, 64), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
    }

    @Override
    public void destroy(IPlane level, TilePosition position) {
        super.destroy(level, position);
        if (!(level instanceof ServerPlane)) return;
        ServerPlane serverLevel = (ServerPlane) level;
        serverLevel.setBuilding(position, Buildings.STICKS_PILE);
    }
}
