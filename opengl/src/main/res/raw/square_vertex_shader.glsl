attribute vec4 vPosition;
uniform mat4 u_Matrix;
void main(){
    gl_Position = u_Matrix*vPosition;
}