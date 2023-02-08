package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {
    Button btnChonHinh, btnChupHinh, btnLuu, btnQuayLai;
    EditText etTen, etSDT, etPhongBan, etEmail;
    ImageView imageView;
    final String DB_NAME = "EmployeeDB.db";
    final int REQUEST_TAKE_PHOTO = 44;
    final int REQUEST_CHOOSE_PHOTO = 66;
    int ma = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addControls();
        addEvents();
        initUI();
    }

    private void addControls(){
        btnChonHinh = findViewById(R.id.btnChonHinh);
        btnChupHinh = findViewById(R.id.btnChupHinh);
        btnLuu = findViewById(R.id.btnLuu);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        etTen = findViewById(R.id.etTen);
        etPhongBan = findViewById(R.id.etPhongBan);
        etEmail = findViewById(R.id.etEmail);
        etSDT = findViewById(R.id.etSDT);
        imageView = findViewById(R.id.imageView);

    }

    private void addEvents(){
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });

        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private void initUI(){
        Intent intent = getIntent();
        ma = intent.getIntExtra("ID", -1);
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien WHERE MaNV = ?", new String[]{ma+"",});
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String phongBan = cursor.getString(2);
        String sdt = cursor.getString(3);
        String email = cursor.getString(4);
        byte[] anh = cursor.getBlob(5);
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        imageView.setImageBitmap(bmHinhDaiDien);
        etTen.setText(ten);
        etPhongBan.setText(phongBan);
        etSDT.setText(sdt);
        etEmail.setText(email);
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(resultCode == REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }else if(requestCode == REQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void update(){
        String hoten = etTen.getText().toString();
        String phongBan = etPhongBan.getText().toString();
        String sdt = etSDT.getText().toString();
        String email = etEmail.getText().toString();
        byte[] anh = getByteArray(imageView);
        ContentValues contentValues = new ContentValues();
        contentValues.put("HoTen", hoten);
        contentValues.put("PhongBan", phongBan);
        contentValues.put("SDT", sdt);
        contentValues.put("Email", email);
        contentValues.put("Anh", anh);

        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        int success = database.update("NhanVien", contentValues, "MaNV = ?", new String[]{ma+""});

        Toast.makeText(this, "Cập nhật thành công "+ success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        database.close();

    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public byte[] getByteArray(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
                Intent intent = new Intent(UpdateActivity.this, PhongBan_activity.class);
                startActivity(intent);
                break;
            case R.id.mnuAbout:
                Intent intent1 = new Intent(UpdateActivity.this, About_Activity.class);
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