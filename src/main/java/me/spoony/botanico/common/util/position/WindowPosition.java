package me.spoony.botanico.common.util.position;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.engine.Window;
import me.spoony.botanico.common.util.DoubleVector2D;

/**
 * Created by Colten on 12/27/2016.
 */
public class WindowPosition implements IVectorable {
    public float x, y;

    public WindowPosition(GamePosition gamePosition) {
        this();
        gamePosition.toWindowPosition(this);
    }

    public WindowPosition(GuiPosition guiPosition) {
        this();
        guiPosition.toWindowPosition(this);
    }

    public WindowPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public WindowPosition() {
        this(0,0);
    }

    @Override
    public DoubleVector2D toVector(DoubleVector2D vector2D) {
        vector2D.set(this.x, this.y);
        return vector2D;
    }

    public WindowPosition set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public GamePosition toGamePosition(GamePosition gamePosition) {
        return BotanicoGame.getGame().getRendererGame().windowPosToGamePos(this, gamePosition);
    }

    public GuiPosition toGuiPosition(GuiPosition guiPosition) {
        return BotanicoGame.getGame().getRendererGUI().windowPosToGuiPos(this, guiPosition);
    }

    public GamePosition toGamePosition() {
        return toGamePosition(new GamePosition());
    }

    public GuiPosition toGuiPosition() {
        return toGuiPosition(new GuiPosition());
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
        final WindowPosition that = (WindowPosition) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}