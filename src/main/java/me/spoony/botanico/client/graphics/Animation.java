package me.spoony.botanico.client.graphics;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 12/27/2016.
 */
public class Animation {
    float fps;
    IntRectangle textureSources[];

    float ftime;

    public Animation(float fps, IntRectangle... textureSources) {
        Preconditions.checkArgument(fps >= 1);
        this.fps = fps/textureSources.length;
        this.textureSources = textureSources;
        ftime = 0;
    }

    public void update(float delta) {
        ftime += delta*fps* textureSources.length;
        ftime %= textureSources.length;
    }

    public void reset() {
        ftime = 0;
    }

    public IntRectangle getSource() {
        return textureSources[(int)ftime];
    }
}
