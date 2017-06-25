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

package me.oskarmendel.mass.util;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import me.oskarmendel.mass.gfx.Material;
import me.oskarmendel.mass.gfx.Mesh;

/**
 * This class is used together with Assimp to load 
 * static meshes without animations.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name StaticMeshLoader.java
 */
public class StaticMeshLoader {
	
	/**
	 * 
	 * @param resourcePath
	 * @param texturePath
	 * @return
	 * @throws Exception
	 */
	public static Mesh[] load(String resourcePath, String texturePath) throws Exception {
	    return load(resourcePath, texturePath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals);
	}
	
	/**
	 * 
	 * @param resourcePath
	 * @param texturesDir
	 * @param flags
	 * @return
	 * @throws Exception
	 */
	public static Mesh[] load(String resourcePath, String texturesDir, int flags) throws Exception {
		AIScene aiScene = Assimp.aiImportFile(resourcePath, flags);
        if (aiScene == null) {
            throw new Exception("Error loading model");
        }
        
        int numMaterials = aiScene.mNumMaterials();
        PointerBuffer aiMaterials = aiScene.mMaterials();
        List<Material> materials = new ArrayList<>();
        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
            processMaterial(aiMaterial, materials, texturesDir);
        }
        
        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        Mesh[] meshes = new Mesh[numMeshes];
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            Mesh mesh = processMesh(aiMesh, materials);
            meshes[i] = mesh;
        }
        
        return meshes;
	}
	
	private static void processMaterial(AIMaterial aiMaterial, List<Material> materials, String texturePath) throws Exception{
		
	}
	
	/**
	 * 
	 * @param aiMesh
	 * @param materials
	 * @return
	 */
	private static Mesh processMesh(AIMesh aiMesh, List<Material> materials) {
		List<Float> vertices = new ArrayList<>();
		List<Float> textures = new ArrayList<>();
		List<Float> normals = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		
		processVertices(aiMesh, vertices);
		processTextureCoordinates(aiMesh, textures);
		processNormals(aiMesh, normals);
		processIndices(aiMesh, indices);
		
		Mesh mesh = new Mesh(ArrayHelper.listToArrayFloat(vertices), 
				ArrayHelper.listToArrayFloat(textures),
				ArrayHelper.listToArrayFloat(normals), ArrayHelper.listToArrayInt(indices));
		
		Material material;
		
		int materialIdx = aiMesh.mMaterialIndex();
        if (materialIdx >= 0 && materialIdx < materials.size()) {
            material = materials.get(materialIdx);
        } else {
            material = new Material();
        }
        mesh.setMaterial(material);

        return mesh;
	}
	
	/**
	 * 
	 * @param aiMesh
	 * @param vertices
	 */
	private static void processVertices(AIMesh aiMesh, List<Float> vertices) {
		AIVector3D.Buffer aiVertices = aiMesh.mVertices();
		
		while(aiVertices.remaining() > 0) {
			AIVector3D aiVertex = aiVertices.get();
	        vertices.add(aiVertex.x());
	        vertices.add(aiVertex.y());
	        vertices.add(aiVertex.z());
		}
	}
	
	/**
	 * 
	 * @param aiMesh
	 * @param textures
	 */
	private static void processTextureCoordinates(AIMesh aiMesh, List<Float> textures) {
		AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);
		int numTextCoords = aiTextures != null ? aiTextures.remaining() : 0;
		
		for (int i = 0; i < numTextCoords; i++) {
			AIVector3D textCoord = aiTextures.get();
            textures.add(textCoord.x());
            textures.add(1 - textCoord.y());
		}
	}
	
	/**
	 * 
	 * @param aiMesh
	 * @param normals
	 */
	private static void processNormals(AIMesh aiMesh, List<Float> normals) {
		AIVector3D.Buffer aiNormals = aiMesh.mNormals();
		
		while(aiNormals != null && aiNormals.remaining() > 0) {
			AIVector3D aiVertex = aiNormals.get();
	        normals.add(aiVertex.x());
	        normals.add(aiVertex.y());
	        normals.add(aiVertex.z());
		}
	}
	
	/**
	 * 
	 * @param aiMesh
	 * @param indices
	 */
	private static void processIndices(AIMesh aiMesh, List<Integer> indices) {
		int numFaces = aiMesh.mNumFaces();
		AIFace.Buffer aiFaces = aiMesh.mFaces();
		
		for (int i = 0; i < numFaces; i++) {
			AIFace aiFace = aiFaces.get();
			IntBuffer buffer = aiFace.mIndices();
			while (buffer.remaining() > 0) {
				indices.add(buffer.get());
			}
		}
	}
}
