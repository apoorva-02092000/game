package game;

import game.renderer.Texture;
import game.util.KeyListener;
import org.joml.Vector2f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameWindow {
    private long window;

    public static Scene currentScene;

    public GameWindow() {
    }
    

    public void run() {

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(1440, 1080, "HELLOOOOOOO", NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetScrollCallback(window, (window, v , b) -> {
            System.out.println(b);
            currentScene.camera.position.y += b * 10;


        });
        glfwSetKeyCallback(window, KeyListener::keyCallback);

//        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//            if(key == GLFW_KEY_W){
//                    GameWindow.currentScene.player.transform.position.y += 1;
//                    currentScene.player.isDirty = true;
//                    GameWindow.currentScene.camera.position.y += 1;
//
//
//
//            }else if(key == GLFW_KEY_S){
//
//                for(int i = 0; i < 15; i++){
//                    GameWindow.currentScene.player.transform.position.y -= 1;
//                    currentScene.player.isDirty = true;
//                    GameWindow.currentScene.camera.position.y -= 1;
//
//                }
//
//            }else if(key == GLFW_KEY_A){
//
//                for(int i = 0; i < 15; i++){
//                    GameWindow.currentScene.player.transform.position.x -= 1;
//                    currentScene.player.isDirty = true;
//                    currentScene.camera.position.x -= 1;
//                }
//
//            }else if(key == GLFW_KEY_D){
//
//                    GameWindow.currentScene.player.transform.position.x += 15;
//                    currentScene.camera.position.x += 15;
//                    currentScene.player.isDirty = true;
//
//            }
//
//        });





        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        currentScene = new GameScene();
        currentScene.init();
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = 0;

        while (!glfwWindowShouldClose(window)) {
            // Poll events
            glfwPollEvents();

            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(window);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
