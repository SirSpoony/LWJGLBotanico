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

/**
 * A class which contains NBT-related utility methods.
 * @author Graham Edgecombe
 *
 */
public final class NBTUtils {
	
	/**
	 * Gets the type name of a tag.
	 * @param clazz The tag class.
	 * @return The type name.
	 */
	public static String getTypeName(Class<? extends NBTTag> clazz) {
		if(clazz.equals(NBTByteArrayTag.class)) {
			return "TAG_Byte_Array";
		} else if(clazz.equals(NBTByteTag.class)) {
			return "TAG_Byte";
		} else if(clazz.equals(NBTCompoundTag.class)) {
			return "TAG_Compound";
		} else if(clazz.equals(NBTDoubleTag.class)) {
			return "TAG_Double";
		} else if(clazz.equals(NBTEndTag.class)) {
			return "TAG_End";
		} else if(clazz.equals(NBTFloatTag.class)) {
			return "TAG_Float";
		} else if(clazz.equals(NBTIntTag.class)) {
			return "TAG_Int";
		} else if(clazz.equals(NBTListTag.class)) {
			return "TAG_List";
		} else if(clazz.equals(NBTLongTag.class)) {
			return "TAG_Long";
		} else if(clazz.equals(NBTShortTag.class)) {
			return "TAG_Short";
		} else if(clazz.equals(NBTStringTag.class)) {
			return "TAG_String";
		} else {
			throw new IllegalArgumentException("Invalid tag classs (" + clazz.getName() + ").");
		}
	}
	
	/**
	 * Gets the type code of a tag class.
	 * @param clazz The tag class.
	 * @return The type code.
	 * @throws IllegalArgumentException if the tag class is invalid.
	 */
	public static int getTypeCode(Class<? extends NBTTag> clazz) {
		if(clazz.equals(NBTByteArrayTag.class)) {
			return NBTConstants.TYPE_BYTE_ARRAY;
		} else if(clazz.equals(NBTByteTag.class)) {
			return NBTConstants.TYPE_BYTE;
		} else if(clazz.equals(NBTCompoundTag.class)) {
			return NBTConstants.TYPE_COMPOUND;
		} else if(clazz.equals(NBTDoubleTag.class)) {
			return NBTConstants.TYPE_DOUBLE;
		} else if(clazz.equals(NBTEndTag.class)) {
			return NBTConstants.TYPE_END;
		} else if(clazz.equals(NBTFloatTag.class)) {
			return NBTConstants.TYPE_FLOAT;
		} else if(clazz.equals(NBTIntTag.class)) {
			return NBTConstants.TYPE_INT;
		} else if(clazz.equals(NBTListTag.class)) {
			return NBTConstants.TYPE_LIST;
		} else if(clazz.equals(NBTLongTag.class)) {
			return NBTConstants.TYPE_LONG;
		} else if(clazz.equals(NBTShortTag.class)) {
			return NBTConstants.TYPE_SHORT;
		} else if(clazz.equals(NBTStringTag.class)) {
			return NBTConstants.TYPE_STRING;
		} else {
			throw new IllegalArgumentException("Invalid tag classs (" + clazz.getName() + ").");
		}
	}
	
	/**
	 * Gets the class of a type of tag.
	 * @param type The type.
	 * @return The class.
	 * @throws IllegalArgumentException if the tag type is invalid.
	 */
	public static Class<? extends NBTTag> getTypeClass(int type) {
		switch(type) {
		case NBTConstants.TYPE_END:
			return NBTEndTag.class;
		case NBTConstants.TYPE_BYTE:
			return NBTByteTag.class;
		case NBTConstants.TYPE_SHORT:
			return NBTShortTag.class;
		case NBTConstants.TYPE_INT:
			return NBTIntTag.class;
		case NBTConstants.TYPE_LONG:
			return NBTLongTag.class;
		case NBTConstants.TYPE_FLOAT:
			return NBTFloatTag.class;
		case NBTConstants.TYPE_DOUBLE:
			return NBTDoubleTag.class;
		case NBTConstants.TYPE_BYTE_ARRAY:
			return NBTByteArrayTag.class;
		case NBTConstants.TYPE_STRING:
			return NBTStringTag.class;
		case NBTConstants.TYPE_LIST:
			return NBTListTag.class;
		case NBTConstants.TYPE_COMPOUND:
			return NBTCompoundTag.class;
		default:
			throw new IllegalArgumentException("Invalid tag type : " + type + ".");
		}
	}
	
	/**
	 * Default private constructor.
	 */
	private NBTUtils() {
		
	}

}
