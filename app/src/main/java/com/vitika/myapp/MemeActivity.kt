package com.vitika.myapp

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.apply
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MemeActivity : AppCompatActivity() {
    lateinit var memeImageView: ImageView
    lateinit var nextButton: ImageView
    lateinit var shareButton: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var memeUrl: String
    lateinit var text: TextView
    lateinit var nextMemeUrl: String
    var count = 0
    lateinit var prevMemeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)
        memeImageView = findViewById(R.id.imageView)
        nextButton = findViewById(R.id.next)
        shareButton = findViewById(R.id.share)
        progressBar = findViewById(R.id.progressBar)
        text = findViewById(R.id.textView)
        prevMemeUrl = ""
        nextMemeUrl = ""

        loadMeme()
    }


    fun loadMeme() {

        text.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        nextButton.isEnabled = false
        shareButton.isEnabled = false

        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                memeUrl = response.getString("url")

                Glide.with(this).load(memeUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        nextButton.isEnabled = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        nextButton.isEnabled = true
                        shareButton.isEnabled = true



                        return false
                    }
                }).into(memeImageView)


            }, {
                text.setText("Check internet")
                text.visibility = View.VISIBLE
            })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun showNextMeme(view: View) {
        count = 0
        if (nextMemeUrl.equals("")) {
            prevMemeUrl = memeUrl
            loadMeme()
        } else {

            Glide.with(this).load(nextMemeUrl).into(memeImageView)
            nextMemeUrl = ""
        }


    }


    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        val shareUrl: String
        if (count == 0) {
            shareUrl = memeUrl
        } else {
            shareUrl = prevMemeUrl
        }
        intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this cool meme " + shareUrl)
        intent.type = "text/plain"
        var chooser = Intent.createChooser(intent, "Share this meme using...")
        try {
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            text.setText("No app found")
            text.visibility = View.VISIBLE
        }


    }

    fun back(view: View) {
        if (prevMemeUrl.equals("")) {
            Toast.makeText(this, "No previous meme found", Toast.LENGTH_LONG).show()
        } else if (count == 0) {
            Glide.with(this).load(prevMemeUrl).into(memeImageView)
            nextMemeUrl = memeUrl
            count++;
        } else {
            Toast.makeText(this, "You can load only one previous image", Toast.LENGTH_SHORT).show()
        }
    }
}
