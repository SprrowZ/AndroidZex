attribute vec4 v_Position;//顶点坐标
attribute vec2 f_Position;//纹理坐标
varying vec2 ft_Position; //纹理坐标传递给片元着色器

uniform mat4 u_Matrix; //投影矩阵
void main() {
    ft_Position = f_Position;
    gl_Position = v_Position*u_Matrix;
}
