package me.spoony.botanico.common.util.position;

import me.spoony.botanico.common.util.DoubleVector2D;

/**
 * Created by Colten on 12/27/2016.
 */
public interface IVectorable {
    DoubleVector2D toVector(DoubleVector2D vector2D);

    default DoubleVector2D toVector() {
        return toVector(new DoubleVector2D());
    }
}
