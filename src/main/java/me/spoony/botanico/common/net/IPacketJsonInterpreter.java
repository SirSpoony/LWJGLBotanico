package me.spoony.botanico.common.net;

import com.google.gson.Gson;

/**
 * Created by Colten on 12/30/2016.
 */
public interface IPacketJsonInterpreter {
    String toJson(Gson gson);
    void fromJson(Gson gson, String json);
}
