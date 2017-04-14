package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityJar;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/18/2016.
 */
public class BuildingJar extends Building implements IBuildingEntityHost {
    public BuildingJar(int id) {
        super(id);
        this.name = "jar";
        this.shouldCollide = true;
        this.alwaysBehindCharacter = false;
        this.textureName = "building/jar.png";
        this.hardness = 1f;
    }

    @Override
    public boolean onClick(IPlane level, EntityPlayer player, TilePosition position) {
        if (!(level instanceof ServerPlane)) return false;
        ServerPlane serverLevel = (ServerPlane) level;

        BuildingEntity be = serverLevel.getBuildingEntity(position);
        if (!(be instanceof BuildingEntityJar)) return false;
        BuildingEntityJar bej = (BuildingEntityJar) be;

        ((RemoteEntityPlayer) player).sendMessage("Jar Contents: " + bej.getEnergyStored() + "/" + bej.getEnergyCapacity());

        return false;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte extra, boolean highlight) {
        Texture texture = rg.getResourceManager().getTexture(textureName);
        rg.sprite(position.toGamePosition(), texture,
                new IntRectangle(0, 0, 16, 32),
                highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
        rg.sprite(position.toGamePosition(), texture,
                new IntRectangle(16, 0, 16, 32),
                highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
    }

    @Override
    public BuildingEntity createNewEntity(IPlane plane, TilePosition position) {
        return new BuildingEntityJar(position, plane);
    }
}
