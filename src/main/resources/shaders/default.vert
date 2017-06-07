#version 150

in vec3 position;
in vec2 texCoord;
in vec3 normals;

out vec2 outTexCoord;
out vec3 vertexNormal;
out vec3 vertexPos;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

void main() {
    vec4 pos = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * pos;
    outTexCoord = texCoord;
    vertexNormal = normalize(modelViewMatrix * vec4(normals, 0.0)).xyz;
    vertexPos = pos.xyz;
}
