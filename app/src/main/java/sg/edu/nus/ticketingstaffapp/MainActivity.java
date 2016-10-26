package sg.edu.nus.ticketingstaffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_ScanQRCode(View view) {
        Intent myIntent = new Intent(this, QRScanner.class);
        startActivity(myIntent);
    }

    public void onClick_SMSMarketingMessage(View view) {

    }

}
