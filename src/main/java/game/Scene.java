package game;

import java.util.List;

public abstract class Scene {

    public Camera camera;
    public Entity player;
    public List<Tile> tiles;

    public Scene(){

    }

    public void init(){
    }

    public abstract void update(float dt);
}
