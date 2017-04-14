package me.spoony.botanico.client.input;

/**
 * Created by Colten on 11/10/2016.
 */
public enum InputPriority
{
    GUI_FRONT(0),
    GUI_MIDDLE(1),
    GUI_BACK(2),
    GAME_FRONT(3),
    GAME_MIDDLE(4),
    GAME_BACK(5);

    private int place;
    private InputPriority(int place) {
        this.place = place;
    }

    public int value() {
        return place;
    }
}
