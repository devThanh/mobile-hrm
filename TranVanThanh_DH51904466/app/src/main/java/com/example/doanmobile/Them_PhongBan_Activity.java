package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Them_PhongBan_Activity extends AppCompatActivity {
    EditText etMaPB, etTenPB;
    Button btnThemPB, btnQuayLaiPB;
    final String DB_NAME = "EmployeeDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phong_ban);
        addControls();
        addEvents();
    }

    private void addControls(){
        etMaPB = findViewById(R.id.etMaPB);
        etTenPB = findViewById(R.id.etTenPB);
        btnThemPB = findViewById(R.id.btnThemPB2);
        btnQuayLaiPB = findViewById(R.id.btnQuayLaiPB);
    }

    private void addEvents(){
        btnThemPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertThemPB();
            }
        });

        btnQuayLaiPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Them_PhongBan_Activity.this, PhongBan_activity.class);
                startActivity(intent);
            }
        });
    }

    private void insertThemPB(){
        String mapb = etMaPB.getText().toString();
        String tenpb = etTenPB.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mapb", mapb);
        contentValues.put("tenpb", tenpb);
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        database.insert("PhongBan", null,  contentValues);
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Them_PhongBan_Activity.this, PhongBan_activity.class);
        startActivity(intent);
        finish();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_pb, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuNV:
                Intent intent = new Intent(Them_PhongBan_Activity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(Them_PhongBan_Activity.this, About_Activity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThoat:
                finishAffinity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}