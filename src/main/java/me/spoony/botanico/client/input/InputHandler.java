package me.spoony.botanico.client.input;

import java.lang.annotation.*;

/**
 * Created by Colten on 11/10/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface InputHandler
{
    InputPriority priority() default InputPriority.GAME_BACK;
}
