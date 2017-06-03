package me.oskarmendel.mass.util;

import me.oskarmendel.mass.gfx.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class handles loading of complex models.
 *
 * @author Oskar Mendel
 * @version 0.00.00
 * @name OBJLoader.java
 */
public class OBJLoader {

    /**
     * Loads an OBJ file at the specified path and parses it into
     * a Mesh.
     *
     * OBJ files are structured through each line defines either a
     * vertex, texture coordinate or polygon. Each line has either one
     * of the following identifiers:
     *
     * # - Comment line.
     * v - Geometric vertex with coordinates (x, y, z w).
     * vn - Vertex normal coordinates (x, y, z).
     * vt - Texture coordinates.
     * f - Face definition. Example: f 6/4/1 3/5/3 7/6/5
     *
     * Example line: v 0.155 0.211 0.32 1.0
     * Defines a geometric vertex with the coordinates (0.155, 0.211, 0.32, 1.0).
     *
     * @param path - File path for the OBJ file.
     *
     * @return - Parsed Mesh from the specified OBJ file.
     *
     * @throws Exception
     */
    public static Mesh loadMesh(String path) throws Exception{
        List<String> lines = readAllLines(path);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            // Split when one or more whitespace characters were encountered.
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    vertices.add(vec3f);
                    break;
                case "vn":
                    // Vertex normal.
                    Vector3f vec3fNorm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    normals.add(vec3fNorm);
                    break;
                case "vt":
                    // Texture coordinate.
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));

                    textures.add(vec2f);
                    break;
                case "f":
                    // Face
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);

                    faces.add(face);
                    break;
                default:
                    // Ignore all other lines.
                    break;
            }
        }

        return reorderLists(vertices, textures, normals, faces, 1);
    }

    /**
     * TODO: Write this javadoc.
     * @param posList
     * @param textCoordList
     * @param normalsList
     * @param facesList
     * @param instances
     *
     * @return
     */
    private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList,
                                     List<Vector3f> normalsList, List<Face> facesList, int instances) {

        List<Integer> indices = new ArrayList<>();

        float[] posArr = new float[posList.size() * 3];
        int i = 0;
        for (Vector3f pos : posList) {
            posArr[i * 3] = pos.x;
            posArr[i * 3 + 1] = pos.y;
            posArr[i * 3 + 2] = pos.z;
            i++;
        }

        float[] textCoordArr = new float[posList.size() * 2];
        float[] normalsArr = new float[posList.size() * 3];

        for (Face face : facesList) {
            IndexGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for (IndexGroup indexValue : faceVertexIndices) {
                processFaceVertex(indexValue, textCoordList, normalsList, indices, textCoordArr, normalsArr);
            }
        }

        int[] indicesArr = new int[indices.size()];
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();

        Mesh mesh;

        /*if (instances > 1) {
        } else {
            mesh = new Mesh(posArr, textCoordArr, normalsArr, indicesArr);
        }*/


        mesh = new Mesh(posArr, textCoordArr, normalsArr, indicesArr);
        return mesh;
    }

    /**
     * TODO: Write this javadoc.
     *
     * @param indices
     * @param textCoordList
     * @param normalsList
     * @param indicesList
     * @param textCoordArr
     * @param normalsArr
     */
    private static void processFaceVertex(IndexGroup indices, List<Vector2f> textCoordList,
                                          List<Vector3f> normalsList, List<Integer> indicesList,
                                          float[] textCoordArr, float[] normalsArr) {
        // Set index for vertex coordinates.
        int positionIndex = indices.indexPos;
        indicesList.add(positionIndex);

        // Reorder texture coordinates.
        if (indices.indexTextCoordinate >= 0) {
            Vector2f textCoord = textCoordList.get(indices.indexTextCoordinate);
            textCoordArr[positionIndex * 2] = textCoord.x;
            textCoordArr[positionIndex * 2 + 1] = textCoord.y;
        }
        if (indices.indexVecNormal >= 0) {
            Vector3f vecNormal = normalsList.get(indices.indexVecNormal);
            normalsArr[positionIndex * 3] = vecNormal.x;
            normalsArr[positionIndex * 3 + 1] = vecNormal.y;
            normalsArr[positionIndex * 3 + 2] = vecNormal.z;
        }
    }

    /**
     * TODO: Wtite this javadoc
     * @param path
     * @return
     * @throws Exception
     */
    private static List<String> readAllLines(String path) throws  Exception {
        List<String> list = new ArrayList<>();

        try(InputStream in = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        }

        return list;
    }

    /**
     * TODO: Write this javadoc
     */
    protected static class Face {

        /**
         * List of index groups for a face triangle, three vertices per face.
         */
        private IndexGroup[] indexGroups;

        public Face(String v1, String v2, String v3) {
            indexGroups  = new IndexGroup[3];

            // Parse the three index groups
            indexGroups[0] = parseIndexGroup(v1);
            indexGroups[1] = parseIndexGroup(v2);
            indexGroups[2] = parseIndexGroup(v3);
        }

        /**
         * TODO: Write this javadoc
         * @param line
         * @return
         */
        private IndexGroup parseIndexGroup(String line) {
            IndexGroup indexGroup = new IndexGroup();

            // Split when a '/' is encountered.
            String[] tokens = line.split("/");
            int len = tokens.length;

            indexGroup.indexPos = Integer.parseInt(tokens[0]) - 1;

            if (len > 1) {
                // Can be empty if no texture coordinates are defined
                String textureCoord = tokens[1];
                indexGroup.indexTextCoordinate = textureCoord.length() > 0 ?
                        Integer.parseInt(textureCoord) - 1 : IndexGroup.NO_VALUE;

                if (len > 2) {
                    indexGroup.indexVecNormal = Integer.parseInt(tokens[2]) - 1;
                }
            }

            return indexGroup;
        }

        /**
         * TODO: Write javadoc
         * @return
         */
        public IndexGroup[] getFaceVertexIndices() {
            return indexGroups;
        }
    }

    /**
     * This inner class is a representation of one of a Face's index
     * groups.
     *
     * A Face is defined using three index groups, Example:
     * f 6/4/1 3/5/3 7/6/5
     */
    protected static class IndexGroup {
        public static final int NO_VALUE = -1;

        public int indexPos;

        public int indexTextCoordinate;

        public int indexVecNormal;

        public IndexGroup() {
            indexPos = NO_VALUE;
            indexTextCoordinate = NO_VALUE;
            indexVecNormal = NO_VALUE;
        }
    }
}
