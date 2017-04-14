package me.spoony.botanico.common.nbt;

/*
 * JNBT License
 * 
 * Copyright (c) 2010 Graham Edgecombe
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *       
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *       
 *     * Neither the name of the JNBT team nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */

import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The <code>TAG_Compound</code> tag.
 *
 * @author Graham Edgecombe
 */
public final class NBTCompoundTag extends NBTTag {

    /**
     * The value.
     */
    private final List<NBTTag> value;

    /**
     * Creates the tag.
     *
     * @param name  The name.
     * @param value The value.
     */
    public NBTCompoundTag(String name, List<NBTTag> value) {
        super(name);
        this.value = Lists.newArrayList(value);
    }

    public NBTCompoundTag(String name, NBTTag... value) {
        super(name);
        this.value = Lists.newArrayList(value);
    }

    @Override
    public List<NBTTag> getValue() {
        return value;
    }

    public NBTTag get(String name) {
        for (NBTTag tag:value) {
            if (tag.getName().equals(name)) return tag;
        }
        return null;
    }

    @Override
    public String toString() {
        String name = getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        StringBuilder bldr = new StringBuilder();
        bldr.append("TAG_Compound" + append + ": " + value.size() + " entries\r\n{\r\n");
        for (NBTTag tag: value) {
            bldr.append("   " + tag.toString().replaceAll("\r\n", "\r\n   ") + "\r\n");
        }
        bldr.append("}");
        return bldr.toString();
    }

    public NBTCompoundTag setByte(String name, byte val) {
        value.add(new NBTByteTag(name, val));
        return this;
    }

    public NBTCompoundTag setInt(String name, int val) {
        value.add(new NBTIntTag(name, val));
        return this;
    }

    public NBTCompoundTag setString(String name, String val) {
        value.add(new NBTStringTag(name, val));
        return this;
    }

    public NBTCompoundTag setByteArray(String name, byte[] val) {
        value.add(new NBTByteArrayTag(name, val));
        return this;
    }

    public NBTCompoundTag setIntArray(String name, int[] val) {
        ByteBuffer bbuf = ByteBuffer.allocate(val.length*Integer.BYTES).order(ByteOrder.BIG_ENDIAN);
        IntBuffer ibuf = bbuf.asIntBuffer();
        ibuf.put(val);
        value.add(new NBTByteArrayTag(name, bbuf.array()));
        return this;
    }

    public NBTCompoundTag setLong(String name, long val) {
        value.add(new NBTLongTag(name, val));
        return this;
    }

    public NBTCompoundTag setList(String name, Class<? extends NBTTag> clazz, List<NBTTag> val) {
        value.add(new NBTListTag(name, clazz, val));
        return this;
    }

    public NBTCompoundTag setFloat(String name, float val) {
        value.add(new NBTFloatTag(name, val));
        return this;
    }

    public NBTCompoundTag setDouble(String name, double val) {
        value.add(new NBTDoubleTag(name, val));
        return this;
    }

    public NBTCompoundTag setShort(String name, short val) {
        value.add(new NBTShortTag(name, val));
        return this;
    }

    public NBTCompoundTag setCompound(NBTCompoundTag val) {
        value.add(val);
        return this;
    }
}
