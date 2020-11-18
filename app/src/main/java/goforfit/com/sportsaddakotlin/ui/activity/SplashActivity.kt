package goforfit.com.sportsaddakotlin.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.helper.AppConfig
import goforfit.com.sportsaddakotlin.helper.Utility

class SplashActivity : AppCompatActivity() {

    val SPLASH_TIME_OUT:Long = 4000
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = Utility.getSharedPrefs(this)

        Handler().postDelayed({
            val intent:Intent

            if (sharedPreferences!!.getBoolean(AppConfig.IS_USER_LOGGED_IN,false)){
                if (sharedPreferences!!.contains(AppConfig.CITY_ID))
                    intent = Intent(this,
                        MainActivity::class.java)
                else
                    intent = Intent(this,SelectCityActivity::class.java)
            }
            else
                intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.activity_in,R.anim.activity_out)
            // close this activity
            finish();

        },SPLASH_TIME_OUT)
    }
}
