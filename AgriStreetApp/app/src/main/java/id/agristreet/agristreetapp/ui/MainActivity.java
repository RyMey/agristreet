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
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.ui.fragment.AkunkuFragment;
import id.agristreet.agristreetapp.ui.fragment.BerandaFragment;
import id.agristreet.agristreetapp.ui.fragment.KerjasamaFragment;
import id.agristreet.agristreetapp.ui.fragment.LowongankuFragment;

public class MainActivity extends AppCompatActivity implements OnMenuTabClickListener,
        BerandaFragment.Listener, LowongankuFragment.Listener, KerjasamaFragment.Listener {
    private static final int INDEX_BERANDA = FragNavController.TAB1;
    private static final int INDEX_LOWONGANKU = FragNavController.TAB2;
    private static final int INDEX_KERJASAMA = FragNavController.TAB3;
    private static final int INDEX_AKUNKU = FragNavController.TAB4;

    private BottomBar bottomBar;
    private FragNavController navController;
    private BerandaFragment berandaFragment;
    private LowongankuFragment lowongankuFragment;
    private KerjasamaFragment kerjasamaFragment;
    private boolean isSearchViewVisible;

    private PengelolaDataLokal.UserType userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userType = PengelolaDataLokal.getInstance(this).getUserType();

        berandaFragment = new BerandaFragment();
        kerjasamaFragment = new KerjasamaFragment();

        if (userType == PengelolaDataLokal.UserType.PEBISNIS) {
            lowongankuFragment = new LowongankuFragment();
            navController = new FragNavController(getSupportFragmentManager(), R.id.fragment_container,
                    Arrays.asList(berandaFragment, lowongankuFragment, kerjasamaFragment, new AkunkuFragment()));
        } else {
            navController = new FragNavController(getSupportFragmentManager(), R.id.fragment_container,
                    Arrays.asList(berandaFragment, kerjasamaFragment, new AkunkuFragment()));
        }

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.useOnlyStatusBarTopOffset();
        bottomBar.setMaxFixedTabs(2);

        bottomBar.setItems(userType == PengelolaDataLokal.UserType.PEBISNIS ? R.menu.bottombar_pebisnis : R.menu.bottombar_petani);

        bottomBar.setOnMenuTabClickListener(this);
        bottomBar.setDefaultTabPosition(getTabIndex(INDEX_BERANDA));
    }

    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.bar_beranda:
                navController.switchTab(getTabIndex(INDEX_BERANDA));
                break;
            case R.id.bar_lowongaku:
                navController.switchTab(getTabIndex(INDEX_LOWONGANKU));
                break;
            case R.id.bar_kerja_sama:
                navController.switchTab(getTabIndex(INDEX_KERJASAMA));
                break;
            case R.id.bar_akunku:
                navController.switchTab(getTabIndex(INDEX_AKUNKU));
                break;
        }
    }

    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.bar_beranda:
                break;
            case R.id.bar_lowongaku:
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

    @Override
    public void onToggleSearchViewVisibility(boolean visible) {
        isSearchViewVisible = visible;
    }

    @Override
    public void onBackPressed() {
        if (isSearchViewVisible && bottomBar.getCurrentTabPosition() == getTabIndex(INDEX_BERANDA)) {
            berandaFragment.hideSearchView();
        } else if (isSearchViewVisible && bottomBar.getCurrentTabPosition() == getTabIndex(INDEX_LOWONGANKU)
                && lowongankuFragment != null) {
            lowongankuFragment.hideSearchView();
        } else if (isSearchViewVisible && bottomBar.getCurrentTabPosition() == getTabIndex(INDEX_KERJASAMA)) {
            kerjasamaFragment.hideSearchView();
        } else {
            super.onBackPressed();
        }
    }

    private int getTabIndex(int index) {
        if (userType == PengelolaDataLokal.UserType.PETANI && index > 1) {
            return index - 1;
        }
        return index;
    }
}
