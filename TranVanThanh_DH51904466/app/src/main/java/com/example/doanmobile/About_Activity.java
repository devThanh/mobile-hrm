package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class About_Activity extends AppCompatActivity {
    ImageView imvCall, imvMap;
    TextView tvSDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = tvSDT.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(About_Activity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(About_Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        imvCall = findViewById(R.id.btnCall);
        imvMap = findViewById(R.id.btnMap);
        tvSDT = findViewById(R.id.tvSDT_About);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_me, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuPB:
                Intent intent = new Intent(About_Activity.this, PhongBan_activity.class);
                startActivity(intent);
                break;
            case R.id.mnuNV:
                Intent intent1 = new Intent(About_Activity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThoat:
                finishAffinity();
                //onStop();
                //Intent intent2 = new Intent(About_Activity.this,Login_Activity.class);
                //startActivity(intent2);
                //finish();
                //System.exit(0);
                //finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}