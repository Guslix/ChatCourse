package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import space.guslix.chat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val krik get() = binding!!

    //авторизация
    private lateinit var mAuth: FirebaseAuth

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

        mAuth = FirebaseAuth.getInstance()
        krik.loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        //данные из полей ввода
        val email: String = krik.emailLogin.text.toString()
        val password: String = krik.passwordLogin.text.toString()

        //обработка если забыл ввести одно из полей
        if(email == ""){
            Toast.makeText(this@LoginActivity, getString(R.string.registerError_noEmail), Toast.LENGTH_LONG).show()
        } else if(password == ""){
            Toast.makeText(this@LoginActivity, getString(R.string.registerError_noPassword), Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task -> if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage: String = getString(R.string.error_) + task.exception!!.message.toString()
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
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