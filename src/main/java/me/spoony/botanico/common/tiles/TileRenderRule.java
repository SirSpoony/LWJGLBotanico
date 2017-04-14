package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileRenderRule
{
    private TileMatcher[] matchers;
    private IntRectangle[] regions;
    private IntRectangle background;

    public TileRenderRule(IntRectangle region, TileMatcher... matchers)
    {
        this.regions = new IntRectangle[]{region};
        this.matchers = matchers;
        this.background = null;
    }

    public TileRenderRule(IntRectangle[] regions, TileMatcher... matchers)
    {
        this.regions = regions;
        this.matchers = matchers;
        this.background = null;
    }

    public boolean matches(AdjacentTiles adj)
    {
        for (int i = 0; i < matchers.length; i++)
        {
            if (!matchers[i].matches(adj)) return false;
        }
        return true;
    }

    public IntRectangle getForeground()
    {
        return regions[0];
    }

    public IntRectangle getForeground(int rand)
    {
        return regions[rand % regions.length];
    }

    public IntRectangle getBackground()
    {
        return background;
    }

    public boolean hasBackground() {
        return background != null;
    }

    public void setBackground(IntRectangle background)
    {
        this.background = background;
    }
}