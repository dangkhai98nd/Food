package com.test.food.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.test.food.R
import com.test.food.data.preferences.PreferenceProvider
import com.test.food.ui.auth.LoginActivity
import com.test.food.ui.main.MainActivity
import herbs.n.more.util.Constant

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        if (PreferenceProvider(this).getLoginUser() == true) {
            runTimeDelay(Constant.SPLASH_TIME_OUT.toLong()) { moveHome() }
        } else {
            runTimeDelay(Constant.SPLASH_TIME_OUT.toLong()) { moveLogin() }
        }
    }

    private fun moveHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        finish()
    }

    private fun moveLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        finish()
    }

    private fun runTimeDelay(timeMoveDelay: Long, process: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            process()
        }, timeMoveDelay)
    }
}