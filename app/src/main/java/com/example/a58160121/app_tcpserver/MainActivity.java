package com.example.a58160121.app_tcpserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageButton ibt;
    TextView infoip, txtMonitor;
    Server server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibt = (ImageButton)findViewById(R.id.ibt);
        infoip = (TextView)findViewById(R.id.infoip);
        txtMonitor = (TextView)findViewById(R.id.txtMonitor);

        ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibt.setImageResource(R.drawable.icon_1);
            }
        });

        server = new  Server(this);
        infoip.setText((server.getIpAddress()+ ":" + server.getPort()));
    }
}
