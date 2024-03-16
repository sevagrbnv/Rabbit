package ru.sevagrbnv.rabbit.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import ru.sevagrbnv.rabbit.R


class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navHost.navController
    }

    val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val navView by lazy { findViewById<NavigationView>(R.id.navView) }
    val drawerLayout by lazy { setDrawer(toolbar, navView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }


    private fun setDrawer(toolbar: Toolbar, navView: NavigationView): DrawerLayout {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(R.id.viewPagerFragment)
                }

                R.id.nav_about -> {
                    navController.navigate(R.id.aboutFragment)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        toggle.setToolbarNavigationClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                navController.popBackStack()
            }
        })
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toggle.isDrawerIndicatorEnabled =
                destination.id in setOf(R.id.viewPagerFragment)
        }

        return drawerLayout
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}