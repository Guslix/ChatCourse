package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.guslix.chat.databinding.ActivityMainBinding
import space.guslix.chat.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    //для поиска элементов из layout
    private var binding: ActivityRegisterBinding? = null
    private val krik get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(krik.root)

        //toolbar
        setSupportActionBar(krik.toolbarRegister)
        supportActionBar!!.title = getString(R.string.sign_up_m)
        //стрелка назад
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        krik.toolbarRegister.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //кнопка Назад - вернуться на Welcome
    override fun onBackPressed() {
        val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}