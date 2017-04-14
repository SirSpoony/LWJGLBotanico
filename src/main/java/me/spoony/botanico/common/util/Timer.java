package me.spoony.botanico.common.util;

/**
 * Created by Colten on 11/22/2016.
 */
public class Timer
{
    private float timer;

    private float time;
    private float laststep;

    public Timer(float timer) {
        this.timer = 1/timer;
        this.reset();
    }

    public boolean step(float delta) {
        time += delta;
        if (laststep != (float) Math.floor(time * timer))
        {
            laststep = (float) Math.floor(time * timer);
            return true;
        }
        return false;
    }

    public void reset() {
        time = 0;
        laststep = -1;
    }
}
