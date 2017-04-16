package me.spoony.botanico.client.engine;

import me.spoony.botanico.client.BotanicoGame;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Colten on 12/22/2016.
 */
public class Window {

  long handle;

  private final int defaultWidth;
  private final int defaultHeight;
  private final String title;

  private int width;
  private int height;

  public Window(int width, int height, String title) {
    defaultWidth = width;
    defaultHeight = height;
    this.width = width;
    this.height = height;
    this.title = title;
  }

  public void init() {
    if (!glfwInit()) {
      throw new IllegalStateException("Could not init glfw");
    }

    GLFWErrorCallback.createPrint(System.err).set();

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); //MAC COMPATIBILITY
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

    handle = glfwCreateWindow(defaultWidth, defaultHeight, title, NULL, NULL);

    if (handle == NULL) {
      throw new RuntimeException("Could not create window!");
    }

    GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    int windowposx = (vidMode.width() - defaultWidth) / 2;
    int windowposy = (vidMode.height() - defaultHeight) / 2;

    glfwSetWindowPos(handle, windowposx, windowposy);

    glfwMakeContextCurrent(handle);
    glfwSwapInterval(0); //vsync

    glfwSetFramebufferSizeCallback(handle, (window, width1, height1) -> {
      this.width = width1;
      this.height = height1;
      if (BotanicoGame.RUNNING) {
        glViewport(0, 0, width, height);
      }
    });
  }

  public long getHandle() {
    return handle;
  }

  public void show() {
    glfwShowWindow(handle);

    GL.createCapabilities();
  }

  public boolean shouldClose() {
    return glfwWindowShouldClose(handle);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
