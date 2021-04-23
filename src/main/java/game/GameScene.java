package game;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameScene extends Scene {

    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 2, 3 // bottom left triangle
    };




//    private final float[] vertexArray = {
//            // position               // color                  // UV Coordinates
//            0f, 100f, 0.0f,         0.0f, 1.0f, 0.0f, 1.0f,     0, 0, // Top left     0
//            100f, 100f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f,     1, 0, // Top right    1
//            100f,   0f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 1, // Bottom right 2
//            0f,   0f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f,     0, 1  // Bottom left  3
//    };


    @Override
    public void init() {

        this.camera = new Camera(new Vector2f(-100, -300));
        this.tiles = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            Random random = new Random();
            if(random.nextInt(10) == 1){
                new Tile("src/main/resources/assets/textures/poppy.png", new ObjectTransform(new Vector2f(-1000 + i * 100, 50), new Vector2f(50, 50)), 2);
            }

            new Tile("src/main/resources/assets/textures/grass_block.png", new ObjectTransform(new Vector2f(-1000 + i * 100, 0), new Vector2f(100, 100)), 1);
            new Tile("src/main/resources/assets/textures/dirt.png", new ObjectTransform(new Vector2f(-1000 + i * 100, -100), new Vector2f(100, 100)), 1);
        }
        player = new Entity("src/main/resources/assets/textures/goomba.png", new ObjectTransform(new Vector2f(100, 75), new Vector2f(75, 75)));
        tiles.forEach(Tile::init);


    }

    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + 1.0f / dt);
        tiles.forEach(Tile::update);
        player.update();
    }

}
