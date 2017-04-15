package me.spoony.botanico.server.integrated;

import me.spoony.botanico.client.IntegratedBotanicoClient;
import me.spoony.botanico.server.BotanicoServer;

/**
 * Created by Colten on 4/14/2017.
 */
public class IntegratedBotanicoServer extends BotanicoServer {

  public IntegratedBotanicoClient client;

  public IntegratedBotanicoServer(IntegratedBotanicoClient client) {
    super();

    this.client = client;
    this.clientManager = new IntegratedPlayerManager(this);
  }

  @Override
  public void log(Object obj) {
    System.out.println("[IntegratedServer] " + obj.toString());
  }
}
