package com.developer.nfcproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAddCard;
    private List<ItemObject> rowListItem = new ArrayList<>();
    private LinearLayoutManager lLayout;
    private RecyclerViewAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonAddCard = findViewById(R.id.buttonAddCard);
        buttonAddCard.setOnClickListener(this);

        lLayout = new LinearLayoutManager(ProfileActivity.this);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        rowListItem.add(new ItemObject("Empty", R.drawable.cardbackground_sky));
        rcAdapter = new RecyclerViewAdapter(ProfileActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (rowListItem.get(0).getName().equals("Empty")) {
            rowListItem.clear();
        }
        rowListItem.add(new ItemObject("Transport Card", R.drawable.cardbackground_sky));
        rcAdapter.notifyDataSetChanged();
    }
}
