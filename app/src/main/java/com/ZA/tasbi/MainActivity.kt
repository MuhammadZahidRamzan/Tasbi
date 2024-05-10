package com.ZA.tasbi

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ZA.tasbi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appPreferences: AppPreferences
    private var counter = 0
    private var rap = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        appPreferences = AppPreferences(this)
        counter= appPreferences.accessCounter?.toInt() ?:0
        rap= appPreferences.accessRap?.toInt() ?:0
        binding.tvCounter.text = counter.toString()
        binding.tvRap.text = rap.toString()
       binding.main.setOnClickListener {

           counter++
           binding.tvCounter.text = counter.toString()
           if (counter > 99){
               playSound()
               rap++
               binding.tvRap.text = rap.toString()
               counter = 0
               binding.tvCounter.text = counter.toString()
           }
           appPreferences.accessCounter=counter.toString()
           appPreferences.accessRap=rap.toString()

       }
      binding.ivReset.setOnClickListener {
          counter = 0
          binding.tvCounter.text = counter.toString()
          appPreferences.accessCounter=counter.toString()
      }
      binding.tvRap.setOnClickListener {
          rap = 0
          binding.tvRap.text = rap.toString()
          appPreferences.accessRap=rap.toString()
      }

    }
    private fun playSound(){
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer.start()
    }

    fun vibrateDevice(context: Context) {
        // Check if the device has a vibrator
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (!vibrator.hasVibrator()) {
            return
        }

        // Create a vibration effect
        val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        } else {
            // Use deprecated method for older Android versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @Suppress("DEPRECATION")
                VibrationEffect.createOneShot(500, 1)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }

        // Vibrate the device
        vibrator.vibrate(vibrationEffect)
    }


}