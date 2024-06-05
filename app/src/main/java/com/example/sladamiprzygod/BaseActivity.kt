package com.example.sladamiprzygod

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name) // Ustawianie tytuĹ‚u na nazwÄ™ aplikacji
    }


    protected fun setLayout(layoutResID: Int) {
        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(layoutResID, contentFrame, true)
    }


}
