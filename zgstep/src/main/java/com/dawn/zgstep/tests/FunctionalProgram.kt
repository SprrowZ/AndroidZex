package com.dawn.zgstep.tests

import java.util.*

/**
 * Create by rye
 * at 2020-05-21
 * @description: kotlin函数编程
 */

data class Country(
        val name: String,
        val continent: String,
        val population: Int
)


class CountryApp(name: String = "jack") {
    init {
        println(name)
    }
    //参数传入函数
    fun filterCountries(countries: List<Country>, test: (Country) -> Boolean): List<Country> {

        val res = mutableListOf<Country>()
        countries.forEach {
            if (test(it)) {
                res.add(it)
            }
        }

        return res
    }


    fun testMethod() {
        val countryApp = CountryApp()
        countryApp.filterCountries(mutableListOf(), fun(country: Country): Boolean {
            return country.continent == "EU"
        })
    }


    private fun filterBook(bookName: List<Book>, filter: (Book) -> Boolean): List<Book> {
        val needBook = mutableListOf<Book>()
        bookName.forEach {
            if (filter(it)) {
                needBook.add(it)
            }
        }
        return needBook
    }

    private fun testFilterBook() {
        filterBook(mutableListOf(), fun(book: Book): Boolean {
            return book.bookName == "百年孤独"
        })
    }

    /**中缀函数表达式*/
    infix fun call(name: String) {
        println("$name call me")
    }
}

data class Book(val bookName: String, val author: String, val date: Date)

class Monkey(val weight: Double = 0.0, val age: Int = 0, val color: String = "blue"){
    val sex:String
    init {
        sex = if (color == "yellow") "muHou" else "gongHou"
    }
}

class T1 {


    fun filter(country: Country): Boolean {//过滤出欧洲国家
        if (country.name == "EU") {
            return true
        }
        return false
    }

    companion object {
        fun testRange() {
            for (i in 1..10) {

            }
            val str = "abc".."xyz"

            //中缀表达式
            mapOf(
                    1 to "one",
                    2 to "two",
                    3 to "three"
            )
        }

        fun testThirdStr() {
            val giao = """I giao ,
                          |you giao fou? biu~""".trimMargin()
            println(giao)
        }

    }

    //中缀函数
    fun testInfix() {
        val countryApp = CountryApp()
        countryApp call "Jack"
    }

    //测试构造方法重载
    fun testConstructors() {
        val monkey1 = Monkey()
        val monkey2 = Monkey(3.33)
        val monkey3 = Monkey(3.33, 9)
        val monkey4 = Monkey(3.33, 9, "yellow")
    }


}


fun getAgeByBirth(date:Date):Int{
    //do sth
    return 1
}
//主从构造方法

class Bird3(age: Int){
    val age:Int
    init {
        this.age = age
    }

    constructor(birth:Date):this(getAgeByBirth(birth)){
        //...
    }

}


fun main(args: Array<String>) {
    T1.testThirdStr()
    //主从构造方法
    val bird3 = Bird3(Date())
}

