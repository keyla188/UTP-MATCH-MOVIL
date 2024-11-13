package com.key.utpmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.key.utpmatch.databinding.ActivityMainBinding;
import com.key.utpmatch.fragments.MatchFragment;
import com.key.utpmatch.fragments.MyMatchesFragment;
import com.key.utpmatch.fragments.ProfileFragment;
import com.key.utpmatch.fragments.TopFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // Configuración del BottomNavigationView
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            final int idNavMenu = item.getItemId();
            if(idNavMenu ==R.id.nav_match) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MatchFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                return true;
            } else if (idNavMenu == R.id.nav_mymatches) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MyMatchesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                return true;
            } else if (idNavMenu == R.id.nav_top) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TopFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                return true;
            }

            return false;
        });

        if (savedInstanceState == null) {
            binding.bottomNavigationView.setSelectedItemId(R.id.nav_match);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MatchFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Log.d("MainActivity", "onCreateOptionsMenu called");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int idMenu = item.getItemId();
        if (idMenu == R.id.action_profile) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .addToBackStack(null) // Agrega la transacción al backstack
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar el binding cuando ya no lo necesitemos
        binding = null;
    }
}