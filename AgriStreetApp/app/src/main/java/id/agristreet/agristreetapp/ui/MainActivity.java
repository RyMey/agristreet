package id.agristreet.agristreetapp.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.Arrays;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.ui.fragment.BerandaFragment;
import id.agristreet.agristreetapp.ui.fragment.KerjasamaFragment;

public class MainActivity extends AppCompatActivity implements OnMenuTabClickListener {
    private static final int INDEX_BERANDA = FragNavController.TAB1;
    private static final int INDEX_KERJASAMA = FragNavController.TAB2;
    private static final int INDEX_AKUNKU = FragNavController.TAB3;

    private BottomBar bottomBar;
    private FragNavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = new FragNavController(getSupportFragmentManager(), R.id.fragment_container,
                Arrays.asList(new BerandaFragment(), new KerjasamaFragment(), new BerandaFragment()));
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.useOnlyStatusBarTopOffset();
        bottomBar.setMaxFixedTabs(2);
        bottomBar.setItems(R.menu.bottombar_home);
        bottomBar.setOnMenuTabClickListener(this);
        bottomBar.setDefaultTabPosition(INDEX_BERANDA);
    }

    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.bar_beranda:
                navController.switchTab(INDEX_BERANDA);
                break;
            case R.id.bar_kerja_sama:
                navController.switchTab(INDEX_KERJASAMA);
                break;
            case R.id.bar_akunku:
                navController.switchTab(INDEX_AKUNKU);
                break;
        }
    }

    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.bar_beranda:
                break;
            case R.id.bar_kerja_sama:
                break;
            case R.id.bar_akunku:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }
}
