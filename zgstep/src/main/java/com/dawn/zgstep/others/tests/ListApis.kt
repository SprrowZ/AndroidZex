package com.dawn.zgstep.others.tests

object ListApis {
    fun dealSth() {
        println("-----dealSth")
    }

    private var mAnyList = mutableListOf<Any>("hello", 1, true, "oh!yeah~", 998)
    private var mStringList = mutableListOf<String>("fun", "ok", "are", "you", "ok", "ok")
    private var mIntegerList = mutableListOf<Int>(1, 98, 726, 1)
    fun testApis() {
        //1. any 如果至少有一个元素与判断条件相符，则返回true
        val anyResult = mStringList.any {
            it.length > 2
        }
        print("any--result:$anyResult")
        //2. all 如果全部元素与判断条件相符，则返回true
        val allResult = mStringList.all {
            it.length > 2
        }
        println("all--result:$allResult")
        //3. associate,associate 通过指定的条件，把list转换成map！
        val associateResult = mStringList.associate {
            Pair("key$it", "value-$it")
        }
        println("associate--result:$associateResult")
        //4. average 求集合的平均值，仅限Byte/Short/Int/Long/Float/Double
        val averageList = mIntegerList.average()
        println("average--result:$averageList")
        //5.【这个有点意思=.=】 component1,....component5 返回集合的第n个元素，越界返回ArrayIndexOutOfBoundsException
        val componentResult = mStringList.component5()
        println("component--result:$componentResult")
        //6. contain 如果指定元素可以在集合中找到，则返回true
        //7. containsAll 如果指定集合中所有元素都可以在目标集合中找到，则返回true
        val containsAllResult = mStringList.containsAll(listOf("ok", "are"))
        println("containsAll--result:$containsAllResult")
        //8. count 返回与判断条件相符的元素个数
        val countResult = mStringList.count {
            it.length > 3
        }
        println("count--result:$countResult")
        //9. distinct 返回一个只包含不同元素的数组,去重
        val distinctResult = mStringList.distinct()
        println("distinct--result:$distinctResult")
        //10. distinctBy 指定过滤条件，同样只包含不同元素，distinct加强版
        val distinctByResult = mStringList.distinctBy {
            it.length <= 3
        }
        println("distinctBy--result:$distinctByResult")
        //11.

    }

    @JvmStatic
    fun main(args: Array<String>) {

    }
}