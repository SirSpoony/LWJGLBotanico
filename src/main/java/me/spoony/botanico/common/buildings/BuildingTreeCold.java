package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.client.ClientPlane;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingTreeCold extends Building {
    public BuildingTreeCold(int id) {
        super(id);
        this.name = "tree_cold";
        this.hardness = 10;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, OmniPosition position, byte d, Color color) {
        position.setX(PositionType.GAME, position.getX(PositionType.GAME) - 1);
        rg.sprite(position, getTextureSheet(),
                new IntRectangle(0, 0, 16, 16), color, position.getGameY());
    }

    @Override
    public void destroy(IPlane level, OmniPosition position) {
        super.destroy(level, position);
        if (!(level instanceof ServerPlane)) return;
        ServerPlane serverLevel = (ServerPlane) level;
        serverLevel.setBuilding(position, Buildings.STICKS_PILE);
    }
}
