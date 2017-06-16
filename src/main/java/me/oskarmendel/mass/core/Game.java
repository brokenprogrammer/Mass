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
import me.oskarmendel.mass.entity.geometry.Cone;
import me.oskarmendel.mass.entity.geometry.Cube;
import me.oskarmendel.mass.entity.geometry.Cylinder;
import me.oskarmendel.mass.entity.geometry.Sphere;
import me.oskarmendel.mass.entity.masster.MassterBall;
import me.oskarmendel.mass.gfx.*;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;
import me.oskarmendel.mass.input.MouseHandler;
import me.oskarmendel.mass.phys.Collidable;
import me.oskarmendel.mass.phys.PhysicsSpace;
import me.oskarmendel.mass.util.OBJLoader;

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
public class Game {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final String SCREEN_TITLE = "Mass";

    public static final boolean VSYNC = true;

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

    /**
     * Renderer to handle rendering.
     */
    private final Renderer renderer;

    /**
     * Camera of the game to handle the view of the game.
     */
    private final Camera camera;

    /**
     * Handles camera updates.
     */
    private final Vector3f cameraInc;
    
    private final MouseHandler mouseHandler;
    
    private PhysicsSpace physicsSpace;
    
    // Temporary light variables.
    private Vector3f ambientLight;
    private PointLight pointLight;
    private DirectionalLight directionalLight;
    private float lightAngle;
    private SpotLight spotLight;
    private float spotAngle = 0;
    private float spotInc = 1;
    
    Sphere s;
    MassterBall massterBall;
    
    /**
     * Default constructor for the game.
     */
    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        mouseHandler = new MouseHandler();
        cameraInc = new Vector3f(0, 0,0);
        lightAngle = -90;
    }

    private Entity[] entities;

    /**
     * This method should be called to initialize and start the game.
     */
    public void start() {
        init();
        gameLoop();
        dispose();
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

        // Create the GLFW screen.
        screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_TITLE, VSYNC);
        
        mouseHandler.init(screen);

        // Initialize renderer.
        renderer.init();

        Texture t = Texture.loadTexture("src/main/resources/textures/hexmap.png");
        try {
            Mesh mesh = OBJLoader.loadMesh("src/main/resources/models/cube.obj");
            Material mat = new Material(t, 1f);
            mesh.setMaterial(mat);

            massterBall = new MassterBall(mesh);
            massterBall.setPosition(0, -1, 0);

            Cube c = new Cube(new Vector3f(6, 2, -2), new Vector3f(0, 0 ,0), 0.5f);
            Cube c2 = new Cube(new Vector3f(0, -2, -2), new Vector3f(0, 0 ,0), 0.5f);
            Cube c3 = new Cube(new Vector3f(-2, 0, -2), new Vector3f(0, 0 ,0), 0.5f);
            Cube c4 = new Cube(new Vector3f(2, 0, -2), new Vector3f(0, 0 ,0), 0.5f);

            c.getMesh().getMaterial().setAmbientColor(new Color(0, 1, 0).toVector4f());
            c.getMesh().getMaterial().setDiffuseColor(new Color(0, 1, 0).toVector4f());
            c.getMesh().getMaterial().setSpecularColor(new Color(0, 1, 0).toVector4f());

            c2.getMesh().getMaterial().setAmbientColor(new Color(0, 0, 1).toVector4f());
            c2.getMesh().getMaterial().setDiffuseColor(new Color(0, 0, 1).toVector4f());
            c2.getMesh().getMaterial().setSpecularColor(new Color(0, 0, 1).toVector4f());
            
            s = new Sphere(new Vector3f(0, 4, -2), new Vector3f(0, 0 ,0), 1f, 1, new Color(1, 0, 0));
            Sphere s2 = new Sphere(new Vector3f(0, -4, -2), new Vector3f(0, 0 ,0), 1f, 1, t);

            Cone co = new Cone(new Vector3f(0, 2, -2), new Vector3f(0, 0 ,0), 1f, t);
            Cone co2 = new Cone(new Vector3f(-2, 3, -2), new Vector3f(0, 0 ,0), 1f, new Color(1, 0, 1));
            
            Cylinder cr = new Cylinder(new Vector3f(3, 2, -2), new Vector3f(10, 180, 10), 1f, t);
            Cylinder cr2 = new Cylinder(new Vector3f(3, 4, -2), new Vector3f(10, 180, 10), 1f, new Color(1, 1, 0));
            
            entities = new Entity[]{massterBall, c, c2, c3, c4, s, s2, co, co2, cr, cr2};
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Position light example.
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
        Color lightColour = new Color(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        
        // Spot light example.
        lightPosition = new Vector3f(0.0f, 0.0f, 10f);
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
        pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutOff = (float) Math.cos(Math.toRadians(140));
        spotLight = new SpotLight(pointLight, coneDir, cutOff);

        // Directional light example.
        lightPosition = new Vector3f(-1, 0,0 );
        lightColour = new Color(1, 1, 1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);

        physicsSpace = new PhysicsSpace();
        
        for (Entity ent : entities) {
        	if (ent instanceof Collidable) {
        		physicsSpace.addRigidBody(((Collidable) ent).getRigidBody());
        	}
        }
        
        // Initialization done, set running to true.
        running = true;
    }

    /**
     * The game loop
     */
    public void gameLoop() {
    	long lastFrameCheck = 0;
    	long  lastTime = System.nanoTime();
    	int frames = 0;
    	
        while(running) {
        	long now = System.nanoTime();
        	long updateLength = now - lastTime;
        	lastTime = now;
        	frames++;
        	
        	lastFrameCheck += updateLength;
        	
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
            
            if (lastFrameCheck >= 1000000000) {
        		System.out.println("FPS: " + frames);
        		frames = 0;
        		lastFrameCheck = 0;
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
    	// Update mouse input.
    	mouseHandler.input();
    	
        cameraInc.set(0, 0,0);

        if (screen.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }

        if (screen.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if (screen.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (screen.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }

        float lightPos = spotLight.getPointLight().getPosition().z;
        if (screen.isKeyPressed(GLFW_KEY_N)) {
            this.spotLight.getPointLight().getPosition().z = lightPos + 0.1f;
        } else if (screen.isKeyPressed(GLFW_KEY_M)) {
            this.spotLight.getPointLight().getPosition().z = lightPos - 0.1f;
        }
    }

    /**
     * Updates the game and logic.
     */
    public void update() {
        // Update camera position.
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        
        // Update camera based on mouse movements
        if (mouseHandler.isRightButtonPressed()) {
        	Vector2f rotVec = mouseHandler.getDispelVec();
        	camera.moveRotation(rotVec.x * 0.2f, rotVec.y * 0.2f, 0);
        }
        
        // Update the camera view matrix.
        camera.updateViewMatrix();

        for (Entity entity : entities) {
            // Do something for every loaded entity.
        	entity.getRotation().y += 1.0f;
        }
        
        // Update spot light direction.
        spotAngle += spotInc * 0.05f;
        if (spotAngle > 2) {
        	spotInc = -1;
        } else if (spotAngle < -2) {
        	spotInc = 1;
        }
        
        double spotAngleRad = Math.toRadians(spotAngle);
        Vector3f coneDir = spotLight.getConeDirection();
        coneDir.y = (float) Math.sin(spotAngleRad);

        // Update directional light direction.
        lightAngle += 1.1f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (Math.abs(lightAngle) -80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().setGreen(Math.max(factor, 0.9f));
            directionalLight.getColor().setBlue(Math.max(factor, 0.5f));
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().setRed(1);
            directionalLight.getColor().setGreen(1);
            directionalLight.getColor().setBlue(1);
        }

        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
        
        physicsSpace.tick();
        massterBall.update(); // TODO: Make small loop to update logic for all entities. & Replace rotation loop above.
        					  // Oskar Mendel 2017-06-16.
    }

    /**
     * Renders the game.
     */
    public void render() {
        renderer.render(this.camera, this.entities, ambientLight, 
        		pointLight, spotLight, directionalLight);
    }
}
