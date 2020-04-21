package com.dawn.zgstep.others.tests

class Enlist {

    companion object {

        var contention: String = "Ss..sS"

        fun conspire(){
            println("----conspire sth")
        }

        @JvmField var woollen :String ="harvest"

        @JvmStatic var genre :String = "romantic"

        @JvmStatic fun harbor(){
            println("----harbor Hong Kong")
        }
    }
}

class Mango @JvmOverloads constructor(val name:String, origin:String ="dd", year:Int = -1){


    companion object {
        @JvmOverloads
        fun dealSth(target: String, isFollow: Boolean = false) {
            println(target)
        }

    }


}