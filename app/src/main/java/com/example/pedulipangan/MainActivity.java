// MainActivity.java - (No changes needed if you used the last corrected version, just for completeness)
package com.example.pedulipangan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

        BottomNavigationView navView = binding.bottomNavigation; // Make sure this ID matches in XML

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.navigation_home);
        topLevelDestinations.add(R.id.navigation_stocks);
        topLevelDestinations.add(R.id.navigation_notifications);
        topLevelDestinations.add(R.id.navigation_statistics);
        topLevelDestinations.add(R.id.navigation_profile);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();

        // Ensure this ID matches your FragmentContainerView in activity_main.xml
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_stocks, R.id.navigation_notifications, R.id.navigation_statistics, R.id.navigation_profile)
                .build()) || super.onSupportNavigateUp();
    }
}