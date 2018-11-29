package com.example.sharonzacharia.noteit;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class NotesListActivity extends AppCompatActivity {


    ArrayList<Note> notesArrayList;
    RecyclerView  notesRecyclerview;
    NotesListAdapter notesadapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    FloatingActionButton addnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        notesArrayList = new ArrayList<>();
        notesRecyclerview = findViewById(R.id.notes_recycler);
      //  notesadapter = new NotesListAdapter(notesArrayList, this);
        toolbar = findViewById(R.id.tollbar);
        toolbar.setTitle("Note it");
        addnotes = findViewById(R.id.add_fab);
        toolbar.setTitleTextColor(Integer.parseInt(String.valueOf(Color.parseColor("#FFFFFF"))));
        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        notesRecyclerview.setLayoutManager(layoutManager);
        notesRecyclerview.hasFixedSize();
        swipeRefreshLayout= findViewById(R.id.swipe_layout);
        Toast.makeText(getApplicationContext(),LoginActivity.USER_ID,Toast.LENGTH_LONG).show();

        //swipeRefreshLayout.setRefreshing(true);
         LoadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }


        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.toolbar_menu,menu);
       return true;
    }


    public void  signout(MenuItem menu)
    {
        addnotes.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fm.beginTransaction();
       fragmentTransaction.replace(R.id.mains,new UserProfileFragment());
       fragmentTransaction.addToBackStack(null);

       fragmentTransaction.commit();

    }


    public void showWrite(View v)
    {
        Intent write = new Intent(this, WriteNoteActivity.class);
        startActivity(write);
    }

    public void LoadData()
    {
         swipeRefreshLayout.setRefreshing(true);
        notesadapter = new NotesListAdapter(notesArrayList,getBaseContext());

         fb.collection(LoginActivity.USER_ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {    // firestore Serailisation not working

                 for(QueryDocumentSnapshot q : queryDocumentSnapshots)
                 {


                     Note n = new Note();

                     String title = q.getString("title");
                       String body = q.getString("body");
                       String TimeStamp = q.getString("TimeStamp");
                       n.title = title;
                       n.body = body;
                       n.TimeStamp = TimeStamp;
                       notesArrayList.add(n);

                 }
                   // notesadapter = new NotesListAdapter(notesArrayList,getBaseContext());
                    notesRecyclerview.setAdapter(notesadapter);
                    swipeRefreshLayout.setRefreshing(false);
             }
         });

    }


    @Override
    protected void onResume() {
        super.onResume();
        addnotes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
       Intent i = new Intent(Intent.ACTION_MAIN);
       i.addCategory(Intent.CATEGORY_HOME);
       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(i);
    }
}
