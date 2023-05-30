package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import space.guslix.chat.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private var binding: ActivityWelcomeBinding? = null
    private val krik get() = binding!!
    private var backPressTime: Long = 0

    //аккаунт
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(krik.root)

        //обработчик кнопок вход и регистрация
        krik.welcomeLoginBtn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        krik.welcomeRegisterBtn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        //если польз уже зареган, пропустить welcome
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser != null){
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //кнопка Назад - выйти из приложения
    override fun onBackPressed() {
        val toast = Toast.makeText(this@WelcomeActivity, getString(R.string.backPress_welcome), Toast.LENGTH_LONG)
        if(System.currentTimeMillis()-backPressTime > 2000){
            toast.show()
            backPressTime = System.currentTimeMillis()
        } else {
            toast.cancel()
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}