package space.guslix.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import space.guslix.chat.databinding.ActivityMainBinding
import space.guslix.chat.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    //для поиска элементов из layout
    private var binding: ActivityRegisterBinding? = null
    private val krik get() = binding!!

    //авторизация
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference //ссылка на базу данных
    private var userID: String = ""

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

        mAuth = FirebaseAuth.getInstance()
        krik.registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        //данные из полей ввода
        val username: String = krik.usernameRegister.text.toString()
        val email: String = krik.emailRegister.text.toString()
        val password: String = krik.passwordRegister.text.toString()

        //обработка если забыл ввести одно из полей
        if(username == ""){
            Toast.makeText(this@RegisterActivity, getString(R.string.registerError_noUsername), Toast.LENGTH_LONG).show()
        } else if(email == ""){
            Toast.makeText(this@RegisterActivity, getString(R.string.registerError_noEmail), Toast.LENGTH_LONG).show()
        } else if(password == ""){
            Toast.makeText(this@RegisterActivity, getString(R.string.registerError_noPassword), Toast.LENGTH_LONG).show()
        } else {
            //создать аккаунт
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task -> if(task.isSuccessful){
                    userID = mAuth.currentUser!!.uid //получить id
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(userID)

                    //атрибуты польз-ля
                    val userHashMap: HashMap<String, Any> = hashMapOf(
                        "uid" to userID,
                        "email" to email,
                        "username" to username
                    )

                    //обновить базу данных
                    refUsers.updateChildren(userHashMap).addOnCompleteListener {
                        task -> if(task.isSuccessful){
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val errorMessage: String = getString(R.string.error_) + task.exception!!.message.toString()
                            Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val errorMessage: String = getString(R.string.error_) + task.exception!!.message.toString()
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
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