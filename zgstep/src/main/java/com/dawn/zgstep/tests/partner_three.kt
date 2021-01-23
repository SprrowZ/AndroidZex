package com.dawn.zgstep.tests

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

class partner_three {

}

class Bird2(weight: Double, age: Int, color: String) {

    val name: String by lazy {
        if (TextUtils.equals(color, "yellow")) "小黄" else "小白"
    }


    init {
        println("do something...")
    }


    val weight: Double
    val sex: String

    init {
        this.weight = weight
        sex = if (TextUtils.equals(color, "yellow")) "female" else "male"
    }

    fun printWeight() {
        println(weight)
    }
}

/**
 * 从构造方法，必须得引用到主构造方法
 */
class Text @JvmOverloads constructor( color: String, name: String) {

    constructor(color: String,name: String,sex:Boolean):this(color,name)

}

class KotlinView : View {//主从构造方法
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defaultStyleAttr: Int) :
            super(context, attrs, defaultStyleAttr)
}

/**
 * 类的访问控制权限
 */
open class Bird{
   open fun fly(){
        println("I can fly")
    }

}


class Penguin :Bird(){

    override fun fly(){
        println(this.javaClass.name + "also fly")
    }


}

fun main() {
    val penguin=Penguin()
    penguin.fly()
}

