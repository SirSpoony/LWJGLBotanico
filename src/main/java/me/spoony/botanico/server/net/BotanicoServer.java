package me.spoony.botanico.server.net;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.spoony.botanico.common.net.*;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.server.*;
import me.spoony.botanico.server.level.ServerLevel;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Colten on 11/18/2016.
 **/
public class BotanicoServer {

  public static final int DEFAULT_PORT = 7214;

  private static BotanicoServer CURRENT_INSTANCE;
  public static boolean RUNNING;

  public static List<RemoteClient> remoteClients = Lists.newArrayList();
  public ServerLevel level;
  public ServerPacketHandler packetHandler;
  protected EventLoopGroup boss;
  protected EventLoopGroup worker;
  protected Thread gameLoopThread;

  protected Timer heartbeatTimer;

  public BotanicoServer() {
    packetHandler = new ServerPacketHandler(this);
    level = new ServerLevel(this);
    heartbeatTimer = new Timer(8f);

    BotanicoServer.CURRENT_INSTANCE = this;
  }

  public static BotanicoServer getCurrentInstance() {
    return BotanicoServer.CURRENT_INSTANCE;
  }

  public ChannelFuture run() {
    boss = new NioEventLoopGroup(0, r -> {
      return new Thread(r, "Server Boss EventLoopGroup");
    });
    worker = new NioEventLoopGroup(0, r -> {
      return new Thread(r, "Server Worker EventLoopGroup");
    });

    ServerBootstrap b = new ServerBootstrap();
    b.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new BotanicoServerChannelInitializer(this));

    ChannelFuture channelFuture = b.bind(DEFAULT_PORT);
    RUNNING = true;

    startUpdateThread();

    return channelFuture;
  }

  public void close() throws Exception {
    RUNNING = false;

    boss.shutdownGracefully();
    worker.shutdownGracefully();
  }

  public void startUpdateThread() {
    gameLoopThread = new Thread(() -> {
      long lastTime = System.nanoTime();
      while (RUNNING) {
        float delta = ((System.nanoTime() - lastTime) / 1_000_000_000f);
        lastTime = System.nanoTime();
        level.update(delta);

        if (heartbeatTimer.step(delta)) {
          for (RemoteClient rc : remoteClients) {
            rc.sendPacket(new SPacketHeartbeatMarco());
          }
        }

        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "Server Update Loop");

    gameLoopThread.start();
  }

  public void sendPacketToAll(Packet packet) {
    for (RemoteClient rc : remoteClients) {
      rc.sendPacket(packet);
    }
  }

  public void sendPacketToAll(Packet packet, Predicate<RemoteClient> predicate) {
    for (RemoteClient rc : remoteClients) {
      if (predicate.test(rc)) {
        rc.sendPacket(packet);
      }
    }
  }

  public void log(Object obj) {
    System.out.println("[Server] " + obj.toString());
  }

  public void sendConsoleCommand(String command) {
    if (command.equalsIgnoreCase("players")) {
      System.out.println("==== Players ====");
      for (RemoteClient rc : remoteClients) {
        if (rc.getPlayer() != null) {
          RemoteEntityPlayer ep = rc.getPlayer();
          System.out.println(ep.name + ": " + ep.getPosition().toFriendlyString());
        }
      }
      System.out.println("=================");
    } else if (command.equalsIgnoreCase("stop")) {
      try {
        System.out.println("Closing...");
        close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }/* else if (command.equalsIgnoreCase("testnbt")) {
            System.out.println("testing nbt");
            NBTTag tag = level.writeToNBT();
            level.readFromNBT(tag);
        } else if (command.equalsIgnoreCase("writenbt")) {
            System.out.println("writing nbt");

            File file = new File("level.bdat");
            System.out.println(file.getAbsolutePath());
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                NBTOutputStream nbtOutputStream = new NBTOutputStream(outputStream);
                nbtOutputStream.writeTag(level.writeToNBT());
                nbtOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equalsIgnoreCase("readnbt")) {
            System.out.println("reading nbt");

            File file = new File("level.bdat");
            System.out.println(file.getAbsolutePath());
            try {
                FileInputStream inputStream = new FileInputStream(file);
                NBTInputStream nbtInputStream = new NBTInputStream(inputStream);
                level.readFromNBT(nbtInputStream.readTag());
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/ else {
      System.out.println("That is an unknown command!");
    }
  }
}
