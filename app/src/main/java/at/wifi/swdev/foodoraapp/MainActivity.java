package at.wifi.swdev.foodoraapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import at.wifi.swdev.foodoraapp.databinding.ActivityMainBinding;
import at.wifi.swdev.foodoraapp.view.fragment.FileFragment;
import at.wifi.swdev.foodoraapp.view.fragment.MealFragment;
import at.wifi.swdev.foodoraapp.view.fragment.RestaurantCategoryFragment;
import at.wifi.swdev.foodoraapp.view.fragment.RestaurantFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupBottomNavigation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Wird aufgerufen, wenn ein Menü-Eintrag geklickt wird
        // -> das richtige Fragment anzeigen

        if (item.getItemId() == R.id.navCategories) {
            // Benutzer hat "Kategorien" ausgewählt
            return displayFragment(new RestaurantCategoryFragment());
        } else if (item.getItemId() == R.id.navRestaurants) {
            // Benutzer hat "Restaurants" ausgewählt
            return displayFragment(new RestaurantFragment());
        } else if (item.getItemId() == R.id.navMeals) {
            // Benutzer hat "Gerichte" ausgewählt
            return displayFragment(new MealFragment());
        } else if (item.getItemId() == R.id.navFiles) {
            // Benutzer hat "Dateien" ausgewählt
            return displayFragment(new FileFragment());
        }

        return false;
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        // Welcher Eintrag wird vorausgewählt
        binding.bottomNav.setSelectedItemId(R.id.navCategories);
    }

    private boolean displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        return true;
    }

}