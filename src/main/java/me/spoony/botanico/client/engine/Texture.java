package me.spoony.botanico.client.engine;

import com.google.common.base.Preconditions;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

/**
 * Created by Colten on 12/24/2016.
 */
public class Texture
{
    protected final int target = GL_TEXTURE_2D;
    protected final int id;
    protected final int width;
    protected final int height;

    public static final int LINEAR = GL_LINEAR;
    public static final int NEAREST = GL_NEAREST;

    public static final int CLAMP = GL_CLAMP;
    public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
    public static final int REPEAT = GL_REPEAT;

    public Texture(URL pngRef)
    {
        this(pngRef, GL_NEAREST);
    }

    public Texture(URL pngRef, int filter)
    {
        this(pngRef, filter, GL_CLAMP_TO_EDGE);
    }

    public Texture(URL pngRef, int filter, int wrap) {
        Preconditions.checkNotNull(pngRef);

        InputStream input = null;
        try {
            input = pngRef.openStream();

            PNGDecoder dec = new PNGDecoder(input);

            width = dec.getWidth();
            height = dec.getHeight();

            final int bpp = 4; // bytes per pixel (4 channels RGBA)

            ByteBuffer buf = BufferUtils.createByteBuffer(bpp*width*height);

            dec.decode(buf, width*bpp, PNGDecoder.Format.RGBA);

            buf.flip();

            glEnable(target);
            id = glGenTextures();
            bind();

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filter);
            glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filter);
            glTexParameteri(target, GL_TEXTURE_WRAP_S, wrap);
            glTexParameteri(target, GL_TEXTURE_WRAP_T, wrap);

            glTexImage2D(target, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        } catch (IOException e) {
            throw new RuntimeException("Could not load texture from URL "+pngRef.getPath(), e);
        } finally {
            if (input != null) {
                try
                {
                    input.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void bind() {
        glBindTexture(target, id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
