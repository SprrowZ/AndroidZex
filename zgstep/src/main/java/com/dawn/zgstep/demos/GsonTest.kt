package com.dawn.zgstep.demos

import com.google.gson.Gson

val gsonStr1 = "{\"name\":\"张三\",\"age\":24}"
val gsonStr2 = "{\"data\":{\"audio_ids\":[223,445,123],\"chapter_ids\":{\"success\":[3224]},\"win\":true,\"prize_name\":\"袋迪请客\"}}"
var idList: MutableList<Int>? = null
fun main(args: Array<String>) {

    GsonTest.simplefromJson(gsonStr1)
    GsonTest.normalfromJson(gsonStr2)
    GsonTest.normalToJson()
}

object GsonTest {
    /**
     * json转实体类
     */
    fun simplefromJson(jsonString: String) {
        val user = Gson().fromJson(jsonString, GsonUser::class.java) as GsonUser
        println("name:${user.name} ,sex:${user.sex}")
    }

    fun normalfromJson(jsonString: String) {
        val audio = Gson().fromJson(jsonString, GsonResult::class.java) as GsonResult
        val audioList = audio.data?.audio_ids as ArrayList
        for (data in audioList.withIndex()) {
            println("index:${data.index},value:${data.value}")
        }
        println("win:${audio.data?.win},prize_name:${audio.data?.prize_name}")
    }

    /**
     * 实体类转jsonString
     */
    fun normalToJson() {

        var gsonResult = GsonResult()
        val data = GsonResult.AudioBean()
        data?.apply {
            val idList = mutableListOf(123, 456, 789)
            audio_ids = idList
            chapter_ids = GsonResult.ChapterBean(mutableListOf(12345, 45678))
            prize_name = "毁灭者的终结"
            win = true
        }
        gsonResult.data = data
        val jsonString = Gson().toJson(gsonResult)
        println(jsonString)
    }
}