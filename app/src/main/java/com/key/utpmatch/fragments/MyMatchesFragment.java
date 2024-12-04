package com.key.utpmatch.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.key.utpmatch.R;
import com.key.utpmatch.adapters.MatchesPagerAdapter;

public class MyMatchesFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MatchesPagerAdapter pagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_matches, container, false);

        // Inicializar vistas
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Configurar el adaptador del ViewPager
        pagerAdapter = new MatchesPagerAdapter(this); // Adaptador personalizado
        viewPager.setAdapter(pagerAdapter);

        // Vincular TabLayout con ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Mis Matches");
                    break;
                case 1:
                    tab.setText("Mis Pendientes");
                    break;
            }
        }).attach();

        return view;
    }
}