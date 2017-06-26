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

package me.oskarmendel.mass.entity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.IndexedMesh;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import me.oskarmendel.mass.gfx.Mesh;
import me.oskarmendel.mass.phys.Collidable;

/**
 * This class represents a testing room.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name TestRoom.java
 */
public class TestRoom extends Entity implements Collidable {

	/**
	 * Shape of the testing room.
	 */
	private CollisionShape collisionShape;
	
	/**
	 * RigidBody for the testing room.
	 */
	private RigidBody rigidBody;
	
	
	
	/**
	 *  TODO: add comments
	 * @param mesh
	 */
	public TestRoom(Mesh mesh) {
		super(mesh);
		
		initPhysics();
	}
	
	/**
	 * 
	 * @param meshes
	 */
	public TestRoom(Mesh[] meshes) {
		super(meshes);
		
		initPhysics();
	}
	
	/**
	 * 
	 */
	@Override
	public void initPhysics() {
		DefaultMotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, -1, 0), 1.0f))); 

		//TODO: Un uglify this code.
		
		// Construct collision shape based on the mesh vertices in the Mesh.
		float[] positions = getMesh().getPositions();
		int[] indices = getMesh().getIndices();
		
		IndexedMesh indexedMesh = new IndexedMesh();
		indexedMesh.numTriangles = indices.length / 3;
		indexedMesh.triangleIndexBase = ByteBuffer.allocateDirect(indices.length*4).order(ByteOrder.nativeOrder());
        indexedMesh.triangleIndexBase.asIntBuffer().put(indices);
        indexedMesh.triangleIndexStride = 3 * 4;
        indexedMesh.numVertices = positions.length / 3;
        indexedMesh.vertexBase = ByteBuffer.allocateDirect(positions.length*4).order(ByteOrder.nativeOrder());
        indexedMesh.vertexBase.asFloatBuffer().put(positions);
        indexedMesh.vertexStride = 3 * 4;
		
		TriangleIndexVertexArray vertArray = new TriangleIndexVertexArray();
		vertArray.addIndexedMesh(indexedMesh);
		
		collisionShape = new BvhTriangleMeshShape(vertArray, true);
		
		
		RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, collisionShape, new Vector3f(0,0,0)); 
		rigidBody = new RigidBody(groundRigidBodyCI);
		this.rigidBody.activate();
	}

	/**
	 * 
	 */
	@Override
	public void updatePhysics() {
		Vector3f v = this.rigidBody.getWorldTransform(new Transform()).origin;
		this.setPosition(v.x, v.y, v.z);
	}

	/**
	 * 
	 */
	@Override
	public RigidBody getRigidBody() {
		return this.rigidBody;
	}

}
