package com.mygdx.game.uis;

import com.badlogic.gdx.Input;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;

public class TextEdit implements Input.TextInputListener {
    @Override
    public void input(String text) {
        MyGdxGame.name = "";
        for (int i = 0; i < Math.min(10, text.length()); i++) {
            MyGdxGame.name += text.toCharArray()[i];
        }
        MemoryManager.saveName(text);
    }

    @Override
    public void canceled() {
    }

}
