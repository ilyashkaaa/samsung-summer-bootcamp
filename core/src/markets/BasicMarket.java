package markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.Mossy;
import com.mygdx.game.uis.Button;

public class BasicMarket {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    public boolean inMarket;
    Texture marketTexture;
    Texture backpackBackground;
    public Button exitButton;

    public BasicMarket (String texturePath, float x, BasicBlock[][] mapArray){
        this.x = x;

        exitButton = new Button("textures/buttons/button_background_square.png", "textures/buttons/main_screen/jump_button_off.png",
                0, -200, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));

        y = GameSettings.MAP_HEIGHT * GameSettings.OBJECT_SCALE * GameSettings.BLOCK_SIDE;

        width = (int) (GameSettings.MARKET_WIDTH * GameSettings.OBJECT_SCALE);
        height = (int) (GameSettings.MARKET_HEIGHT * GameSettings.OBJECT_SCALE);

        marketTexture = new Texture(texturePath);
        backpackBackground = new Texture("textures/buttons/backpack_background.png");

        for (int i = 0; i < GameSettings.MARKET_WIDTH / 2 / GameSettings.BLOCK_SIDE + 1; i++) {
            mapArray[(int) x - i][GameSettings.MAP_HEIGHT - 1] = new Mossy();
            mapArray[(int) x + i][GameSettings.MAP_HEIGHT - 1] = new Mossy();
        }
    }

    public boolean isNearBy(Vector2 playerPos){
        return playerPos.x <= x * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE + width / 2f &&
                playerPos.x >= x * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE - width / 2f &&
                playerPos.y >= GameSettings.MAP_HEIGHT * GameSettings.OBJECT_SCALE * GameSettings.BLOCK_SIDE;
    }
    public void draw(SpriteBatch batch) {
        batch.draw(marketTexture,
                x * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE - width / 2f,
                y, width, height);
    }
    public void drawInterface(Vector3 cameraPos, MyGdxGame myGdxGame){
        myGdxGame.batch.draw(backpackBackground,
                cameraPos.x - myGdxGame.camera.viewportWidth / 2 + (float) (GameSettings.SCR_WIDTH - backpackBackground.getWidth()) / 2 * GameSettings.SCALE,
                cameraPos.y - myGdxGame.camera.viewportHeight / 2 + (float) (GameSettings.MAP_HEIGHT - backpackBackground.getHeight()) / 2 * GameSettings.SCALE,
                backpackBackground.getWidth() * GameSettings.SCALE,
                backpackBackground.getHeight() * GameSettings.SCALE);
        exitButton.draw(myGdxGame.batch, cameraPos);
    }
    public void setState(int state){}
}
