package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.Random;

/**
 * Created by Colten on 12/16/2016.
 */
public class BuildingEntityCrop extends BuildingEntity implements Updatable
{
    private static Random GROWTH_RAND = new Random();
    public float growthmod;
    public float growth;
    public byte maxstate;

    public BuildingEntityCrop(TilePosition position, IPlane plane, byte maxstate) {
        super(position, plane);

        this.maxstate = maxstate;
        this.growthmod = GROWTH_RAND.nextFloat()/4f+.75f;
    }

    public byte getGrowthState() {
        return (byte)Math.floor((float)maxstate*getGrowth());
    }

    public float getGrowth() {
        return BMath.clamp(growth, 0, 1);
    }

    public void setGrowth(float growth) {
        this.growth = growth;
    }

    public void incrementGrowth(float growth) {
        this.growth += growth;
    }

    public boolean isMature() {
        return growth >= 1;
    }

    @Override
    public void update(float delta)
    {
        byte laststate = getGrowthState();
        incrementGrowth(.001f*GROWTH_RAND.nextFloat()*growthmod);

        if (getGrowthState() != laststate) {
            // probably shouldn't be using this, but hey, i'm stupid edit: nah it's all good lmao
            ((ServerPlane)plane).setBuildingData(position, getGrowthState());
        }
    }
}
