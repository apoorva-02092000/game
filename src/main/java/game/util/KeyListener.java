package game.util;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {

    private final boolean[] keyPressed = new boolean[350];
    private static KeyListener instance;


    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long glwWindow, int key, int scancode, int action, int mods ){
        if(action == GLFW_PRESS){
            get().keyPressed[key] = true;
            System.out.println(key);
        }else if(action == GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }

}
