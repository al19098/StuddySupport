package com.example.studysupport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Calendar_UI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calndar__ui);
        BottomNavigationView menu = findViewById(R.id.bnv);
        menu.getMenu().findItem(R.id.Calendar).setChecked(true);

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.Main :
                        intent = new Intent(Calendar_UI.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Timer :
                        intent = new Intent(Calendar_UI.this, MainTimerFrame.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Study :
                        intent = new Intent(Calendar_UI.this, StudyRecord_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                    case R.id.Calendar :
                        return true;
                    case R.id.ToDo :
                        intent = new Intent(Calendar_UI.this, ToDo_UI.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return true;
                }
                return false;
            }
        });
    }
}