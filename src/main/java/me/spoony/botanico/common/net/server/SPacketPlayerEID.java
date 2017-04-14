package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 11/20/2016.
 */
public class SPacketPlayerEID extends AutoPacketAdapter implements IClientHandler {

  public int eid;

  @Override
  public void onReceive(BotanicoClient client) {
    client.getLocalLevel().LOCAL_PLAYER_EID = eid;
    client.getLocalLevel().setLocalEID();
  }
}
