package com.dawn.zgstep.tests

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

    /**
     * 测试 Kotlin 位运算
     */
    fun testBit1() {
        val n1 = 1 shl 1
        val n2 = 1 shl 2
        val n3 = 1 shl 3
        val x1 = n1 or n2 or n3
        println("x1:$x1")
        //① 测试是奇数还是偶数
        val x2 = 123 and 1
        if (x2 == 1) {
            println("x2是奇数")
        } else {
            println("x2是偶数")
        }
        //② 求平均值...可能会超过最大范围值，懒得写...

        //③ int类型值交换
        var y1 = 138
        var y2 = 14
        println("交换前，y1:$y1,y2:$y2")
        y1 = y1 xor y2
        y2 = y2 xor y1
        y1 = y1 xor y2
        println("交换后，y1:$y1,y2:$y2")
        //④ 求相反数
        var v1 = 123
        var v2 = v1.inv() + 1
        println("v1:$v1,相反数:$v2")
        //⑤ 权限控制
        val permission = NewPermission()
        permission.resetPermission(11) //1011
        val hasPermission = permission.hasPermission(1)
        permission.disable(8) //1000
        permission.enable(4) // 0100
        //现在权限应该是0111，也就是7
        println("hasPermission:$hasPermission，currentPermission:${permission.currentPermission}")
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
    TestKotlins.testBit1()
}




