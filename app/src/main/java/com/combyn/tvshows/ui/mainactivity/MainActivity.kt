package com.combyn.tvshows.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.combyn.tvshows.R
import com.combyn.tvshows.databinding.ActivityMainBinding
import com.combyn.tvshows.ui.homefragment.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit()
        }
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