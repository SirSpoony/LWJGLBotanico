package me.spoony.botanico.server.level;

import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.server.level.levelgen.ChunkGeneratorOverworld;
import me.spoony.botanico.server.level.levelgen.ChunkGeneratorUnderworld;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 1/1/2017.
 */
public class ServerPlaneUnderworld extends ServerPlane {
    public ServerPlaneUnderworld(BotanicoServer server, ServerLevel level, long seed) {
        super(server, level, seed);

        this.chunkGenerator = new ChunkGeneratorUnderworld(seed);
    }

    @Override
    public int getID() {
        return IPlane.UNDERWORLD;
    }
}
