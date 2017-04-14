package me.spoony.botanico.server.net;

import io.netty.channel.ChannelFuture;
import java.util.List;
import java.util.function.Predicate;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.server.RemoteClient;

/**
 * Created by Colten on 4/14/2017.
 */
public interface ServerManager {

  void run();

  void close();

  public void sendPacketToAll(Packet packet);

  public void sendPacketToAll(Packet packet, Predicate<RemoteClient> predicate);

  public List<RemoteClient> getRemoteClients();

  void receivePacket(Packet packet, RemoteClient client);

  ServerPacketHandler getPacketHandler();
}
