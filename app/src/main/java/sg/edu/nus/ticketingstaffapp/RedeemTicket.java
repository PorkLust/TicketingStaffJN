package sg.edu.nus.ticketingstaffapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RedeemTicket extends AppCompatActivity {

    TextView qrCodeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_ticket);

        String qrContent = getIntent().getStringExtra("content");

        qrCodeInfo = (TextView) findViewById(R.id.qrcode_Info);
        qrCodeInfo.setText(qrContent);
       // Toast.makeText(this, "QR Content = " + qrContent, Toast.LENGTH_SHORT).show();
    }
}
