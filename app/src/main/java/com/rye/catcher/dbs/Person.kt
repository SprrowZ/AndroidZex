package com.rye.catcher.dbs

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

 open class Person:RealmObject() {
    @PrimaryKey
    var id : Long ?= 0
    var sex: Boolean ?= false
    @Required
    var name:String ?= null
    var home_address:String ?= null
    var job:String ?= null
    var company_address:String ?= null
    var interest:String ?=null
    var singleDog:Boolean ?=true
    var dogs:RealmList<Dog> ?= null

}