package me.spoony.botanico.common.entities;

import me.spoony.botanico.common.util.DoubleRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class CollisionCheck
{
    public boolean collided;
    public DoubleRectangle intersection;

    public CollisionCheck(boolean collided)
    {
        this.collided = collided;
        this.intersection = new DoubleRectangle();
    }
}
