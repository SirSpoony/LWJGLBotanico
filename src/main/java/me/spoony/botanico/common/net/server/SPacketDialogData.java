package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.dialog.DialogKnappingStation;
import me.spoony.botanico.common.net.IPacketInterpreter;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;

/**
 * Created by Colten on 12/2/2016.
 */
public class SPacketDialogData implements Packet
{
    public Dialog dialog;

    @Override
    public void encode(PacketEncoder encoder) {
        if (dialog instanceof IPacketInterpreter)
        {
            ((IPacketInterpreter)dialog).encodeToPacket(encoder);
        }
    }

    @Override
    public void decode(PacketDecoder decoder) {
        dialog = GameView.get().getDialog();
        if (dialog instanceof IPacketInterpreter)
        {
            ((IPacketInterpreter)dialog).decodeFromPacket(decoder);
        }
    }
}