package me.spoony.botanico.server.integrated;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import me.spoony.botanico.client.IntegratedBotanicoClient;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.net.server.SPacketPlayerEID;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.ServerManager;
import me.spoony.botanico.server.net.ServerPacketHandler;
import org.lwjgl.system.CallbackI.P;

/**
 * Created by Colten on 4/14/2017.
 */
public class IntegratedPlayerManager implements ServerManager {
  public IntegratedBotanicoServer server;
  public ServerPacketHandler packetHandler;

  public RemoteEntityPlayer player;

  public IntegratedBotanicoClient client;

  public IntegratedPlayerManager(IntegratedBotanicoServer server) {
    this.server = server;
    this.client = server.client;

    this.packetHandler = new ServerPacketHandler(server);
  }

  @Override
  public void start() {
    player = new RemoteEntityPlayer("{self}", server.level.getOverworld());
    player.getPlane().addEntity(player);

    SPacketPlayerEID peid = new SPacketPlayerEID();
    peid.eid = player.eid;
    sendPacketToAll(peid);
  }

  @Override
  public void stop() {
    player.getPlane().removeEntity(player);
  }

  @Override
  public void sendPacketToAll(Packet packet) {
    client.receivePacket(packet);
  }

  @Override
  public void sendPacketToAll(Packet packet, Predicate<RemoteEntityPlayer> predicate) {
    if (predicate.test(player)) {
      client.receivePacket(packet);
    }
  }

  @Override
  public void sendPacket(Packet packet, RemoteEntityPlayer player) {
    if (player == this.player) {
      client.receivePacket(packet);
    }
  }

  @Override
  public void receivePacket(Packet packet, RemoteEntityPlayer client) {
    ((IServerHandler) packet).onReceive(server, client);
  }

  @Override
  public Set<RemoteEntityPlayer> getPlayers() {
    return Sets.newHashSet(player);
  }

  @Override
  public ServerPacketHandler getPacketHandler() {
    return this.packetHandler;
  }
}
