package game;

import org.joml.Vector2f;

import java.util.Objects;

public class ObjectTransform {
    public Vector2f position;
    public Vector2f scale;
    
    public ObjectTransform(Vector2f position,Vector2f scale){
        this.position = position;
        this.scale = scale;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectTransform that = (ObjectTransform) o;
        return Objects.equals(position, that.position) && Objects.equals(scale, that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, scale);
    }
}
