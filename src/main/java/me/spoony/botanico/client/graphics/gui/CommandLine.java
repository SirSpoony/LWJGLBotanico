package me.spoony.botanico.client.graphics.gui;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;

import java.util.List;

/**
 * Created by Colten on 11/13/2016.
 */
public class CommandLine
{
    public CommandLine()
    {
        this.messages = Lists.newArrayList();
        //addMessage(Config.getLocalization().get("start_message"));
    }

    List<CommandLineMessage> messages;

    public void update(float deltatime) {
        for (int i = 0; i < messages.size(); i++) {
            CommandLineMessage current = messages.get(i);
            current.update(deltatime);
        }
    }

    public void render(RendererGUI rg) {
        for (int i = 0; i < messages.size(); i++) {
            CommandLineMessage current = messages.get(i);
            //if (!current.shouldDisplay()) break;
            rg.text(rg.guiViewport.width -10, rg.guiViewport.height - 10 - 10*i,
                    "" + current.getMessage(),
                    new TextColors(new Color(1,1,1,1f*current.getOpacity()), new Color(0,0,0,.5f*current.getOpacity())), CallAlign.TOP_RIGHT);
        }
    }

    public void addMessage(String message) {
        this.messages.add(0, new CommandLineMessage(message));
    }
}
