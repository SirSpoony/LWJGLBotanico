package me.spoony.botanico.client.graphics;

import me.spoony.botanico.client.engine.SpriteBatch;

/**
 * Created by Colten on 11/8/2016.
 */
public abstract class OrderedRenderable implements Renderable
{
    public final double order;

    protected OrderedRenderable(double order)
    {
        this.order = order;
    }

    public static OrderedRenderable create(double order, Renderable renderable) {
        return new OrderedRenderable(order)
        {
            @Override
            public void render(SpriteBatch batch)
            {
                renderable.render(batch);
            }
        };
    }
}
