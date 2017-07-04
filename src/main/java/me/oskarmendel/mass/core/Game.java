/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Oskar Mendel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.oskarmendel.mass.core;

import me.oskarmendel.mass.entity.Entity;
import me.oskarmendel.mass.entity.SkyBox;
import me.oskarmendel.mass.entity.TestRoom;
import me.oskarmendel.mass.entity.masster.MassterBall;
import me.oskarmendel.mass.entity.mob.Player;
import me.oskarmendel.mass.gfx.*;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.weather.Fog;
import me.oskarmendel.mass.input.MouseHandler;
import me.oskarmendel.mass.phys.Collidable;
import me.oskarmendel.mass.phys.PhysicsSpace;
import me.oskarmendel.mass.util.OBJLoader;
import me.oskarmendel.mass.util.Timer;
import me.oskarmendel.mass.util.assimp.StaticMeshLoader;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The Game class initializes the game and starts the game loop.
 * When the loop ends this class disposes the game and its components.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name Game.java
 */
public class Game implements Runnable {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final String SCREEN_TITLE = "Mass";

    public static final boolean VSYNC = false;

    public static final float CAMERA_POS_STEP = 0.05f;

    /**
     * The error callback for GLFW.
     * Strong reference due to the garbage collector.
     */
    private GLFWErrorCallback errorCallback;

    /**
     * Shows if the game is running.
     */
    private boolean running;

    /**
     * GLFW window or screen used by the game.
     */
    private Screen screen;
    
    private final Thread gameLoopThread;

    /**
     * Renderer to handle rendering.
     */
    private final Renderer renderer;

    /**
     * Camera of the game to handle the view of the game.
     */
    private final Camera camera;
    
    private boolean sceneChanged = true;
    
    /**
     * Handles camera updates.
     */
    private final Vector3f cameraInc;
    
    private final MouseHandler mouseHandler;
    private final Timer timer;
    
    private PhysicsSpace physicsSpace;
    
    MassterBall massterBall;
    TestRoom room;
    Player player;
    
    Scene scene;
    
    /**
     * Default constructor for the game.
     */
    public Game() {
        renderer = new Renderer();
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        camera = new Camera();
        mouseHandler = new MouseHandler();
        timer = new Timer();
        cameraInc = new Vector3f(0, 0,0);
    }

    private Entity[] entities;

    /**
     * This method should be called to initialize and start the game.
     */
    public void start() {
        //init();
        //gameLoop();
        //dispose();
    	gameLoopThread.run();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        // Set error callback.
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        ScreenOptions screenOptions = new ScreenOptionsBuilder().cullFace(false).showTriangles(false)
                .showFPS(false).compatibleProfile(false).antialiasing(false).frustumCulling(true).build();

        // Create the GLFW screen.
        screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_TITLE, VSYNC, screenOptions);
        
        timer.init();
        
        mouseHandler.init(screen);

        // Initialize renderer.
        try {
			renderer.init();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        scene = new Scene();
        scene.setRenderShadows(false);
        
        Texture t = Texture.loadTexture("src/main/resources/textures/hexmap.png");
        try {
            Mesh cubeMesh = OBJLoader.loadMesh("src/main/resources/models/cube.obj");
            Mesh[] testRoomMesh = StaticMeshLoader.load("src/main/resources/models/office/cs_office.obj", "src/main/resources/models/office/");
            
            Material mat = new Material(t, 1.0f);
            cubeMesh.setMaterial(mat);
            //testRoomMesh.setMaterial(new Material());
            
            player = new Player(cubeMesh);
            
            massterBall = new MassterBall(cubeMesh);
            massterBall.setPosition(0, -1, 0);
            
            room = new TestRoom(testRoomMesh, 0.1f);
            room.setPosition(0, -1, 0);
            room.setDisableFrustrumCulling(true);
            
            float skyBoxScale = 300.0f;
            SkyBox skyBox = new SkyBox("src/main/resources/models/skybox.obj", new Color(1f, 0.0f, 0.0f));
            skyBox.setScale(skyBoxScale);
            scene.setSkyBox(skyBox);
            
            entities = new Entity[]{massterBall, room, player};
        } catch (Exception e) {
            e.printStackTrace();
        }

        physicsSpace = new PhysicsSpace();
        
        for (Entity ent : entities) {
        	if (ent instanceof Collidable) {
        		physicsSpace.addRigidBody(((Collidable) ent).getRigidBody());
        	}
        }
        
        scene.setEntities(entities);
        scene.setEntityMeshes(entities);
        scene.setFog(Fog.NO_FOG);
        
        // Initialize all the lights for the scene.
        initLights();
        
        // Initialization done, set running to true.
        running = true;
    }
    
    /**
     * 
     */
    public void initLights() {
    	SceneLight sceneLight = new SceneLight();
    	scene.setSceneLight(sceneLight);
    	
    	// Ambient Light
    	sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
    	
    	// Directional Light
    	float intensity = 1.0f;
    	Vector3f direction = new Vector3f(0, 1, 1);
    	DirectionalLight directionalLight = new DirectionalLight(Color.WHITE, direction, intensity);
    	sceneLight.setDirectionalLight(directionalLight);
    }
    
    @Override
    public void run() {
    	try {
    		init();
    		gameLoop();
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		this.dispose();
    	}
    }

    /**
     * The game loop
     */
    public void gameLoop() {
    	int fps = 0;
    	
        while(running) {
            // Check if the game should close.
            if (screen.isClosing()) {
                running = false;
            }

            // Handle input
            input();
            
            // Update game and game logic.
            update();

            render();

            screen.update();
            
            fps++;
            if (timer.getTime() - timer.getLastTime() >= 1) {
            	System.out.println(fps);
            	timer.updateLastTime();
            	fps = 0;
            }
        }
    }

    /**
     * This method releases resources used by the game.
     */
    public void dispose() {
        // Dispose the renderer.
        renderer.dispose();

        // Release the screen and its resources.
        screen.destroy();

        // Terminate GLFW release the error callback.
        glfwTerminate();
        errorCallback.free();
    }

    /**
     * Handles input.
     */
    private void input() {
    	sceneChanged = false;
    	
    	// Update mouse input.
    	mouseHandler.input();
    	
        cameraInc.set(0, 0, 0);

        if (screen.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        	//player.move(0, 0, -1);
           // player.forward();
            sceneChanged = true;
        } else if (screen.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
            //player.backward();
            sceneChanged = true;
        }

        if (screen.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
            //player.left();
        	//player.move(-1, 0, 0);
            sceneChanged = true;
        } else if (screen.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
            //player.right();
        	//player.move(1, 0, 0);
            sceneChanged = true;
        }

        if (screen.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    /**
     * Updates the game and logic.
     */
    public void update() {
        // Update camera position.
        //camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        player.movePosition(cameraInc.x, cameraInc.y, cameraInc.z, camera.getRotation().y);
    	camera.setPosition(player.getPosition().x, player.getPosition().y + 1.5f, player.getPosition().z);
    	
        // Update camera based on mouse movements
        if (mouseHandler.isRightButtonPressed()) {
        	Vector2f rotVec = mouseHandler.getDispelVec();
        	camera.moveRotation(rotVec.x * 0.2f, rotVec.y * 0.2f, 0);
        	player.moveRotation(rotVec.y * 0.2f, 0, 0);
        	sceneChanged = true;
        }
        
        // Update the camera view matrix.
        camera.updateViewMatrix();
        
        for (Entity entity : entities) {
            // Do something for every loaded entity.
        	
        	// Update physics of all collidable entities.
        	if (entity instanceof Collidable) {
        		((Collidable) entity).updatePhysics();
        	}
        }
        
        physicsSpace.tick();
    }

    /**
     * Renders the game.
     */
    public void render() {
        renderer.render(this.screen, this.camera, this.scene, sceneChanged);
    }
}
