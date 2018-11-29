package com.example.sharonzacharia.noteit;

import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;




public class UploadNotes{
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void upload(String body, String title, final Context context, final View view,Boolean b) {

        if (!(body.equals("") || title.equals(""))) {


            FirebaseFirestore fb = FirebaseFirestore.getInstance();
            Map<String, String> notedata = new HashMap<>();
            notedata.put("title", title);
            notedata.put("body", body);
            notedata.put("TimeStamp", findDate());


            fb.collection(LoginActivity.USER_ID).add(notedata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Snackbar.make(view, "Notes Addded", Snackbar.LENGTH_LONG).show();
                }
            });

        }
        else
        {
            Snackbar.make(view, "Invalid contents ", Snackbar.LENGTH_LONG).show();
        }
    }


        @RequiresApi(api = Build.VERSION_CODES.N)
        private static String findDate ()
        {
            return DateFormat.getDateTimeInstance().format(new Date());
        }


}
