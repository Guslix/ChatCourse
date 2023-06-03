package space.guslix.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import space.guslix.chat.databinding.ActivityMainBinding
import space.guslix.chat.fragments.ChatsFragment
import space.guslix.chat.fragments.SearchFragment
import space.guslix.chat.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    //для поиска элементов из layout
    private var binding: ActivityMainBinding? = null
    private val krik get() = binding!!
    private var backPressTime: Long = 0
    private lateinit var mAuth: FirebaseAuth
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(krik.root)
        mAuth = FirebaseAuth.getInstance()

        //toolbar
        setSupportActionBar(krik.toolbar)
        supportActionBar!!.title = ""

        //управление фрагментами
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatsFragment(), getString(R.string.chats))
        viewPagerAdapter.addFragment(SearchFragment(), getString(R.string.search))
        viewPagerAdapter.addFragment(SettingsFragment(), getString(R.string.settings))
        krik.viewPager.adapter = viewPagerAdapter
        krik.tabLayout.setupWithViewPager(krik.viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //обработчик нажатий на меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //выйти из аккаунта
            R.id.action_logout -> {
                signOut()
                return true
            }
        }
        return false
    }

    //кнопка Назад - выйти из аккаунта
    override fun onBackPressed() {
        if(System.currentTimeMillis()-backPressTime > 2000){
            toast = Toast.makeText(this@MainActivity, getString(R.string.backPress_main), Toast.LENGTH_LONG)
            toast.show()
        } else {
            toast.cancel()
            signOut()
            return
        }
        backPressTime = System.currentTimeMillis()
    }

    //сбросить binding чтобы очистить память
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    //выход из аккаунта
    private fun signOut(){
        mAuth.signOut()
        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    //управление фрагментами
    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {
        private val fragments: ArrayList<Fragment> = ArrayList<Fragment>()
        private val titles: ArrayList<String> = ArrayList<String>()

        override fun getCount(): Int {
            return fragments.size
        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }
        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }
    }
}