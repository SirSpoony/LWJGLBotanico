package me.spoony.botanico.common.util.position;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.math.DoubleMath;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.common.nbt.NBTCompoundTag;
import me.spoony.botanico.common.util.DoubleVector2D;

import java.math.RoundingMode;

/**
 * Created by Colten on 12/27/2016.
 */
public class GamePosition implements IVectorable {
    public double x, y;

    public GamePosition(GuiPosition guiPosition) {
        this();
        guiPosition.toGamePosition(this);
    }

    public GamePosition(WindowPosition windowPosition) {
        this();
        windowPosition.toGamePosition(this);
    }

    public GamePosition(GamePosition gamePosition) {
        this();
        set(gamePosition);
    }

    public GamePosition(TilePosition tilePosition) {
        this();
        set(tilePosition.toGamePosition(this));
    }

    public GamePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GamePosition() {
        this(0,0);
    }

    public GamePosition set(GamePosition that) {
        this.x = that.x;
        this.y = that.y;
        return this;
    }

    public GamePosition set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public GamePosition add(GamePosition gamePosition) {
        this.x += gamePosition.x;
        this.y += gamePosition.y;
        return this;
    }

    public GamePosition add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    @Override
    public DoubleVector2D toVector(DoubleVector2D vector2D) {
        vector2D.set(this.x, this.y);
        return vector2D;
    }

    public GuiPosition toGuiPosition(GuiPosition guiPosition) {
        WindowPosition wp = BotanicoGame.getGame().getRendererGame().gamePosToWindowPos(this, new WindowPosition());
        return BotanicoGame.getGame().getRendererGUI().windowPosToGuiPos(wp, guiPosition);
    }

    public WindowPosition toWindowPosition(WindowPosition windowPosition) {
        return BotanicoGame.getGame().getRendererGame().gamePosToWindowPos(this, windowPosition);
    }

    public TilePosition toTilePosition(TilePosition tilePosition) {
        tilePosition.x = DoubleMath.roundToLong(this.x, RoundingMode.FLOOR);
        tilePosition.y = DoubleMath.roundToLong(this.y, RoundingMode.FLOOR);
        return tilePosition;
    }

    public TilePosition toTilePosition() {
        return toTilePosition(new TilePosition());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .toString();
    }

    public String toFriendlyString() {
        return "["+x+", "+y+"]";
    }

    public NBTCompoundTag writeData(NBTCompoundTag tag) {
        tag.setDouble("x", x).setDouble("y", y);
        return tag;
    }

    public void readData(NBTCompoundTag data) {
        x = (Integer) data.get("x").getValue();
        y = (Integer) data.get("y").getValue();
    }

    public double distance(GamePosition pos) {
        if (pos == null) return Double.MAX_VALUE;

        double xdif = pos.x-this.x;
        double ydif = pos.y-this.y;
        return Math.sqrt(xdif*xdif+ydif*ydif);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final GamePosition that = (GamePosition) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}
