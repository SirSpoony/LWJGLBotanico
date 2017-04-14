package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 11/25/2016.
 */
public class SPacketChangeDialog extends AutoPacketAdapter implements IClientHandler
{
    public static final byte OPEN_DIALOG = 0;
    public static final byte CLOSE_DIALOG = 1;

    public int dialogID;
    public byte operation;

    public SPacketChangeDialog setOperation(byte operation) {
        this.operation = operation;
        return this;
    }

    @Override
    public void onReceive(BotanicoClient client) {
        if (operation == SPacketChangeDialog.OPEN_DIALOG) {
            Dialog dialog = Dialog.fromID(dialogID);
            dialog.onOpen(client.getLocalPlayer());
            GameView.get().openDialog(dialog);
        } else if (operation == SPacketChangeDialog.CLOSE_DIALOG) {
            if (dialogID == GameView.get().getDialog().id){
                GameView.getPlayer().closeCurrentDialog();
            }
        }
    }
}
