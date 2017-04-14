package me.spoony.botanico.client.engine;

import com.google.common.collect.Maps;
import me.spoony.botanico.common.util.position.GuiRectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Colten on 12/26/2016.
 */
public class Font {
    Map<Character, Glyph> glyphMap;
    Texture texture;

    public Font(InputStream fontDescription, Texture texture) {
        this.glyphMap = Maps.newHashMap();
        this.texture = texture;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(fontDescription);
            Node font = doc.getElementsByTagName("Font").item(0);
            for (int i = 0; i < font.getChildNodes().getLength(); i++) {
                if (font.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Node character = font.getChildNodes().item(i);

                    String code = character.getAttributes().getNamedItem("code").getNodeValue();
                    String offset = character.getAttributes().getNamedItem("offset").getNodeValue();
                    String rect = character.getAttributes().getNamedItem("rect").getNodeValue();


                    String[] parserect = rect.split(" ");
                    int rectx = Integer.parseInt(parserect[0]);
                    int recty = Integer.parseInt(parserect[1]);
                    int rectw = Integer.parseInt(parserect[2]);
                    int recth = Integer.parseInt(parserect[3]);

                    String[] parseoffset = offset.split(" ");
                    int offsetx = Integer.parseInt(parseoffset[0]);
                    int offsety = Integer.parseInt(parseoffset[1]);

                    Glyph glyph = new Glyph(rectx, recty, rectw, recth, offsetx, offsety);

                    glyphMap.put(code.charAt(0), glyph);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Glyph getGlyph(char character) {
        return glyphMap.get(character);
    }

    public void renderString(SpriteBatch batch, String characters, float x, float y, float scale, Color color) {
        y -= 18*scale;
        char[] chars = characters.toCharArray();
        float xrenderpos = x;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                xrenderpos += 4 * scale;
                continue;
            }
            Glyph glyph = getGlyph(chars[i]);
            renderChar(batch, chars[i], xrenderpos, y, scale, color);
            xrenderpos += (glyph.width + 1) * scale;
        }
    }

    public float getStringWidth(String characters, float scale) {
        char[] chars = characters.toCharArray();
        int width = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                width += 4 * scale;
                continue;
            }
            Glyph glyph = getGlyph(chars[i]);
            width += (glyph.width + 1) * scale;
        }
        return width;
    }

    public float getStringHeight(String characters, float scale) {
        return 10 * scale;
    }

    public GuiRectangle getStringBounds(String characters, float scale) {
        return new GuiRectangle(0, 0, getStringWidth(characters, scale), getStringHeight(characters, scale));
    }

    public void renderChar(SpriteBatch batch, char character, float x, float y, float scale, Color color) {
        Glyph g = getGlyph(character);
        batch.sprite(x + g.xoffset, y, g.width * scale, 16 * scale, color, g.x, 0, g.width, texture.getHeight(), texture);
    }
}
