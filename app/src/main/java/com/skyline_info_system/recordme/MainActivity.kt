package com.skyline_info_system.recordme

import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skyline_info_system.recordme.utils.createLogTrashFile


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createLogTrashFile()
    }
}