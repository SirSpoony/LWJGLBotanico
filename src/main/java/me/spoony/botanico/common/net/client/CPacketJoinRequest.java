package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.server.SPacketNewEntity;
import me.spoony.botanico.common.net.server.SPacketPlayerEID;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.BotanicoServer;

/**
 * Created by Colten on 11/20/2016.
 */
public class CPacketJoinRequest extends AutoPacketAdapter {

  public String name;

  // this is handled in BotanicoServerChannelInitializer
}
