package me.spoony.botanico.client.engine;

import com.google.common.collect.Maps;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;

import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Colten on 12/21/2016.
 */
public class ShaderProgram {

  protected static final int VERTEX_SHADER = GL_VERTEX_SHADER;
  protected static final int FRAGMENT_SHADER = GL_FRAGMENT_SHADER;

  public int programID;
  public int fragmentID;
  public int vertexID;

  private final Map<String, Integer> uniforms;
  private final Map<String, Integer> attributes;

  public ShaderProgram() {
    programID = glCreateProgram();
    if (programID == 0) {
      throw new IllegalStateException("Couldn't create shaderprogram");
    }

    uniforms = Maps.newHashMap();
    attributes = Maps.newHashMap();
  }

  public void createFragmentShader(String code) throws Exception {
    fragmentID = createShader(code, FRAGMENT_SHADER);
  }

  public void createVertexShader(String code) throws Exception {
    vertexID = createShader(code, VERTEX_SHADER);
  }

  public int createShader(String shadercode, int type) throws Exception {
    int shader = glCreateShader(type);
    if (shader == 0) {
      throw new IllegalStateException("Couldn't create shader");
    }

    glShaderSource(shader, shadercode);
    glCompileShader(shader);

    if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
      throw new Exception("Error compiling shader " + glGetShaderInfoLog(shader, 1024));
    }

    glAttachShader(programID, shader);

    return shader;
  }

  public void link() {
    glLinkProgram(programID);

    if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
      throw new IllegalStateException("Error linking shader!");
    }

    glValidateProgram(programID);

    if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
      System.err
          .println("Warning while validating shader code: " + glGetProgramInfoLog(programID, 1024));
    }
  }

  public void bind() {
    glUseProgram(programID);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void cleanup() {
    unbind();
    if (programID != 0) {
      if (vertexID != 0) {
        glDetachShader(programID, vertexID);
      }
      if (fragmentID != 0) {
        glDetachShader(programID, fragmentID);
      }
    }
    glDeleteProgram(programID);
  }

  public void createUniform(String uniformName) throws Exception {
    int uniformLocation = glGetUniformLocation(programID, uniformName);
    if (uniformLocation < 0) {
      throw new Exception("Could not find uniform:" +
          uniformName);
    }
    uniforms.put(uniformName, uniformLocation);
  }

  public void setUniform(String uniformName, Matrix4f value) {
    FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    value.get(fb);
    glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
  }

  public void setUniform(String uniformName, int value) {
    glUniform1i(uniforms.get(uniformName), value);
  }

  public void setUniform(String uniformName, Color value) {
    Vector4f v = new Vector4f(value.r, value.g, value.b, value.a);
    FloatBuffer fb = BufferUtils.createFloatBuffer(4);
    v.get(fb);
    glUniform4fv(uniforms.get(uniformName), fb);
  }

  public void createVertexAttribute(String attributeName) {
    int attribLocation = glGetAttribLocation(programID, attributeName);
    if (attribLocation < 0) {
      throw new RuntimeException("Could not find attribute:" +
          attributeName);
    }
    attributes.put(attributeName, attribLocation);
  }

  public void enableVertexAttribute(String attribute) {
    glEnableVertexAttribArray(attributes.get(attribute));
  }

  public void disableVertexAttribute(String attribute) {
    glDisableVertexAttribArray(attributes.get(attribute));
  }

  public void pointVertexAttribute(String attribute, int size, int stride, int offset) {
    glVertexAttribPointer(attributes.get(attribute), size, GL_FLOAT, false, stride, offset);
  }
}
