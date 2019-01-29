package com.josamuna.smartmanagerest

import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.josamuna.smartmanagerest.classes.Factory
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
            when(Factory.FRAGMENT_VALUE_ID){
                0 -> super.onBackPressed()
                1,2 -> {
                    replaceFragmentSafely(
                        fragment = DefaultFragment(),
                        tag = "DEFAULT_FRAGMENT",
                        containerViewId = R.id.framelayout,
                        allowStateLoss = true
                    )
                }
            }
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
//                makeText(applicationContext,"Handle the camera action", LENGTH_LONG).show()
//                val fragmentCaptureMain = FragmentCaptureMain()
//                openFragment(fragmentCaptureMain, R.id.framelayout)

                replaceFragmentSafely(
                    fragment = FragmentCaptureMain(),
                    tag = "FRAGMENT_CAPTURE_MAIN",
                    containerViewId = R.id.framelayout,
                    allowStateLoss = true
                )
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

    //Safely open fragment
    fun AppCompatActivity.replaceFragmentSafely(fragment: Fragment,
                                                tag: String,
                                                allowStateLoss: Boolean = false,
                                                @IdRes containerViewId: Int,
                                                @AnimRes enterAnimation: Int = 0,
                                                @AnimRes exitAnimation: Int = 0,
                                                @AnimRes popEnterAnimation: Int = 0,
                                                @AnimRes popExitAnimation: Int = 0) {
        val ft = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
            .replace(containerViewId, fragment, tag)
        if (!supportFragmentManager.isStateSaved) {
            ft.commit()
        } else if (allowStateLoss) {
            ft.commitAllowingStateLoss()
        }
    }

    //Another way to open Fragment
//    fun openFragment(frg:Fragment){
//        val fragment = supportFragmentManager.beginTransaction()
//        fragment.replace(R.id.framelayout, frg)
//        fragment.commit()
//    }
}
