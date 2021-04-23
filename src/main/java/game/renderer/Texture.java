package game.renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    public String filepath;
    public boolean isNull = false;
    private int texID;

    public Texture(String filePath){
        this.filepath = filePath;
        if(this.filepath != null){
            initImage();
        }else{
            isNull = true;
        }

    }

    private void initImage(){
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);


        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if(image != null){
            if(channels.get(0) == 3){
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            }else if(channels.get(0) == 4){
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            }else{
                System.out.println("Error: unknown number of channels: " + channels.get(0));
                System.exit(1);
            }

            //stbi_set_flip_vertically_on_load(true);

            stbi_image_free(image);
        }else{
            System.out.println("Error: couldn't load image at path: " + this.filepath);
            this.filepath = "src/main/resources/assets/textures/missing_texture.png";
            initImage();
            //System.exit(1);

        }


    }
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
