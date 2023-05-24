package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    //кнопка Назад - вернуться на Welcome
    override fun onBackPressed() {
        val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}