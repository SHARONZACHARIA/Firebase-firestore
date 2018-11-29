package com.example.sharonzacharia.noteit;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import android.widget.TextView;





public class WriteNoteActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 123;
    Toolbar toolbar ;
    AppCompatImageView image_camera;
    private static Boolean CHECK_BOX_STATE = false;
     CoordinatorLayout main_layout;
     FloatingActionButton camera_fab;

    TextView note_body,note_title;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        toolbar = findViewById(R.id.note_layout_toolbar);
        int color = Color.parseColor("#FFFFFF");
        toolbar.setTitleTextColor(color);
        toolbar.setTitle("Compose note");
        main_layout = findViewById(R.id.note_write);
        note_body= findViewById(R.id.note_body);
        note_title = findViewById(R.id.note_title);
        image_camera = findViewById(R.id.image_view);
        image_camera.setVisibility(View.INVISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);

         camera_fab = findViewById(R.id.camaera_fab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.writenotes_options,menu);
        shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        menu.findItem(R.id.keep_local_copy).setChecked(shared.getBoolean("ENABLE_LOCAL_COPY",false));
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)

         {   try {
             image_camera.setVisibility(View.VISIBLE);
             Bundle extras = data.getExtras();
             Bitmap image = (Bitmap) extras.get("data");
             assert image != null;
             image_camera.setMaxWidth(image.getWidth());
             image_camera.setMaxHeight(image.getHeight());
             image_camera.setImageBitmap(image);
         }catch (Exception e){
             Log.e("exception ","cannot start camera  "+e.getMessage());
         }
         }
    }

    public void addImage(View v)
    {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(camera,REQUEST_IMAGE_CAPTURE);

        }
    }

    @SuppressLint("NewApi")
    public void uploadData(View v)
    {
        UploadNotes.upload(note_body.getText().toString(),note_title.getText().toString(),
                getApplicationContext(),main_layout,CHECK_BOX_STATE);

             if(CHECK_BOX_STATE)
             {
                 MakeLocalCopy.localCopy();
             }

    }

     public void exit_from_activity(MenuItem menu )
     {
         onBackPressed();
     }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        if(item.getItemId()==R.id.keep_local_copy)
        {

            if (item.isChecked())
            {
                item.setChecked(false);
            }
            else
                item.setChecked(true);
            CHECK_BOX_STATE =item.isChecked();


        }
        return true;
    }
}
