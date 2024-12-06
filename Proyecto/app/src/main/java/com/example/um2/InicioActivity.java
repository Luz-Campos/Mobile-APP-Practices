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

public class InicioActivity extends AppCompatActivity {

    private RecyclerView universidadesDestacadas, cursosDestacados;
    private DatabaseReference mDatabaseUnis, mDatabaseCursos;
    private FirebaseRecyclerAdapter<InicioUnisDes, InicioActivity.UnisDesViewHolder> firebaseRecyclerAdapterUnis;
    private FirebaseRecyclerAdapter<InicioCursosDes, InicioActivity.CursosDesViewHolder> firebaseRecyclerAdapterCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Configuración de los RecyclerViews
        universidadesDestacadas = findViewById(R.id.universidades_destacadas);
        cursosDestacados = findViewById(R.id.cursos_destacados);

        universidadesDestacadas.setHasFixedSize(true);
        universidadesDestacadas.setLayoutManager(new LinearLayoutManager(this));

        cursosDestacados.setHasFixedSize(true);
        cursosDestacados.setLayoutManager(new LinearLayoutManager(this));

        // Configuración de Firebase
        mDatabaseUnis = FirebaseDatabase.getInstance().getReference("UniversityMatch_UnisDestacadas");
        mDatabaseCursos = FirebaseDatabase.getInstance().getReference("UniversityMatch_CursosDestacados");

        mDatabaseUnis.keepSynced(true);
        mDatabaseCursos.keepSynced(true);

        // Configuración del adaptador para universidades destacadas
        FirebaseRecyclerOptions<InicioUnisDes> optionsUnis = new FirebaseRecyclerOptions.Builder<InicioUnisDes>()
                .setQuery(mDatabaseUnis, InicioUnisDes.class)
                .build();

        firebaseRecyclerAdapterUnis = new FirebaseRecyclerAdapter<InicioUnisDes, InicioActivity.UnisDesViewHolder>(optionsUnis) {
            @Override
            protected void onBindViewHolder(InicioActivity.UnisDesViewHolder holder, int position, InicioUnisDes model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getApplicationContext(), model.getImg());
                holder.setDire(model.getDire());
                holder.setTel(model.getTel());
            }

            @Override
            public InicioActivity.UnisDesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_universidad, parent, false);
                return new InicioActivity.UnisDesViewHolder(view);
            }
        };

        universidadesDestacadas.setAdapter(firebaseRecyclerAdapterUnis);

        // Configuración del adaptador para cursos destacados
        FirebaseRecyclerOptions<InicioCursosDes> optionsCursos = new FirebaseRecyclerOptions.Builder<InicioCursosDes>()
                .setQuery(mDatabaseCursos, InicioCursosDes.class)
                .build();

        firebaseRecyclerAdapterCursos = new FirebaseRecyclerAdapter<InicioCursosDes, InicioActivity.CursosDesViewHolder>(optionsCursos) {
            @Override
            protected void onBindViewHolder(InicioActivity.CursosDesViewHolder holder, int position, InicioCursosDes model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImg(getApplicationContext(), model.getImg());
                holder.setDire(model.getDire());
                holder.setTel(model.getTel());
            }

            @Override
            public InicioActivity.CursosDesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cursos, parent, false);
                return new InicioActivity.CursosDesViewHolder(view);
            }
        };

        cursosDestacados.setAdapter(firebaseRecyclerAdapterCursos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapterUnis != null) {
            firebaseRecyclerAdapterUnis.startListening();
        }
        if (firebaseRecyclerAdapterCursos != null) {
            firebaseRecyclerAdapterCursos.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapterUnis != null) {
            firebaseRecyclerAdapterUnis.stopListening();
        }
        if (firebaseRecyclerAdapterCursos != null) {
            firebaseRecyclerAdapterCursos.stopListening();
        }
    }

    public static class UnisDesViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public UnisDesViewHolder(View itemView) {
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

    public static class CursosDesViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CursosDesViewHolder(View itemView) {
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
