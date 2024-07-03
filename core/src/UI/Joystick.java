package UI;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;


public class Joystick {
    Vector3 relativeCords;
    private Texture texture;
    public Joystick(){
        relativeCords = new Vector3();
        texture = new Texture("textures/blocks/dirt.png");
    }
    public void changeRelativeCords(Vector3 pos){
        relativeCords.set(pos);
    }
    public void draw(SpriteBatch batch, Vector3 cameraPos){
        Vector3 cords = new Vector3(relativeCords.x + cameraPos.x, relativeCords.y + cameraPos.y, 0);
        batch.draw(texture, cords.x, cords.y, GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE);
    }
    public Vector2 getDirection(Vector3 touch, Vector3 cameraPos){
        Vector3 cords = new Vector3(relativeCords.x + cameraPos.x, relativeCords.y + cameraPos.y, 0);
        return new Vector2(touch.x - cords.x, touch.y - cords.y);
    }
}
