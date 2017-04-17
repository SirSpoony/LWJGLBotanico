package me.spoony.botanico.client;

import com.google.common.base.Preconditions;
import me.spoony.botanico.client.engine.Font;
import me.spoony.botanico.client.engine.Texture;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	Map<String, Object> loadedResources;
	
	public ResourceManager() {
		loadedResources = new HashMap<String, Object>();
	}
	
	public Texture getTexture(String texturepath) {
		if (!loadedResources.containsKey(texturepath)) 
			loadedResources.put(texturepath.toLowerCase(), new Texture(getInternalResource("/assets/"+texturepath)));
		return (Texture)loadedResources.get(texturepath);
	}
	
/*	public Music getMusic(String musicpath) {
		if (!loadedResources.containsKey(musicpath)) 
			loadedResources.put(musicpath.toLowerCase(), 
					Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/music/"+musicpath)));
		return (Music)loadedResources.get(musicpath);
	}

	public Sound getSound(String soundpath) {
		if (!loadedResources.containsKey(soundpath.toLowerCase()))
			loadedResources.put(soundpath.toLowerCase(),
					Gdx.audio.newSound(Gdx.files.internal("assets/sounds/"+soundpath)));
		return (Sound)loadedResources.get(soundpath.toLowerCase());
	}*/
	
	public Font getFont(String fontpath) {
		if (!loadedResources.containsKey(fontpath))
			try {
				loadedResources.put(fontpath.toLowerCase(),
                        new Font(getInternalResource("/assets/fonts/"+fontpath+".xml").openStream(),
								getTexture("fonts/"+fontpath+".png")));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		return (Font)loadedResources.get(fontpath);
	}

	public URL getInternalResource(String path) {
		Preconditions.checkNotNull(path, "Internal resource path cannot be null");
		return this.getClass().getResource(path);
	}

	public void preLoad() {
		getTexture("button.png");
		getTexture("character_sheet.png");
		getTexture("damage_indicator.png");
		getTexture("foreign_character_sheet.png");
		getTexture("hotbar.png");
		getTexture("items.png");
		getTexture("logo.png");
		getTexture("slot_highlight.png");
		getTexture("spoony_logo.png");
		getTexture("text_field.png");
		getTexture("tiles.png");
		getTexture("tooltip.png");

		getTexture("dialog/dialog_inventory.png");
		getTexture("dialog/dialog_tool_station.png");
		getTexture("dialog/tint.png");
	}
}
