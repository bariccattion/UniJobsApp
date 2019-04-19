package com.beesocial.unijobs.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.beesocial.unijobs.R;
import com.beesocial.unijobs.models.User;
import com.beesocial.unijobs.storage.SharedPrefManager;

public class ProfileActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Intent intent = getIntent();
    User user;
    TextView textProfileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            /*Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/

        }
        user=SharedPrefManager.getInstance(this).getUser();
        textProfileName = findViewById(R.id.textProfileName);
        textProfileName.setText(user.getName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.menu_home:
                //fragment =
                break;
            case R.id.menu_users:
                //fragment =
                break;
            case R.id.menu_settings:
                //fragment =
                break;
        }

        if(fragment != null){
            //displayFragment(fragment);
        }

        return false;
    }
}
