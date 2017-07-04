#version 330

const int MAX_WEIGHTS = 4;
const int MAX_JOINTS = 150;

vec3 position;
vec2 texCoord;
vec3 vertexNormal;
vec4 jointWeights;
ivec4 jointIndices;
mat4 modelInstancedMatrix;

uniform int isInstanced;
uniform mat4 modelNonInstancedMatrix;
uniform mat4 lightViewMatrix;
uniform mat4 jointsMatrix[MAX_JOINTS];
uniform mat4 orthoProjectionMatrix;

void main() {
    vec4 initPos = vec4(0, 0, 0, 0);
    mat4 modelMatrix;
    
    if ( isInstanced > 0 ) {
        modelMatrix = modelInstancedMatrix;
        initPos = vec4(position, 1.0);
    } else {
        modelMatrix = modelNonInstancedMatrix;

        int count = 0;
        for(int i = 0; i < MAX_WEIGHTS; i++) {
            float weight = jointWeights[i];
            if(weight > 0) {
                count++;
                int jointIndex = jointIndices[i];
                vec4 tmpPos = jointsMatrix[jointIndex] * vec4(position, 1.0);
                initPos += weight * tmpPos;
            }
        } if (count == 0) {
            initPos = vec4(position, 1.0);
        }
    }
    gl_Position = orthoProjectionMatrix * lightViewMatrix * modelMatrix * initPos;
}
