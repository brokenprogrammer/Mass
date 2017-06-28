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
import me.oskarmendel.mass.entity.TestRoom;
import me.oskarmendel.mass.entity.masster.MassterBall;
import me.oskarmendel.mass.entity.mob.Player;
import me.oskarmendel.mass.gfx.*;
import me.oskarmendel.mass.gfx.light.DirectionalLight;
import me.oskarmendel.mass.gfx.light.PointLight;
import me.oskarmendel.mass.gfx.light.SpotLight;
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
    private final Timer timer;
    
    private PhysicsSpace physicsSpace;
    
    // Temporary light variables.
    private Vector3f ambientLight;
    private PointLight[] pointLights;
    private DirectionalLight directionalLight;
    private float lightAngle;
    private SpotLight[] spotLights;
    private float spotAngle = 0;
    private float spotInc = 1;
    
    MassterBall massterBall;
    TestRoom room;
    Player player;
    
    /**
     * Default constructor for the game.
     */
    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        mouseHandler = new MouseHandler();
        timer = new Timer();
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
        
        timer.init();
        
        mouseHandler.init(screen);

        // Initialize renderer.
        renderer.init();
        
        Texture t = Texture.loadTexture("src/main/resources/textures/hexmap.png");
        try {
            Mesh cubeMesh = OBJLoader.loadMesh("src/main/resources/models/cube.obj");
            //Mesh testRoomMesh = OBJLoader.loadMesh("src/main/resources/models/room.obj");
            //src/main/resources/models/office/Amadeusmaps/Amadeus maps\cs_office_texture_0.jpg
            Mesh[] testRoomMesh = StaticMeshLoader.load("src/main/resources/models/office/cs_office.obj", "src/main/resources/models/office/");
            System.out.println(testRoomMesh.length);
            Material mat = new Material(t, 1.0f);
            cubeMesh.setMaterial(mat);
            //testRoomMesh.setMaterial(new Material());
            
            player = new Player(cubeMesh);
            
            massterBall = new MassterBall(cubeMesh);
            massterBall.setPosition(0, -1, 0);
            
            room = new TestRoom(testRoomMesh);
            room.setPosition(0, -1, 0);

//            Cube c = new Cube(new Vector3f(6, 2, -2), new Vector3f(0, 0 ,0), 0.5f);
//            Cube c2 = new Cube(new Vector3f(0, -2, -2), new Vector3f(0, 0 ,0), 0.5f);
//            Cube c3 = new Cube(new Vector3f(-2, 0, -2), new Vector3f(0, 0 ,0), 0.5f);
//            Cube c4 = new Cube(new Vector3f(2, 0, -2), new Vector3f(0, 0 ,0), 0.5f);
//
//            c.getMesh().getMaterial().setAmbientColor(new Color(0, 1, 0).toVector4f());
//            c.getMesh().getMaterial().setDiffuseColor(new Color(0, 1, 0).toVector4f());
//            c.getMesh().getMaterial().setSpecularColor(new Color(0, 1, 0).toVector4f());
//
//            c2.getMesh().getMaterial().setAmbientColor(new Color(0, 0, 1).toVector4f());
//            c2.getMesh().getMaterial().setDiffuseColor(new Color(0, 0, 1).toVector4f());
//            c2.getMesh().getMaterial().setSpecularColor(new Color(0, 0, 1).toVector4f());
//            
//            s = new Sphere(new Vector3f(0, 4, -2), new Vector3f(0, 0 ,0), 1f, 1, new Color(1, 0, 0));
//            Sphere s2;//= new Sphere(new Vector3f(0, -4, -2), new Vector3f(0, 0 ,0), 1f, 1, t);
//
//            SphereBuilder sb = new SphereBuilder();
//            sb.setPosition(new Vector3f(0, -4, -2)).setRotation(new Vector3f(0, 0, 0)).setScale(1).setColor(new Color(0, 1, 0)).setSubdivisions(3);
//            s2 = sb.build();
//            
//            Cone co = new Cone(new Vector3f(0, 2, -2), new Vector3f(0, 0 ,0), 1f, t);
//            Cone co2;// = new Cone(new Vector3f(-2, 3, -2), new Vector3f(0, 0 ,0), 1f, new Color(1, 0, 1));
//            
//            ConeBuilder cob = new ConeBuilder();
//            cob.setPosition(new Vector3f(-2, 3, -2)).setRotation(new Vector3f(00, 0, 0)).setScale(1).setHeight(3).setColor(new Color(1, 0.8f, 1));
//            co2 = cob.build();
//            
//            Cylinder cr = new Cylinder(new Vector3f(3, 2, -2), new Vector3f(10, 180, 10), 1f, t);
//            Cylinder cr2; // = new Cylinder(new Vector3f(3, 4, -2), new Vector3f(10, 180, 10), 1f, new Color(1, 1, 0));
//            
//            CylinderBuilder cb = new CylinderBuilder();
//            cb.setPosition(new Vector3f(3, 4, -2)).setRotation(new Vector3f(10, 180, 10)).setScale(1).setHeight(3).setColor(new Color(1, 1, 0));
//            cr2 = cb.build();
            
            entities = new Entity[]{massterBall, room, player};
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Position light example.
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
        Color lightColour = new Color(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        pointLights = new PointLight[]{pointLight};
        
        // Spot light example.
        lightPosition = new Vector3f(4.0f, -406.0f, -3.0f);
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
        pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutOff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight = new SpotLight(pointLight, coneDir, cutOff);
        spotLights = new SpotLight[]{spotLight};
        
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
    	// Update mouse input.
    	mouseHandler.input();
    	
        cameraInc.set(0, 0, 0);

        if (screen.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -100;
        	//player.move(0, 0, -1);
           // player.forward();
        } else if (screen.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 100;
            //player.backward();
        }

        if (screen.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -100;
            //player.left();
        	//player.move(-1, 0, 0);
        } else if (screen.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 100;
            //player.right();
        	//player.move(1, 0, 0);
        }

        if (screen.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -100;
        } else if (screen.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 100;
        }

        float lightPos = spotLights[0].getPointLight().getPosition().z;
        if (screen.isKeyPressed(GLFW_KEY_N)) {
            this.spotLights[0].getPointLight().getPosition().z = lightPos + 0.1f;
        } else if (screen.isKeyPressed(GLFW_KEY_M)) {
            this.spotLights[0].getPointLight().getPosition().z = lightPos - 0.1f;
        }
    }

    /**
     * Updates the game and logic.
     */
    public void update() {
        // Update camera position.
        //camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        player.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP, camera.getRotation().y);
    	camera.setPosition(player.getPosition().x, player.getPosition().y + 1.5f, player.getPosition().z);
    	
        // Update camera based on mouse movements
        if (mouseHandler.isRightButtonPressed()) {
        	Vector2f rotVec = mouseHandler.getDispelVec();
        	camera.moveRotation(rotVec.x * 0.2f, rotVec.y * 0.2f, 0);
        	player.moveRotation(rotVec.y * 0.2f, 0, 0);
        	//System.out.println(camera.getPosition().y);
        }
        
        // Update the camera view matrix.
        camera.updateViewMatrix();
        
        // Update spot light direction.
        spotAngle += spotInc * 0.05f;
        if (spotAngle > 2) {
        	spotInc = -1;
        } else if (spotAngle < -2) {
        	spotInc = 1;
        }
        
        double spotAngleRad = Math.toRadians(spotAngle);
        Vector3f coneDir = spotLights[0].getConeDirection();
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
        
        for (Entity entity : entities) {
            // Do something for every loaded entity.
        	
        	// Update physics of all collidable entities.
        	if (entity instanceof Collidable) {
        		((Collidable) entity).updatePhysics();
        	}
        }
    }

    /**
     * Renders the game.
     */
    public void render() {
        renderer.render(this.camera, this.entities, ambientLight, 
        		pointLights, spotLights, directionalLight);
    }
}
