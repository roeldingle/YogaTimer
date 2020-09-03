package com.example.yogatimerexample

import android.content.Intent
import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_timer.*
import kotlin.math.round

class TimerActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var countDownTimer: CountDownTimer
    var stopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        stopped = false
        mediaPlayer = MediaPlayer.create(this, R.raw.ting)
        var clickCount = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            clickCount++
            val intent: Intent = intent
            val seconds = intent?.extras?.getLong("seconds")

            if(seconds != null){
                val milliseconds = seconds * 1000
                startWorkout(milliseconds)
            }
            if(clickCount >= 2){
                stopped = true
                timerViewTxt.setText("00:00")
                val activityIntent = Intent(this,MainActivity::class.java)
                startActivity(activityIntent)
            }
        }
    }


    private fun startWorkout(timeInMilliseconds: Long){
        var totalSecondsRemaining = 0.0
        countDownTimer = object : CountDownTimer(timeInMilliseconds, 1000){
            override fun onTick(timeInMilliseconds: Long) {
                val roundedSeconds = round(timeInMilliseconds.toDouble() / 1000)
                if(roundedSeconds != totalSecondsRemaining){
                    totalSecondsRemaining = roundedSeconds
                    val millisUntilFinish = (totalSecondsRemaining * 1000).toLong()
                    val minutesRemaining = millisUntilFinish / 60000
                    val secondsRemaining = (millisUntilFinish % 60000) / 1000
                    val minutes = appendZero(minutesRemaining)
                    val seconds = appendZero(secondsRemaining)
                    val timerText = "${minutes}:${seconds}"
                    timerViewTxt.setText(timerText)
                }

            }
            override fun onFinish() {
                if(!stopped){
                    playSound()
                    startWorkout(timeInMilliseconds)
                }
            }
        }.start()
    }

    override fun onStop() {
        countDownTimer.cancel()
        mediaPlayer.stop()
        super.onStop()
    }

    private fun playSound(){
        try{
            mediaPlayer.start()
        }
        catch(e: Exception){
            println(e)
        }
    }

    private fun appendZero(time: Long): String{
        val timeString = time.toString()
        return if (time < 10) "0$timeString" else timeString

    }
}
