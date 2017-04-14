package me.spoony.botanico.server.level;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.spoony.botanico.common.util.position.ChunkPosition;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.IBuildingEntityHost;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.Updatable;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.entities.EntityItemStack;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.net.server.SPacketNewEntity;
import me.spoony.botanico.common.net.server.SPacketRemoveEntity;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.levelgen.ChunkGeneratorOverworld;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.server.level.levelgen.IChunkGenerator;
import me.spoony.botanico.server.BotanicoServer;

import java.util.*;

public class ServerPlane implements IPlane {
    private final Map<Integer, Entity> entities;
    private final Set<BuildingEntity> buildingEntities;
    public IChunkGenerator chunkGenerator;
    private final Map<ChunkPosition, Chunk> chunks;

    public int seed;

    BotanicoServer server;
    ServerLevel level;

    public ServerPlane(BotanicoServer server, ServerLevel level, long seed) {
        entities = Maps.newConcurrentMap();
        buildingEntities = Sets.newConcurrentHashSet();
        chunks = Maps.newConcurrentMap();
        chunkGenerator = new ChunkGeneratorOverworld(seed);

        this.server = server;
        this.level = level;

        System.out.println("Initialized: ServerLevel " + getClass().getSimpleName());
    }

    public void dispose() {

    }

    @Override
    public Chunk getChunk(ChunkPosition position) {
        Chunk ret = chunks.get(position);
        return ret != null ? ret : generateChunk(position);
    }

    @Override
    public int getID() {
        return IPlane.OVERWORLD;
    }

    public Chunk generateChunk(ChunkPosition position) {
        Chunk newChunk = chunkGenerator.generateChunk(position);
        chunks.put(position, newChunk);
        return newChunk;
    }

    public void setTile(TilePosition position, Tile tile) {
        Chunk chunk = getChunk(position.toChunkPosition(new ChunkPosition()));
        chunk.setTile(position.getXInChunk(), position.getYInChunk(), tile);

        server.getClientManager().getPacketHandler().sendTileChange(position, this, tile);
    }

    @Override
    public Tile getTile(TilePosition position) {
        return getChunk(position.toChunkPosition()).getTile(position.getXInChunk(), position.getYInChunk());
    }

    public void setBuilding(TilePosition position, Building b) {
        Chunk chunk = getChunk(position.toChunkPosition());

        Building prevbuild = chunk.getBuilding(position.getXInChunk(), position.getYInChunk());
        chunk.setBuilding(position.getXInChunk(), position.getYInChunk(), b);

        server.getClientManager().getPacketHandler().sendBuildingChange(position, this, b);

        if (prevbuild != null) {
            prevbuild.destroy(this, position);
            if (prevbuild instanceof IBuildingEntityHost) {
                BuildingEntity entity = getBuildingEntity(position);
                if (entity == null)
                    throw new RuntimeException("IBuildingEntityHost failed to create entity when it was created!");
                entity.destroy();
                removeBuildingEntity(position);
            }
        }
        if (b != null) {
            b.create(this, position);
            if (b instanceof IBuildingEntityHost) {
                IBuildingEntityHost entityHost = (IBuildingEntityHost) b;
                BuildingEntity entity = entityHost.createNewEntity(this, position);
                if (entity == null) throw new RuntimeException("IBuildingEntityHost returned null entity!");
                entity.create();
                addBuildingEntity(entity);
            }
        }
    }

    @Override
    public Building getBuilding(TilePosition position) {
        return getChunk(position.toChunkPosition()).getBuilding(position.getXInChunk(), position.getYInChunk());
    }

    public void addEntity(Entity e) {
        SPacketNewEntity pne = new SPacketNewEntity();

        pne.eid = e.eid;
        pne.type = e.getTypeID();
        pne.x = e.position.x;
        pne.y = e.position.y;
        if (e instanceof EntityItemStack && ((EntityItemStack) e).stack != null) {
            pne.misc = ((EntityItemStack) e).stack.getItem().getID();
        }

        server.getClientManager().sendPacketToAll(pne);

        synchronized (entities) {
            entities.put(e.eid, e);
        }
    }

    public void removeEntity(Entity e) {
        SPacketRemoveEntity pre = new SPacketRemoveEntity();
        pre.eid = e.eid;
        server.getClientManager().sendPacketToAll(pre);

        synchronized (entities) {
            entities.remove(e);
        }
    }

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
        return false;
    }

    public void addBuildingEntity(BuildingEntity e) {
        buildingEntities.add(e);
    }

    public BuildingEntity getBuildingEntity(TilePosition position) {
        for (BuildingEntity e : buildingEntities) {
            if (e.position.equals(position)) return e;
        }
        return null;
    }

    public void removeBuildingEntity(TilePosition position) {
        buildingEntities.remove(getBuildingEntity(position));
    }

    public void removeBuilding(TilePosition position) {
        this.setBuilding(position, null);
    }

    public void update(float timeDiff) {
        synchronized (chunks) {
            for (Chunk chunk : chunks.values()) {
                chunk.update(timeDiff);
            }
        }


        synchronized (entities) {
            Set<Integer> eids = entities.keySet();
            for (Integer eid : eids) {
                Entity e = entities.get(eid);
                e.update(timeDiff, this);
            }
        }

        synchronized (buildingEntities) {
            Iterator<BuildingEntity> i = buildingEntities.iterator();
            while (i.hasNext()) {
                BuildingEntity be = i.next();
                if (be instanceof Updatable) ((Updatable) be).update(timeDiff);
            }
        }
    }

    public void dropItemStack(GamePosition position, ItemStack stack) {
        Random randpos = new Random();
        while (!stack.isEmpty()) {
            ItemStack tempstack = ItemStack.clone(stack);
            tempstack.setCount(1);
            EntityItemStack eis = new EntityItemStack(
                    new GamePosition(position.x + ((-.5f + randpos.nextFloat()) / 2), position.y + ((-.5f + randpos.nextFloat()) / 2)),
                    this,
                    tempstack, false);
            addEntity(eis);
            stack.increaseCount(-1);
        }
    }

    public void breakBuildingAndDrop(TilePosition position, EntityPlayer player) {
        ItemStack[] ist = getBuilding(position).getDrops(this, position);
        if (ist != null) {
            for (ItemStack s : ist) {
                dropItemStack(position.toGamePosition(new GamePosition()).add(.3f, .3f), s);
            }
        }
        setBuilding(position, null);
    }

    public void setBuildingData(TilePosition position, byte data) {
        Chunk chunk = getChunk(position.toChunkPosition());
        chunk.setBuildingData(position.getXInChunk(), position.getYInChunk(), data);

        server.getClientManager().getPacketHandler().sendBuildingDataChange(position, this, data);
    }

    public byte getBuildingData(TilePosition position) {
        return getChunk(position.toChunkPosition()).getBuildingData(position.getXInChunk(), position.getYInChunk());
    }

/*    public NBTTag writeToNBT() {
        List<NBTTag> ret = Lists.newArrayList();

        List<NBTTag> chunksTagList = Lists.newArrayList();
        for (Chunk chunk : this.chunks.values()) {
            NBTLongTag xpos = new NBTLongTag("x", chunk.position.x);
            NBTLongTag ypos = new NBTLongTag("y", chunk.position.y);
            int[] itiles = new int[32 * 32];
            int[] ibuildings = new int[32 * 32];
            byte[] ibuildingdata = new byte[32 * 32];
            for (int x = 0; x < 32; x++) {
                for (int y = 0; y < 32; y++) {
                    itiles[x * 32 + y] = (chunk.tiles[x][y] != null ? chunk.tiles[x][y].getID() : -1);
                    ibuildings[x * 32 + y] = (chunk.buildings[x][y] != null ? chunk.buildings[x][y].getID() : -1);
                    ibuildingdata[x * 32 + y] = (chunk.buildingData[x][y]);
                }
            }
            ByteBuffer tilesBBuf = ByteBuffer.allocate(32 * 32 * Integer.BYTES).order(ByteOrder.BIG_ENDIAN);
            IntBuffer tilesIBuf = tilesBBuf.asIntBuffer();
            tilesIBuf.put(itiles);
            NBTByteArrayTag tiles = new NBTByteArrayTag("tiles", tilesBBuf.array());

            ByteBuffer buildingsBBuf = ByteBuffer.allocate(32 * 32 * Integer.BYTES).order(ByteOrder.BIG_ENDIAN);
            IntBuffer buildingsIBuf = buildingsBBuf.asIntBuffer();
            buildingsIBuf.put(ibuildings);
            NBTByteArrayTag buildings = new NBTByteArrayTag("buildings", buildingsBBuf.array());


            ByteBuffer buildingdataBBuf = ByteBuffer.allocate(32 * 32 * Integer.BYTES).order(ByteOrder.BIG_ENDIAN);
            buildingdataBBuf.put(ibuildingdata);
            NBTByteArrayTag buildingdata = new NBTByteArrayTag("buildingdata", buildingdataBBuf.array());

            chunksTagList.add(new NBTCompoundTag("", xpos, ypos, tiles, buildings, buildingdata));
        }
        ret.add(new NBTListTag("chunks", NBTCompoundTag.class, chunksTagList));

        List<NBTTag> buildingEntityList = Lists.newArrayList();
        for (BuildingEntity be : buildingEntities) {

        }
        ret.add(new NBTListTag("buildingentities", NBTCompoundTag.class, buildingEntityList));

        List<NBTTag> entityList = Lists.newArrayList();
        for (Entity e : entities.values()) {
            entityList.add(e.writeData(new NBTCompoundTag("")));
        }
        ret.add(new NBTListTag("entities", NBTCompoundTag.class, entityList));

        ret.add(new NBTLongTag("seed", seed));

        return new NBTCompoundTag("level", ret);
    }

    public void readFromNBT(NBTTag tag) {
        chunks.clear();
        NBTCompoundTag levelTag = (NBTCompoundTag) tag;
        System.out.println(levelTag.get("chunks").getValue());

        List<NBTTag> chunksTags = (List<NBTTag>) levelTag.get("chunks").getValue();
        for (NBTTag ct : chunksTags) {
            NBTCompoundTag chunkTag = (NBTCompoundTag) ct;

            long x = (Long) chunkTag.get("x").getValue();
            long y = (Long) chunkTag.get("y").getValue();

            byte[] bTiles = (byte[]) chunkTag.get("tiles").getValue();
            byte[] bBuildings = (byte[]) chunkTag.get("buildings").getValue();

            IntBuffer tintBuf = ByteBuffer.wrap(bTiles).asIntBuffer();
            int[] iTiles = new int[tintBuf.remaining()];
            tintBuf.get(iTiles);

            IntBuffer bintBuf = ByteBuffer.wrap(bBuildings).asIntBuffer();
            int[] iBuildings = new int[bintBuf.remaining()];
            bintBuf.get(iBuildings);

            Tile[][] tiles = new Tile[32][32];
            Building[][] buildings = new Building[32][32];
            for (int ix = 0; ix < 32; ix++) {
                for (int iy = 0; iy < 32; iy++) {
                    tiles[ix][iy] = Tile.REGISTRY.get(iTiles[ix * 32 + iy]);
                    buildings[ix][iy] = Building.REGISTRY.getBuilding(iBuildings[ix * 32 + iy]);
                }
            }
            Chunk chunktoadd = new Chunk(new ChunkPosition(x, y), tiles, buildings);

            chunks.put(chunktoadd.position, chunktoadd);
        }
    }*/

    public ServerLevel getLevel() {
        return this.level;
    }
}
