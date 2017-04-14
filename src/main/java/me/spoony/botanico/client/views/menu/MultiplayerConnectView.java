package me.spoony.botanico.client.views.menu;

import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.ClientConnectionConfig;
import me.spoony.botanico.client.graphics.gui.control.GUIButton;
import me.spoony.botanico.client.graphics.gui.control.GUIControlAlignmentType;
import me.spoony.botanico.client.graphics.gui.control.GUITextBox;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.server.net.ServerNetworkManager;

/**
 * Created by Colten on 11/18/2016.
 */
public class MultiplayerConnectView extends MenuView
{
    GUITextBox guiIPTextField;
    GUITextBox guiNameTextField;

    @Override
    public void loadContent()
    {
        super.loadContent();

        GUIButton guiConnectButton;
        guiConnectButton = new GUIButton("Connect") {
            @Override
            public void onClick()
            {
                super.onClick();
                connect();
            }
        };
        guiConnectButton.setWidth(100);
        guiConnectButton.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiConnectButton.setOffset(new GuiPosition(0, -10));

        GUIButton guiCancelButton;
        guiCancelButton = new GUIButton("Cancel") {
            @Override
            public void onClick()
            {
                super.onClick();
                cancel();
            }
        };
        guiCancelButton.setWidth(100);
        guiCancelButton.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiCancelButton.setOffset(new GuiPosition(0, -30));

        guiIPTextField = new GUITextBox("IP") {
            @Override
            public boolean acceptChar(char character)
            {
                if (Character.isDigit(character) || Character.isLetter(character) || "./:".indexOf(character) != -1) return true;
                return false;
            }

            @Override
            public void onEnter()
            {
                connect();
            }
        };
        guiIPTextField.setWidth(150);
        guiIPTextField.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiIPTextField.setOffset(new GuiPosition(0,10));

        guiNameTextField = new GUITextBox("Name") {
            @Override
            public boolean acceptChar(char character)
            {
                if (Character.isDigit(character) || Character.isLetter(character) || "_".indexOf(character) != -1) return true;
                return false;
            }
        };
        guiNameTextField.setWidth(150);
        guiNameTextField.setAlignment(GUIControlAlignmentType.CENTER_CENTER);
        guiNameTextField.setOffset(new GuiPosition(0,30));

        addControl(guiIPTextField);
        addControl(guiNameTextField);
        addControl(guiConnectButton);
        addControl(guiCancelButton);
    }

    public void connect() {
        try {
            String serverIP = guiIPTextField.text;
            int port = ServerNetworkManager.DEFAULT_PORT;

            if (guiIPTextField.text.contains(":")) {
                String[] split = guiIPTextField.text.split(":");
                serverIP = split[0];
                port = Integer.parseInt(split[1]);
            }

            ClientConnectionConfig config = new ClientConnectionConfig(guiNameTextField.text, serverIP, port);

            BotanicoGame.setView(new GameView(config));
        } catch (Exception e) {
            BotanicoGame.setView(new KickedView("Invalid Server IP"));
        }
    }

    public void cancel() {
        BotanicoGame.setView(new MainMenuView());
    }
}
