package com.example.um2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class DonacionesActivity extends AppCompatActivity {

    private RecyclerView mDonacionesList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Donaciones, DonacionesActivity.DonacionesViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaciones);

        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Donaciones");
        mDatabase.keepSynced(true);

        mDonacionesList = findViewById(R.id.donaciones);
        mDonacionesList.setHasFixedSize(true);
        mDonacionesList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Donaciones> options = new FirebaseRecyclerOptions.Builder<Donaciones>()
                .setQuery(mDatabase, Donaciones.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Donaciones, DonacionesActivity.DonacionesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(DonacionesActivity.DonacionesViewHolder holder, int position, Donaciones model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getApplicationContext(), model.getImg());
                holder.setDesc(model.getMarca());
                holder.setDesc(model.getColor());
            }

            @Override
            public DonacionesActivity.DonacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donaciones, parent, false);
                return new DonacionesActivity.DonacionesViewHolder(view);
            }
        };

        mDonacionesList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public static class DonacionesViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public DonacionesViewHolder(View itemView) {
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

        public void setMarca(String marca) {
            TextView postMarca = mView.findViewById(R.id.post_marca);
            postMarca.setText(marca);
        }

        public void setColor(String color) {
            TextView postColor = mView.findViewById(R.id.post_color);
            postColor.setText(color);
        }
    }
}