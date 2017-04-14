package me.spoony.botanico.server.net;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.server.RemoteClient;

/**
 * Created by Colten on 4/14/2017.
 */
public class ServerNetworkManager implements ServerManager {

  public static final int DEFAULT_PORT = 7214;

  public BotanicoServer server;

  protected List<RemoteClient> remoteClients = Lists.newArrayList();
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

    ChannelFuture channelFuture = b.bind(DEFAULT_PORT);
  }

  @Override
  public void close() {
    boss.shutdownGracefully();
    worker.shutdownGracefully();
  }

  @Override
  public void sendPacketToAll(Packet packet) {
    for (RemoteClient rc : remoteClients) {
      rc.sendPacket(packet);
    }
  }

  @Override
  public void sendPacketToAll(Packet packet, Predicate<RemoteClient> predicate) {
    for (RemoteClient rc : remoteClients) {
      if (predicate.test(rc)) {
        rc.sendPacket(packet);
      }
    }
  }

  @Override
  public void receivePacket(Packet packet, RemoteClient client) {
    ((IServerHandler) packet).onReceive(server, client);
  }

  @Override
  public ServerPacketHandler getPacketHandler() {
    return packetHandler;
  }

  @Override
  public List<RemoteClient> getRemoteClients() {
    return remoteClients;
  }
}
