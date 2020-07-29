package asteroids;

/**
 *
 * @author henri
 */
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Object that will be responsible for initiating game state and controlling the 
// board/pane
public class GameBoardPane {

    public static int WIDTH = 300;
    public static int HEIGHT = 200;
    public Text text = new Text(10, 20, "Points: 0");
    AtomicInteger points = new AtomicInteger();

    public Pane start() {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        pane.getChildren().add(text);

        return pane;
    }
    
    public void setText() {
        this.text.setText("Points: " + points.incrementAndGet());
    }

}
