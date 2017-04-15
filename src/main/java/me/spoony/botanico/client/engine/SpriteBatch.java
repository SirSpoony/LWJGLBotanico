package me.spoony.botanico.client.engine;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.primitives.Floats;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Colten on 12/25/2016.
 */
public class SpriteBatch {
    ShaderProgram shaderProgram;

    final FloatBuffer vertices;
    int vertindex;
    Texture lastTexture = null;

    private boolean rendering;

    VertexArrayObject vao;
    VertexBufferObject vbo;

    private Matrix4f projectionMatrix = new Matrix4f();

    public float viewwidth;
    public float viewheight;

    public Color ambientColor;

    public SpriteBatch(int size) {
        Preconditions.checkArgument(Range.closed(0, 8191 * 7).contains(size), "Can't have more than 8191 sprites per batch: " + size);

        vao = new VertexArrayObject();
        vao.bind();

        vbo = new VertexBufferObject();
        vbo.bind(GL_ARRAY_BUFFER);

        vertices = BufferUtils.createFloatBuffer(size);

        vbo.uploadData(GL_ARRAY_BUFFER, vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);

        vertindex = 0;
        rendering = false;

        shaderProgram = new ShaderProgram();
        try {
            shaderProgram.createFragmentShader(DEFAULT_FRAGMENT_SHADER);
            shaderProgram.createVertexShader(DEFAULT_VERTEX_SHADER);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create shader program", e);
        }
        shaderProgram.link();
        shaderProgram.bind();

        shaderProgram.createVertexAttribute("position");
        shaderProgram.enableVertexAttribute("position");
        shaderProgram.pointVertexAttribute("position", 2, 8 * Float.BYTES, 0);

        shaderProgram.createVertexAttribute("color");
        shaderProgram.enableVertexAttribute("color");
        shaderProgram.pointVertexAttribute("color", 4, 8 * Float.BYTES, 2 * Float.BYTES);

        shaderProgram.createVertexAttribute("texcoord");
        shaderProgram.enableVertexAttribute("texcoord");
        shaderProgram.pointVertexAttribute("texcoord", 2, 8 * Float.BYTES, 6 * Float.BYTES);

        try {
            shaderProgram.createUniform("projection");
            shaderProgram.createUniform("ambientcolor");
            shaderProgram.createUniform("texImage");

        } catch (Exception e) {
            e.printStackTrace();
        }

        shaderProgram.unbind();

        ambientColor = new Color(Color.WHITE);
    }

    public static final String DEFAULT_VERTEX_SHADER = "#version 330\n" +
            "in vec2 position;\n" +
            "in vec4 color;\n" +
            "in vec2 texcoord;\n" +
            "\n" +
            "out vec4 vertexColor;\n" +
            "out vec2 textureCoord;\n" +
            "\n" +
            "uniform mat4 projection;\n" +
            "uniform vec4 ambientcolor;\n" +
            "\n" +
            "void main() {\n" +
            "    vertexColor = color * ambientcolor;\n" +
            "    textureCoord = texcoord;\n" +
            "    gl_Position = projection * vec4(position, 0.0, 1.0);\n" +
            "}";


    public static final String DEFAULT_FRAGMENT_SHADER = "#version 330\n" +
            "in vec4 vertexColor;\n" +
            "in vec2 textureCoord;\n" +
            "\n" +
            "out vec4 fragColor;\n" +
            "\n" +
            "uniform sampler2D texImage;\n" +
            "\n" +
            "void main() {\n" +
            "    vec4 textureColor = texture(texImage, textureCoord);\n" +
            "    fragColor = vec4(vertexColor) * textureColor;\n" +
            "}";

    public void begin() {
        Preconditions.checkState(!rendering, "Cannot start rendering without calling end()");

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        shaderProgram.bind();

        rendering = true;
        vertindex = 0;
        lastTexture = null;
    }

    public void end() {
        Preconditions.checkState(rendering, "Cannot end rendering without calling start()");

        flush();

        shaderProgram.unbind();
        rendering = false;
    }

    public void sprite(float x, float y, float width, float height, float rot, float originx, float originy, Color color, int texx, int texy, int texwidth, int texheight, Texture tex) {
        if (!rendering) {
            throw new IllegalStateException("Attempted to render sprite without calling being()");
        }

        x = (float) Math.round(x);
        y = (float) Math.round(y);

        if (tex != lastTexture) {
            if (lastTexture == null) {
                lastTexture = tex;
            } else {
                flush();
                lastTexture = tex;
            }
        }

        if ((vertindex + 6) * 8 >= vertices.capacity()) {
            flush();
        }

        // VERTICE POS
        float rad = rot;

        float tx1 = x;
        float ty1 = y;
        float tx2 = x + width;
        float ty2 = y + height;

        Vector2f center = new Vector2f(originx, originy);

        Vector2f bl = rotate(center, new Vector2f(tx1, ty1), rad);
        float blx = bl.x;
        float bly = bl.y;

        Vector2f ul = rotate(center, new Vector2f(tx1, ty2), rad);
        float ulx = ul.x;
        float uly = ul.y;

        Vector2f ur = rotate(center, new Vector2f(tx2, ty2), rad);
        float urx = ur.x;
        float ury = ur.y;

        Vector2f br = rotate(center, new Vector2f(tx2, ty1), rad);
        float brx = br.x;
        float bry = br.y;

        // VERTEX COLOR
        float r = color.r;
        float g = color.g;
        float b = color.b;
        float a = color.a;

        // TEXTURE REGION
        float s1 = texx / (float) tex.getWidth();
        float t1 = (texy + texheight) / (float) tex.getHeight();
        float s2 = (texx + texwidth) / (float) tex.getWidth();
        float t2 = texy / (float) tex.getHeight();

        vertices.put(blx).put(bly).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(ulx).put(uly).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices.put(urx).put(ury).put(r).put(g).put(b).put(a).put(s2).put(t2);

        vertices.put(blx).put(bly).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(urx).put(ury).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(brx).put(bry).put(r).put(g).put(b).put(a).put(s2).put(t1);

        vertindex += 6;
    }

    public void sprite(float x, float y, float width, float height, Color color, int texx, int texy, int texwidth, int texheight, Texture tex) {
        if (!rendering) {
            throw new IllegalStateException("Attempted to render sprite without calling being()");
        }

        x = (float) Math.round(x);
        y = (float) Math.round(y);

        if (tex != lastTexture) {
            if (lastTexture == null) {
                lastTexture = tex;
            } else {
                flush();
                lastTexture = tex;
            }
        }

        if ((vertindex + 6) * 8 >= vertices.capacity()) {
            flush();
        }

        // VERTICE POS
        float tx1 = x;
        float ty1 = y;
        float tx2 = x + width;
        float ty2 = y + height;

        // VERTEX COLOR
        float r = color.r;
        float g = color.g;
        float b = color.b;
        float a = color.a;

        // TEXTURE REGION
        float s1 = texx / (float) tex.getWidth();
        float t1 = (texy + texheight) / (float) tex.getHeight();
        float s2 = (texx + texwidth) / (float) tex.getWidth();
        float t2 = texy / (float) tex.getHeight();

        vertices.put(tx1).put(ty1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(tx1).put(ty2).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices.put(tx2).put(ty2).put(r).put(g).put(b).put(a).put(s2).put(t2);

        vertices.put(tx1).put(ty1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(tx2).put(ty2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(tx2).put(ty1).put(r).put(g).put(b).put(a).put(s2).put(t1);

        vertindex += 6;
    }

    public void flush() {
        if (lastTexture == null) return;
        //System.out.println(String.join(", ", "vertindex "+vertindex, "texture "+lastTexture));

        if (viewheight % 2 == 0) {
            projectionMatrix.identity().setOrtho2D(0, viewwidth, 0, viewheight);
        } else {
            projectionMatrix.identity().setOrtho2D(0, viewwidth, 0, viewheight);
        }
        shaderProgram.setUniform("projection", projectionMatrix);
        shaderProgram.setUniform("ambientcolor", ambientColor);
        shaderProgram.setUniform("texImage", 0);

        vertices.flip();
        vao.bind();

        vbo.bind(GL_ARRAY_BUFFER);
        vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        lastTexture.bind();

        glDrawArrays(GL_TRIANGLES, 0, vertindex);

        vertices.clear();
        vertindex = 0;
        lastTexture = null;
    }

    private static Vector2f rotate(Vector2f center, Vector2f vector, float rot) {
        float x = center.x + (vector.x - center.x) * (float) Math.cos(rot) - (vector.y - center.y) * (float) Math.sin(rot);
        float y = center.y + (vector.x - center.x) * (float) Math.sin(rot) + (vector.y - center.y) * (float) Math.cos(rot);
        return new Vector2f(x, y);
    }
}
