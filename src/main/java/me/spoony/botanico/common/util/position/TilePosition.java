package me.spoony.botanico.common.util.position;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Direction;

import java.math.RoundingMode;

/**
 * Created by Colten on 12/31/2016.
 */
public class TilePosition {
    public long x, y;

    public TilePosition(GamePosition position) {
        x = DoubleMath.roundToLong(position.x, RoundingMode.FLOOR);
        y = DoubleMath.roundToLong(position.y, RoundingMode.FLOOR);
    }

    public TilePosition(TilePosition position) {
        this();
        set(position);
    }

    public TilePosition(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public TilePosition() {
        this.x = 0;
        this.y = 0;
    }

    public ChunkPosition toChunkPosition() {
        return new ChunkPosition(this);
    }

    public int getXInChunk() {
        return LongMath.mod(this.x, Chunk.CHUNK_SIZE);
    }

    public int getYInChunk() {
        return LongMath.mod(this.y, Chunk.CHUNK_SIZE);
    }

    public GamePosition toGamePosition() {
        return toGamePosition(new GamePosition());
    }

    public GamePosition toGamePosition(GamePosition position) {
        position.x = x;
        position.y = y;
        return position;
    }

    public TilePosition set(long x, long y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public TilePosition set(TilePosition position) {
        this.x = position.x;
        this.y = position.y;
        return this;
    }

    public TilePosition add(long x, long y) {
        this.x+=x;
        this.y+=y;
        return this;
    }

    public TilePosition getNeighbor(TileDirection direction) {
        Preconditions.checkNotNull(direction);
        switch(direction) {
            case NORTH:
                return new TilePosition(this).add(0,1);
            case SOUTH:
                return new TilePosition(this).add(0,-1);
            case EAST:
                return new TilePosition(this).add(1,0);
            case WEST:
                return new TilePosition(this).add(-1,0);
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final TilePosition that = (TilePosition) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}
