package me.spoony.botanico.common.net;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by Colten on 12/7/2016.
 */
public class AutoPacketAdapter implements Packet {

  public AutoPacketAdapter() {
  }

  public void preEncode() {

  }

  @Override
  public void encode(PacketEncoder encoder) {
    preEncode();
    for (Field field : transferableFields()) {
      try {
        encodeField(encoder, field);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  protected void encodeField(PacketEncoder encoder, Field field) throws IllegalAccessException {
    Object o = field.get(this);
    if (o instanceof Integer) {
      encoder.writeInt((Integer) o);
    } else if (o instanceof Byte) {
      encoder.writeByte((Byte) o);
    } else if (o instanceof Float) {
      encoder.writeFloat((Float) o);
    } else if (o instanceof String) {
      encoder.writeString((String) o);
    } else if (o instanceof int[]) {
      encoder.writeIntArray((int[]) o);
    } else if (o instanceof Short) {
      encoder.writeShort((Short) o);
    } else if (o instanceof Double) {
      encoder.writeDouble((Double) o);
    } else if (o instanceof Long) {
      encoder.writeLong((Long) o);
    }
  }

  @Override
  public void decode(PacketDecoder decoder) {
    for (Field field : transferableFields()) {
      try {
        decodeField(decoder, field);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    postDecode();
  }

  protected void decodeField(PacketDecoder decoder, Field field) throws IllegalAccessException {
    Class<?> type = field.getType();
    if (type == int.class) {
      field.set(this, decoder.readInt());
    } else if (type == byte.class) {
      field.set(this, decoder.readByte());
    } else if (type == float.class) {
      field.set(this, decoder.readFloat());
    } else if (type == String.class) {
      field.set(this, decoder.readString());
    } else if (type == int[].class) {
      field.set(this, decoder.readIntArray());
    } else if (type == short.class) {
      field.set(this, decoder.readShort());
    } else if (type == double.class) {
      field.set(this, decoder.readDouble());
    } else if (type == long.class) {
      field.set(this, decoder.readLong());
    }
  }

  public void postDecode() {

  }

  protected Field[] transferableFields() {
    List<Field> ret = Lists.newArrayList();
    Class clazz = this.getClass();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      int mods = field.getModifiers();
      if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
        continue;
      }
      if (field.getAnnotation(NotTransferable.class) != null) {
        continue;
      }
      if (!field.isAccessible()) {
        field.setAccessible(true);
      }
      ret.add(field);
    }
    return ret.toArray(new Field[ret.size()]);
  }

  public void log(Object toLog) {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    System.out.println(
        "[" + this.getClass().getSimpleName() + " " + stackTraceElements[2].getMethodName() + "] "
            + toLog);
  }
}
