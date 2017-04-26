package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 12/16/2016.
 */
public class SPacketBuildingData extends AutoPacketAdapter implements IClientHandler {
    public long x;
    public long y;
    public int data;

    @Override
    public void onReceive(BotanicoClient client) {
        client.getLocalLevel().receiveBuildingDataUpdate(new OmniPosition(PositionType.GAME, x, y), data);
    }
}
