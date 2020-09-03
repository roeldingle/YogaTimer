package com.example.yogatimerexample

import android.content.Intent
import android.media.AsyncPlayer
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, TimerActivity::class.java)
            val seconds = parseSeconds()
            if(seconds > 5){
                activityIntent.putExtra("seconds", parseSeconds())
                startActivity(activityIntent)
            }else{
                Toast.makeText(this, "Please input number of seconds greater than five(5)", Toast.LENGTH_SHORT).show()
                //Snackbar.make(view, "Please enter seconds", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseSeconds(): Long {
        if(secondsTxt?.text != null && !secondsTxt.text.toString().isEmpty()){
            return secondsTxt.text.toString().toLong()
        }
        return 0
    }


}