package me.spoony.botanico.common.util.position;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Created by Colten on 12/27/2016.
 */
public class GuiRectangle {
    public float x,y,width,height;

    public GuiRectangle() {
        this(0, 0, 0, 0);
    }

    public GuiRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GuiRectangle(GuiPosition position, float width, float height) {
        this.x = position.x;
        this.y = position.y;
        this.width = width;
        this.height = height;
    }

    public GuiRectangle(GuiRectangle that) {
        this.x = that.x;
        this.y = that.y;
        this.width = that.width;
        this.height = that.height;
    }

    public GuiPosition getPosition(GuiPosition pos) {
        pos.x = this.x;
        pos.y = this.y;
        return pos;
    }

    public GuiPosition getPosition() {
        return getPosition(new GuiPosition());
    }

    public GuiRectangle set(GuiRectangle that) {
        this.x = that.x;
        this.y = that.y;
        this.width = that.width;
        this.height = that.height;
        return this;
    }

    public GuiRectangle setPosition(GuiPosition pos) {
        this.x = pos.x;
        this.y = pos.y;
        return this;
    }

    public GuiRectangle setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public GuiRectangle setCenter(GuiPosition pos) {
        this.x = pos.x - this.width / 2f;
        this.y = pos.y - this.height / 2f;
        return this;
    }

    public GuiPosition getCenter(GuiPosition guiPosition) {
        guiPosition.x = x + (width / 2f);
        guiPosition.y = y + (height / 2f);
        return guiPosition;
    }

    public GuiPosition getCenter() {
        return getCenter(new GuiPosition());
    }

    /**
     * Checks if the rectangle contains the vector. Lower bounds are inclusive, upper bounds are exclusive.
     * @param pos The vector to be tested
     * @return
     */
    public boolean contains(GuiPosition pos) {
        Preconditions.checkState(width >= 0);
        Preconditions.checkState(height >= 0);
        return !(pos.x <= this.x ||
                pos.y <= this.y ||
                pos.x > this.x+this.width ||
                pos.y > this.y+this.height);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .add("width", width)
                .add("height", height)
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
        final GuiRectangle that = (GuiRectangle) obj;
        return Objects.equal(this.x, that.x)
                && Objects.equal(this.y, that.y);
    }
}
