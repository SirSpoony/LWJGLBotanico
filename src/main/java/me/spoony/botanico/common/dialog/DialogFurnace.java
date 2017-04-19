package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.net.IPacketInterpreter;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogFurnace extends Dialog implements IPacketInterpreter {

  public static final int INGREDIENT_SLOT_ID = 0;
  public static final int FUEL_SLOT_ID = 1;
  public static final int PRODUCT_SLOT_ID = 2;

  public float progress;
  public float burnProgress;

  public float initialBurnTime;
  public float currentBurnTime;

  public DialogFurnace() {
    super(Dialog.FURNACE_ID);
    this.inventory = new Inventory(3, Dialog.FURNACE_ID);
    this.inventory.getSlot(2).setMode(ItemSlotMode.TAKE_ONLY);
  }

  @Override
  public void encodeToPacket(PacketEncoder encoder) {
    encoder.writeFloat(progress);
    encoder.writeFloat(burnProgress);
  }

  @Override
  public void decodeFromPacket(PacketDecoder decoder) {
    progress = decoder.readFloat();
    burnProgress = decoder.readFloat();
  }
}
