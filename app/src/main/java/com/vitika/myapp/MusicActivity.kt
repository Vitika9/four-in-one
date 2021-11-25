package com.vitika.myapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MusicActivity : AppCompatActivity() {
    var play: Button? = null
    var pause: Button? = null
    var stop: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        play = findViewById<View>(R.id.play) as Button
        pause = findViewById<View>(R.id.pause) as Button
        stop = findViewById<View>(R.id.stop) as Button
        media()
    }
    fun media(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.m)
        play!!.setOnClickListener {
            mp.start() }
        pause!!.setOnClickListener {
            if(mp.isPlaying()) {
                mp.pause()
            }}
        stop!!.setOnClickListener {
            mp.stop()
        media()
        }
    }
}
