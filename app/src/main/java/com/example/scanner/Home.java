package com.example.scanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
    public static double PRICE = 0.1;
    public static double total = scanCount * PRICE;
    // string information
    public static String customerName = "";
    public static String employeeName = "";
    public static String emailAddress = "";
    // array to hold all the scanned items
    public static String[] scannedItems = new String[10];
    // set up variables for input and display
    Button scanButton;
    Button emailButton;
    Button clearButton;
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

        // set up text view label
        dateLabel = findViewById(R.id.dateLabel);
        dateLabel.setText(date);
        itemsScannedLabel = findViewById(R.id.itemsScannedLabel);
        itemsScannedLabel.setText(String.valueOf(scanCount));
        totalEarnedLabel = findViewById(R.id.totalEarnedLabel);
        totalEarnedLabel.setText(String.format("$ %.2f", total));
        // edit text
        emailEditText = findViewById(R.id.editTextEmailAddress);
        emailEditText.setText(emailAddress);
        customerNameEditText = findViewById(R.id.editTextCustomerName);
        customerNameEditText.setText(customerName);
        employeeNameEditText = findViewById(R.id.editTextEmployeeName);
        employeeNameEditText.setText(employeeName);
        // set up buttons
        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this::onClickScan);
        emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(this::onClickEmail);
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this::onClickClear);
    }

    public void onClickScan(View v) {
        scanCode();
    }

    public void onClickEmail(View v) {
        sendEmail();
    }

    public void onClickClear(View v) {
        clear();
    }

    private void clear() {
        // clear all the values
        dateLabel.setText(Calendar.getInstance().getTime().toString());
        customerNameEditText.setText("");
        employeeNameEditText.setText("");
        scanCount = 0;
        total = PRICE * scanCount;
        emailEditText.setText("");

        // we can't count any more bottles, go back to home, toast a warning to close out
        Intent backToHome = new Intent(Home.this, Home.class);
        Home.this.startActivity(backToHome);
        Home.this.finish();
        // if nothing has been scanned, and user goes back
        Toast.makeText(this, "Cleared all data!", Toast.LENGTH_LONG).show();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Total items scanned: "+scanCount);
        integrator.initiateScan();
    }

    private void sendEmail() {
        // if we are sending email, check if the email to send is provided
        if (scanCount == 0) {
            // if null send a error message
            Toast.makeText(Home.this, "Nothing scanned to pay!", Toast.LENGTH_SHORT).show();
        } else if (customerNameEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty customer name provided.", Toast.LENGTH_SHORT).show();
        } else if (employeeNameEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty employee name provided.", Toast.LENGTH_SHORT).show();
        } else if (emailEditText.getText().toString().equals("")) {
            // if null send a error message
            Toast.makeText(Home.this, "Empty email provided.", Toast.LENGTH_SHORT).show();
        } else {
            String to = emailEditText.getText().toString();
            String subject="Ashfield North News - Total Returned";
            String message="Receipt time: " + date;
            message += "\n\nCustomer Name: " + customerNameEditText.getText().toString();
            message += "\nEmployee Name: " + employeeNameEditText.getText().toString();
            message += "\nItems Scanned: " + scanCount;
            message += "\nTotal Earned: $" + total;
            message += "\n\nPlease pay the customer $"+total+" with a smile!";

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to });
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // if this is the first scan, we don't need to see if this bar code was already scanned
                if (scanCount == 0) {
                    // we scanned something, we want to store the scanned string into an array
                    scannedItems[scanCount] = result.getContents();
                    Log.d("Status array", scannedItems[scanCount]);
                    // see how many we have counted
                    // setting these variables up will also help us retain all the information which can be lost
                    scanCount++;
                    total = scanCount * PRICE;
                    emailAddress = emailEditText.getText().toString();
                    customerName = customerNameEditText.getText().toString();
                    employeeName = employeeNameEditText.getText().toString();
                    // show a alert to see if the user want's to continue or is done
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Continue scanning?");
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user want's to scan again
                            scanCode();
                        }
                    }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // user is done
                            Intent backToHome = new Intent(Home.this, Home.class);
                            Home.this.startActivity(backToHome);
                            Home.this.finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (scanCount == scannedItems.length) {
                    // we can't count any more bottles, go back to home, toast a warning to close out
                    Intent backToHome = new Intent(Home.this, Home.class);
                    Home.this.startActivity(backToHome);
                    // if nothing has been scanned, and user goes back
                    Toast.makeText(this, "Scanning Limit reached. Please start a new order.", Toast.LENGTH_LONG).show();
                } else {
                    // check if this scanned bar code was already scanned
                    boolean flag = false;
                    for (int i = 0; i < scannedItems.length; i++) {
                        if (result.getContents().equals(scannedItems[i])) {
                            flag = true;
                            break;
                        }
                    }
                    // check for flag
                    if (flag) {
                        // show a alert to the user saying this was already scanned
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Error Scanning?");
                        builder.setMessage("Barcode already Scanned.");
                        builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // user want's to scan again
                                scanCode();
                            }
                        }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // user is done
                                Intent backToHome = new Intent(Home.this, Home.class);
                                Home.this.startActivity(backToHome);
                                Home.this.finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        // we scanned something, we want to store the scanned string into an array
                        scannedItems[scanCount] = result.getContents();
                        Log.d("Status array", scannedItems[scanCount]);
                        // see how many we have counted, see the total
                        // setting these variables up will also help us retain all the information which can be lost
                        scanCount++;
                        total = scanCount * PRICE;
                        emailAddress = emailEditText.getText().toString();
                        customerName = customerNameEditText.getText().toString();
                        employeeName = employeeNameEditText.getText().toString();
                        // show a alert to see if the user want's to continue or is done
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Continue scanning?");
                        builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // user want's to scan again
                                scanCode();
                            }
                        }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // user is done
                                Intent backToHome = new Intent(Home.this, Home.class);
                                Home.this.startActivity(backToHome);
                                Home.this.finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            } else {
                // if nothing has been scanned, and user goes back
                if (scanCount == 0)
                    Toast.makeText(this, "No Results!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
