package me.spoony.botanico.client.views.menu;

import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.graphics.gui.control.GUIButton;
import me.spoony.botanico.client.graphics.gui.control.GUILabel;
import me.spoony.botanico.client.graphics.gui.control.GUIControlAlignmentType;
import me.spoony.botanico.client.input.BinaryInputListener;

/**
 * Created by Colten on 11/24/2016.
 */
public class KickedView extends MenuView implements BinaryInputListener
{
    public String message;

    public KickedView(String message) {
        this.message = message;
    }

    @Override
    public void loadContent()
    {
        super.loadContent();

        setBackground(BackgroundType.TILE);

        GUILabel guiKickMessage;
        guiKickMessage = new GUILabel(message);
        guiKickMessage.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiKickMessage.setOffset(new GuiPosition(0, 10));

        GUIButton guiBackButton;
        guiBackButton = new GUIButton("Back") {
            @Override
            public void onClick()
            {
                super.onClick();
                back();
            }
        };
        guiBackButton.setWidth(100);
        guiBackButton.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiBackButton.setOffset(new GuiPosition(0, -10));

        addControl(guiKickMessage);
        addControl(guiBackButton);

        System.out.println("loaded kicked view content");
    }

    public void back() {
        System.out.println("Returning to Main Menu...");
        BotanicoGame.setView(new MainMenuView());
    }
}
