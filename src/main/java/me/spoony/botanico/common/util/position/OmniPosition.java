package me.spoony.botanico.common.util.position;

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import java.math.RoundingMode;
import me.spoony.botanico.client.BotanicoGame;

/**
 * Created by Colten on 4/16/2017.
 *
 * This should only be used when the position has the possibility of being used for both the GUI and World, i.e. cursor position.
 */
public class OmniPosition {

  public PositionType type;
  public double x, y;

  public OmniPosition(PositionType type, double x, double y) {
    this.type = type;
    this.x = x;
    this.y = y;
  }

  public OmniPosition(OmniPosition pos) {
    set(pos);
  }

  public void convertType(PositionType type) {
    this.x = getX(type);
    this.y = getY(type);
    this.type = type;
  }

  public double getX(PositionType covertToType) {
    switch (this.type) {
      case GUI:
        switch (this.type) {
          case GUI:
            return x;
          case WINDOW:
            return BotanicoGame.getGame().getRendererGUI().guiXToWindow((float)x);
          case GAME:
            double wp = BotanicoGame.getGame().getRendererGUI().guiXToWindow((float)x);
            return BotanicoGame.getGame().getRendererGame().windowXToGame(wp);
          case CHUNK:
            break; // todo
        }
        break;
      case WINDOW:
        switch (this.type) {
          case GUI:
            return BotanicoGame.getGame().getRendererGUI().windowXToGui((float)x);
          case WINDOW:
            return x;
          case GAME:
            return BotanicoGame.getGame().getRendererGame().windowXToGame(x);
          case CHUNK:
            break; // todo
        }
        break;
      case GAME:
        switch (this.type) {
          case GUI:
            double wp = BotanicoGame.getGame().getRendererGame().gameXToWindow(x);
            return BotanicoGame.getGame().getRendererGUI().windowXToGui((float)wp);
          case WINDOW:
            return BotanicoGame.getGame().getRendererGame().gameXToWindow(x);
          case GAME:
            return x;
          case CHUNK:
            break; // todo
        }
        break;
      case CHUNK:
        switch (this.type) {
          case GUI:
            break;
          case WINDOW:
            break;
          case GAME:
            break;
          case CHUNK:
            return x;
        }
        break;
    }
    return 0;
  }

  public double getY(PositionType covertToType) {
    switch (this.type) {
      case GUI:
        switch (this.type) {
          case GUI:
            return y;
          case WINDOW:
            return BotanicoGame.getGame().getRendererGUI().guiYToWindow((float)y);
          case GAME:
            double wp = BotanicoGame.getGame().getRendererGUI().guiYToWindow((float)y);
            return BotanicoGame.getGame().getRendererGame().windowYToGame(wp);
          case CHUNK:
            break; // todo
        }
        break;
      case WINDOW:
        switch (this.type) {
          case GUI:
            return BotanicoGame.getGame().getRendererGUI().windowYToGui((float)y);
          case WINDOW:
            return y;
          case GAME:
            return BotanicoGame.getGame().getRendererGame().windowYToGame(y);
          case CHUNK:
            break; // todo
        }
        break;
      case GAME:
        switch (this.type) {
          case GUI:
            double wp = BotanicoGame.getGame().getRendererGame().gameYToWindow(y);
            return BotanicoGame.getGame().getRendererGUI().windowYToGui((float)wp);
          case WINDOW:
            return BotanicoGame.getGame().getRendererGame().gameYToWindow(y);
          case GAME:
            return y;
          case CHUNK:
            break; // todo
        }
        break;
      case CHUNK:
        switch (this.type) {
          case GUI:
            break;
          case WINDOW:
            break;
          case GAME:
            break;
          case CHUNK:
            return y;
        }
        break;
    }
    return 0;
  }

  public long getChunkX() {
    return DoubleMath.roundToLong(getX(PositionType.GAME) / 32d, RoundingMode.FLOOR);
  }

  public long getChunkY() {
    return DoubleMath.roundToLong(getY(PositionType.GAME) / 32d, RoundingMode.FLOOR);
  }

  public int getXInChunk() {
    return LongMath.mod((long) getX(PositionType.GAME), 32);
  }

  public int getYInChunk() {
    return LongMath.mod((long) getY(PositionType.GAME), 32);
  }

  public double distance(PositionType type, OmniPosition pos) {
    if (pos == null) {
      return Double.MAX_VALUE;
    }

    double xdif = pos.getX(type) - this.getX(type);
    double ydif = pos.getY(type) - this.getY(type);
    return Math.sqrt(xdif * xdif + ydif * ydif);
  }

  public void set(OmniPosition position) {
    this.type = position.type;
    this.x = position.x;
    this.y = position.y;
  }

  public void set(PositionType type, double x, double y) {
    this.type = type;
    this.x = x;
    this.y = y;
  }

  public OmniPosition getTileNeighbor(TileDirection direction) {
    Preconditions.checkNotNull(direction);
    switch (direction) {
      case NORTH:
        return new OmniPosition(PositionType.GAME, getX(PositionType.GAME) + 0,
            getY(PositionType.GAME) + 1);
      case SOUTH:
        return new OmniPosition(PositionType.GAME, getX(PositionType.GAME) + 0,
            getY(PositionType.GAME) - 1);
      case EAST:
        return new OmniPosition(PositionType.GAME, getX(PositionType.GAME) + 1,
            getY(PositionType.GAME) + 0);
      case WEST:
        return new OmniPosition(PositionType.GAME, getX(PositionType.GAME) - 1,
            getY(PositionType.GAME) + 0);
    }
    return null;
  }

  public long getTileX() {
    return (long) getX(PositionType.GAME);
  }

  public long getTileY() {
    return (long) getY(PositionType.GAME);
  }

  public void setX(PositionType type, double x) {
    this.convertType(type);
    this.x = x;
  }

  public void setY(PositionType type, double y) {
    this.convertType(type);
    this.y = y;
  }

  public OmniPosition add(PositionType type, double x, double y) {
    this.convertType(type);
    this.x += x;
    this.y += y;
    return this;
  }
}
