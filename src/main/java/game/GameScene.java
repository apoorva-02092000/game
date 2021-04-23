package game;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScene extends Scene {

    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 2, 3 // bottom left triangle
    };

    private final List<Tile> tiles = new ArrayList<>();


//    private final float[] vertexArray = {
//            // position               // color                  // UV Coordinates
//            0f, 100f, 0.0f,         0.0f, 1.0f, 0.0f, 1.0f,     0, 0, // Top left     0
//            100f, 100f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f,     1, 0, // Top right    1
//            100f,   0f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 1, // Bottom right 2
//            0f,   0f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f,     0, 1  // Bottom left  3
//    };


    @Override
    public void init() {

        this.camera = new Camera(new Vector2f(-580, -300));
        for(int i = 0; i < 10; i++){
//            Tile test = new Tile("src/main/resources/assets/textures/grass_block.png", makeVertexArray(-500 + i * 100, 300, 100, 100, true), elementArray, 0);
//            Tile test1 = new Tile( "src/main/resources/assets/textures/dirt.png", makeVertexArray(-500 + i * 100, 200, 100, 100, true), elementArray, 0);
//            Tile test2 = new Tile("src/main/resources/assets/textures/dirt.png", makeVertexArray(-500 + i * 100, 100, 100, 100, true), elementArray, 0);
//            Tile test3 = new Tile("src/main/resources/assets/textures/dirt.png", makeVertexArray(-500 + i * 100, 0, 100, 100, true), elementArray, 0);
//            tiles.add(test);
//            tiles.add(test1);
//            tiles.add(test2);
//            tiles.add(test3);
        }

        Tile test = new Tile("src/main/resources/assets/textures/dirt.png", new ObjectTransform(new Vector2f(200, -100), new Vector2f(100, 100)), 1);
        Tile test2 = new Tile("src/main/resources/assets/textures/grass_block.png", new ObjectTransform(new Vector2f(0, 100), new Vector2f(100, 100)), 1);

        player = new Entity("src/main/resources/assets/textures/Idle__000.png", new ObjectTransform(new Vector2f(100, 100), new Vector2f(100, 100)));

        tiles.add(test);
        tiles.add(test2);
        Collections.sort(tiles);
        tiles.forEach(Tile::init);


        player.init();
    }

    @Override
    public void update(float dt) {
        System.out.println(1.0f / dt);
        tiles.forEach(Tile::update);
        player.update();
    }

}
