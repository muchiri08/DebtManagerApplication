package com.example.debtmanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNav;
    private NavController navController;
    private MaterialToolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolBar);
        bottomNav = findViewById(R.id.bottomNav);
        navController = Navigation.findNavController(this, R.id.fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.paidFragment).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupWithNavController(bottomNav, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_password:
                //TODO: logic for changing the user password
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Debt Manager App");
                builder.setMessage("Are you sure you want to logout");
                builder.setCancelable(false);
                builder.setNegativeButton("No", ((dialog, which) -> dialog.dismiss()));
                builder.setPositiveButton("Yes", ((dialog, which) -> {
                   Intent intent = new Intent(this, LogActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                }));
                builder.create().show();
                break;
            case R.id.call_support:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setCancelable(false);
                builder1.setTitle("Call For Support");
                builder1.setMessage(R.string.support_message);
                builder1.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());
                builder1.show();
                break;
            case R.id.faq:
                navController.navigate(R.id.action_global_FAQFragment);
                break;
            case R.id.exit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Debt Manager App");
                builder2.setMessage("Do you want to exit the application?");
                builder2.setCancelable(false);
                builder2.setNegativeButton("No", ((dialog, which) -> dialog.dismiss()));
                builder2.setPositiveButton("Yes", ((dialog, which) -> {
                    this.finish();
                    System.exit(0);
                    LogActivity logActivity = new LogActivity();
                    logActivity.finish();
                }));
                builder2.create().show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private Bundle data;
    public void saveData(int id, Bundle data) {
        // based on the id you'll know which fragment is trying to save data(see below)
        // the Bundle will hold the data
        this.data = data;
    }

    public Bundle getSavedData() {
        // here you'll save the data previously retrieved from the fragments and
        // return it in a Bundle
        return data;
    }
}