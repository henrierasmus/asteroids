package asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {
    

    @Override
    public void start(Stage stage) {
        GameBoardPane gameBoardObject = new GameBoardPane();
        Ship ship = new Ship(gameBoardObject.WIDTH / 2,
                gameBoardObject.WIDTH / 2);
        
        List<Asteroid> asteroids = new ArrayList<>();
        List<Projectile> projectiles = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(gameBoardObject.WIDTH / 3)
                    , rnd.nextInt(gameBoardObject.WIDTH));
            asteroids.add(asteroid);
        }

        Map<KeyCode, Boolean> keys = ship.getMap();
        Pane board = gameBoardObject.start();

        board.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> board.getChildren()
                .add(asteroid.getCharacter()));

        Scene view = new Scene(board);

        view.setOnKeyPressed((event) -> {
            ship.turnPress(event.getCode());
        });

        view.setOnKeyReleased((event) -> {
            ship.stopTurn(event.getCode());
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(GameBoardPane.WIDTH, GameBoardPane.HEIGHT);
                    if (!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        board.getChildren().add(asteroid.getCharacter());
                    }
                }
                
                if (keys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (keys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (keys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
                
                if (keys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3) {
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);
                    
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                                        
                    board.getChildren().add(projectile.getCharacter());
                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });
                
                List<Projectile> projectilesToRemove = projectiles.stream().filter(projectile -> {
                   List<Asteroid> collisions = asteroids.stream().filter(asteroid -> asteroid.collide(projectile)).collect(Collectors.toList());
                   
                   if(collisions.isEmpty()) {
                       return false;
                   }
                   
                   collisions.stream().forEach(collided -> {
                       asteroids.remove(collided);
                       board.getChildren().remove(collided.getCharacter());
                   });
                   
                   return true;                       
                   }).collect(Collectors.toList());
                
                projectilesToRemove.forEach(projectile -> {
                    board.getChildren().remove(projectile.getCharacter());
                    projectiles.remove(projectile);
                    gameBoardObject.setText();
                });
            }
        }.start();

        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
        launch(AsteroidsApplication.class);
    }

}
