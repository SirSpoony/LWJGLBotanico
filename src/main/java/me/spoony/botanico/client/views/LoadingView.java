package me.spoony.botanico.client.views;

import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.graphics.gui.control.GUITexture;
import me.spoony.botanico.client.graphics.gui.control.GUIControlAlignmentType;
import me.spoony.botanico.client.views.menu.BackgroundType;
import me.spoony.botanico.client.views.menu.MainMenuView;
import me.spoony.botanico.client.views.menu.MenuView;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/23/2016.
 */
public class LoadingView extends MenuView {

  public int loading;

  @Override
  public void initialize() {
    super.initialize();
    loading = 1;
  }

  @Override
  public void loadContent() {
    super.loadContent();

    setBackground(BackgroundType.NONE);

    GUITexture guiSpoonyLogo = new GUITexture(
        BotanicoGame.getResourceManager().getTexture("spoony_logo.png"),
        new IntRectangle(0, 0, 80, 18));
    guiSpoonyLogo.setWidth(80 * 3);
    guiSpoonyLogo.setHeight(18 * 3);
    guiSpoonyLogo.setAlignment(GUIControlAlignmentType.CENTER_CENTER);

    addControl(guiSpoonyLogo);
  }

  float wait = 0;

  @Override
  public void update(float delta) {
    super.update(delta);

    wait += delta;
    if (loading != 2 || wait < 1) {
      return;
    }
    BotanicoGame.getResourceManager().preLoad();
    BotanicoGame.setView(new MainMenuView());
  }

  @Override
  public void render() {
    super.render();
    loading = 2;
  }
}
