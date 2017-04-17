package me.spoony.botanico.common.util.position;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Created by Colten on 12/27/2016.
 */
public class GuiRectangle {

  public float x, y, width, height;

  public GuiRectangle() {
    this(0, 0, 0, 0);
  }

  public GuiRectangle(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public GuiRectangle(GuiRectangle that) {
    this.x = that.x;
    this.y = that.y;
    this.width = that.width;
    this.height = that.height;
  }

  public OmniPosition getPosition() {
    return new OmniPosition(PositionType.GUI, x, y);
  }

  public GuiRectangle set(GuiRectangle that) {
    this.x = that.x;
    this.y = that.y;
    this.width = that.width;
    this.height = that.height;
    return this;
  }

  public GuiRectangle setPosition(OmniPosition pos) {
    this.x = (float) pos.getX(PositionType.GUI);
    this.y = (float) pos.getY(PositionType.GUI);
    return this;
  }

  public GuiRectangle setPosition(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public GuiRectangle setCenter(OmniPosition pos) {
    this.x = (float) (pos.getX(PositionType.GUI) - this.width / 2f);
    this.y = (float) (pos.getY(PositionType.GUI) - this.height / 2f);
    return this;
  }

  public GuiRectangle setCenter(float x, float y) {
    this.x = x - this.width / 2f;
    this.y = x - this.height / 2f;
    return this;
  }

  public OmniPosition getCenter() {
    return new OmniPosition(PositionType.GUI, x + (width / 2f), y + (height / 2f));
  }

  /**
   * Checks if the rectangle contains the vector. Lower bounds are inclusive, upper bounds are
   * exclusive.
   *
   * @param pos The vector to be tested
   */
  public boolean contains(OmniPosition pos) {
    Preconditions.checkState(width >= 0);
    Preconditions.checkState(height >= 0);
    return !(pos.x <= this.x ||
        pos.y <= this.y ||
        pos.x > this.x + this.width ||
        pos.y > this.y + this.height);
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
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final GuiRectangle that = (GuiRectangle) obj;
    return Objects.equal(this.x, that.x)
        && Objects.equal(this.y, that.y);
  }
}
