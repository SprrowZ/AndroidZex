precision mediump float; //定义数据精度
//uniform vec4 u_Color;
varying vec4 v_Color;
void main()
{
    gl_FragColor = v_Color;
}