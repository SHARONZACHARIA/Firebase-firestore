package com.example.sharonzacharia.noteit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ReadActivity extends AppCompatActivity {

    TextView title_note,date_note,body;
    Toolbar read_toolbar;
    ShakeSensor s;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        final Intent i = getIntent();
        Note details = i.getParcelableExtra("object");
        title_note = findViewById(R.id.note_title);
        date_note = findViewById(R.id.timestamp);
        body= findViewById(R.id.note_body);

        title_note.setText(details.title);
        date_note.setText(details.TimeStamp);
        body.setText(details.body);
       read_toolbar = findViewById(R.id.read_toolbar);
        read_toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        int   lastindex;
      if(details.title.length()<=12)
        {
          lastindex= details.title.length(); }
        else
            lastindex = details.title.length()/2;
       String Titlebar_note = details.title.substring(0,lastindex)+"...";
       read_toolbar.setTitle(Titlebar_note);
       setSupportActionBar(read_toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       s = new ShakeSensor();
        s.initialize(this);

       intentFilter = new IntentFilter(ShakeSensor.INTENT_KEY);
       broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(ShakeSensor.INTENT_KEY))
                {   try {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getSupportActionBar().show();

                }catch(Exception e){}
                }
            }
        };



    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(broadcastReceiver,intentFilter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.readmenu,menu);
        return  true;
    }


    public void share(MenuItem menuItem)
    {

    }

    public void fullView(MenuItem menuItem)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

    }





    @Override
    protected void onPause() {
        super.onPause();
        ShakeSensor.sensorManager.unregisterListener(s);
        this.unregisterReceiver(broadcastReceiver);
    }

    public void shareData(MenuItem menuItem)
    {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT,title_note.getText().toString());
        share.putExtra(Intent.EXTRA_TEXT,body.getText().toString());
        startActivity(share);
    }



    public void edit(MenuItem menu)
    {
        Intent edit_intent = new Intent(this,WriteNoteActivity.class);
        startActivity(edit_intent);
    }
}


