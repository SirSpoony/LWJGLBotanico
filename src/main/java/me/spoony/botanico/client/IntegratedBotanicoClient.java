package me.spoony.botanico.client;

import io.netty.channel.ChannelFuture;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.net.client.CPacketJoinRequest;
import me.spoony.botanico.server.integrated.IntegratedBotanicoServer;

/**
 * Created by Colten on 4/14/2017.
 */
public class IntegratedBotanicoClient extends BotanicoClient {
  private IntegratedBotanicoServer server;

  public IntegratedBotanicoClient(GameView gameView) {
    super(gameView, null);

    server = new IntegratedBotanicoServer(this);
  }

  @Override
  public void start() {
    server.start();
  }

  @Override
  public void stop() {
    server.stop();
  }

  @Override
  public void receivePacket(Packet packet) {
    super.receivePacket(packet);

//    System.out.println("[client] received packet "+packet.getClass().getSimpleName());
  }

  @Override
  public void sendPacket(Packet packet) {
    server.getClientManager().receivePacket(packet, server.getClientManager().getPlayers().iterator().next());
  }
}
