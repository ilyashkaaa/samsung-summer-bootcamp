package UI;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;

public class Joystick {
    int x, y;
    private MyGdxGame myGdxGame;
    public Joystick(int x, int y, MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public Vector2 getDirection(){
        Vector3 direction = new Vector3();
        if (Gdx.input.isTouched()) {
            direction.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(direction);
            System.out.println(direction);
            return new Vector2(direction.x - x, direction.y - y).setLength(Player.speed * SCALE);
        }
        else
            return new Vector2();
    }
}
