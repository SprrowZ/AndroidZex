#extension GL_OES_EGL_image_external : require

precision mediump float;
varying vec2 ft_Position;
uniform samplerExternalOES sTexutre;

void main() {
    gl_FragColor = texture2D(sTexutre,ft_Position);
}