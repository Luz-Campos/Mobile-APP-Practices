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
import com.squareup.picasso.Picasso;

public class UniversidadesActivity extends AppCompatActivity {

    private RecyclerView mUnisList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Unis, UnisViewHolder> firebaseRecyclerAdapter;

    private SearchView searchView;
    private String currentQuery = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universidades);

        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch");
        mDatabase.keepSynced(true);

        mUnisList = findViewById(R.id.universidades);
        mUnisList.setHasFixedSize(true);
        mUnisList.setLayoutManager(new LinearLayoutManager(this));
        
        // Configuración inicial del adaptador de Firebase
        setupFirebaseAdapter("");
    }

    private void setupFirebaseAdapter(String query) {
        FirebaseRecyclerOptions<Unis> options = new FirebaseRecyclerOptions.Builder<Unis>()
                .setQuery(query.isEmpty() ? mDatabase : mDatabase.orderByChild("title").startAt(query).endAt(query + "\uf8ff"), Unis.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Unis, UniversidadesActivity.UnisViewHolder>(options) {
            @Override
            protected void onBindViewHolder(UniversidadesActivity.UnisViewHolder holder, int position, Unis model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
            }

            @Override
            public UniversidadesActivity.UnisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unis, parent, false);
                return new UniversidadesActivity.UnisViewHolder(view);
            }
        };

        mUnisList.setAdapter(firebaseRecyclerAdapter);
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

    public static class UnisViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public UnisViewHolder(View itemView) {
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

        public void setImg(Context ctx, String img) {
            ImageView postImage = mView.findViewById(R.id.post_image);
            Picasso.get().load(img).into(postImage);
            Picasso.get()
                    .load(img)
                    .resize(200, 200) // Especifica el ancho y el alto deseado
                    .centerCrop()    // Escala la imagen para llenar las dimensiones
                    .into(postImage);
        }

        public void setDire(String dire) {
            TextView postDire = mView.findViewById(R.id.post_dire);
            postDire.setText(dire);
        }

        public void setTel(String tel) {
            TextView postTel = mView.findViewById(R.id.post_tel);
            postTel.setText(tel);
        }
    }
}