package me.spoony.botanico.client.engine;

import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Colten on 12/26/2016.
 */
public class VertexArrayObject {

  private final int id;

  public VertexArrayObject() {
    id = glGenVertexArrays();
  }

  public void bind() {
    glBindVertexArray(id);
  }

  /**
   * Deletes the VAO.
   */
  public void delete() {
    glDeleteVertexArrays(id);
  }

  /**
   * Getter for the Vertex Array Object ID.
   *
   * @return Handle of the VAO
   */
  public int getID() {
    return id;
  }

}