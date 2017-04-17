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
    this.position = position;
    this.collider = new DoubleRectangle(.1f, .1f, renderscale, renderscale);

    Random posrand = new Random();
    if (randomPosInBlock) {
      position.x += (1 - renderscale) * posrand.nextFloat();
      position.y += (1 - renderscale) * posrand.nextFloat();
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
        OmniPosition target = new OmniPosition(PositionType.GAME,
            collector.position.x + collector.collider.getCenter().x,
            collector.position.y + collector.collider.getCenter().y + .5d);
        position.x = BMath.lerp(position.x, target.x, prog);
        position.y = BMath.lerp(position.y, target.y, prog);

        if (target.distance(PositionType.GAME, position) < .4) {
          ((ClientPlane) level).removeEntity(this);
        }
      }
    }
  }

  @ClientOnly
  @Override
  public void render(RendererGame rg) {
    float offset = (float) Math.sin(renderpos);
    OmniPosition renderPosition = new OmniPosition(PositionType.GAME,
        position.getX(PositionType.GAME), position.getY(PositionType.GAME) + (offset + 1) / 16d);
    rg.sprite(renderPosition, renderscale, rg.getResourceManager().getTexture("items.png"),
        stack.getItem().textureBounds, Color.WHITE, renderPosition.y);
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
    positionBeforeCollection = new OmniPosition(PositionType.GAME, position.getX(PositionType.GAME), position.getY(PositionType.GAME));
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
