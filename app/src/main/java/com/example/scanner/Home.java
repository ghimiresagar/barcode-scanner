package com.example.scanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {
    // get current time with Calendar library
    static Date currentTime = Calendar.getInstance().getTime();
    // set up variables
    public static String date = currentTime.toString();
    public static int scanCount = 0;
    static double PRICE = 0.1;
    public static double total = scanCount * PRICE;
    // array to hold all the scanned items
    public static String[] scannedItems = new String[100];
    // set up variables for input and display
    Button scanButton;
    Button emailButton;
    TextView dateLabel;
    TextView itemsScannedLabel;
    TextView totalEarnedLabel;
    EditText customerNameEditText;
    EditText employeeNameEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("Status", date);

        // set up text view label
        dateLabel = findViewById(R.id.dateLabel);
        dateLabel.setText(date);
        itemsScannedLabel = findViewById(R.id.itemsScannedLabel);
        itemsScannedLabel.setText(String.valueOf(scanCount));
        totalEarnedLabel = findViewById(R.id.totalEarnedLabel);
        totalEarnedLabel.setText(String.format("$ %s", total));
        // set up buttons
        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this::onClickScan);
        emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(this::onClickEmail);
    }

    public void onClickScan(View v) {
        scanCode();
    }

    public void onClickEmail(View v) {
        Log.d("Status", "Email Clicked!");
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Total items scanned: "+scanCount);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder((this));
//                builder.setMessage(result.getContents());
                scanCount++;
                builder.setMessage("Scanned " + scanCount + " items.");
                builder.setTitle("Scanning Result");
//                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        scanCode();
//                    }
//                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        finish();
////                        Intent backToHome = new Intent(Home.this, Home.class);
////                        Home.this.startActivity(backToHome);
////                        Home.this.finish();
//                    }
//                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
