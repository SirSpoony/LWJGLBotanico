package me.spoony.botanico.common.util;

import com.google.common.base.Preconditions;

import java.util.Random;

/**
 * Created by Colten on 12/18/2016.
 */
public class BMath {
    public static long smear(long a, long val) {
        a = scramble(a);
        a += val;
        return a;
    }

    public static long scramble(long a) {
        //System.out.println(a);
        return new Random(a).nextLong();
    }

    static public int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public long clamp(long value, long min, long max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public float lerp(float fromValue, float toValue, float progress) {
        Preconditions.checkArgument(progress >= 0 && progress <= 1, "Lerp progress must be 0-1 inclusive!");
        return fromValue + (toValue - fromValue) * progress;
    }

    static public double lerp(double fromValue, double toValue, double progress) {
        Preconditions.checkArgument(progress >= 0 && progress <= 1, "Lerp progress must be 0-1 inclusive!");
        return fromValue + (toValue - fromValue) * progress;
    }

    public static int floor(double val) {
        return (int) Math.floor(val);
    }

    public static int ceil(double val) {
        return (int) Math.ceil(val);
    }

    public static int roundedPercent(float val) {
        return Math.round(val*100);
    }
}
