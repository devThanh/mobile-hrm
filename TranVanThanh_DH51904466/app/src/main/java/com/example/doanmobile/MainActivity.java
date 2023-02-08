package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doanmobile.model.NhanVien;
import com.example.doanmobile.util.DBUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String DB_NAME = "EmployeeDB.db";
    final String DB_PATH_SUFIX = "/databases/";
    ListView lvNhanVien;
    ArrayList<NhanVien> list;
    NhanVienAdapter adapter;
    Button btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        copyDatabase();
        getDataFromDB();
    }

    private void getDataFromDB() {
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
         //Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        // Cursor cursor = database.rawQuery("SELECT * FROM SinhVien WHERE namsinh >= ?", new String[]{"2000"});
//        Cursor cursor = database.query(
//                "SinhVien",
//                null, //new String[]{"ten", "namsinh"},
//                "namsinh >= ?",
//                new String[]{"2000"},
//                null,
//                null,
//                null
//        );
        Cursor cursor = database.query(
                "NhanVien",
                null, //new String[]{"ten", "namsinh"},
                null,
                null,
                null,
                null,
                null
        );
        list.clear();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String phongBan = cursor.getString(2);
            String sdt = cursor.getString(3);
            String email = cursor.getString(4);
            byte[] anh = cursor.getBlob(5);
            //list.add(new NhanVien(ma, ten, gioiTinh, sdt, noiSinh, anh));
            list.add(new NhanVien(ma, ten,phongBan,sdt, email, anh));
        }
        cursor.close();
        database.close();
        adapter.notifyDataSetChanged();
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
                Intent intent = new Intent(MainActivity.this, PhongBan_activity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(MainActivity.this, About_Activity.class);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.lvNhanVien);
        getMenuInflater().inflate(R.menu.menu_phongban, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){
            case R.id.mnuSua:
                break;
            case R.id.mnuXoa:
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    private void addControls(){
        btnThem = findViewById(R.id.btnThem);
        lvNhanVien = findViewById(R.id.lvNhanVien);
        list = new ArrayList<>();
        adapter = new NhanVienAdapter(MainActivity.this, list);
        lvNhanVien.setAdapter(adapter);
        registerForContextMenu(lvNhanVien);

    }

    private void addEvents(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Them_NhanVien_Activity.class);
                startActivity(intent);
            }
        });


        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = list.get(i).getMa();
                Intent intent = new Intent(MainActivity.this, ThongTin_NhanVien_activity.class);
                intent.putExtra("ID_NV", id);
                startActivity(intent);
                System.out.println(id);
                Toast.makeText(MainActivity.this, "324324", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void copyDatabase() {
        DBUtil.copyDBFileFromAssets(
                MainActivity.this,
                DB_NAME,
                DB_PATH_SUFIX
        );
    }
}