package me.spoony.botanico.common.util.position;

import com.google.common.base.Objects;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import me.spoony.botanico.common.level.Chunk;

import java.math.RoundingMode;

/**
 * Created by Colten on 12/31/2016.
 */
public class ChunkPosition {
    public final long x, y;

    public ChunkPosition() {
        x = 0;
        y = 0;
    }

    public ChunkPosition(GamePosition position) {
        x = DoubleMath.roundToLong(position.x / 32d, RoundingMode.FLOOR);
        y = DoubleMath.roundToLong(position.y / 32d, RoundingMode.FLOOR);
    }

    public ChunkPosition(TilePosition position) {
        x = DoubleMath.roundToLong(position.x / 32d, RoundingMode.FLOOR);
        y = DoubleMath.roundToLong(position.y / 32d, RoundingMode.FLOOR);
    }

    public ChunkPosition(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ChunkPosition that = (ChunkPosition) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}
