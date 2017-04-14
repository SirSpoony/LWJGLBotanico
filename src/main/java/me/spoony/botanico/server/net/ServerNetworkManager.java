package me.spoony.botanico.server.net;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.Set;
import java.util.function.Predicate;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.net.server.SPacketHeartbeatMarco;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.server.BotanicoServer;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 4/14/2017.
 */
public class ServerNetworkManager implements ServerManager {

  public static final int DEFAULT_PORT = 7214;

  public BotanicoServer server;

  protected BiMap<RemoteEntityPlayer, Channel> players = HashBiMap.create();
  protected Set<Channel> preChannels = Sets.newHashSet();
  protected Timer heartbeatTimer;

  public ServerPacketHandler packetHandler;

  protected EventLoopGroup boss;
  protected EventLoopGroup worker;

  public ServerNetworkManager(BotanicoServer server) {
    this.server = server;

    packetHandler = new ServerPacketHandler(server);
    heartbeatTimer = new Timer(8f);
  }

  @Override
  public void run() {
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

    b.bind(DEFAULT_PORT);

    Thread heartbeat = new Thread(() -> {
      while (server.RUNNING) {
        sendPacketToAll(new SPacketHeartbeatMarco());

        try {
          Thread.sleep(15_000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "heartbeat");

    heartbeat.start();
  }

  @Override
  public void close() {
    boss.shutdownGracefully();
    worker.shutdownGracefully();
  }

  @Override
  public void sendPacketToAll(Packet packet) {
    for (RemoteEntityPlayer rc : getPlayers()) {
      sendPacket(packet, rc);
    }
  }

  @Override
  public void sendPacketToAll(Packet packet, Predicate<RemoteEntityPlayer> predicate) {
    for (RemoteEntityPlayer rc : getPlayers()) {
      if (predicate.test(rc)) {
        sendPacket(packet, rc);
      }
    }
  }

  @Override
  public void sendPacket(Packet packet, RemoteEntityPlayer player) {
    sendPacket(packet, players.get(player));
  }

  public void sendPacket(Packet packet, Channel channel) {
    channel.writeAndFlush(packet);
  }

  @Override
  public void receivePacket(Packet packet, RemoteEntityPlayer client) {
    ((IServerHandler) packet).onReceive(server, client);
  }

  @Override
  public ServerPacketHandler getPacketHandler() {
    return packetHandler;
  }

  @Override
  public Set<RemoteEntityPlayer> getPlayers() {
    return players.keySet();
  }

  public void initializePlayer(Channel channel, RemoteEntityPlayer player) {
    if (!preChannels.contains(channel)) {
      throw new RuntimeException("Unknown channel. Cannot create player.");
    } else {
      preChannels.remove(channel);
    }

    players.put(player, channel);
  }
}
