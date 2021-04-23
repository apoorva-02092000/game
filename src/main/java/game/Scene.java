package game;

public abstract class Scene {

    public Camera camera;
    public Entity player;

    public Scene(){

    }

    public void init(){
    }

    public abstract void update(float dt);
}
