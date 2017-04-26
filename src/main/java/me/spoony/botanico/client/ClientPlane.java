package me.spoony.botanico.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import java.math.RoundingMode;
import me.spoony.botanico.client.graphics.renderers.RendererPlane;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.BuildingBreakMaterial;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;

import java.util.*;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientPlane implements IPlane {

  public BotanicoClient client;
  protected ClientEntityPlayer localPlayer;

  private BiMap<Integer, Entity> entities;
  private Set<Chunk> chunks;

  private RendererPlane renderer;

  public int LOCAL_PLAYER_EID = 0;
  public int planeID;

  public ClientPlane(BotanicoClient client) {
    super();

    entities = HashBiMap.create();
    chunks = Sets.newConcurrentHashSet();

    this.client = client;

    this.localPlayer = new ClientEntityPlayer(client, this);

    this.renderer = new RendererPlane(this);
  }

  public ClientEntityPlayer getLocalPlayer() {
    return localPlayer;
  }

  public void render(RendererGame rg) {
    this.renderer.render(rg);
  }

  public void update(float delta) {
    synchronized (entities) {
      Set<Integer> eids = Sets.newHashSet(entities.keySet());
      for (Integer eid : eids) {
        Entity e = getEntity(eid);
        e.update(delta, this);
      }
    }
  }

  @Override
  public Tile getTile(OmniPosition position) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    if (chunk == null) {
      return null;
    }

    return chunk.getTile(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public Tile getTile(long x, long y) {
    Chunk chunk = getChunk(DoubleMath.roundToLong(x / 32d, RoundingMode.FLOOR), DoubleMath.roundToLong(y / 32d, RoundingMode.FLOOR));
    if (chunk == null) {
      return null;
    }

    return chunk.getTile(LongMath.mod(x, 32), LongMath.mod(y, 32));
  }

  @Override
  public Building getBuilding(OmniPosition position) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    if (chunk == null) {
      return null;
    }

    return chunk.getBuilding(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public Building getBuilding(long x, long y) {
    Chunk chunk = getChunk(DoubleMath.roundToLong(x / 32d, RoundingMode.FLOOR), DoubleMath.roundToLong(y / 32d, RoundingMode.FLOOR));
    if (chunk == null) {
      return null;
    }

    return chunk.getBuilding(LongMath.mod(x, 32), LongMath.mod(y, 32));
  }

  @Override
  public int getBuildingData(OmniPosition position) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    if (chunk == null) {
      return -1;
    }

    return chunk.getBuildingData(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public int getBuildingData(long x, long y) {
    Chunk chunk = getChunk(DoubleMath.roundToLong(x / 32d, RoundingMode.FLOOR), DoubleMath.roundToLong(y / 32d, RoundingMode.FLOOR));
    if (chunk == null) {
      return -1;
    }

    return chunk.getBuildingData(LongMath.mod(x, 32), LongMath.mod(y, 32));
  }

  @Override
  public Chunk getChunk(long x, long y) {
    synchronized (chunks) {
      for (Chunk c : chunks) {
        if (c.x == x && c.y == y) {
          return c;
        }
      }
    }
    return null;
  }

  @Override
  public int getID() {
    return planeID;
  }

  public void receiveBuildingUpdate(OmniPosition position, Building b) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    Building prevb = chunk.getBuilding(position.getXInChunk(), position.getYInChunk());
    chunk.setBuilding(position.getXInChunk(), position.getYInChunk(), b);
    if (b == null && prevb != null) {
      GameView.getRendererGame().particleBuildingBreak(position, prevb.getBreakParticle());
    }
  }

  public void receiveTileUpdate(OmniPosition position, Tile tile) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    chunk.setTile(position.getXInChunk(), position.getYInChunk(), tile);
    if (tile == Tiles.FERTILIZED_GROUND) {
      GameView.getRendererGame().particleBuildingBreak(position, BuildingBreakMaterial.DIRT);
    }
  }

  @Override
  public Collection<Entity> getEntities() {
    synchronized (entities) {
      return entities.values();
    }
  }

  @Override
  public Entity getEntity(int eid) {
    return entities.getOrDefault(eid, null);
  }

  @Override
  public boolean isLocal() {
    return true;
  }

  public void removeEntity(int eid) {
    synchronized (entities) {
      entities.remove(eid);
    }
  }

  public void removeEntity(Entity e) {
    removeEntity(e.eid);
  }

  public void addEntity(Entity e) {
    Preconditions.checkNotNull(e, "Cannot add null entity!");
    entities.put(e.eid, e);
  }

  public void setLocalEID() {
    // when the local player is added to the entity list, the remotentity that was created is removed automatically by entities.put
    localPlayer.eid = LOCAL_PLAYER_EID;
    addEntity(localPlayer);
  }

  public void receiveBuildingDataUpdate(OmniPosition position, int data) {
    Chunk chunk = getChunk(position.getChunkX(), position.getChunkY());
    chunk.setBuildingData(position.getXInChunk(), position.getYInChunk(), data);
  }

  public void receiveChunk(Chunk chunk) {
    Chunk testChunk = getChunk(chunk.x, chunk.y);
    if (testChunk != null) {
      System.out.println("Overwriting chunk");
      chunks.remove(testChunk);
    }
    chunks.add(chunk);
  }
}
