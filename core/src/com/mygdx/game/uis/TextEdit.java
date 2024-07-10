package com.mygdx.game.uis;

import com.badlogic.gdx.Input;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;

public class TextEdit implements Input.TextInputListener {
    @Override
    public void input(String text) {
        MyGdxGame.name = text;
        MemoryManager.saveName(text);
    }

    @Override
    public void canceled() {
    }

}
