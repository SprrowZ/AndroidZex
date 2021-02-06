package com.dawn.zgstep.kotlins.inaction.pattern08

val sum = { x: Int, y: Int -> x + y }
val action = { println(sum) }

//         类型                值；x，y的类型已经在声明中定义了，不需要再重复定义类型了
val sum2: (Int, Int) -> Int = { x, y -> x + y }
val action2: () -> Unit = { println() }

class Pattern08 {
    /**
     * 输入一个列表，拿到符合条件的元素的个数
     */
    fun <T> getLength(items:List<T>,action3: (List<T>) -> List<T>) {
        val size = action3.invoke(items).size
        println(size)
    }

    fun test1(items: List<String>): List<String> {
        return items.filter {
            it.length > 5
        }
    }

    val items = mutableListOf<String>("111", "333333", "222", "33")
    fun testList() {
        items.forEach {

        }
        items.let {

        }
    }
}

fun main() {

    val instance = Pattern08()
    instance.getLength<String>(instance.items) {
        instance.test1(it)
    }
}