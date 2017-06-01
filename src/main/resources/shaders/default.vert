#version 150

in vec3 position;
in vec3 inColor;

out vec3 exColor;

uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * vec4(position, 1.0);
    exColor = inColor;
}
