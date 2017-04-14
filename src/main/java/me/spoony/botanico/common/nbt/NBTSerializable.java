package me.spoony.botanico.common.nbt;

/**
 * Created by Colten on 12/29/2016.
 */
public interface NBTSerializable {
    NBTTag writeData(String name);
    void readData(NBTTag data);
}
