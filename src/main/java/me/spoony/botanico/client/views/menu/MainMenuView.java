package me.spoony.botanico.client.views.menu;

import me.spoony.botanico.Botanico;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.graphics.gui.control.GUIButton;
import me.spoony.botanico.client.graphics.gui.control.GUILabel;
import me.spoony.botanico.client.graphics.gui.control.GUITexture;
import me.spoony.botanico.client.graphics.gui.control.GUIControlAlignmentType;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/11/2016.
 */
public class MainMenuView extends MenuView
{
    @Override
    public void loadContent()
    {
        super.loadContent();

        setBackground(BackgroundType.CLOUDS);

        GUITexture guiLogo;
        guiLogo = new GUITexture(
                BotanicoGame.getResourceManager().getTexture("logo.png"),
                new IntRectangle(0,0,85,26));
        guiLogo.setWidth(85*2);
        guiLogo.setHeight(26*2);
        guiLogo.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiLogo.setOffset(new GuiPosition(0,30));

        GUILabel versionLabel = new GUILabel("Version "+ Botanico.VERSION);
        versionLabel.setAlignment(GUIControlAlignmentType.TOP_LEFT);

        GUIButton guiStartButton;
        guiStartButton = new GUIButton("Start") {
            @Override
            public void onClick()
            {
                super.onClick();
                BotanicoGame.setView(new GameView());
            }
        };
        guiStartButton.setWidth(100);
        guiStartButton.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiStartButton.setOffset(new GuiPosition(0,-20));

        GUIButton guiMultiplayerButton;
        guiMultiplayerButton = new GUIButton("Multiplayer") {
            @Override
            public void onClick()
            {
                super.onClick();
                BotanicoGame.setView(new MultiplayerConnectView());
            }
        };
        guiMultiplayerButton.setWidth(100);
        guiMultiplayerButton.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiMultiplayerButton.setOffset(new GuiPosition(0,-40));

        addControl(guiLogo);
        addControl(versionLabel);
        addControl(guiStartButton);
        addControl(guiMultiplayerButton);
    }
}
