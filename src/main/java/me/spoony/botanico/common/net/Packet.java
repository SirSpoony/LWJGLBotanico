package me.spoony.botanico.common.net;

import java.io.Serializable;

/**
 * Created by Colten on 11/19/2016.
 */
public interface Packet extends Serializable {

  void encode(PacketEncoder encoder);

  void decode(PacketDecoder decoder);
}
