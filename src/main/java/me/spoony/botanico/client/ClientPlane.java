package me.spoony.botanico.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.spoony.botanico.common.util.position.ChunkPosition;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.client.graphics.renderers.PlaneRenderer;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.BuildingBreakMaterial;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.*;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientPlane implements IPlane {

  public BotanicoClient client;
  protected ClientEntityPlayer localPlayer;

  private BiMap<Integer, Entity> entities;
  private Map<ChunkPosition, Chunk> chunks;

  private PlaneRenderer renderer;

  public int LOCAL_PLAYER_EID = 0;
  public int planeID;

  public ClientPlane(BotanicoClient client) {
    super();

    entities = HashBiMap.create();
    chunks = Maps.newConcurrentMap();

    this.client = client;

    this.localPlayer = new ClientEntityPlayer(client, this);

    this.renderer = new PlaneRenderer(this);
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
  public Tile getTile(TilePosition position) {
    Chunk chunk = getChunk(position.toChunkPosition());
    if (chunk == null) {
      return null;
    }

    return chunk.getTile(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public Building getBuilding(TilePosition position) {
    Chunk chunk = getChunk(position.toChunkPosition());
    if (chunk == null) {
      return null;
    }

    return chunk.getBuilding(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public byte getBuildingData(TilePosition position) {
    Chunk chunk = getChunk(position.toChunkPosition());
    if (chunk == null) {
      return -1;
    }

    return chunk.getBuildingData(position.getXInChunk(), position.getYInChunk());
  }

  @Override
  public Chunk getChunk(ChunkPosition position) {
    synchronized (chunks) {
      return chunks.get(position);
    }
  }

  @Override
  public int getID() {
    return planeID;
  }

  public void receiveBuildingUpdate(TilePosition position, Building b) {
    Chunk chunk = getChunk(position.toChunkPosition());
    Building prevb = chunk.getBuilding(position.getXInChunk(), position.getYInChunk());
    chunk.setBuilding(position.getXInChunk(), position.getYInChunk(), b);
    if (b == null && prevb != null) {
      GameView.getRendererGame().particleBuildingBreak(position, prevb.getBreakParticle());
    }
  }

  public void receiveTileUpdate(TilePosition position, Tile tile) {
    Chunk chunk = getChunk(position.toChunkPosition());
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

  public void receiveBuildingDataUpdate(TilePosition position, byte data) {
    Chunk chunk = getChunk(position.toChunkPosition());
    chunk.setBuildingData(position.getXInChunk(), position.getYInChunk(), data);
  }

  public void receiveChunk(Chunk chunk) {
    Chunk testChunk = getChunk(chunk.position);
    if (testChunk != null) {
      System.out.println("Overwriting chunk");
      chunks.remove(testChunk.position);
    }
    chunks.put(chunk.position, chunk);
  }
}
