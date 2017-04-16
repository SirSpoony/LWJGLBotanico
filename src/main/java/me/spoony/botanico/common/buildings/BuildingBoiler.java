package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityBoiler;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 1/1/2017.
 */
public class BuildingBoiler extends Building implements IBuildingEntityHost {

  public BuildingBoiler(int id) {
    super(id);

    this.name = "boiler";
    this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
    this.setTextureBounds(16, 0, 16, 32);
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
    return new ItemStack[]{new ItemStack(Items.BOILER)};
  }

  @Override
  public boolean onClick(IPlane level, EntityPlayer player, TilePosition position) {
      if (!(level instanceof ServerPlane)) {
        return false;
      }
    ServerPlane serverLevel = (ServerPlane) level;
    BuildingEntityBoiler entity = (BuildingEntityBoiler) serverLevel.getBuildingEntity(position);
    ItemStack heldStack = ((RemoteEntityPlayer) player).getHeldSlot().getStack();

    if (heldStack != null && heldStack.getItem() == Items.WATER_BUCKET
        && entity.dialog.waterProgress < 1) {
      entity.dialog.waterProgress += .25f;
      entity.dialog.waterProgress = BMath.clamp(entity.dialog.waterProgress, 0, 1);
      heldStack.setItem(Items.BUCKET);
      return true;
    }

    if (entity != null) {
      if (player instanceof RemoteEntityPlayer) {
        ((RemoteEntityPlayer) player).openDialog(entity.dialog);
        return true;
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
    return new BuildingEntityBoiler(position, plane);
  }
}
