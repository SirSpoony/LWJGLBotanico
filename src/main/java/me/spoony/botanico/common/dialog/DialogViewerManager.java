package me.spoony.botanico.common.dialog;

import com.google.common.collect.Sets;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.server.RemoteEntityPlayer;

import java.util.Set;

/**
 * Created by Colten on 12/4/2016.
 */
public class DialogViewerManager
{
    public Set<EntityPlayer> viewers;
    protected Dialog dialog;

    public DialogViewerManager(Dialog dialog) {
        viewers = Sets.newHashSet();
        this.dialog = dialog;
    }

    public void addViewer(EntityPlayer player) {
        viewers.add(player);
    }

    public void removeViewer(EntityPlayer player)
    {
        viewers.remove(player);
    }

    public void closeDialogAll() {
        for (EntityPlayer viewer : viewers) {
            if (viewer instanceof RemoteEntityPlayer) {
                ((RemoteEntityPlayer) viewer).closeDialog();
            }
        }
    }

    public void updateDialogAll() {
        for (EntityPlayer viewer : viewers) {
            dialog.updateViewer(viewer);
        }
    }
}
