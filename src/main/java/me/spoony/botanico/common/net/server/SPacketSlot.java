package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.items.Item;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.net.NotTransferable;

/**
 * Created by Colten on 11/23/2016.
 */
public class SPacketSlot extends AutoPacketAdapter implements IClientHandler
{
    public int dialogID;
    public int slotpos;
    @NotTransferable
    public ItemStack stack;

    private int stackcount;
    private int stackid;

    @Override
    public void preEncode()
    {
        if (stack == null) {
            stackcount = 0;
            stackid = 0;
            return;
        } else {
            stackcount = stack.getCount();
            stackid = stack.getItem().getID();
            return;
        }
    }

    @Override
    public void postDecode()
    {
        if (stackcount == 0) {
            stack = null;
        } else {
            Item item = Item.REGISTRY.get(stackid);
            this.stack = new ItemStack(item, stackcount);
        }
    }

    @Override
    public void onReceive(BotanicoClient client) {
        if (dialogID == Dialog.PLAYER_INV_ID) {
            client.getLocalPlayer().inventory.setStack(slotpos, stack);
        } else {
            client.gameView.getDialog().inventory.setStack(slotpos, stack);
        }
    }
}
