package game;

import org.joml.Vector2f;

public class ObjectTransform {
    public Vector2f position;
    public Vector2f scale;
    
    public ObjectTransform(Vector2f position,Vector2f scale){
        this.position = position;
        this.scale = scale;

    }

    public ObjectTransform(Vector2f position){
        this.position = position;
        this.scale = new Vector2f(100, 100);
    }
}
