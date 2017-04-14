package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 12/16/2016.
 */
public class SPacketBuildingData extends AutoPacketAdapter implements IClientHandler {
    public long x;
    public long y;
    public byte data;


    @Override
    public void onReceive(BotanicoClient client) {
        client.getLocalLevel().receiveBuildingDataUpdate(new TilePosition(x, y), data);
    }
}
