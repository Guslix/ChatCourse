package space.guslix.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import space.guslix.chat.databinding.ActivityMainBinding
import space.guslix.chat.fragments.ChatsFragment
import space.guslix.chat.fragments.SearchFragment
import space.guslix.chat.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    //для поиска элементов из layout
    private var binding: ActivityMainBinding? = null
    private val krik get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(krik.root)

        //toolbar
        setSupportActionBar(krik.toolbar)
        supportActionBar!!.title = ""

        //управление фрагментами
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatsFragment(),"Чаты")
        viewPagerAdapter.addFragment(SearchFragment(),"Поиск")
        viewPagerAdapter.addFragment(SettingsFragment(),"Настройки")
        krik.viewPager.adapter = viewPagerAdapter
        krik.tabLayout.setupWithViewPager(krik.viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //сбросить binding чтобы очистить память
    override fun onDestroy() {
        super.onDestroy()
        binding = null
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