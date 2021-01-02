package com.dawn.zgstep.kotlins

object TestKotlins {
    val dataList = mutableListOf<Fruit>()

    fun testMethod() {
        val dataList = ArrayList<Boolean>()
        for (index in 1..100) {
            dataList.add(false)
            println(dataList.size)
        }
    }

    private fun getFakeDatas(): List<Fruit> {
        val datas = mutableListOf<Fruit>()
        for (i in 1..20) {
            val item = if (i < 10) {
                Fruit("yellow", "banana")
            } else {
                Fruit("red", "apple")
            }

            datas.add(item)
        }
        return datas
    }

    /** map  遍历集合*/
    fun testMap() {
        val dataList = getFakeDatas()
        val afterList = dataList.map {
            it.name = it.name + " is 香蕉"
            it
        }

        afterList.map {
            println(it.name)
        }
    }

    /**filter filterNotNull filterNot 过滤集合*/
    fun testFilter() {
        val dataList = getFakeDatas()
        val afterList = dataList.filter {
            it.name == "apple"
        }

        afterList.map {
            println(it.name)
        }
    }

    /**count 统计个数*/
    fun testCount() {
        val dataList = getFakeDatas()
        val count = dataList.count {
            it.name == "apple"
        }

        println("how many apple you have: $count")
    }
    /**sumBy、sum、fold、reduce 求和*/

    /**groupBy 分组*/
    fun testGroupBy() {
        val dataList = getFakeDatas()
        val map = dataList.groupBy {
            it.name
        }
        map.keys.map {
            println("mapKey:$it")
        }
    }

    /**扁平化参数处理*/
    fun testFlatten() {
        val dataList = listOf(listOf(1, 2, 3), listOf("2", "3", "4"))
        val resultList = dataList.flatten()
        for (item in resultList) {
            println(item.javaClass)
        }
    }

    fun testFlatMap() {
        val dataList = listOf(getFakeDatas(), getFakeDatas())
        val resultList = dataList.flatMap {
            it.map { it1 ->
                it1.name
            }
        }
        resultList.map {
            println(it)
        }
    }

    //引用传递
    val apple = Fruit("red", "apple")
    fun testModify(apple: Fruit) {
        apple.name = "banana"
        println(apple)
    }

    //值传递
    var number = 1
    fun testNumber(num: Int) {
        number++
        println(num)
    }

    fun testHigh(predicate: (Int, Int) -> Int) {
        println(predicate)
    }
}

data class Fruit(val color: String, var name: String)

fun main() {
//    TestKotlins.testMethod()
//    runOnMainThread()
    // TestKotlins.testMap()
    //  TestKotlins.testFilter()
    //  TestKotlins.testCount()
    //   TestKotlins.testGroupBy()
    //  TestKotlins.testFlatten()
    // TestKotlins.testFlatMap()
//    TestKotlins.testModify(TestKotlins.apple)
//    println(TestKotlins.apple)
//
//    TestKotlins.testNumber(TestKotlins.number)
//    println(TestKotlins.number)

}

fun test222(num1: Int, num2: Int): Int {
    return num1 + num2
}



