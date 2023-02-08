package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanmobile.model.NhanVien;

public class ThongTin_NhanVien_activity extends AppCompatActivity {
    TextView tvMA, tvTen, tvSDT, tvEmail, tvPhongBan;
    ImageView imgv;
    final String DB_NAME = "EmployeeDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nhan_vien);
        addControls();
        addEvents();
    }

    private void addControls(){
        tvMA = findViewById(R.id.tvID);
        tvTen = findViewById(R.id.tvTen);
        tvSDT = findViewById(R.id.tvSDT);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhongBan = findViewById(R.id.tvPhongBan);
        imgv = findViewById(R.id.imgHinhDaiDien);

    }

    private void addEvents(){
        Intent intent = getIntent();
//        int ma = intent.getIntExtra("ID_NV", -1);
//        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
//        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien WHERE MaNV = ?", new String[]{ma+"",});
//        cursor.moveToFirst();
//        int manv = cursor.getInt(0);
//        String ten = cursor.getString(1);
//        String phongBan = cursor.getString(2);
//        String sdt = cursor.getString(3);
//        String email = cursor.getString(4);
//        byte[] anh = cursor.getBlob(5);
//        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(anh, 0, anh.length);
//        tvMA.setText(manv+"");
//        tvTen.setText(ten);
//        tvSDT.setText(sdt);
//        tvEmail.setText(email);
//        tvPhongBan.setText(phongBan);
//        imgv.setImageBitmap(bmHinhDaiDien);
//        cursor.close();
//        database.close();
        NhanVien nv = (NhanVien) intent.getSerializableExtra("NV");
        tvMA.setText("MÃ: "+nv.ma);
        tvTen.setText("HỌ TÊN: "+nv.hoten);
        tvSDT.setText("SĐT: "+nv.sdt);
        tvEmail.setText("EMAIL: "+nv.email);
        tvPhongBan.setText("PHÒNG BAN: "+nv.phongban);
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nv.anh, 0, nv.anh.length);
        imgv.setImageBitmap(bmHinhDaiDien);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuPB:
                Intent intent = new Intent(ThongTin_NhanVien_activity.this, PhongBan_activity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(ThongTin_NhanVien_activity.this, About_Activity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThoat:
                //onStop();
                finishAffinity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}