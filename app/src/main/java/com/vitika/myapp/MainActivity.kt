package com.vitika.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun news(view: View) {
        val numIntent =Intent(this,NewsActivity::class.java)
        startActivity(numIntent)
    }

    fun Meme(view: View) {
        val numIntent =Intent(this,MemeActivity::class.java)
        startActivity(numIntent)
    }

    fun Dice(view: View) {
        val numIntent =Intent(this,DiceActivity::class.java)
        startActivity(numIntent)
    }

    fun Music(view: View) {
        val numIntent =Intent(this,MusicActivity::class.java)
        startActivity(numIntent)
    }
}