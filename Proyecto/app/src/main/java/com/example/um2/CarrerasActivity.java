package com.example.um2;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CarrerasActivity extends AppCompatActivity {

    private RecyclerView mCarrerasList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Carreras, CarrerasViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras);

        // Configuración del RecyclerView
        mCarrerasList = findViewById(R.id.carreras);
        mCarrerasList.setHasFixedSize(true);
        mCarrerasList.setLayoutManager(new LinearLayoutManager(this));

        // Configuración de Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Carreras");
        mDatabase.keepSynced(true);

        // Configuración inicial del adaptador de Firebase
        setupFirebaseAdapter("");
    }

    private void setupFirebaseAdapter(String query) {
        FirebaseRecyclerOptions<Carreras> options = new FirebaseRecyclerOptions.Builder<Carreras>()
                .setQuery(query.isEmpty() ? mDatabase : mDatabase.orderByChild("title").startAt(query).endAt(query + "\uf8ff"), Carreras.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Carreras, CarrerasViewHolder>(options) {
            @Override
            protected void onBindViewHolder(CarrerasViewHolder holder, int position, Carreras model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
            }

            @Override
            public CarrerasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carreras, parent, false);
                return new CarrerasViewHolder(view);
            }
        };

        mCarrerasList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    public static class CarrerasViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CarrerasViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView postTitle = mView.findViewById(R.id.post_title);
            postTitle.setText(title);
        }

        public void setDesc(String desc) {
            TextView postDesc = mView.findViewById(R.id.post_desc);
            postDesc.setText(desc);
        }
    }
}
