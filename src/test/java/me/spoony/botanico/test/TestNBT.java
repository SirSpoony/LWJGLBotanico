package me.spoony.botanico.test;

import com.google.common.collect.Maps;
import me.spoony.botanico.common.nbt.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Colten on 12/28/2016.
 */
public class TestNBT {
    public static void main(String[] args) throws IOException {
        System.out.println("Testing NBT");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        NBTOutputStream outputStream = new NBTOutputStream(stream);
        NBTTag name = new NBTStringTag("name", "pleb");
        NBTCompoundTag tag = new NBTCompoundTag("main", name);

        outputStream.writeTag(tag);

        outputStream.close();


        // now read it

        ByteArrayInputStream inputStream = new ByteArrayInputStream(stream.toByteArray());

        NBTInputStream nbtInputStream = new NBTInputStream(inputStream);
        List<NBTTag> tags = (List<NBTTag>)nbtInputStream.readTag().getValue();
        System.out.println(tags.get(0).getValue());
    }
}
