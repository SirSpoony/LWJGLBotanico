package me.spoony.botanico.common.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Created by Colten on 12/24/2016.
 */
public class DoubleRectangle {
    public double x;
    public double y;
    public double width;
    public double height;

    public DoubleRectangle() {
        this(0, 0, 0, 0);
    }

    public DoubleRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public DoubleRectangle(DoubleRectangle that) {
        this.x = that.x;
        this.y = that.y;
        this.width = that.width;
        this.height = that.height;
    }


    public DoubleRectangle(IntRectangle that) {
        this.x = that.x;
        this.y = that.y;
        this.width = that.width;
        this.height = that.height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public DoubleVector2D getPosition() {
        return new DoubleVector2D(getX(), getY());
    }

    public void set(DoubleRectangle that) {
        this.x = that.x;
        this.y = that.y;
        this.width = that.width;
        this.height = that.height;
    }

    public void set(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setPosition(DoubleVector2D pos) {
        this.setX(pos.getX());
        this.setY(pos.getY());
    }

    public void setPosition(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public DoubleRectangle setCenter(DoubleVector2D vector2D) {
        this.x = vector2D.x - this.width / 2d;
        this.y = vector2D.y - this.height / 2d;
        return this;
    }

    public DoubleVector2D getCenter() {
        return new DoubleVector2D(
                x + (width / 2d),
                y + (height / 2d)
        );
    }

    /**
     * Checks if the rectangle contains the vector. Lower bounds are inclusive, upper bounds are exclusive.
     *
     * @param vector2D The vector to be tested
     * @return
     */
    public boolean contains(DoubleVector2D vector2D) {
        Preconditions.checkState(width >= 0);
        Preconditions.checkState(height >= 0);
        return !(vector2D.x < this.x ||
                vector2D.y < this.y ||
                vector2D.x > this.x + this.width ||
                vector2D.y > this.y + this.height);
    }

    public boolean overlaps(DoubleRectangle r) {
        Preconditions.checkState(width >= 0);
        Preconditions.checkState(height >= 0);
        Preconditions.checkState(r.width >= 0);
        Preconditions.checkState(r.height >= 0);
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }

    public DoubleRectangle intersect(DoubleRectangle that) {
        Preconditions.checkState(this.width >= 0);
        Preconditions.checkState(this.height >= 0);
        Preconditions.checkState(that.width >= 0);
        Preconditions.checkState(that.height >= 0);
        DoubleRectangle ret = new DoubleRectangle();
        intersectRectangles(this, that, ret);
        return ret;
    }

    static private boolean intersectRectangles(DoubleRectangle rectangle1, DoubleRectangle rectangle2, DoubleRectangle intersection) {
        if (rectangle1.overlaps(rectangle2)) {
            intersection.x = Math.max(rectangle1.x, rectangle2.x);
            intersection.width = Math.min(rectangle1.x + rectangle1.width, rectangle2.x + rectangle2.width) - intersection.x;
            intersection.y = Math.max(rectangle1.y, rectangle2.y);
            intersection.height = Math.min(rectangle1.y + rectangle1.height, rectangle2.y + rectangle2.height) - intersection.y;
            return true;
        }
        return false;
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
}
