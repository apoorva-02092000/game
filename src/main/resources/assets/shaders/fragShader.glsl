#version 330 core
uniform sampler2D TEX_SAMPLER;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(){
    if(fTexCoords != (vec2(0, 0))){
        color = texture(TEX_SAMPLER , fTexCoords);
        //color = vec4(rand(color.xy), color.y, color.z, color.w);
    }else{
        color = fColor;
    }

}
