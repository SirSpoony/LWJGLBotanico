package me.spoony.botanico.server;

import me.spoony.botanico.server.level.ServerLevel;
import me.spoony.botanico.server.net.ServerManager;
import me.spoony.botanico.server.net.ServerNetworkManager;

/**
 * Created by Colten on 11/18/2016.
 **/
public class BotanicoServer {

  public static boolean RUNNING;

  public ServerLevel level;
  protected Thread gameLoopThread;

  protected ServerManager clientManager;

  public BotanicoServer() {
    clientManager = new ServerNetworkManager(this);

    level = new ServerLevel(this);
  }

  public void start() {
    clientManager.start();

    RUNNING = true;

    startUpdateThread();
  }

  public void stop() {
    clientManager.stop();

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
