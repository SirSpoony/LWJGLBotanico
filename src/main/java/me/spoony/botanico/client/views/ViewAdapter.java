package me.spoony.botanico.client.views;

/**
 * Created by Colten on 11/24/2016.
 */
public class ViewAdapter implements IView
{
    protected boolean isContentLoaded;

    @Override
    public void initialize()
    {
        isContentLoaded = false;
    }

    @Override
    public void loadContent()
    {
        isContentLoaded = true;
    }

    @Override
    public void unloadContent()
    {

    }

    @Override
    public void update(float delta)
    {

    }

    @Override
    public void render()
    {

    }

    @Override
    public boolean isContentLoaded()
    {
        return isContentLoaded;
    }
}
