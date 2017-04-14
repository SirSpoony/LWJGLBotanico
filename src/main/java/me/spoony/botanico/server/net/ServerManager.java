package me.spoony.botanico.server.net;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 4/14/2017.
 */
public interface ServerManager {

  void run();

  void close();

  void sendPacketToAll(Packet packet);

  void sendPacketToAll(Packet packet, Predicate<RemoteEntityPlayer> predicate);

  Set<RemoteEntityPlayer> getPlayers();

  void sendPacket(Packet packet, RemoteEntityPlayer player);

  void receivePacket(Packet packet, RemoteEntityPlayer client);

  ServerPacketHandler getPacketHandler();
}
