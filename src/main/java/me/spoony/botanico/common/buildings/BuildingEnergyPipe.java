package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityEnergyPipe;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TileDirection;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/28/2016.
 */
public class BuildingEnergyPipe extends Building implements IBuildingEntityHost {
    public BuildingEnergyPipe(int id) {
        super(id);

        this.shouldCollide = true;
        this.name = "energy_pipe";
        this.textureName = "building/energy_pipe.png";
        this.alwaysBehindCharacter = false;
        this.hardness = 1;
        this.collisionBounds.set(.2d, .2d, .6d, .6d);
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        renderConPipe(rg, level, position, d, highlight);
        renderPipeConnections(rg, level, position, d, highlight);
    }

    private void renderConPipe(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        boolean u, dwn, l, r;
        u = dwn = l = r = false;
        if (level.getBuilding(position.getNeighbor(TileDirection.NORTH)) == Buildings.ENERGY_PIPE) u = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.SOUTH)) == Buildings.ENERGY_PIPE) dwn = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.EAST)) == Buildings.ENERGY_PIPE) r = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.WEST)) == Buildings.ENERGY_PIPE) l = true;

        if (l && r && !u && !dwn) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(0, 48, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
            return;
        }
        if (!l && !r && u && dwn) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(16, 48, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
            return;
        }

        if (u) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(48, 32, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                new IntRectangle(0, 16, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        if (dwn) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(16, 32, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        if (l) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(0, 32, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        if (r) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(32, 32, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
    }

    private void renderPipeConnections(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        boolean u, dwn, l, r;
        u = dwn = l = r = false;
        if (level.getBuilding(position.getNeighbor(TileDirection.NORTH)) == Buildings.JAR) u = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.SOUTH)) == Buildings.JAR) dwn = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.WEST)) == Buildings.JAR) l = true;
        if (level.getBuilding(position.getNeighbor(TileDirection.EAST)) == Buildings.JAR) r = true;
        if (u) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(48, 0, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        if (dwn) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(16, 0, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        if (l) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(0, 0, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
        if (r) {
            rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                    new IntRectangle(32, 0, 16, 16), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        }
    }

    @Override
    public boolean onClick(IPlane level, EntityPlayer player, TilePosition position) {
        if (!(level instanceof ServerPlane)) return false;
        ServerPlane serverLevel = (ServerPlane) level;

        BuildingEntity be = serverLevel.getBuildingEntity(position);
        if (!(be instanceof BuildingEntityEnergyPipe)) return false;
        BuildingEntityEnergyPipe beep = (BuildingEntityEnergyPipe) be;

        ((RemoteEntityPlayer) player).sendMessage("Pipe Contents: " + beep.getEnergyStored() + "/" + beep.getEnergyCapacity());

        return false;
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition position) {
        return new ItemStack[]{new ItemStack(Items.ENERGY_PIPE)};
    }

    @Override
    public BuildingEntity createNewEntity(IPlane plane, TilePosition position) {
        return new BuildingEntityEnergyPipe(position, plane);
    }
}
