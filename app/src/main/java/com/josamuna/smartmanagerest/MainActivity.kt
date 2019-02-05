package com.josamuna.smartmanagerest

import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.fragment.DefaultFragment
import com.josamuna.smartmanagerest.fragment.FragmentCaptureMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

    /**
     * Use to make action on back button
     */
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

    /**
     * Use for Action Menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Perform a action when Menu option item are clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Perform action when clic on Menu item i drawer
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_capture -> {
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

    override fun onStop() {
        super.onStop()

        try {
                Factory.closeConnection(Factory.CONN_VALUE)
        }catch (e: IllegalArgumentException){
            Log.e("Error", "Error when close connection ${e.message}")
        }catch (e: Exception){
            Log.e("Error", "Error not managed when close connection ${e.message}")
        }
    }

    /**
     * Safely open fragment througt Activity
     */
    private fun AppCompatActivity.replaceFragmentSafely(fragment: Fragment,
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
