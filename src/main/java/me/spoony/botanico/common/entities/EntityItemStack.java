package me.spoony.botanico.common.entities;

import me.spoony.botanico.ClientOnly;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.List;
import java.util.Random;

/**
 * Created by Colten on 12/3/2016.
 */
public class EntityItemStack extends Entity {

  public static final int ID = 2;

  public ItemStack stack;

  private float renderpos;
  private float renderscale;

  public Entity collector;
  public float collectionProgress = 0;
  public OmniPosition positionBeforeCollection;

  public EntityItemStack(OmniPosition position, IPlane plane, ItemStack stack,
      boolean randomPosInBlock) {
    super(plane);

    this.typeID = EntityItemStack.ID;
    this.renderscale = .4f;
    this.posX = position.getGameX();
    this.posY = position.getGameY();
    this.collider = new DoubleRectangle(.1f, .1f, renderscale, renderscale);

    Random posrand = new Random();
    if (randomPosInBlock) {
      position.setGameX(position.getGameX() + (1 - renderscale) * posrand.nextFloat());
      position.setGameY(position.getGameY() + (1 - renderscale) * posrand.nextFloat());
    }
    renderpos = (float) (posrand.nextFloat() * Math.PI) * 2;

    this.stack = stack;

    this.collector = null;
    this.collectionProgress = 0f;
    this.positionBeforeCollection = null;
  }

  @Override
  public void update(float delta, IPlane level) {
    super.update(delta, level);

    if (!isBeingCollected()) {
      if (!level.isLocal()) {
        EntityCollider collider = new EntityCollider(level, this,
            new DoubleRectangle(-1d, -1d, 3d, 3d));
        List<Entity> collisions = collider.checkCollisionsEntities();
        for (Entity collision : collisions) {
          if (collision instanceof RemoteEntityPlayer) {
            ItemStack retstack = ((RemoteEntityPlayer) collision).giveItemStack(stack, false);
            if (retstack == null) {
              collector = collision;
              updateState();
              ((ServerPlane) level).removeEntity(this);
            } else {
              stack = retstack;
            }
            return;
          }
        }
      } else {
        renderpos += delta;
      }
    } else {
      if (level.isLocal()) {
        float prog = delta * 10;
        if (prog > 1) {
          prog = 1;
        }
        double targetX = collector.posX + collector.collider.getCenter().x;
        double targetY = collector.posY + collector.collider.getCenter().y + .5d;
        posX = (BMath.lerp(posX, targetX, prog));
        posY = (BMath.lerp(posY, targetY, prog));

        if (Math.pow(targetX-posX, 2) + Math.pow(targetY-posY, 2) < .16) {
          ((ClientPlane) level).removeEntity(this);
        }
      }
    }
  }

  @ClientOnly
  @Override
  public void render(RendererGame rg) {
    float offset = (float)(Math.sin(renderpos) + 1) / 16f;
    rg.sprite(posX, posY+offset, renderscale, rg.getResourceManager().getTexture("items.png"),
        stack.getItem().textureBounds, Color.WHITE, posY+offset);
  }

  @Override
  public int getState() {
    return collector == null ? 0 : collector.eid;
  }

  @Override
  public void receiveState(int state) {
    if (state == 0) {
      return; // the state is 0 if there is no collector, otherwise the state IS the collector!
    }
    collectionProgress = 0;
    collector = GameView.getClientLevel().getEntity(state);
    positionBeforeCollection = new OmniPosition(PositionType.GAME, posX, posY);
  }

  public boolean isBeingCollected() {
    return collector != null;
  }

  @Override
  public void remove(IPlane level) {
    if (!level.isLocal() || !isBeingCollected()) {
      ((ServerPlane) level).removeEntity(this);
    }
  }
}
