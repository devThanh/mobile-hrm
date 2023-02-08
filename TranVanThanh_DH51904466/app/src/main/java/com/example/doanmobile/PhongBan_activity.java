package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.model.NhanVien;
import com.example.doanmobile.model.PhongBan;

import java.util.ArrayList;

public class PhongBan_activity extends AppCompatActivity {
    Spinner spinnerDanhSach;
    ArrayAdapter<PhongBan> adapter;
    ListView lvNhanVienPB;
    ArrayList<NhanVien> list;
    ArrayAdapter<NhanVien> adapterNV;
    final String DB_NAME = "EmployeeDB.db";
    Button btnThem, btnSua, btnXoa;
    PhongBan chon = null;
    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_ban);
        addControls();
        addEvents();
        initSpinner();
        adapter.notifyDataSetChanged();
    }

    private void addControls(){
        btnThem = findViewById(R.id.btnThemPhongBan1);
        btnSua = findViewById(R.id.btnSuaPhongBan1);
        btnXoa = findViewById(R.id.btnXoaPhongBan1);
        list = new ArrayList<>();
        lvNhanVienPB = findViewById(R.id.lvNVPB);
        spinnerDanhSach = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //View emptyView = getLayoutInflater().inflate()
        spinnerDanhSach.setAdapter(adapter);
        adapterNV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvNhanVienPB.setAdapter(adapterNV);
        registerForContextMenu(lvNhanVienPB);
    }

    private void addEvents(){
        spinnerDanhSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterNV.clear();
                chon = adapter.getItem(i);
                //maPB = pb.getMaPB();
                SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("SELECT * FROM NhanVien WHERE PhongBan = ?", new String[]{chon.getTenPB(),});
                while (cursor.moveToNext()) {
                int ma = cursor.getInt(0);
                String ten = cursor.getString(1);
                String phongBan = cursor.getString(2);
                String sdt = cursor.getString(3);
                String email = cursor.getString(4);
                byte[] anh = cursor.getBlob(5);
                    //list.add(new NhanVien(ma, ten, gioiTinh, sdt, noiSinh, anh));
                NhanVien nv = new NhanVien(ma, ten,phongBan,sdt, email, anh);
                adapterNV.add(nv);
                adapterNV.notifyDataSetChanged();
                }
                cursor.close();
                database.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterNV.clear();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhongBan_activity.this, Them_PhongBan_Activity.class);
                startActivity(intent);
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhongBan_activity.this, Update_PhongBan_Activity.class);
                intent.putExtra("MA", chon.getMaPB());
                startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!adapterNV.isEmpty())
                Toast.makeText(PhongBan_activity.this, "PHÒNG BAN CÓ CHỨA NHÂN VIÊN. KHÔNG THỂ XÓA", Toast.LENGTH_SHORT).show();
                else
                    deletePB();
            }
        });



    }

    private void deletePB(){
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        database.delete("PhongBan", "MaPB = ?", new String[]{chon.getMaPB(),});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM PhongBan", null);
        if(cursor.getCount()!=0){
            Toast.makeText(PhongBan_activity.this,"XÓA THÀNH CÔNG", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(PhongBan_activity.this,"XÓA THẤT BẠI", Toast.LENGTH_SHORT).show();

        }
        while (cursor.moveToNext()){
            String mapb = cursor.getString(0);
            String tenpb = cursor.getString(1);
            adapter.add(new PhongBan(mapb, tenpb));
        }
        cursor.close();
        database.close();
        initSpinner();
    }

    private void initSpinner(){

        adapter.clear();
        list.clear();
        SQLiteDatabase database = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query(
                "PhongBan",
                null, //new String[]{"ten", "namsinh"},
                null,
                null,
                null,
                null,
                null
        );
        //dsPB.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String tenphongBan = cursor.getString(1);
            //list.add(new NhanVien(ma, ten, gioiTinh, sdt, noiSinh, anh));
            adapter.add(new PhongBan(ma, tenphongBan));
        }
        cursor.close();
        database.close();
        adapter.notifyDataSetChanged();
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
                Intent intent = new Intent(PhongBan_activity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(PhongBan_activity.this, About_Activity.class);
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