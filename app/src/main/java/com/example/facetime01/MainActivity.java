package com.example.facetime01;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.facetime01.R;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_SMS_PERMISSION = 2;

    private EditText etsdt;
    private EditText ettn;
    private Button btngoi;
    private Button btnnt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etsdt = findViewById(R.id.etsdt);
        ettn = findViewById(R.id.ettn);
        btngoi = findViewById(R.id.btngoi);
        btnnt = findViewById(R.id.btnnt);

        btngoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goi();
            }
        });

        btnnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ntin();
            }
        });
    }

    private void goi() {
        String phoneNumber = etsdt.getText().toString();
        if (phoneNumber.trim().isEmpty()) {
            Toast.makeText(this, "Nhập số", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
    }

    private void ntin() {
        String phoneNumber = etsdt.getText().toString();
        String message = ettn.getText().toString();

        if (phoneNumber.trim().isEmpty() || message.trim().isEmpty()) {
            Toast.makeText(this, "Nhập số", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goi();
                } else {
                    Toast.makeText(this, "Cấp quyền call", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_SMS_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ntin();
                } else {
                    Toast.makeText(this, "Cấp quyền nhắn tin", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
