package com.example.pedulipangan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.pedulipangan.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ Set Toolbar sebagai ActionBar
        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = binding.bottomNavigation;

        // Fragment yang dianggap sebagai top-level destinations
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.navigation_home);
        topLevelDestinations.add(R.id.navigation_stocks);
        topLevelDestinations.add(R.id.navigation_notifications);
        topLevelDestinations.add(R.id.navigation_statistics);
        topLevelDestinations.add(R.id.navigation_profile);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        return NavigationUI.navigateUp(navController,
                new AppBarConfiguration.Builder(
                        R.id.navigation_home,
                        R.id.navigation_stocks,
                        R.id.navigation_notifications,
                        R.id.navigation_statistics,
                        R.id.navigation_profile).build()
        ) || super.onSupportNavigateUp();
    }
}
