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

public class CursosActivity extends AppCompatActivity {

    private RecyclerView mCursosList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Cursos, CursosActivity.CursosViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Cursos");
        mDatabase.keepSynced(true);

        mCursosList = findViewById(R.id.cursos);
        mCursosList.setHasFixedSize(true);
        mCursosList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cursos> options = new FirebaseRecyclerOptions.Builder<Cursos>()
                .setQuery(mDatabase, Cursos.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Cursos, CursosActivity.CursosViewHolder>(options) {
            @Override
            protected void onBindViewHolder(CursosActivity.CursosViewHolder holder, int position, Cursos model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getApplicationContext(), model.getImg());
                holder.setDesc(model.getDire());
                holder.setDesc(model.getTel());
            }

            @Override
            public CursosActivity.CursosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cursos, parent, false);
                return new CursosActivity.CursosViewHolder(view);
            }
        };

        mCursosList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public static class CursosViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CursosViewHolder(View itemView) {
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