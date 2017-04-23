package me.spoony.botanico.common.util;

import com.google.common.base.Preconditions;

/**
 * Created by Colten on 12/22/2016.
 */
public class DoubleVector2D {

  public double x;
  public double y;

  public DoubleVector2D() {
    this(0, 0);
  }

  public DoubleVector2D(DoubleVector2D vec) {
    Preconditions.checkNotNull(vec, "Cannot create vector from null vector!");
    this.x = vec.x;
    this.y = vec.y;
  }

  public DoubleVector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public DoubleVector2D add(DoubleVector2D vector) {
    return add(vector.x, vector.y);
  }

  public DoubleVector2D add(double x, double y) {
    this.x += x;
    this.y += y;
    return this;
  }

  public DoubleVector2D set(DoubleVector2D vector) {
    this.x = vector.x;
    this.y = vector.y;
    return this;
  }

  public DoubleVector2D set(double x, double y) {
    this.x = x;
    this.y = y;
    return this;
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

  public DoubleVector2D normalize() {
    double length = length();
    if (length != 0) {
      x /= length;
      y /= length;
    }
    return this;
  }

  public double length() {
    return Math.sqrt(x * x + y * y);
  }

  public void mult(double a) {
        this.x *= a;
        this.y *= a;
  }
}
