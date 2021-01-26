package com.show.example

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import com.show.element.transition.callback.setExtraShareElementCallBack
import com.show.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setExtraShareElementCallBack()

        binding.btn.setOnClickListener {
            val transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(this, Pair(binding.btn,"button"))
            startActivity(Intent(this,MainActivity2::class.java), transitionActivityOptions.toBundle())
        }



    }
}