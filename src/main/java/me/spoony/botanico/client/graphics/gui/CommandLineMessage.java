package me.spoony.botanico.client.graphics.gui;

/**
 * Created by Colten on 11/13/2016.
 */
public class CommandLineMessage
{
    public float lifetime;
    public String message;

    public float getLifetime()
    {
        return lifetime;
    }

    public void update(float delta)
    {
        this.lifetime += delta;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public CommandLineMessage(String message) {
        this.message = message;
        this.lifetime = 0;
    }

    public boolean shouldDisplay() {
        return lifetime < 5;
    }

    public float getOpacity() {
        if (lifetime > 5) return 0;
        if (lifetime < 4) return 1;
        return 5-lifetime; // implement commandlinemessage in commandline
    }
}
