#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 textureCoords;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;

void main(){
    fColor = color;
    fTexCoords = textureCoords;
    gl_Position = uProjection * uView * vec4(position, 0.0, 1.0);
}
