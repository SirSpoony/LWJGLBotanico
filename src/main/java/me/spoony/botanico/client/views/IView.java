package me.spoony.botanico.client.views;

public interface IView
{
	void initialize();
    void loadContent();
    void unloadContent();

    void update(float delta);
    void render();

    boolean isContentLoaded();
}
