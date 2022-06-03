precision mediump float;
varying vec2 ft_Position;
uniform sampler2D sTexture; //sampler2D 是GLES 内置的取样器

void main() {
    gl_FragColor = texture2D(sTexture, ft_Position); //texture2D是内置函数，用于2D纹理取样
}
