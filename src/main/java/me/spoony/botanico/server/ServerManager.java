package me.spoony.botanico.server;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.ServerPacketHandler;

/**
 * Created by Colten on 4/14/2017.
 */
public interface ServerManager {

  void start();

  void stop();

  void sendPacketToAll(Packet packet);

  void sendPacketToAll(Packet packet, Predicate<RemoteEntityPlayer> predicate);

  Set<RemoteEntityPlayer> getPlayers();

  void sendPacket(Packet packet, RemoteEntityPlayer player);

  void receivePacket(Packet packet, RemoteEntityPlayer client);

  ServerPacketHandler getPacketHandler();
}
