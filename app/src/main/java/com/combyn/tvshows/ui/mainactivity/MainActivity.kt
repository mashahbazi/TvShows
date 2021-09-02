package com.combyn.tvshows.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.combyn.tvshows.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * When there is any fragment in backstack it will pop it from stack. It will close
     * activity when there is no fragment in back stack
     */
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (supportFragmentManager.backStackEntryCount > 0 && fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}