package game.renderer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {
    private int vertexID, shaderProgramID, fragmentID;
    String vertexShaderSource;
    String fragmentShaderSource;

    //private final float[] vertexArray;
    //private final int[] elementArray;

    public Shader(){
        //this.vertexArray = vertexArray;
        //this.elementArray = elementArray;

        init();
    }

    public void init(){
        try{
            vertexShaderSource = new String(Files.readAllBytes(Paths.get(
                    "src/main/resources/assets/shaders/vertexShader.glsl")));

            fragmentShaderSource = new String(Files.readAllBytes(Paths.get(
                    "src/main/resources/assets/shaders/fragShader.glsl")));

        }catch (IOException io){
            System.out.println("Error opening vertexShader/fragShader File");
            System.exit(1);
        }


        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexShaderSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            System.out.println("Vertex Shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)));
            System.exit(1);
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentShaderSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            System.out.println("Fragment Shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)));
            System.exit(1);
        }

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            System.out.println("Shader linking failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH)));
        }


    }

    public void use(){
        glUseProgram(shaderProgramID);

    }

    public void uploadMat4f(String varName, Matrix4f mat4f){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        glUseProgram(shaderProgramID);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4f.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadTexture(String varName, int slot){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }
}
