package me.spoony.botanico.common.entities;

import com.google.common.math.DoubleMath;
import java.math.RoundingMode;
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

  public double posX;
  public double posY;

  public IPlane plane;

  public DoubleRectangle collider;
  protected int typeID;
  public int eid;
  public int animation;

  public int getTypeID() {
    return typeID;
  }

  public Entity(IPlane plane) {
    this.posX = 0;
    this.posY = 0;
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
    return new OmniPosition(PositionType.GAME, this.posX, this.posY);
  }

  public void setPosition(double x, double y) {
    this.posX = x;
    this.posY = y;
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

  public void onUpdate() {

  }

  public long getTileX() {
    return DoubleMath.roundToLong(posX, RoundingMode.FLOOR);
  }

  public long getTileY() {
    return DoubleMath.roundToLong(posY, RoundingMode.FLOOR);
  }
}
