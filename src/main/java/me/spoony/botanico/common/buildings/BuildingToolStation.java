package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityToolStation;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colten on 11/12/2016.
 */
public class BuildingToolStation extends Building implements IBuildingEntityHost {
    public BuildingToolStation(int id) {
        super(id);
        this.name = "tool_station";
        this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
        this.setTextureBounds(0, 208, 16, 32);
        this.hardness = 1f;
    }

    @Override
    public ItemStack[] getDrops(IPlane level, OmniPosition position) {
        if (!(level instanceof ServerPlane)) return null;
        ServerPlane serverLevel = (ServerPlane) level;
        BuildingEntityToolStation bew = (BuildingEntityToolStation) serverLevel.getBuildingEntity(position);
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < bew.getInventory().getLength(); i++) {
            if (bew.getInventory().getSlot(i).getStack() == null) continue;
            items.add(bew.getInventory().getSlot(i).getStack());
        }
        items.add(new ItemStack(Items.TOOL_STATION));

        return items.toArray(new ItemStack[items.size()]);
    }

    @Override
    public void onClick(IPlane level, EntityPlayer player, OmniPosition position) {
        if (!(level instanceof ServerPlane))
          return;
        ServerPlane serverLevel = (ServerPlane) level;

        BuildingEntityToolStation bew = (BuildingEntityToolStation) serverLevel.getBuildingEntity(position);
        if (bew != null) {
            if (player instanceof RemoteEntityPlayer) {
                ((RemoteEntityPlayer) player).openDialog(bew.dialog);
            }
        }
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.WOOD;
    }

    @Override
    public BuildingEntity createNewEntity(IPlane plane, OmniPosition position) {
        return new BuildingEntityToolStation(position, plane);
    }
}
