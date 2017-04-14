package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileRenderRules
{
    protected List<TileRenderRule> renderRules;

    public IntRectangle[] getDefaultRegions()
    {
        return defaultRegions;
    }

    protected IntRectangle[] defaultRegions;

    public TileRenderRules(IntRectangle DefaultRegion)
    {
        renderRules = new ArrayList<TileRenderRule>();
        this.defaultRegions = new IntRectangle[] { DefaultRegion };
    }

    public TileRenderRules(IntRectangle[] DefaultRegions)
    {
        renderRules = new ArrayList<TileRenderRule>();
        this.defaultRegions = DefaultRegions;
    }

    public TileRenderRule addRule(IntRectangle region, TileMatcher... matchers)
    {
        TileRenderRule trr = new TileRenderRule(region, matchers);
        renderRules.add(trr);
        return trr;
    }

    public TileRenderRule addRule(IntRectangle[] regions, TileMatcher... matchers)
    {
        TileRenderRule trr = new TileRenderRule(regions, matchers);
        renderRules.add(trr);
        return trr;
    }

    public TileRenderRule findRenderRule(AdjacentTiles adjTiles)
    {
        TileRenderRule bestMatch = null;
        for (TileRenderRule trr : renderRules)
        {
            if (trr.matches(adjTiles)) bestMatch = trr;
        }
        return bestMatch;
    }
}