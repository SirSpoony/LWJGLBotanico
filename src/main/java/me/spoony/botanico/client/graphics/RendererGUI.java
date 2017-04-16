package me.spoony.botanico.client.graphics;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.ResourceManager;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Font;
import me.spoony.botanico.client.engine.SpriteBatch;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.common.util.position.WindowPosition;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class RendererGUI {

  private SpriteBatch batch;
  public GuiRectangle guiViewport;

  private float scale = 3;

  public GuiPosition windowPosToGuiPos(WindowPosition windowpos, GuiPosition guiPosition) {
    guiPosition.x = windowpos.x / scale;
    guiPosition.y = windowpos.y / scale;
    return guiPosition;
  }

  public WindowPosition guiPosToWindowPos(GuiPosition guipos, WindowPosition windowPosition) {
    windowPosition.x = guipos.x * scale;
    windowPosition.y = guipos.y * scale;
    return windowPosition;
  }

  public GuiPosition getCursorPosition(GuiPosition guipos) {
    return windowPosToGuiPos(Input.CURSOR_POS, guipos);
  }

  public RendererGUI() {
    this.batch = new SpriteBatch(8191);

    this.guiViewport = new GuiRectangle();
    updateGuiViewport();
  }

  public ResourceManager getResourceManager() {
    return BotanicoGame.getResourceManager();
  }

  public void begin() {
    updateGuiViewport();

    batch.begin();
  }

  public void unload() {
    //batch.dispose(); TODO: Dispose logic
  }

  public void end() {
    batch.end();
  }

  private void updateGuiViewport() {
    int windowWidth = BotanicoGame.WINDOW.getWidth();
    int windowHeight = BotanicoGame.WINDOW.getHeight();

    guiViewport.x = 0;
    guiViewport.y = 0;
    guiViewport.width = windowWidth / scale;
    guiViewport.height = windowHeight / scale;

    batch.viewwidth = windowWidth;
    batch.viewheight = windowHeight;

    if ((float) windowWidth / (float) windowHeight < 1) {
      scale = BMath.clamp((int) Math.floor(windowWidth / 300), 2, 4); // width less than height
    } else {
      scale = BMath.clamp((int) Math.floor(windowHeight / 200), 2, 4); // height less than width
    }
  }

  public GuiRectangle getTextBounds(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return new GuiRectangle();
    }
    Font font = getResourceManager().getFont("coder's_crux_regular_9");
    return font.getStringBounds(text, 1);
  }

  public void tint() {
        /*batch.setColor(Color.WHITE);
        TextureRegion tr = new TextureRegion(getResourceManager().getTexture("assets/dialog/tint.png"));
        batch.draw(tr, 0, 0, getGUIWidth() * scale + scale, getGUIHeight() * scale + scale);*/
  }

  public void sprite(GuiRectangle bounds, Texture texture, IntRectangle source) {
    sprite(bounds, texture, source, Color.WHITE);
  }

  public void sprite(GuiPosition pos, Texture texture, IntRectangle source) {
    sprite(pos, texture, source, Color.WHITE);
  }

  public void sprite(GuiPosition pos, Texture texture, IntRectangle source, Color color) {
    WindowPosition windowPos = new WindowPosition(pos);

    batch.sprite(
        windowPos.x, windowPos.y, source.getWidth() * scale, source.getHeight() * scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture);
  }

  public void sprite(GuiRectangle bounds, Texture texture, IntRectangle source, Color color) {
    WindowPosition windowPos = new WindowPosition(bounds.getPosition());
    //System.out.println("render in renderergui x "+windowPos.x+" y "+windowPos.y);
    //System.out.println("renderergui size w "+guiViewport.width+" h "+guiViewport.height);
    batch.sprite(
        windowPos.x, windowPos.y, bounds.width * scale, bounds.height * scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture);
  }

  public void text(GuiPosition pos, String text, TextColors textColor, CallAlign callAlign) {
    Preconditions.checkNotNull(pos);
    Preconditions.checkNotNull(textColor);
    callAlign = Optional.of(callAlign).or(CallAlign.BOTTOM_LEFT);

    switch (callAlign) {
      case BOTTOM_CENTER:
        text(new GuiPosition(pos.x - (int) Math.floor(getTextBounds(text).width / 2),
            pos.y + getTextBounds(text).height), text, textColor, CallAlign.TOP_LEFT);
        break;
      case BOTTOM_LEFT:
        text(new GuiPosition(pos.x, pos.y + getTextBounds(text).height), text, textColor,
            CallAlign.TOP_LEFT);
        break;
      case BOTTOM_RIGHT:
        text(new GuiPosition(pos.x - getTextBounds(text).width, pos.y + getTextBounds(text).height),
            text, textColor, CallAlign.TOP_LEFT);
        break;
      case TOP_RIGHT:
        text(new GuiPosition(pos.x - getTextBounds(text).width, pos.y), text, textColor,
            CallAlign.TOP_LEFT);
        break;
      case TOP_LEFT:
        renderText(pos, text, textColor);
        break;
    }

  }

  private void renderText(GuiPosition pos, String text, TextColors colors) {
    if (Strings.isNullOrEmpty(text)) {
      return;
    }
    Font font = getResourceManager().getFont("coder's_crux_regular_9");
    WindowPosition windowPos = new WindowPosition(pos);
    int renderx = (int) windowPos.x;
    int rendery = (int) windowPos.y;
    font.renderString(batch, text, renderx + scale, rendery - scale, scale, colors.background);
    font.renderString(batch, text, renderx, rendery, scale, colors.foreground);
  }
}
