package me.spoony.botanico.common.entities;

import me.spoony.botanico.ClientOnly;
import me.spoony.botanico.ServerOnly;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.nbt.NBTCompoundTag;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.server.BotanicoServer;

import java.util.Random;

/**
 * Created by Colten on 11/8/2016.
 */
public class Entity {

  public GamePosition position;
  public IPlane plane;

  public DoubleRectangle collider;
  protected int typeID;
  public int eid;
  public int animation;

  public int getTypeID() {
    return typeID;
  }

  public Entity(IPlane plane) {
    this.position = new GamePosition(0, 0);
    this.plane = plane;
    this.collider = new DoubleRectangle(0, 0, 1, 1);
    this.eid = new Random().nextInt();
  }

  public void update(float delta, IPlane level) {

  }

  @ClientOnly
  public void render(RendererGame rg) {

  }

  public void setPosition(GamePosition position) {
    this.position.set(position);
  }

  public void setPosition(double x, double y) {
    position.set(x, y);
  }

  public GamePosition getPosition() {
    return position;
  }

  public NBTCompoundTag writeData(NBTCompoundTag tag) {
    tag.setCompound(position.writeData(new NBTCompoundTag("pos")));
    tag.setInt("eid", eid);
    tag.setInt("typeID", typeID);
    return tag;
  }

  public void readData(NBTCompoundTag tag) {
    position.readData((NBTCompoundTag) tag.get("pos").getValue());
    eid = (Integer) tag.get("eid").getValue();
    typeID = (Integer) tag.get("typeID").getValue();
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
