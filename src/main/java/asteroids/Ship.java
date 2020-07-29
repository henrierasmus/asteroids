package asteroids;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;

/**
 *
 * @author henri
 */
// Ship Object, will control all the ship functionality
public class Ship extends Character {

    private Map<KeyCode, Boolean> pressedKeys;

    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
        this.pressedKeys = new HashMap<>();

    }

    public void turnPress(KeyCode code) {
        pressedKeys.put(code, Boolean.TRUE);
    }
 
    public void stopTurn(KeyCode code) {
        pressedKeys.put(code, Boolean.FALSE);
    }

    public Map getMap() {
        return this.pressedKeys;
    }

}
