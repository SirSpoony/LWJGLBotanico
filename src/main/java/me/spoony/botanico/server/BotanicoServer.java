package me.spoony.botanico.server;

import me.spoony.botanico.server.level.ServerLevel;
import me.spoony.botanico.server.net.ServerManager;
import me.spoony.botanico.server.net.ServerNetworkManager;

/**
 * Created by Colten on 11/18/2016.
 **/
public class BotanicoServer {

  private static BotanicoServer CURRENT_INSTANCE;
  public static boolean RUNNING;

  public ServerLevel level;
  protected Thread gameLoopThread;

  ServerManager clientManager;

  public BotanicoServer() {
    clientManager = new ServerNetworkManager(this);

    level = new ServerLevel(this);
    BotanicoServer.CURRENT_INSTANCE = this;
  }

  public static BotanicoServer getCurrentInstance() {
    return BotanicoServer.CURRENT_INSTANCE;
  }

  public void run() {
    clientManager.run();

    RUNNING = true;

    startUpdateThread();
  }

  public void close() throws Exception {
    clientManager.close();

    RUNNING = false;
  }

  public void startUpdateThread() {
    gameLoopThread = new Thread(() -> {
      long lastTime = System.nanoTime();
      while (RUNNING) {
        float delta = ((System.nanoTime() - lastTime) / 1_000_000_000f);
        lastTime = System.nanoTime();
        level.update(delta);

        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "Server Update Loop");

    gameLoopThread.start();
  }

  public ServerManager getClientManager() {
    return clientManager;
  }

  public void log(Object obj) {
    System.out.println("[Server] " + obj.toString());
  }
}
