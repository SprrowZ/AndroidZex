package com.rye.catcher.base.dbs

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * 动漫表
 */
open class Anime:RealmObject(){
    @PrimaryKey
    var id:String?=null
    //中文name
    @Required
    var chineseName:String?=null
    //原版name
    var originalName:String?=null
    //别名
    var alias:String?=null
    //原作
    var originalAuthor:String?=null
    //导演
    var director:String?=null
    //发行时间
    var publishTime:String?=null
    //是否已经完结
    var isEnd:Boolean?=false
    //国籍
    var nationality:String?=null
    //简介
    var introduction:String?=null
    //详情
    var datails:String?=null
    //角色
    var characters:RealmList<Character>?=null
    //主角
    var leads:RealmList<Character>?=null

}