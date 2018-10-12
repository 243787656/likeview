package com.example.likeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.likeview.util.LogUtil
import kotlinx.android.synthetic.main.activity_main.*
import com.example.likeview.widget.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt.setOnClickListener{
            fi.clickStart()
        }
        menu_bt.setClickItem(object : ClickItem {
            override fun clickItem(i: Int) {
            LogUtil.e("-------i-----------------="+i)
            }
        })

    }


}
