#version 150

in vec3 position;
in vec2 texCoord;
in vec3 vertexNormal;

out outTexCoord;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

void main() {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
	outTexCoord = texCoord;
}