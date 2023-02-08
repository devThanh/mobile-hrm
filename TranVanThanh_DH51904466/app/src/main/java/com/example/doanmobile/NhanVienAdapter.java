package com.example.doanmobile;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends BaseAdapter {
    Activity context;
    ArrayList<NhanVien> list;
    final String DB_NAME = "EmployeeDB.db";
    final String DB_PATH_SUFIX = "/databases/";

    public NhanVienAdapter(Activity context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        ImageView imgHinhDaiDien = row.findViewById(R.id.imgHinhDaiDien);
        TextView tvID = row.findViewById(R.id.tvID);
        TextView tvHoTen = row.findViewById(R.id.tvHoTen);
        TextView tvPhongBan = row.findViewById(R.id.tvPhongBan);
        Button btnSua = row.findViewById(R.id.btnSua);
        Button btnXoa = row.findViewById(R.id.btnXoa);
        Button btnXem = row.findViewById(R.id.btnXem);

        final NhanVien nv = list.get(i);
        tvID.setText(nv.ma+"");
        tvHoTen.setText(nv.hoten);
        //tvGioiTinh.setText(nv.gioiTinh);
        //tvSDT.setText(nv.sdt);
        tvPhongBan.setText(nv.phongban);
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nv.anh, 0, nv.anh.length);
        imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", nv.ma);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete(nv.ma);

                }
            });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
                dialog.show();
        }
        });

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThongTin_NhanVien_activity.class);
                intent.putExtra("NV", nv);
                context.startActivity(intent);
            }
        });


        return row;
    }

    private void delete(int id){
        SQLiteDatabase database = this.context.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        database.delete("NhanVien", "MaNV = ?", new String[]{id+""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        while (cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String phongBan = cursor.getString(2);
            byte[] anh = cursor.getBlob(5);
            //list.add(new NhanVien(ma, ten, gioiTinh, sdt, noiSinh, anh));
            list.add(new NhanVien(ma, ten,phongBan, anh));
        }
        cursor.close();
        database.close();
        notifyDataSetChanged();
    }



}
