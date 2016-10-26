package sg.edu.nus.ticketingstaffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick_Login(View view){
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }
}
