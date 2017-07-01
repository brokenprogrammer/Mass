#version 150

void main() {
    gl_FragDepth = gl_FragCoord.z;
}