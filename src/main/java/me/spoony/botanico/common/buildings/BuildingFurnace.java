package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityFurnace;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/30/2016.
 */
public class BuildingFurnace extends Building implements IBuildingEntityHost {
    public BuildingFurnace(int id) {
        super(id);
        this.name = "furnace";
        this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
        this.setTextureBounds(32, 128, 16, 32);
        this.hardness = 4f;
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition position) {
/*        if (!(level instanceof ServerLevel)) return null;
        ServerLevel serverLevel = (ServerLevel) level;
        BuildingEntityKnappingStation bew = (BuildingEntityKnappingStation) serverLevel.getBuildingEntity(x, y);
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < bew.inventory.getLength(); i++)
        {
            if (bew.inventory.getSlot(i).getStack() == null) continue;
            items.add(bew.inventory.getSlot(i).getStack());
        }
        items.add(new ItemStack(Items.KNAPPING_STATION));

        return items.toArray(new ItemStack[items.size()]);*/
        return new ItemStack[] {new ItemStack(Items.FURNACE)};
    }

    @Override
    public boolean onClick(IPlane level, EntityPlayer player, TilePosition position) {
        if (!(level instanceof ServerPlane)) return false;
        ServerPlane serverLevel = (ServerPlane) level;

        BuildingEntityFurnace bef = (BuildingEntityFurnace) serverLevel.getBuildingEntity(position);
        if (bef != null) {
            if (player instanceof RemoteEntityPlayer) {
                ((RemoteEntityPlayer) player).openDialog(bef.dialog);
            }
        }
        return false;
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.ROCK;
    }

    @Override
    public BuildingEntity createNewEntity(IPlane plane, TilePosition position) {
        return new BuildingEntityFurnace(position, plane);
    }
}
