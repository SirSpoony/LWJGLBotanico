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
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class RendererGUI {

  private SpriteBatch batch;
  public GuiRectangle guiViewport;

  private float scale = 3;

  public float windowXToGui(float x) {
    return x / scale;
  }

  public float windowYToGui(float y) {
    return y / scale;
  }

  public float guiXToWindow(float x) {
    return x * scale;
  }

  public float guiYToWindow(float y) {
    return y * scale;
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

  public void sprite(float x, float y, Texture texture, IntRectangle source) {
    sprite(x, y, texture, source, Color.WHITE);
  }

  public void sprite(float x, float y, Texture texture, IntRectangle source, Color color) {
    batch.sprite(
            (float)guiXToWindow(x), (float)guiYToWindow(y), source.getWidth() * scale, source.getHeight() * scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture);
  }

  public void sprite(GuiRectangle bounds, Texture texture, IntRectangle source, Color color) {
    batch.sprite(
            guiXToWindow(bounds.x), guiYToWindow(bounds.y), bounds.width * scale, bounds.height * scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture);
  }

  public void text(float x, float y, String text, TextColors textColor, CallAlign callAlign) {
    Preconditions.checkNotNull(textColor);
    callAlign = Optional.of(callAlign).or(CallAlign.BOTTOM_LEFT);

    switch (callAlign) {
      case BOTTOM_CENTER:
        text(x - (int) Math.floor(getTextBounds(text).width / 2),
            y + getTextBounds(text).height, text, textColor, CallAlign.TOP_LEFT);
        break;
      case BOTTOM_LEFT:
        text(x, y + getTextBounds(text).height, text, textColor,
            CallAlign.TOP_LEFT);
        break;
      case BOTTOM_RIGHT:
        text(x - getTextBounds(text).width, y + getTextBounds(text).height,
            text, textColor, CallAlign.TOP_LEFT);
        break;
      case TOP_RIGHT:
        text(x - getTextBounds(text).width, y, text, textColor,
            CallAlign.TOP_LEFT);
        break;
      case TOP_LEFT:
        renderText(x, y, text, textColor);
        break;
    }

  }

  private void renderText(float x, float y, String text, TextColors colors) {
    if (Strings.isNullOrEmpty(text)) {
      return;
    }
    Font font = getResourceManager().getFont("coder's_crux_regular_9");
    int renderx = (int) guiXToWindow(x);
    int rendery = (int) guiYToWindow(y);
    font.renderString(batch, text, renderx + scale, rendery - scale, scale, colors.background);
    font.renderString(batch, text, renderx, rendery, scale, colors.foreground);
  }
}
