#version 150

const int MAX_WEIGHTS = 4;
const int MAX_JOINTS = 150;
const int NUM_CASCADES = 3;

in vec3 position;
in vec2 texCoord;
in vec3 vertexNormal;
in vec4 jointWeights;
in ivec4 jointIndices;
in mat4 modelInstancedMatrix;
in vec2 texOffset;
in float selectedInstanced;

out vec2 outTexCoord;
out vec3 mvVertexNormal;
out vec3 mvVertexPos;
out vec4 mlightviewVertexPos[NUM_CASCADES];
out mat4 outModelViewMatrix;
out float outSelected;

uniform int isInstanced;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 modelNonInstancedMatrix;
uniform mat4 jointsMatrix[MAX_JOINTS];
uniform mat4 lightViewMatrix[NUM_CASCADES];
uniform mat4 orthoProjectionMatrix[NUM_CASCADES];
uniform int numCols;
uniform int numRows;
uniform float selectedNonInstanced;


void main() {
	vec4 initPos = vec4(0, 0, 0, 0);
    vec4 initNormal = vec4(0, 0, 0, 0);
    mat4 modelMatrix;
    if ( isInstanced > 0 ) {
        outSelected = selectedInstanced;
        modelMatrix = modelInstancedMatrix;

        initPos = vec4(position, 1.0);
        initNormal = vec4(vertexNormal, 0.0);
    }
    else {
        outSelected = selectedNonInstanced;
        modelMatrix = modelNonInstancedMatrix;

        int count = 0;
        for(int i = 0; i < MAX_WEIGHTS; i++) {
            float weight = jointWeights[i];
            if(weight > 0) {
                count++;
                int jointIndex = jointIndices[i];
                vec4 tmpPos = jointsMatrix[jointIndex] * vec4(position, 1.0);
                initPos += weight * tmpPos;

                vec4 tmpNormal = jointsMatrix[jointIndex] * vec4(vertexNormal, 0.0);
                initNormal += weight * tmpNormal;
            }
        }
        if (count == 0) {
            initPos = vec4(position, 1.0);
            initNormal = vec4(vertexNormal, 0.0);
        }
    }
    mat4 modelViewMatrix =  viewMatrix * modelMatrix;
    vec4 mvPos = modelViewMatrix * initPos;
    gl_Position = projectionMatrix * mvPos;

    // Support for texture atlas, update texture coordinates
    float x = (texCoord.x / numCols + texOffset.x);
    float y = (texCoord.y / numRows + texOffset.y);
    outTexCoord = vec2(x, y);

    mvVertexNormal = normalize(modelViewMatrix * initNormal).xyz;
    mvVertexPos = mvPos.xyz;
    for (int i = 0 ; i < NUM_CASCADES ; i++) {
        mlightviewVertexPos[i] = orthoProjectionMatrix[i] * lightViewMatrix[i] * modelMatrix * vec4(position, 1.0);
    }
    outModelViewMatrix = modelViewMatrix;
}
