#version 150

in vec3 exColor;

out vec4 fragColor;

void main() {
    fragColor = vec4(exColor, 1.0);
}
