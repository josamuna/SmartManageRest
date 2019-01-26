package com.josamuna.smartmanagerest

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.josamuna.smartmanagerest.fragment.DefaultFragment
import com.josamuna.smartmanagerest.fragment.FragmentCaptureMain
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ISharedFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Use for Default Fragment and his Layout
        val fragment = DefaultFragment()
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragment).commit()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_capture -> {
                // Handle the camera action
               // makeText(applicationContext,"Handle the camera action", LENGTH_LONG).show()
                val fragmentCaptureMain = FragmentCaptureMain()
                openFragment(fragmentCaptureMain, R.id.framelayout)
            }
            R.id.nav_search -> {

            }
            R.id.nav_history -> {

            }
            R.id.nav_about -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        supportFragmentManager.beginTransaction().replace(fragment_id, fragment).commit()
    }

    //Another way to open Fragment
//    fun openFragment(frg:Fragment){
//        val fragment = supportFragmentManager.beginTransaction()
//        fragment.replace(R.id.framelayout, frg)
//        fragment.commit()
//    }
}
