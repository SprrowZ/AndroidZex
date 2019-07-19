package com.rye.catcher.activity.fragment

import android.widget.TextView
import com.rye.catcher.BaseFragment
import com.rye.catcher.R
import com.rye.catcher.base.dbs.Person
import com.rye.catcher.base.dbs.RealmDataUtils

//Realm
class RealmFragment: BaseFragment() {

    lateinit  var insert:TextView
    lateinit var  update:TextView
    lateinit var  delete:TextView
    lateinit var  search:TextView
    lateinit var  personDb: RealmDataUtils.PersonDao
    override fun getLayoutResId(): Int {
      return  R.layout.fragment_realm_test
    }

    override fun initData() {
        insert=view!!.findViewById(R.id.insert)
        update=view!!.findViewById(R.id.update)
        delete=view!!.findViewById(R.id.delete)
        search=view!!.findViewById(R.id.search)
        personDb=RealmDataUtils.getInstance().personDb

    }

    override fun initEvent() {
        insert.setOnClickListener {
            personDb.doInsertOrUpdate(virtualData())
        }

        delete.setOnClickListener {
            personDb.doDelete(virtualData())
        }



    }

    fun virtualData():Person{
        val person=Person()
        person.id=7
        person.name="RyeCatcher"
        person.job="Coder"
        person.home_address="HeNan"
        return person
    }

}