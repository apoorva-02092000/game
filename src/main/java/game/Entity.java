package game;

import game.renderer.Shader;
import game.renderer.Texture;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Entity {
    public final Texture texture;
    private final Shader shader;
    private int vaoID, vboID, eboID;
    public ObjectTransform transform;
    private ObjectTransform prevTransform;

    public boolean isDirty;

    private float[] vertexArray;
    private final int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 2, 3 // bottom left triangle
    };


    public Entity(String filepath, ObjectTransform transform){
        this.texture = new Texture(filepath);
        this.shader = new Shader();
        this.transform = transform;
        makeVertexArray();
        init();


    }

    public void init(){
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;


        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
        isDirty = true;
    }

    public void update(){
        boolean changed = false;

        if(isDirty){
            makeVertexArray();
            isDirty = false;
            changed = true;
        }


        if(changed){
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
        }



        shader.use();
        shader.uploadMat4f("uProjection", GameWindow.currentScene.camera.getProjectionMatrix());
        shader.uploadMat4f("uView", GameWindow.currentScene.camera.getViewMatrix());
        shader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();


        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glUseProgram(0);

        texture.unbind();

        if(!transform.equals(prevTransform)){
            System.out.println("yo");
            isDirty = true;
            prevTransform = transform;
        }


    }


    private void makeVertexArray() {
        float x = this.transform.position.x;
        float y = this.transform.position.y;
        float height = this.transform.scale.x;
        float width = this.transform.scale.y;
        boolean isTexture = !this.texture.isNull;
        int vertexSize = 9;
        float z = 0;

        float r = 1.0f;
        float g = 0.1f;
        float b = 1.0f;
        float a = 1;

        float ux = 0;
        float uy = 0;

        Vector2f[] uvTexCoordinates = {
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(1, 1),
                new Vector2f(0, 1)
        };


        float xAdd = 0;
        float yAdd = 0;

        float[] vertexArray = new float[vertexSize * 4];
        for (int i = 0; i < 4; i++) {
            if (isTexture) {
                ux = uvTexCoordinates[i].x;
                uy = uvTexCoordinates[i].y;
            }
            switch (i) {
                case 1:
                    xAdd = 1.0f;
                    yAdd = 0.0f;
                    break;
                case 2:
                    xAdd = 1.0f;
                    yAdd = -1.0f;
                    break;
                case 3:
                    yAdd = -1.0f;
                    xAdd = 0.0f;
                    break;

            }
            int offset = i * 9;
            vertexArray[offset] = x + (xAdd * width);
            vertexArray[offset + 1] = y + (yAdd * height);
            vertexArray[offset + 2] = z;

            vertexArray[offset + 3] = r;
            vertexArray[offset + 4] = g;
            vertexArray[offset + 5] = b;
            vertexArray[offset + 6] = a;

            vertexArray[offset + 7] = ux;
            vertexArray[offset + 8] = uy;

        }
        this.vertexArray = vertexArray;
    }
}
