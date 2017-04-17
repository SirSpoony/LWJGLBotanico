package me.spoony.botanico.common.entities;

import me.spoony.botanico.ClientOnly;
import me.spoony.botanico.ServerOnly;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.nbt.NBTCompoundTag;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.Random;

/**
 * Created by Colten on 11/8/2016.
 */
public class Entity {

  public OmniPosition position;
  public IPlane plane;

  public DoubleRectangle collider;
  protected int typeID;
  public int eid;
  public int animation;

  public int getTypeID() {
    return typeID;
  }

  public Entity(IPlane plane) {
    this.position = new OmniPosition(PositionType.GAME, 0, 0);
    this.plane = plane;
    this.collider = new DoubleRectangle(0, 0, 1, 1);
    this.eid = new Random().nextInt();
  }

  public void update(float delta, IPlane level) {

  }

  @ClientOnly
  public void render(RendererGame rg) {

  }

  public OmniPosition getPosition() {
    return position;
  }

  @ClientOnly
  public void receiveState(int state) {

  }

  public int getState() {
    return 0;
  }

  @ServerOnly
  public void updateState() {
    ((ServerPlane) (plane)).server.getClientManager().getPacketHandler().sendEntityState(this);
  }

  public void remove(IPlane level) {
    if (level.isLocal()) {
      ((ClientPlane) level).removeEntity(this);
    } else {
      ((ServerPlane) level).removeEntity(this);
    }
  }

  public IPlane getPlane() {
    return plane;
  }
}
