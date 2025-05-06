package com.dawn.zgstep.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dawn.zgstep.R
import com.dawn.zgstep.ui.activity.ui.main.NestedScrollFragment

class NestedScrollActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun jump(context: Context) {
            val intent = Intent(context, NestedScrollActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NestedScrollFragment.newInstance())
                .commitNow()
        }
    }
}