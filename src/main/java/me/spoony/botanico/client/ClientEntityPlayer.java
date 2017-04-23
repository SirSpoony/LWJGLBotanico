package me.spoony.botanico.client;

import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.Animation;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.entities.*;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.client.graphics.gui.BuildingDamageIndicator;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemBuilding;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.net.client.CPacketBuildingInteraction;
import me.spoony.botanico.common.net.client.CPacketChangeDialog;
import me.spoony.botanico.common.net.client.CPacketPlayerMove;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.DoubleVector2D;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientEntityPlayer extends EntityPlayer implements EntityContainer {

  private float movementSpeed = 4;
  private Animation downAnimation;
  private Animation leftAnimation;
  private Animation rightAnimation;
  private Animation upAnimation;

  public BuildingDamageIndicator indicator;

  Timer timer;
  Timer footstepTimer;
  BotanicoClient client;

  private OmniPosition lastPacketPosition;

  public Inventory inventory;

  public boolean initialized;

  public ClientEntityPlayer(BotanicoClient client, IPlane plane) {
    super(plane);

    this.animation = 0;

    this.timer = new Timer(1f / 32f);
    this.footstepTimer = new Timer(1f / 2f);
    this.client = client;

    this.inventory = new Inventory(EntityPlayer.INVENTORY_SIZE, Dialog.PLAYER_INV_ID);

    this.lastPacketPosition = new OmniPosition(PositionType.GAME, 0, 0);

    this.initialized = false;
  }

  public void loadAnimation() {
    float speed = 15;

    downAnimation = new Animation(speed,
        new IntRectangle(0, 0, 16, 32),
        new IntRectangle(16, 0, 16, 32),
        new IntRectangle(16, 0, 16, 32),
        new IntRectangle(0, 0, 16, 32),
        new IntRectangle(32, 0, 16, 32),
        new IntRectangle(32, 0, 16, 32));

    rightAnimation = new Animation(speed,
        new IntRectangle(0, 32, 16, 32),
        new IntRectangle(16, 32, 16, 32),
        new IntRectangle(16, 32, 16, 32),
        new IntRectangle(0, 32, 16, 32),
        new IntRectangle(32, 32, 16, 32),
        new IntRectangle(32, 32, 16, 32));

    upAnimation = new Animation(speed,
        new IntRectangle(0, 64, 16, 32),
        new IntRectangle(16, 64, 16, 32),
        new IntRectangle(16, 64, 16, 32),
        new IntRectangle(0, 64, 16, 32),
        new IntRectangle(32, 64, 16, 32),
        new IntRectangle(32, 64, 16, 32));

    leftAnimation = new Animation(speed,
        new IntRectangle(0, 96, 16, 32),
        new IntRectangle(16, 96, 16, 32),
        new IntRectangle(16, 96, 16, 32),
        new IntRectangle(0, 96, 16, 32),
        new IntRectangle(32, 96, 16, 32),
        new IntRectangle(32, 96, 16, 32));
  }

  @Override
  public void update(float timeDiff, IPlane plane) {
    super.update(timeDiff, plane);

    if (downAnimation == null) {
      loadAnimation();
    }

    OmniPosition prevPos = new OmniPosition(getPosition());
    DoubleVector2D coorddir = getInputAxis();
    if (coorddir.x < 0) {
      animation = 0;
    } else if (coorddir.x > 0) {
      animation = 1;
    } else if (coorddir.y < 0) {
      animation = 2;
    } else if (coorddir.y > 0) {
      animation = 3;
    }
    if (Input.SHIFT.isDown()) {
      coorddir.mult(5);
    }

    if (coorddir.y != 0) {
      posY += coorddir.y * movementSpeed * timeDiff;
      CollisionCheck check = new EntityCollider(plane, this).checkCollisionsChunk();
      if (check.collided) {
        if (coorddir.y > 0) {
          posY -= check.intersection.getHeight();
        } else {
          posY += check.intersection.getHeight();
        }
      }
    }

    if (coorddir.x != 0) {
      posX += coorddir.x * movementSpeed * timeDiff;
      CollisionCheck check = new EntityCollider(plane, this).checkCollisionsChunk();
      if (check.collided) {
        if (coorddir.x > 0) {
          posX -= check.intersection.getWidth();
        } else {
          posX += check.intersection.getWidth();
        }
      }
    }

    if (getPosition().getGameX() == prevPos.getGameX() && getPosition().getGameY() == prevPos
        .getGameY()) {
      if (animation > 3) {
        animation -= 4;
      }
      footstepTimer.reset();

      leftAnimation.reset();
      rightAnimation.reset();
      upAnimation.reset();
      downAnimation.reset();
    } else {
      leftAnimation.update(timeDiff);
      rightAnimation.update(timeDiff);
      upAnimation.update(timeDiff);
      downAnimation.update(timeDiff);

      if (footstepTimer.step(timeDiff)) {
        Tile tile = plane.getTile(getTileX(), getTileY());
        if (tile != null) {
          //tile.getFootStepMaterial().getRandomSound(Botanico.INSTANCE.getResourceManager()).play(.5f); todo footstep
        }
      }
    }

    if (Input.BUTTON_RIGHT.isDown() && !client.gameView.hasDialogOpen()) {
      OmniPosition buildingPosition = getHighlightedBuildingPosition();
      if (buildingPosition != null) {
        Building b = plane.getBuilding(buildingPosition);

        if (b != null) {
          if (indicator == null) {
            indicator = new BuildingDamageIndicator(buildingPosition,
                b.getHardness(plane, buildingPosition));
          } else {
            if (!(indicator.tilePosition.getTileX() == buildingPosition.getTileX()
                && indicator.tilePosition.getTileY() == buildingPosition.getTileY())) {
              indicator = new BuildingDamageIndicator(buildingPosition,
                  b.getHardness(plane, buildingPosition));
            }

            indicator.maxHealth = b.getHardness(plane, buildingPosition);

            indicator.health -= timeDiff * (1f / getBuildingDamageModifier(b));

            if (indicator.health <= 0) {
              CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
              cbi.x = buildingPosition.getTileX();
              cbi.y = buildingPosition.getTileY();
              cbi.type = CPacketBuildingInteraction.DESTROY;
              client.sendPacket(cbi);
              indicator = null;
            }
          }
        } else {
          indicator = null;
        }
      } else {
        indicator = null;
      }
    } else {
      indicator = null;
    }

    if (timer.step(1f / 16f)) {
      if (lastPacketPosition.getGameX() != posX || lastPacketPosition.getGameY() != posY) {
        CPacketPlayerMove pem = new CPacketPlayerMove();
        pem.x = posX;
        pem.y = posY;
        client.sendPacket(pem);

        lastPacketPosition = new OmniPosition(PositionType.GAME, posX, posY);
      }
    }
  }

  public OmniPosition getHighlightedBuildingPosition() {
    OmniPosition cursor = Input.CURSOR_POS;
    if (!canReach(Input.CURSOR_POS)) {
      return null;
    }

    if (getPlane().getBuilding(cursor) != null) {
      return new OmniPosition(PositionType.GAME, cursor.getTileX(), cursor.getTileY());
    } else {
      for (int xo = -3; xo <= 3; xo++) {
        for (int yo = -3; yo <= 3; yo++) {
          Building b = getPlane().getBuilding(
              new OmniPosition(PositionType.GAME, cursor.getTileX() + xo,
                  cursor.getTileY() + yo));
          if (b == null) {
            continue;
          }
          DoubleRectangle testRect = new DoubleRectangle(
              cursor.getTileX() + xo + b.getCollisionBounds().getX(),
              cursor.getTileY() + yo + b.getCollisionBounds().getY(),
              b.getCollisionBounds().getWidth(),
              b.getCollisionBounds().getHeight());
          if (testRect.contains(Input.CURSOR_POS.getGameX(),
              Input.CURSOR_POS.getGameY())) {
            return new OmniPosition(PositionType.GAME, cursor.getTileX() + xo,
                cursor.getTileY() + yo);
          }
        }
      }
    }
    return null;
  }

  public boolean isMining() {
    return indicator != null;
  }

  public float getBuildingDamageModifier(Building b) {
    float lowest = 1;
    for (int i = 0; i < 7; i++) {
      ItemStack stack = inventory.getStack(i);
      if (stack == null) {
        continue;
      }
      float mod = stack.getItem().getBuildingDamageModifier(b);
      if (mod < lowest) {
        lowest = mod;
      }
    }
    return lowest;
  }

  public ItemSlot getCurrentBuildingDamageTool() {
    OmniPosition tilePosition = Input.CURSOR_POS;
    Building b = getPlane().getBuilding(tilePosition);
    if (b == null) {
      return null;
    }

    float lowest = 1;
    ItemSlot lowestSlot = null;
    for (int i = 0; i < 7; i++) {
      ItemStack stack = inventory.getStack(i);
      if (stack == null) {
        continue;
      }
      float mod = stack.getItem().getBuildingDamageModifier(b);
      if (mod < lowest) {
        lowest = mod;
        lowestSlot = inventory.getSlot(i);
      }
    }
    return lowestSlot;
  }

  public DoubleVector2D getInputAxis() {
    DoubleVector2D ret = new DoubleVector2D();
    if (Input.MOVE_DOWN.isDown()) {
      ret.y--;
    }

    if (Input.MOVE_RIGHT.isDown()) {
      ret.x++;
    }

    if (Input.MOVE_UP.isDown()) {
      ret.y++;
    }

    if (Input.MOVE_LEFT.isDown()) {
      ret.x--;
    }
    ret.normalize();
    return ret;
  }

  public boolean onBinaryInputPressed(BinaryInput binaryInput) {
    if (binaryInput == Input.INVENTORY) {
      if (!hasDialogOpen()) {
        openInventoryDialog();
      }
    }

    if (binaryInput == Input.BUTTON_LEFT) {
      OmniPosition tilePosition = Input.CURSOR_POS;
      Building b = GameView.getClientLevel().getBuilding(tilePosition);

      if (b != null && this.canReach(tilePosition)) {
        CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
        cbi.x = tilePosition.getTileX();
        cbi.y = tilePosition.getTileY();
        cbi.type = CPacketBuildingInteraction.CLICK;
        client.sendPacket(cbi);
        return false;
      }

      ItemStack cursorStack = GameView.getCursor().getStack();
      if (this.canReach(tilePosition)) {
        if (cursorStack != null && cursorStack.getItem() instanceof ItemBuilding) {
          Building hypotheticalBuilding = ((ItemBuilding) cursorStack.getItem()).getBuilding();

          EntityCollider ec = new EntityCollider(client.getLocalLevel(), this);
          if (ec.wouldCollide(tilePosition, hypotheticalBuilding)) {
            return false;
          }

          CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
          cbi.x = tilePosition.getTileX();
          cbi.y = tilePosition.getTileY();
          cbi.type = CPacketBuildingInteraction.CREATE;
          client.sendPacket(cbi);

          return false;
        } else if (cursorStack != null) {
          client.packetHandler.sendUseItem(tilePosition);
          return false;
        }
      }
    }

    int slotToExchange = -1;
    if (binaryInput == Input.INV_KEY_1) {
      slotToExchange = 0;
    } else if (binaryInput == Input.INV_KEY_2) {
      slotToExchange = 1;
    } else if (binaryInput == Input.INV_KEY_3) {
      slotToExchange = 2;
    } else if (binaryInput == Input.INV_KEY_4) {
      slotToExchange = 3;
    } else if (binaryInput == Input.INV_KEY_5) {
      slotToExchange = 4;
    } else if (binaryInput == Input.INV_KEY_6) {
      slotToExchange = 5;
    } else if (binaryInput == Input.INV_KEY_7) {
      slotToExchange = 6;
    } else if (binaryInput == Input.INV_KEY_8) {
      slotToExchange = 7;
    }

    if (slotToExchange != -1) {
      client.packetHandler.sendItemSlotExchange(inventory.getSlot(slotToExchange), (byte) 0);
    }

    return false;
  }

  @Override
  public void render(RendererGame rg) {
    Texture spritesheet = BotanicoGame.getResourceManager().getTexture("character_sheet.png");

    if (downAnimation == null) {
      loadAnimation();
    }

    rg.sprite(posX, posY - 2 / 16f, spritesheet,
        new IntRectangle(64, 0, 16, 32), posY);

    if (animation == 1) {
      rg.sprite(posX, posY, spritesheet, rightAnimation.getSource(), posY);
    } else if (animation == 0) {
      rg.sprite(posX, posY, spritesheet, leftAnimation.getSource(), posY);
    } else if (animation == 2) {
      rg.sprite(posX, posY, spritesheet, downAnimation.getSource(), posY);
    } else if (animation == 3) {
      rg.sprite(posX, posY, spritesheet, upAnimation.getSource(), posY);
    }

    if (indicator != null) {
      indicator.render(rg);
    }
  }

  public void openInventoryDialog() {
    client.sendPacket(
        new CPacketChangeDialog().setOperation(CPacketChangeDialog.OPEN_PLAYER_INVENTORY));
  }

  public void closeCurrentDialog() {
    if (GameView.get().dialogRenderer != null) {
      GameView.get().dialogRenderer.close();
    }
    GameView.get().dialogRenderer = null;

    client.sendPacket(new CPacketChangeDialog().setOperation(CPacketChangeDialog.CLOSE_DIALOG));
  }

  public boolean hasDialogOpen() {
    return GameView.get().hasDialogOpen();
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  public boolean canReach(OmniPosition position) {
    float reachDistance = 5;
    double dx = position.getGameX() - posX;
    double dy = position.getGameY() - posY;
    return dx*dx + dy*dy < reachDistance*reachDistance;
  }
}
