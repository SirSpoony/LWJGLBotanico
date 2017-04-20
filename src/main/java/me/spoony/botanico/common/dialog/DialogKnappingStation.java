package me.spoony.botanico.common.dialog;

import com.google.common.collect.Range;
import me.spoony.botanico.common.items.*;
import me.spoony.botanico.common.net.IPacketInterpreter;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;

/**
 * Created by Colten on 11/27/2016.
 */
public class DialogKnappingStation extends Dialog {

  public static final byte BLADE = 0;
  public static final byte HOE = 1;
  public static final byte PICKAXE = 2;
  public static final byte AXE = 3;

  public DialogKnappingStation() {
    super(Dialog.KNAPPING_STATION_ID);
    this.inventory = new Inventory(2, Dialog.KNAPPING_STATION_ID);
    this.inventory.getSlot(1).setMode(ItemSlotMode.TAKE_ONLY);
  }
}
