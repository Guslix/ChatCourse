package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.guslix.chat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val krik get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(krik.root)

        //toolbar
        setSupportActionBar(krik.toolbarLogin)
        supportActionBar!!.title = getString(R.string.sign_in_screen)
        //стрелка назад
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        krik.toolbarLogin.setNavigationOnClickListener {
            val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //кнопка Назад - вернуться на Welcome
    override fun onBackPressed() {
        val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}