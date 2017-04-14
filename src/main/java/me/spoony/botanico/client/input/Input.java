package me.spoony.botanico.client.input;

import static org.lwjgl.glfw.GLFW.*;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.common.util.position.WindowPosition;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class Input
{
    private static InputProcessor INPUT_PROCESSOR;
    private static List<BinaryInputListener> INPUT_LISTENERS;
    private static List<TextInputListener> TEXT_INPUT_LISTENERS;

    private static List<BinaryInput> BINARY_INPUTS;

    public static BinaryInput ESCAPE = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_ESCAPE);

    public static BinaryInput MOVE_DOWN = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_S);
    public static BinaryInput MOVE_RIGHT = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_D);
    public static BinaryInput MOVE_UP = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_W);
    public static BinaryInput MOVE_LEFT = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_A);

    public static BinaryInput INVENTORY = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_E);

    public static BinaryInput INV_KEY_1 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_1);
    public static BinaryInput INV_KEY_2 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_2);
    public static BinaryInput INV_KEY_3 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_3);
    public static BinaryInput INV_KEY_4 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_4);
    public static BinaryInput INV_KEY_5 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_5);
    public static BinaryInput INV_KEY_6 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_6);
    public static BinaryInput INV_KEY_7 = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_7);

    public static BinaryInput INV_KEY_SWORD = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_Z);
    public static BinaryInput INV_KEY_HOE = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_X);
    public static BinaryInput INV_KEY_AXE = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_C);
    public static BinaryInput INV_KEY_PICKAXE = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_V);

    public static BinaryInput BUTTON_LEFT = new BinaryInput(BinaryInputType.BUTTON, GLFW_MOUSE_BUTTON_LEFT);
    public static BinaryInput SHIFT = new BinaryInput(BinaryInputType.KEY, GLFW_KEY_LEFT_SHIFT);

    public static BinaryInput BUTTON_RIGHT = new BinaryInput(BinaryInputType.BUTTON, GLFW_MOUSE_BUTTON_RIGHT);

    public static WindowPosition CURSOR_POS = new WindowPosition();

    public static void init() {
        INPUT_PROCESSOR = new InputProcessor(BotanicoGame.WINDOW.getHandle()) {
            @Override
            public void keyDown(int keycode)
            {
                for (BinaryInput input:BINARY_INPUTS) {
                    if (input.type == BinaryInputType.KEY && input.id == keycode) {
                        input.down = true;
                        onBinaryInputPressed(input);
                    }
                }
            }

            @Override
            public void buttonDown(int button)
            {
                for (BinaryInput input:BINARY_INPUTS) {
                    if (input.type == BinaryInputType.BUTTON && input.id == button) {
                        input.down = true;
                        onBinaryInputPressed(input);
                    }
                }
            }

            @Override
            public void keyUp(int keycode) {
                for (BinaryInput input:BINARY_INPUTS) {
                    if (input.type == BinaryInputType.KEY && input.id == keycode) {
                        input.down = false;
                    }
                }
            }

            @Override
            public void buttonUp(int button) {
                for (BinaryInput input:BINARY_INPUTS) {
                    if (input.type == BinaryInputType.BUTTON && input.id == button) {
                        input.down = false;
                    }
                }
            }

            @Override
            public void keyTyped(char character)
            {
                onKeyTyped(character);
            }

            @Override
            public void cursorMoved(float x, float y)
            {
                CURSOR_POS.set(x, y);
            }
        };

        INPUT_LISTENERS = Lists.newArrayList();
        TEXT_INPUT_LISTENERS = Lists.newArrayList();

        BINARY_INPUTS = Lists.newArrayList();

        BINARY_INPUTS.add(ESCAPE);

        BINARY_INPUTS.add(MOVE_DOWN);
        BINARY_INPUTS.add(MOVE_RIGHT);
        BINARY_INPUTS.add(MOVE_UP);
        BINARY_INPUTS.add(MOVE_LEFT);

        BINARY_INPUTS.add(INVENTORY);

        BINARY_INPUTS.add(INV_KEY_1);
        BINARY_INPUTS.add(INV_KEY_2);
        BINARY_INPUTS.add(INV_KEY_3);
        BINARY_INPUTS.add(INV_KEY_4);
        BINARY_INPUTS.add(INV_KEY_5);
        BINARY_INPUTS.add(INV_KEY_6);
        BINARY_INPUTS.add(INV_KEY_7);

        BINARY_INPUTS.add(INV_KEY_SWORD);
        BINARY_INPUTS.add(INV_KEY_HOE);
        BINARY_INPUTS.add(INV_KEY_AXE);
        BINARY_INPUTS.add(INV_KEY_PICKAXE);

        BINARY_INPUTS.add(BUTTON_LEFT);
        BINARY_INPUTS.add(BUTTON_RIGHT);

        BINARY_INPUTS.add(SHIFT);
    }

    public static void registerListener(BinaryInputListener il) {
        Preconditions.checkNotNull(il);
        INPUT_LISTENERS.add(il);

        Collections.sort(INPUT_LISTENERS, (o1, o2) ->
        {
            if (getInputPriority(o1).value() >= getInputPriority(o2).value()) return 0;
            return 1;
        });
    }

    public static void unregisterListener(BinaryInputListener il) {
        Preconditions.checkNotNull(il);
        INPUT_LISTENERS.remove(il);
    }

    private static InputPriority getInputPriority(BinaryInputListener binaryInputListener) {
        Class obj = binaryInputListener.getClass();
        try {
            Method m = obj.getMethod("onBinaryInputPressed", BinaryInput.class);
            InputHandler a = m.getAnnotation(InputHandler.class);
            return a.priority();
        } catch (Exception e) {
        }
        return InputPriority.GAME_BACK;
    }

    public static void onBinaryInputPressed(BinaryInput bin) {
        List<BinaryInputListener> temp = new ArrayList<BinaryInputListener>(INPUT_LISTENERS);

        for (BinaryInputListener il : temp){
            il.onBinaryInputPressed(bin);
        }
    }

    public static void registerListener(TextInputListener il) {
        Preconditions.checkNotNull(il);
        TEXT_INPUT_LISTENERS.add(il);
    }

    public static void unregisterListener(TextInputListener il) {
        Preconditions.checkNotNull(il);
        TEXT_INPUT_LISTENERS.remove(il);
    }

    public static void onKeyTyped(char character) {
        List<TextInputListener> temp = new ArrayList<TextInputListener>(TEXT_INPUT_LISTENERS);

        for (TextInputListener il : temp){
            il.onCharTyped(character);
        }
    }
}
