package com.example.quiz_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_question.*
const val CHANNEL_ID = "com.example.quiz_app"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get full screen without title bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //close application
        btnExit.setOnClickListener {
            finish()
        }

        //to open question fragment
        btnStart.setOnClickListener {
            openFragment()

        }

        //create notification channel
        createNotificationChannel()



    }

    //provide visibility main activity's buttons
    override fun onBackPressed() {
        super.onBackPressed()
        if (!btnExit.isVisible and !btnLastScore.isVisible and !btnLastScore.isVisible){
            btnExit.visibility = View.VISIBLE
            btnStart.visibility = View.VISIBLE
            btnLastScore.visibility = View.VISIBLE
        }
    }




    //method opens question fragment
    private fun openFragment() {
        val fragment = QuestionFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        btnStart.visibility = View.GONE
        btnExit.visibility = View.GONE
        btnLastScore.visibility = View.GONE

    }

    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Main channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}