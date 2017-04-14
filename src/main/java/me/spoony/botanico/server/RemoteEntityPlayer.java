package me.spoony.botanico.server;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import me.spoony.botanico.common.util.position.ChunkPosition;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.*;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.net.server.SPacketMessage;
import me.spoony.botanico.common.net.server.SPacketChangeDialog;
import me.spoony.botanico.common.net.server.SPacketDialogData;
import me.spoony.botanico.common.net.server.SPacketSlot;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.server.net.BotanicoServer;

import java.util.Set;

/**
 * Created by Colten on 11/20/2016.
 */
public class RemoteEntityPlayer extends EntityPlayer {
    public final RemoteClient remoteClient;
    public final Set<Chunk> commonChunks;

    protected final Timer chunkUpdateTimer;

    public final Inventory inventory;

    public Dialog currentDialog;

    public String name;

    public RemoteEntityPlayer(final RemoteClient remoteClient, String name, ServerPlane plane) {
        super(plane);

        this.remoteClient = remoteClient;
        this.remoteClient.setPlayer(this);

        this.inventory = new Inventory(EntityPlayer.INVENTORY_SIZE, Dialog.PLAYER_INV_ID);
        this.inventory.getSlot(EntityPlayer.SLOT_PICKAXE).setRestriction(ItemSlotRestriction.PICKAXE);
        this.inventory.getSlot(EntityPlayer.SLOT_HOE).setRestriction(ItemSlotRestriction.HOE);
        this.inventory.getSlot(EntityPlayer.SLOT_AXE).setRestriction(ItemSlotRestriction.AXE);
        this.inventory.getSlot(EntityPlayer.SLOT_SWORD).setRestriction(ItemSlotRestriction.BLADE);
        this.inventory.getSlot(EntityPlayer.SLOT_RING1).setRestriction(ItemSlotRestriction.RING);
        this.inventory.getSlot(EntityPlayer.SLOT_RING2).setRestriction(ItemSlotRestriction.RING);
        this.currentDialog = null;

        this.commonChunks = Sets.newConcurrentHashSet();

        this.chunkUpdateTimer = new Timer(1f);

        this.name = name;
    }

    @Override
    public void update(float delta, IPlane level) {
        super.update(delta, level);

        if (chunkUpdateTimer.step(delta)) {
            updateCommonChunks();
        }
    }

    public void updateCommonChunks() {
        Set<Chunk> shouldBeCommonChunks = Sets.newHashSet();

        for (int ox = -1; ox <= 1; ox++) {
            for (int oy = -1; oy <= 1; oy++) {
                ChunkPosition position = new ChunkPosition(new GamePosition(this.position).add(ox * 32, oy * 32));
                shouldBeCommonChunks.add(plane.getChunk(position));
            }
        }

        Set<Chunk> chunksToUpdate = Sets.difference(shouldBeCommonChunks, commonChunks);

        for (Chunk c : chunksToUpdate) {
            BotanicoServer.getCurrentInstance().getClientManager().getPacketHandler().sendChunk(c, this);
            commonChunks.add(c);
        }
    }

    public void forceUpdateCommonChunks() {
        commonChunks.clear();
        updateCommonChunks();
    }

    public void updatePlayerInventorySlot(int... slot) {
        for (int i : slot) {
            if (i == -1) {
                continue;
            }
            SPacketSlot pps = new SPacketSlot();
            pps.dialogID = Dialog.PLAYER_INV_ID;
            pps.slotpos = i;
            pps.stack = inventory.getStack(i);
            remoteClient.sendPacket(pps);
        }
    }

    public void updatePlayerInventory() {
        for (int i = 0; i < inventory.getLength(); i++) {
            SPacketSlot pps = new SPacketSlot();
            pps.dialogID = Dialog.PLAYER_INV_ID;
            pps.slotpos = i;
            pps.stack = inventory.getStack(i);
            remoteClient.sendPacket(pps);
        }
    }

    public void updateDialogInventory() {
        Preconditions.checkState(currentDialog != null, "There is no dialog being displayed");
        for (int i = 0; i < currentDialog.inventory.getLength(); i++) {
            SPacketSlot pps = new SPacketSlot();
            pps.dialogID = this.currentDialog.id;
            pps.slotpos = i;
            pps.stack = this.currentDialog.inventory.getStack(i);
            remoteClient.sendPacket(pps);
        }
    }

    public void updateQueuedPlayerInventory() {
        int[] slotsToUpdate = inventory.getSlotsToUpdate();
        //System.out.println(Arrays.toString(slotsToUpdate));

        for (int i = 0; i < slotsToUpdate.length; i++) {
            ItemSlot slot = inventory.getSlot(slotsToUpdate[i]);
            updatePlayerInventorySlot(slot.getSlotIndex());
            slot.needsSync = false;
        }
        //System.out.println(Arrays.toString(inventory.getSlotsToUpdate()));
    }

    public void openDialog(Dialog dialog) {
        this.currentDialog = dialog;
        dialog.onOpen(this);

        SPacketChangeDialog pd = new SPacketChangeDialog();
        pd.dialogID = dialog.id;
        pd.operation = SPacketChangeDialog.OPEN_DIALOG;
        remoteClient.sendPacket(pd);

        dialog.viewers.updateDialogAll();
        updatePlayerInventory();
    }

    public void closeDialog() {
        currentDialog.onClose(this);
        SPacketChangeDialog pd = new SPacketChangeDialog();
        pd.dialogID = currentDialog.id;
        pd.operation = SPacketChangeDialog.CLOSE_DIALOG;
        remoteClient.sendPacket(pd);
    }

    public void updateDialogData() {
        if (currentDialog == null) {
            throw new RuntimeException("Attempted updateDialogData() without a dialog!");
        }
        SPacketDialogData dd = new SPacketDialogData();
        dd.dialog = this.currentDialog;
        remoteClient.sendPacket(dd);
    }

    public ItemStack giveItemStack(ItemStack stack, boolean ordrop) {
        if (ordrop) {
            ItemStack retstack = giveItemStack(stack, false);
            if (retstack != null) {
                ((ServerPlane)plane).dropItemStack(new GamePosition(position.x + .5d, position.y), retstack);
            }
            return null;
        } else {
            ItemStack retstack = inventory.addItem(stack, Range.closed(0, 41));
            updateQueuedPlayerInventory();
            if (retstack != null) {
                return stack;
            } else {
                return null;
            }
        }
    }

    public ItemSlot getHeldSlot() {
        return inventory.getSlot(SLOT_CURSOR);
    }

    public void giveItemStack(ItemStack stack) {
        giveItemStack(stack, true);
    }

    public void onJoin() {
        System.out.println("Player [" + name + "] joined the game!");

/*        this.inventory.addItem(new ItemStack(Items.KNAPPING_STATION));
        this.inventory.addItem(new ItemStack(Items.TOOL_STATION));
        this.inventory.addItem(new ItemStack(Items.ROCK, 50));
        this.inventory.addItem(new ItemStack(Items.WOOD, 50));*/

/*        this.inventory.addItem(new ItemStack(Items.ROCK_HOE));
        this.inventory.addItem(new ItemStack(Items.WHEAT_SEEDS, 10));
        this.inventory.addItem(new ItemStack(Items.ROCK_PICKAXE));
        this.inventory.addItem(new ItemStack(Items.ENERGY_PIPE, 50));
        this.inventory.addItem(new ItemStack(Items.FLUID_PIPE, 50));
        this.inventory.addItem(new ItemStack(Items.ROPE, 5));*/

/*        this.inventory.addItem(new ItemStack(Items.JAR, 10));
        this.inventory.addItem(new ItemStack(Items.MYSTIC_FLOWER, 9));
        this.inventory.addItem(new ItemStack(Items.BUCKET, 1));*/


        updateQueuedPlayerInventory();
    }

    public void onLeave() {
        System.out.println("Player [" + name + "] left the game!");

        if (currentDialog != null) {
            closeDialog();
        }
    }

    public ItemSlot getCursor() {
        return inventory.getSlot(EntityPlayer.SLOT_CURSOR);
    }

    public void sendMessage(String message) {
        remoteClient.sendPacket(new SPacketMessage(message));
    }

    @Override
    public ServerPlane getPlane() {
        return (ServerPlane)super.getPlane();
    }

    public void teleport(GamePosition position, ServerPlane plane) {
        if (plane != this.getPlane()) {
            getPlane().removeEntity(this);
            this.plane = plane;
            plane.addEntity(this);

            this.forceUpdateCommonChunks();
        }
        this.setPosition(position);
        System.out.println(position);
        BotanicoServer.getCurrentInstance().getClientManager().getPacketHandler().sendTeleport(this);
    }
}
