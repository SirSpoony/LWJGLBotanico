package me.spoony.botanico.common.util.position;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.common.util.DoubleVector2D;

/**
 * Created by Colten on 12/27/2016.
 */
public class GuiPosition implements IVectorable {
    public float x, y;

    public GuiPosition(GamePosition gamePosition) {
        this();
        gamePosition.toGuiPosition(this);
    }

    public GuiPosition(WindowPosition windowPosition) {
        this();
        windowPosition.toGuiPosition(this);
    }

    public GuiPosition(GuiPosition pos) {
        this();
        set(pos);
    }

    public GuiPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public GuiPosition() {
        this(0,0);
    }

    public GuiPosition set(GuiPosition that) {
        this.x = that.x;
        this.y = that.y;
        return this;
    }

    @Override
    public DoubleVector2D toVector(DoubleVector2D vector2D) {
        vector2D.set(this.x, this.y);
        return vector2D;
    }

    public GamePosition toGamePosition(GamePosition gamePosition) {
        WindowPosition wp = BotanicoGame.getGame().getRendererGUI().guiPosToWindowPos(this, new WindowPosition());
        return BotanicoGame.getGame().getRendererGame().windowPosToGamePos(wp, gamePosition);
    }

    public WindowPosition toWindowPosition(WindowPosition windowPosition) {
        return BotanicoGame.getGame().getRendererGUI().guiPosToWindowPos(this, windowPosition);
    }

    public GuiPosition add(GuiPosition pos) {
        this.x += pos.x;
        this.y += pos.y;
        return this;
    }

    public GuiPosition add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final GuiPosition that = (GuiPosition) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}
