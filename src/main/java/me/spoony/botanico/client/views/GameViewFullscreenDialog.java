package me.spoony.botanico.client.views;

import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.common.util.IntRectangle;

import java.util.Random;

/**
 * Created by Colten on 11/23/2016.
 */
public class GameViewFullscreenDialog {

  public GameViewFullscreenDialog(String text) {
    this.text = text;
  }

  private final String text;

  public void update(float delta) {

  }

  public void render(RendererGUI rendererGUI) {
    Random rand = new Random(1);
    for (int x = 0; x < Math.ceil(rendererGUI.guiViewport.width / 16f); x++) {
      for (int y = 0; y < Math.ceil(rendererGUI.guiViewport.height / 16f); y++) {
        rendererGUI.sprite(x * 16, y * 16,
            rendererGUI.getResourceManager().getTexture("tiles.png"),
            new IntRectangle(240, 208, 16,
                16));
      }
    }

    GuiRectangle textBounds = rendererGUI.getTextBounds(text);
    textBounds.setCenter(rendererGUI.guiViewport.getCenter());
    rendererGUI.text(
        textBounds.x, textBounds.y,
        text, TextColors.WHITE, CallAlign.BOTTOM_LEFT);
  }
}
