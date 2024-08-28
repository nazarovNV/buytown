import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.buytown.ru.R
import com.buytown.ru.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Initial graph setup
        val graph = navController.navInflater.inflate(R.navigation.nav_login)
        if (isUserLoggedIn()) {
            graph.setStartDestination(R.id.nav_main)
        } else {
            graph.setStartDestination(R.id.nav_login)
        }
        navController.graph = graph

        binding.bottomNavigationView.setupWithNavController(navController)

        // Hide BottomNavigationView initially if user is not logged in
        if (!isUserLoggedIn()) {
            binding.bottomNavigationView.visibility = View.GONE
        }

        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("token", null) != null
    }

    fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
