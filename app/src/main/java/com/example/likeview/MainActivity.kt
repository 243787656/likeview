package com.example.likeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View
import com.example.likeview.util.LogUtil


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt.setOnClickListener{
            fi.clickStart()
        }
    }


}
