package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Colten on 11/12/2016.
 */
public class ItemRegistry
{
    protected Map<Integer, Item> items;

    public ItemRegistry()
    {
        items = new HashMap<Integer, Item>();
    }


    public void register(Item item)
    {
        Preconditions.checkNotNull(item);
        items.put(item.getID(), item);
    }

    public Item get(String name)
    {
        Preconditions.checkNotNull(name);
        for (Item i : items.values())
        {
            if (i.getName().equalsIgnoreCase(name)) return i;
        }
        return null;
    }

    public Item get(int id)
    {
        Preconditions.checkNotNull(items.containsKey(id));
        return items.get(id);
    }
}
