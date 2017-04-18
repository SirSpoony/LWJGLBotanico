package me.spoony.botanico.common.entities;

import com.google.common.collect.Lists;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.DoubleRectangle;

import java.util.List;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/8/2016.
 */
public class EntityCollider {

  private IPlane level;
  public DoubleRectangle entityBounds;

  public EntityCollider(IPlane level, Entity entity) {
    this(level, entity, entity.collider);
  }

  public EntityCollider(IPlane level, Entity entity, DoubleRectangle aabounds) {
    this.level = level;
    entityBounds = new DoubleRectangle(
        entity.position.getGameX() + aabounds.x, entity.position.getGameY() + aabounds.y,
        aabounds.width, aabounds.height);
  }

  public CollisionCheck checkCollisionsChunk() {
    CollisionCheck cc = new CollisionCheck(false);
    for (int offx = -3; offx < 3; offx++) {
      for (int offy = -3; offy < 3; offy++) {
        OmniPosition offsetPosition = new OmniPosition(PositionType.GAME,
            entityBounds.x + offx,
            entityBounds.y + offy);

        Building b = level.getBuilding(offsetPosition);
        if (b == null) {
          continue;
        }
        if (!b.shouldCollide()) {
          continue;
        }

        DoubleRectangle buildingBounds = new DoubleRectangle(
            offsetPosition.getTileX() + b.getCollisionBounds().x,
            offsetPosition.getTileY() + b.getCollisionBounds().y,
            b.getCollisionBounds().width, b.getCollisionBounds().height);
        if (entityBounds.overlaps(buildingBounds)) {
          cc.intersection.set(buildingBounds.intersect(entityBounds));
          cc.collided = true;
          return cc;
        }
      }
    }

    for (int offx = -3; offx < 3; offx++) {
      for (int offy = -3; offy < 3; offy++) {
        OmniPosition offsetPosition = new OmniPosition(
            PositionType.GAME, entityBounds.x + offx, entityBounds.y + offy);

        Tile t = level.getTile(offsetPosition);
        if (t == null) {
          continue;
        }
        if (!t.shouldCollide()) {
          continue;
        }

        DoubleRectangle tileBounds = new DoubleRectangle(offsetPosition.getTileX(), offsetPosition.getTileY(),
            1, 1);
        if (entityBounds.overlaps(tileBounds)) {
          cc.intersection.set(tileBounds.intersect(entityBounds));
          cc.collided = true;
          return cc;
        }
      }
    }

    cc.collided = false;
    return cc;
  }

  public List<Entity> checkCollisionsEntities() {
    List<Entity> ret = Lists.newArrayList();
    for (Entity entity : level.getEntities()) {
      DoubleRectangle collisionBounds = new DoubleRectangle(entity.position.getGameX() + entity.collider.x,
          entity.position.getGameY() + entity.collider.y,
          entity.collider.width, entity.collider.height);
      if (collisionBounds.overlaps(this.entityBounds)) {
        ret.add(entity);
      }
    }
    return ret;
  }

  public boolean wouldCollide(OmniPosition position, Building b) {
    if (!b.shouldCollide()) {
      return false;
    }
    DoubleRectangle buildingBounds = new DoubleRectangle(
        position.getGameX() + b.getCollisionBounds().x, position.getGameY() + b.getCollisionBounds().y,
        b.getCollisionBounds().width, b.getCollisionBounds().height);
    return (entityBounds.overlaps(buildingBounds));
  }
}