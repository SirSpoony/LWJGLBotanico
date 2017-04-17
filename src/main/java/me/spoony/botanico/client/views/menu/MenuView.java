package me.spoony.botanico.client.views.menu;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.gui.control.*;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.BinaryInputListener;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.input.TextInputListener;
import me.spoony.botanico.client.views.ViewAdapter;
import me.spoony.botanico.common.util.IntRectangle;

import java.util.List;
import java.util.Random;

/**
 * Created by Colten on 11/26/2016.
 */
public class MenuView extends ViewAdapter implements BinaryInputListener, TextInputListener {

  private static float bg_offset;
  BackgroundType bgtype;

  List<IGUIControl> controlList;
  private RendererGUI rendererGUI;

  public RendererGUI getRendererGUI() {
    return rendererGUI;
  }

  public MenuView() {
    controlList = Lists.newArrayList();
  }

  @Override
  public void loadContent() {
    super.loadContent();

    bgtype = BackgroundType.TILE;
    rendererGUI = new RendererGUI();

    Input.registerListener((BinaryInputListener) this);
    Input.registerListener((TextInputListener) this);
  }

  @Override
  public void unloadContent() {
    super.unloadContent();

    Input.unregisterListener((BinaryInputListener) this);
    Input.unregisterListener((TextInputListener) this);
  }

  @Override
  public void update(float delta) {
    super.update(delta);

    bg_offset += delta / 120f;
  }

  @Override
  public void render() {
    super.render();

    rendererGUI.begin();

    if (bgtype == BackgroundType.TILE) {
      Random rand = new Random(1);
      for (int x = 0; x < Math.ceil(rendererGUI.guiViewport.width / 16f); x++) {
        for (int y = 0; y < Math.ceil(rendererGUI.guiViewport.height / 16f); y++) {
          rendererGUI.sprite(x * 16, y * 16,
              rendererGUI.getResourceManager().getTexture("tiles.png"),
              new IntRectangle(32 + 16 * (rand.nextInt() & 1), 16 + 16 * (rand.nextInt() & 1), 16,
                  16));
        }
      }
    } else if (bgtype == BackgroundType.CLOUDS) {
      float x = bg_offset % 1 * 512;
      rendererGUI.sprite(new GuiRectangle(x + 512, 0, 512, 512),
          getRendererGUI().getResourceManager().getTexture("cloud_bg.png"),
          new IntRectangle(0, 0, 256, 256), Color.WHITE);
      rendererGUI.sprite(new GuiRectangle(x, 0, 512, 512),
          getRendererGUI().getResourceManager().getTexture("cloud_bg.png"),
          new IntRectangle(0, 0, 256, 256), Color.WHITE);
      rendererGUI.sprite(new GuiRectangle(x - 512, 0, 512, 512),
          getRendererGUI().getResourceManager().getTexture("cloud_bg.png"),
          new IntRectangle(0, 0, 256, 256), Color.WHITE);
      rendererGUI.sprite(new GuiRectangle(x - 512 * 2, 0, 512, 512),
          getRendererGUI().getResourceManager().getTexture("cloud_bg.png"),
          new IntRectangle(0, 0, 256, 256), Color.WHITE);
    }

    for (IGUIControl control : controlList) {
      control.render(rendererGUI);
    }

    rendererGUI.end();
  }

  @Override
  public boolean onBinaryInputPressed(BinaryInput binaryInput) {
    for (IGUIControl control : controlList) {
      if (control instanceof IClickable) {
        IClickable clickable = (IClickable) control;
        clickable.checkClick(binaryInput);
      }
    }
    return false;
  }

  public void addControl(IGUIControl control) {
    this.controlList.add(control);
  }

  public void setBackground(BackgroundType type) {
    this.bgtype = type;
  }

  @Override
  public void onCharTyped(char character) {
    for (IGUIControl control : controlList) {
      if (control instanceof ITextInputable) {
        ITextInputable textInputable = (ITextInputable) control;
        textInputable.onCharTyped(character);
      }
    }
  }
}
