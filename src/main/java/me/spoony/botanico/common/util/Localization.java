package me.spoony.botanico.common.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Colten on 12/2/2016.
 */
public class Localization
{
    private String name;
    private Map<String, String> localizations;

    protected Localization(String name, Map<String, String> localizations)
    {
        this.name = name;
        this.localizations = localizations;

        System.out.println("Initialized Localization: "+name);
    }

    public String getName()
    {
        return name;
    }

    public String get(String text)
    {
        return localizations.get(text);
    }

    public String get(String text, String or)
    {
        return localizations.containsKey(text) ? get(text) : or;
    }
}
