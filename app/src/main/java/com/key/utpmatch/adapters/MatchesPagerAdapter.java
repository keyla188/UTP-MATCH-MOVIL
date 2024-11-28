package com.key.utpmatch.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.key.utpmatch.fragments.MyMatchesFragment;
import com.key.utpmatch.fragments.MyMatchesOptionFragment;
import com.key.utpmatch.fragments.MyPendingsOptionFragment;

public class MatchesPagerAdapter extends FragmentStateAdapter {
    public MatchesPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyMatchesOptionFragment(); // Fragmento para "Mis Matches"
            case 1:
                return new MyPendingsOptionFragment(); // Fragmento para "Mis Pendientes"
            default:
                return new MyMatchesOptionFragment(); // Por defecto
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Número de pestañas
    }
}
