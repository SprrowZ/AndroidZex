package com.dawn.zgstep.others.demos

class GsonResult {
    var data: AudioBean?=null
    class AudioBean{
        var audio_ids:MutableList<Int>?=null//用arrayList即可
        var chapter_ids: ChapterBean?=null
        var win:Boolean=false
        var prize_name:String?=null
    }
    class ChapterBean(suc: MutableList<Int>) {
        var success:MutableList<Int>?= suc
    }

}