package me.spoony.botanico.client.views;

import io.netty.channel.ChannelFutureListener;
import me.spoony.botanico.Botanico;
import me.spoony.botanico.client.*;
import me.spoony.botanico.client.graphics.dialog.DialogRenderer;
import me.spoony.botanico.client.graphics.dialog.DialogRenderers;
import me.spoony.botanico.client.views.menu.MainMenuView;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.client.graphics.renderers.CursorRenderer;
import me.spoony.botanico.client.graphics.renderers.HotbarRenderer;
import me.spoony.botanico.client.views.menu.KickedView;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.client.graphics.*;
import me.spoony.botanico.client.graphics.gui.*;
import me.spoony.botanico.client.input.*;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.server.net.BotanicoServer;

import java.net.UnknownHostException;

public class GameView extends ViewAdapter implements IView, BinaryInputListener {
    protected BotanicoClient client;
    protected BotanicoServer server;
    protected ClientConnectionConfig connectionConfig;
    public RendererGame rendererGame;
    public RendererGUI rendererGUI;

    private CommandLine commandLine;
    private GameViewFullscreenDialog fullscreenDialog;
    public DialogRenderer dialogRenderer;

    protected static String debugValue;
    protected static String debugValue2;

    // ------------------------ //
    // Renderers
    // ------------------------ //
    protected HotbarRenderer hotbarRenderer;
    private CursorRenderer cursorRenderer;

    private float waittime;

    public GameView() {
        connectionConfig = null;
    }

    public GameView(ClientConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    @Override
    public void initialize() {
        super.initialize();

        // BEGIN SERVER AND CLIENT
        System.out.println("Initialize Client/Server");
        waittime = -1;



        if (connectionConfig != null) {
            client = new BotanicoClient(this, connectionConfig);

            fullscreenDialog = new GameViewFullscreenDialog("Connecting to Server...");
            client.begin().addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    StringBuilder errorMessage = new StringBuilder("Could not connect to server");
                    if (future.cause() instanceof UnknownHostException) {
                        errorMessage.append(" (Unknown Host).");
                    } else {
                        errorMessage.append(" (Connection Refused).");
                    }
                    BotanicoGame.setView(new KickedView(errorMessage.toString()));
                } else {
                    waittime = 0;
                }
            });
        } else {
            fullscreenDialog = new GameViewFullscreenDialog("beginning");
            server = new BotanicoServer();
            client = new BotanicoClient(this, null);

            try {
                fullscreenDialog = new GameViewFullscreenDialog("Starting Server...");
                server.run().sync();
                fullscreenDialog = new GameViewFullscreenDialog("Starting Client...");
                client.begin().sync();
                fullscreenDialog = new GameViewFullscreenDialog("Letting Chunks Load...");
                waittime = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // INITIALIZE GAMEVIEW
        // initialize rest of gameview

        commandLine = new CommandLine();
        hotbarRenderer = new HotbarRenderer(GameView.getPlayer().getInventory());
        cursorRenderer = new CursorRenderer(GameView.getPlayer());

        Input.registerListener(this);
    }

    @Override
    public void loadContent() {
        super.loadContent();

        rendererGame = new RendererGame();
        rendererGUI = new RendererGUI();

        GamePosition playerPosition = client.getLocalPlayer().position;
        rendererGame.centerOn(new DoubleRectangle(playerPosition.x, playerPosition.y, 1, 2).getCenter(), -1);
    }

    @Override
    public void update(float delta) {
        commandLine.update(delta);

        client.getLocalLevel().update(delta);

        GamePosition playerPosition = client.getLocalPlayer().position;
        rendererGame.centerOn(new DoubleRectangle(playerPosition.x, playerPosition.y, 1, 2).getCenter(), delta * 16);

        if (waittime > 2) {
            waittime = -1;
            fullscreenDialog = null;
        } else {
            if (waittime != -1) waittime += delta;
        }

        if (fullscreenDialog != null) {
            fullscreenDialog.update(delta);
        }
        if (hasDialogOpen())
            dialogRenderer.update(delta);
    }

    @Override
    public void render() {
        rendererGame.tint = hasDialogOpen();

        rendererGame.begin();

        client.getLocalLevel().render(rendererGame);

        rendererGame.end();

        rendererGUI.begin();

        if (Botanico.DEBUG) {
            rendererGUI.text(new GuiPosition(0, rendererGUI.guiViewport.height), "FPS: " +
                    Math.round(BotanicoGame.FPS), TextColors.YELLOW, CallAlign.TOP_LEFT);

            double x = client.getLocalPlayer().position.x;
            double y = client.getLocalPlayer().position.y;

            rendererGUI.text(new GuiPosition(0, rendererGUI.guiViewport.height - 10), "Player X: " +
                    x, TextColors.WHITE, CallAlign.TOP_LEFT);
            rendererGUI.text(new GuiPosition(0, rendererGUI.guiViewport.height - 20), "Player Y: " +
                    y, TextColors.WHITE, CallAlign.TOP_LEFT);

            rendererGUI.text(new GuiPosition(0, rendererGUI.guiViewport.height - 35), "DBG 1: " +
                    debugValue, TextColors.WHITE, CallAlign.TOP_LEFT);
            rendererGUI.text(new GuiPosition(0, rendererGUI.guiViewport.height - 45), "DBG 2: " +
                    debugValue2, TextColors.WHITE, CallAlign.TOP_LEFT);
        }


        commandLine.render(rendererGUI);

        if (hasDialogOpen()) {
            rendererGUI.tint();
            dialogRenderer.render(rendererGUI);
        }

        hotbarRenderer.render(rendererGUI);

        cursorRenderer.render(rendererGUI);

        if (fullscreenDialog != null) {
            fullscreenDialog.render(rendererGUI);
        }

        rendererGUI.end();
    }

    @Override
    public void unloadContent() {
        Input.unregisterListener(this);
        if (isRunningServer()) {
            try {
                getServer().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getClient().close();
    }

    @Override
    @InputHandler(priority = InputPriority.GUI_MIDDLE)
    public boolean onBinaryInputPressed(BinaryInput binaryInput) {
        if (binaryInput == Input.ESCAPE) {
            BotanicoGame.setView(new MainMenuView());
        }

        if (hotbarRenderer.onBinaryInputPressed(binaryInput)) return true;
        if (hasDialogOpen()) {
            dialogRenderer.onBinaryInputPressed(binaryInput);
            return true;
        }

        if (client.getLocalPlayer().onBinaryInputPressed(binaryInput)) return true;
        return false;
    }

    public void openDialog(Dialog dialog) {
        if (dialog != null) {
            DialogRenderer renderer = DialogRenderers.newDialogRenderer(dialog);
            renderer.open();
            this.dialogRenderer = renderer;
        }
    }

    public void forceCenterCameraOnPlayer() {
        rendererGame.centerOn(new DoubleRectangle(client.getLocalPlayer().getPosition().x, client.getLocalPlayer().getPosition().y, 1, 2).getCenter(), 0);
    }

    public boolean hasDialogOpen() {
        return dialogRenderer != null && dialogRenderer.isOpen();
    }

    public Dialog getDialog() {
        return hasDialogOpen() ? dialogRenderer.getDialog() : null;
    }

    public static GameView get() {
        return BotanicoGame.getGame();
    }

    public static BotanicoClient getClient() {
        return BotanicoGame.getGame().client;
    }

    public static BotanicoServer getServer() {
        return BotanicoGame.getGame().server;
    }

    public static boolean isRunningServer() {
        return BotanicoGame.getGame().server != null;
    }

    public static ClientEntityPlayer getPlayer() {
        return getClient().getLocalPlayer();
    }

    public static CursorRenderer getCursor() {
        return BotanicoGame.getGame().cursorRenderer;
    }

    public static RendererGUI getRendererGUI() {
        return BotanicoGame.getRendererGUI();
    }

    public static RendererGame getRendererGame() {
        return BotanicoGame.getGame().rendererGame;
    }

    public static CommandLine getCommandLine() {
        return BotanicoGame.getGame().commandLine;
    }

    public static ClientPlane getClientLevel() {
        return BotanicoGame.getGame().client.getLocalLevel();
    }

    public static void setDebugValue1(String value) {
        debugValue = value;
    }

    public static void setDebugValue2(String value) {
        debugValue2 = value;
    }
}
