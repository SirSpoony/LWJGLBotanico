package me.spoony.botanico.server.level;

import me.spoony.botanico.common.level.ILevel;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 1/1/2017.
 */
public class ServerLevel implements ILevel {
    BotanicoServer server;

    final ServerPlane overworld;
    final ServerPlaneUnderworld underworld;

    public ServerLevel(BotanicoServer server) {
        this.server = server;

        overworld = new ServerPlane(server, this, 0);
        underworld = new ServerPlaneUnderworld(server, this, 0);
    }

    public void update(float delta) {
        overworld.update(delta);
        underworld.update(delta);
    }

    public ServerPlane getOverworld() {
        return overworld;
    }

    public ServerPlaneUnderworld getUnderworld() {
        return underworld;
    }
}
