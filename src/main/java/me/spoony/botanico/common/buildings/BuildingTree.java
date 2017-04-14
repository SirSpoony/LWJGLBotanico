package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.client.ClientPlane;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingTree extends Building {
    public BuildingTree(int id) {
        super(id);
        this.name = "tree";
        this.textureName = "building/tree.png";
        this.hardness = 10;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        rg.sprite(new GamePosition(position.x - 1, position.y), rg.getResourceManager().getTexture(textureName),
                new IntRectangle(0, 0, 46, 64), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
    }

    @Override
    public void destroy(IPlane level, TilePosition position) {
        if (!(level instanceof ServerPlane)) return;
        ServerPlane serverLevel = (ServerPlane) level;
        serverLevel.setBuilding(position, Buildings.STICKS_PILE);
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.WOOD;
    }
}
