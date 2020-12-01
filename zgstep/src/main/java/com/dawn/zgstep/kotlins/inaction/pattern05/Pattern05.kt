package com.dawn.zgstep.kotlins.inaction.pattern05


data class Person(val name: String, val age: Int)

object Test {
    fun test1(params: String): String {
        //顶层函数,延迟初始化
        val createPerson = ::Person
        val p = createPerson("二狗", 18)
        createPerson::name
        println(p)
        return params
    }

    fun test2() {
        val method = ::test1
        println(method("11"))
    }

    fun test3() {
        val persons = listOf<Person>(Person("Alice", 19), Person("Bob", 31))
        persons.map {
            it.name
        }
        //map+成员引用
        val nameList = persons.map(Person::name)
        println(nameList)
        //all、any
        val young = {p:Person -> p.age <= 20}
       val allYoung =  persons.all(young)
       val hasYoung = persons.any(young)
    }
}

fun main() {
    Test.test3()
}