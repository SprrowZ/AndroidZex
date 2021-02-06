package com.dawn.zgstep.kotlins.inaction.pattern07

/**
 * 重载运算符，operator关键字
 * 意义在于，实体类也可以使用运算符操作？？？
 */
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }
}

fun main() {
    val p1 = Point(10, 20)
    val p2 = Point(10, 30)
    val p3 = p1 + p2
    println(p3)
}