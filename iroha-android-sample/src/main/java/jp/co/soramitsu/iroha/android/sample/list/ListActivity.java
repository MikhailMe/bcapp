package jp.co.soramitsu.iroha.android.sample.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.soramitsu.iroha.android.sample.list.Fragments.AccountFragment;
import jp.co.soramitsu.iroha.android.sample.list.Fragments.GameListFragment;
import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.bet.BetActivity;
import jp.co.soramitsu.iroha.android.sample.list.Fragments.SelectHandler;
import lombok.NonNull;

public final class ListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SelectHandler {

    @NonNull
    private Fragment fAccount;

    @NonNull
    private Fragment fGameList;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fAccount = new AccountFragment();
        fGameList = new GameListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fGameList).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_account:
                ft.replace(R.id.container, fAccount);
                break;
            case R.id.nav_list_of_games:
                ft.replace(R.id.container, fGameList);
                break;
            case R.id.nav_exit:
                finishAffinity();
                System.exit(0);
                break;
        }
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onGameSelected(@NonNull final String team1,
                               @NonNull final String team2,
                               @NonNull final String timestamp) {
        startActivity(BetActivity.newIntent(this, team1, team2, timestamp));
    }
}