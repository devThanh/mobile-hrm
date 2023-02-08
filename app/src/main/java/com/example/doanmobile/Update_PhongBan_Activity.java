package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_PhongBan_Activity extends AppCompatActivity {
    Button btnSua, btnHuy;
    EditText etTen, etMa;
    final String DB_NAME = "EmployeeDB.db";
    public String mapb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phong_ban);
        addControls();
        addEvents();
        getData();
    }

    private void addControls(){
        btnSua = findViewById(R.id.btnThemPBU);
        btnHuy = findViewById(R.id.btnQuayLaiPBU);
        etMa = findViewById(R.id.etMaPB1);
        etTen = findViewById(R.id.etTenPB1);
    }

    private void getData(){
        Intent intent = getIntent();
        mapb = intent.getStringExtra("MA");
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM PhongBan WHERE MaPB = ?", new String[]{mapb,});
        cursor.moveToFirst();
        String mapban = cursor.getString(0);
        String tenpban = cursor.getString(1);
        etMa.setText(mapban);
        etTen.setText(tenpban);
    }

    private void addEvents(){
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePB();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Update_PhongBan_Activity.this, PhongBan_activity.class);
                startActivity(intent);
            }
        });
    }

    private void updatePB(){
        String ma = etMa.getText().toString();
        String ten = etTen.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAPB", ma);
        contentValues.put("TENPB", ten);
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        int success = database.update("PhongBan", contentValues, "MaPB = ?", new String[]{mapb,});
        Toast.makeText(this, "Cập nhật thành công "+ success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhongBan_activity.class);
        startActivity(intent);
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
                Intent intent = new Intent(Update_PhongBan_Activity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(Update_PhongBan_Activity.this, About_Activity.class);
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