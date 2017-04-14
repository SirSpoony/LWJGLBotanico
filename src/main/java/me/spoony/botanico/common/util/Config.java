package me.spoony.botanico.common.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Colten on 11/10/2016.
 */
public class Config
{
/*    public static final int CONFIG_VERSION = 1;
    protected static Map<String, String> settings;

    protected static Localization localization;

    protected static FileHandle configDir;
    protected static Gson gson;

    public static boolean init(String directory)
    {
        Preconditions.checkNotNull(directory);

        settings = Maps.newHashMap();

        configDir = Gdx.files.local(directory);
        //Preconditions.checkArgument(handle.isDirectory(), "Specified config directory is not a directory!");

        writeConfigDirectory(true); // TODO force overwrite

        FileHandle mainconfig = configDir.child("config.json");
        if (!mainconfig.exists())
        {
            writeConfigDirectory(true);
            return false;
        }

        gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject mainconfigJson = gson.fromJson(mainconfig.reader(), JsonObject.class);

        if (!mainconfigJson.has("version"))
        {
            writeConfigDirectory(true);
            return false;
        }
        int version = mainconfigJson.get("version").getAsInt();
        if (version != version)
        {
            writeConfigDirectory(true);
            return false;
        }

        if (!mainconfigJson.has("settings"))
        {
            writeConfigDirectory(true);
            return false;
        }
        Type settingsType = new TypeToken<Map<String, String>>()
        {
        }.getType();
        Map<String, String> settingsmap = gson.fromJson(mainconfigJson.get("settings"), settingsType);
        settings = settingsmap;

        //localization = new Localization(settings.get("lang"));
        loadLocalization(settings.get("lang"));

        return true;
    }

    protected static void writeConfigDirectory(boolean force)
    {
        Preconditions.checkState(configDir != null, "Config not initialized");

        if (configDir.exists() && !force) return;
        FileHandle internalDirectory = Gdx.files.internal("assets/config");

        configDir.mkdirs();

        internalDirectory.child("config.json").copyTo(configDir.child("config.json"));
        internalDirectory.child("local/en.json").copyTo(configDir.child("local/en.json"));
    }

    protected static void loadLocalization(String name)
    {
        FileHandle handle = configDir.child("local").child(name + ".json");
        Preconditions.checkState(handle.exists(), "Lang file " + handle.name() + " does not exist!");
        Type maptype = new TypeToken<Map<String, String>>()
        {
        }.getType();
        Map<String, String> map = gson.fromJson(handle.reader(), maptype);
        localization = new Localization(name, map);
    }

    public static Localization getLocalization()
    {
        Preconditions.checkState(localization != null, "No localization has been loaded yet!");
        return localization;
    }*/

}
