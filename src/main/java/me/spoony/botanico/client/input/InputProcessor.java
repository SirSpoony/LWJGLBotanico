package me.spoony.botanico.client.input;

import me.spoony.botanico.client.BotanicoGame;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Colten on 11/10/2016.
 */
public class InputProcessor
{
    long windowhandle;

    public InputProcessor(long windowhandle) {
        this.windowhandle = windowhandle;

        glfwSetKeyCallback(windowhandle, (window, key, scancode, action, mods) -> {
            if ((action == GLFW_PRESS || action == GLFW_REPEAT) && key == GLFW_KEY_BACKSPACE) {
                keyTyped((char)8);
            }

            if (action == GLFW_PRESS) {
                keyDown(key);
            } else if (action == GLFW_RELEASE) {
                keyUp(key);
            }
        });

        glfwSetCharCallback(windowhandle, (window, codepoint) -> {
            keyTyped((char)codepoint);
        });

        glfwSetMouseButtonCallback(windowhandle, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                buttonDown(button);
            } else if (action == GLFW_RELEASE) {
                buttonUp(button);
            }
        });

        glfwSetScrollCallback(windowhandle, (window, xoffset, yoffset) -> {
            //TODO: check what this offset returns (it's a double casted to int)
            scrolled((int) yoffset);
        });

        glfwSetCursorPosCallback(windowhandle, (window, xpos, ypos) -> {
            // GLFW maps mouse position to top left by default,
            // so mouse position is changed to be relative to the bottom left
            cursorMoved((float)xpos, (float)(BotanicoGame.WINDOW.getHeight()-ypos));
        });
    }

    public void free() {
        glfwFreeCallbacks(windowhandle);
    }

    public void keyDown(int keycode)
    {

    }

    public void keyUp(int keycode)
    {

    }

    public void keyTyped(char character)
    {

    }

    public void buttonDown(int button) {

    }

    public void buttonUp(int button) {

    }

    public void scrolled(int amount)
    {

    }

    public void cursorMoved(float x, float y) {

    }
}
