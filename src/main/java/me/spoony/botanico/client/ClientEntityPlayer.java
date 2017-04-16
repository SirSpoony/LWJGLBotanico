package me.spoony.botanico.client;

import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.Animation;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.util.position.GamePosition;
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
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientEntityPlayer extends EntityPlayer implements EntityContainer {

  private float movementSpeed = 4;
  private Animation downAnimation;
  private Animation leftAnimation;
  private Animation rightAnimation;
  private Animation upAnimation;

  public BuildingDamageIndicator buildingDamageIndicator;

  Timer timer;
  Timer footstepTimer;
  BotanicoClient client;

  private GamePosition lastPacketPosition;

  public Inventory inventory;

  public boolean initialized;

  public ClientEntityPlayer(BotanicoClient client, IPlane plane) {
    super(plane);

    this.animation = 0;

    this.timer = new Timer(1f / 32f);
    this.footstepTimer = new Timer(1f / 2f);
    this.client = client;

    this.inventory = new Inventory(EntityPlayer.INVENTORY_SIZE, Dialog.PLAYER_INV_ID);

    this.lastPacketPosition = new GamePosition();

    this.initialized = false;
  }

  public void loadAnimation() {
    float speed = 10;

    downAnimation = new Animation(speed,
        new IntRectangle(0, 0, 16, 32),
        new IntRectangle(16, 0, 16, 32),
        new IntRectangle(32, 0, 16, 32),
        new IntRectangle(48, 0, 16, 32));

    rightAnimation = new Animation(speed,
        new IntRectangle(0, 32, 16, 32),
        new IntRectangle(16, 32, 16, 32),
        new IntRectangle(32, 32, 16, 32),
        new IntRectangle(48, 32, 16, 32));

    upAnimation = new Animation(speed,
        new IntRectangle(0, 64, 16, 32),
        new IntRectangle(16, 64, 16, 32),
        new IntRectangle(32, 64, 16, 32),
        new IntRectangle(48, 64, 16, 32));

    leftAnimation = new Animation(speed,
        new IntRectangle(0, 96, 16, 32),
        new IntRectangle(16, 96, 16, 32),
        new IntRectangle(32, 96, 16, 32),
        new IntRectangle(48, 96, 16, 32));
  }

  @Override
  public void update(float timeDiff, IPlane level) {
    super.update(timeDiff, level);

    if (downAnimation == null) {
      loadAnimation();
    }

    GamePosition prevPos = new GamePosition(getPosition());
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

    if (coorddir.y != 0) {
      position.y += coorddir.y * movementSpeed * timeDiff;
      CollisionCheck check = new EntityCollider(level, this).checkCollisionsChunk();
      if (check.collided) {
        if (coorddir.y > 0) {
          position.y -= check.intersection.getHeight();
        } else {
          position.y += check.intersection.getHeight();
        }
      }
    }

    if (coorddir.x != 0) {
      position.x += coorddir.x * movementSpeed * timeDiff;
      CollisionCheck check = new EntityCollider(level, this).checkCollisionsChunk();
      if (check.collided) {
        if (coorddir.x > 0) {
          position.x -= check.intersection.getWidth();
        } else {
          position.x += check.intersection.getWidth();
        }
      }
    }

    if (getPosition().x == prevPos.x && getPosition().y == prevPos.y) {
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
        Tile tile = level.getTile(new TilePosition(position));
        if (tile != null) {
          //tile.getFootStepMaterial().getRandomSound(Botanico.INSTANCE.getResourceManager()).play(.5f); todo footstep
        }
      }
    }

    if (Input.BUTTON_RIGHT.isDown() && !client.gameView.hasDialogOpen()) {
      TilePosition tilePosition = new TilePosition(Input.CURSOR_POS.toGamePosition());
      Building b = level.getBuilding(tilePosition);

      if (b != null && this.canReach(tilePosition.toGamePosition(new GamePosition()))) {
        if (buildingDamageIndicator == null) {
          buildingDamageIndicator = new BuildingDamageIndicator(tilePosition,
              b.getHardness(level, tilePosition));
        } else {
          if (!(buildingDamageIndicator.tilePosition.equals(tilePosition))) {
            buildingDamageIndicator = new BuildingDamageIndicator(tilePosition,
                b.getHardness(level, tilePosition));
          }

          buildingDamageIndicator.maxHealth = b.getHardness(level, tilePosition);

          buildingDamageIndicator.health -= timeDiff * (1f / getBuildingDamageModifier(b));

          if (buildingDamageIndicator.health <= 0) {
            CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
            cbi.x = tilePosition.x;
            cbi.y = tilePosition.y;
            cbi.type = CPacketBuildingInteraction.DESTROY;
            client.sendPacket(cbi);
            buildingDamageIndicator = null;
          }
        }
      } else {
        buildingDamageIndicator = null;
      }
    } else {
      buildingDamageIndicator = null;
    }

    if (timer.step(1f / 16f)) {
      if (!lastPacketPosition.equals(position)) {
        CPacketPlayerMove pem = new CPacketPlayerMove();
        pem.x = this.position.x;
        pem.y = this.position.y;
        client.sendPacket(pem);

        lastPacketPosition = new GamePosition(position);
      }
    }
  }

  public boolean isMining() {
    return buildingDamageIndicator != null;
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
    TilePosition tilePosition = new TilePosition(Input.CURSOR_POS.toGamePosition());
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
      TilePosition tilePosition = new TilePosition(Input.CURSOR_POS.toGamePosition());
      Building b = GameView.getClientLevel().getBuilding(tilePosition);

      if (b != null && this.canReach(tilePosition.toGamePosition(new GamePosition()))) {
        CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
        cbi.x = tilePosition.x;
        cbi.y = tilePosition.y;
        cbi.type = CPacketBuildingInteraction.CLICK;
        client.sendPacket(cbi);
        return false;
      }

      ItemStack cursorStack = GameView.getCursor().getStack();
      if (this.canReach(tilePosition.toGamePosition(new GamePosition()))) {
        if (cursorStack != null && cursorStack.getItem() instanceof ItemBuilding) {
          Building hypotheticalBuilding = ((ItemBuilding) cursorStack.getItem()).getBuilding();

          EntityCollider ec = new EntityCollider(client.getLocalLevel(), this);
          if (ec.wouldCollide(tilePosition, hypotheticalBuilding)) {
            return false;
          }

          CPacketBuildingInteraction cbi = new CPacketBuildingInteraction();
          cbi.x = tilePosition.x;
          cbi.y = tilePosition.y;
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

    rg.sprite(new GamePosition(position).add(0, -2 / 16f), spritesheet,
        new IntRectangle(64, 0, 16, 32), position.y);

    if (animation == 1) {
      rg.sprite(position, spritesheet, rightAnimation.getSource(), position.y);
    } else if (animation == 0) {
      rg.sprite(position, spritesheet, leftAnimation.getSource(), position.y);
    } else if (animation == 2) {
      rg.sprite(position, spritesheet, downAnimation.getSource(), position.y);
    } else if (animation == 3) {
      rg.sprite(position, spritesheet, upAnimation.getSource(), position.y);
    }

    if (buildingDamageIndicator != null) {
      buildingDamageIndicator.render(rg);
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

  public boolean canReach(GamePosition position) {
    return (this.position.distance(position) < 5);
  }
}
