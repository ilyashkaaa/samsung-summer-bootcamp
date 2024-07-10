package com.mygdx.game.uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;

public class Slider implements Disposable {
    Texture sliderTexture = GameResources.SLIDER_TEXTURE;
    Texture toggleTexture = GameResources.TOGGLE_SLIDER_TEXTURE;
    float y;
    float width, height;
    float togglePos;

    public Slider(float x, float y, float width, float height) {
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos) {
        batch.draw(sliderTexture, cameraPos.x - width / 2f, y * GameSettings.OBJECT_SCALE - height / 2f + cameraPos.y, width, height);
        batch.draw(toggleTexture,
                cameraPos.x + getValueToggle(),
                y * GameSettings.OBJECT_SCALE - height / 2f + cameraPos.y - toggleTexture.getHeight() / 2f * GameSettings.SCALE + height / 2,
                toggleTexture.getWidth() * GameSettings.SCALE,
                toggleTexture.getHeight() * GameSettings.SCALE
        );
       //System.out.println(map((int) (cameraPos.x - width / 2f), (int) (cameraPos.x + width / 2f), -4, 99, cameraPos.x + getValueToggle()));

    }

    private int map(int getMin, int getMax, float setMin, float setMax, float value) {
        return Math.round((value - getMin + 1) / (getMax - getMin + 1) * (setMax - setMin + 1) + setMin - 1);
    }
    private float getValueToggle() {
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            if (touchPos.x <= GameSettings.SCR_WIDTH / 2f + width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.x >= GameSettings.SCR_WIDTH / 2f - width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.y >= -y + GameSettings.SCR_HEIGHT / 2f - height / GameSettings.OBJECT_SCALE / 2f - toggleTexture.getHeight() / 2f &&
                    touchPos.y <= -y + GameSettings.SCR_HEIGHT / 2f + height / GameSettings.OBJECT_SCALE / 2f + toggleTexture.getHeight() / 2f) {
                togglePos = Gdx.input.getX() * GameSettings.SCALE - GameSettings.SCR_WIDTH / 2f * GameSettings.SCALE - toggleTexture.getWidth()/2f*GameSettings.SCALE;
            }
        }
        return togglePos;
    }
    public int getValue(Vector3 cameraPos) {
        return map((int) (cameraPos.x - width / 2f), (int) (cameraPos.x + width / 2f), 4, 105, cameraPos.x + getValueToggle());
    }
    @Override
    public void dispose() {
        toggleTexture.dispose();
        sliderTexture.dispose();
    }

}
