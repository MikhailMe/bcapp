package com.mishas.bcapp_client.Activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mishas.bcapp_client.Activities.Fragments.AccountFragment;
import com.mishas.bcapp_client.Activities.Fragments.GameListFragment;
import com.mishas.bcapp_client.R;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AccountFragment accountFragment;
    private GameListFragment gameListFragment;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountFragment = new AccountFragment();
        gameListFragment = new GameListFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.nav_account:
                transaction.replace(R.id.main_container, accountFragment);
                break;
            case R.id.nav_list_of_games:
                transaction.replace(R.id.main_container, gameListFragment);
                break;
            case R.id.nav_exit:
                finish();
                System.exit(0);
                break;
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START );
        return true;

    }
}
