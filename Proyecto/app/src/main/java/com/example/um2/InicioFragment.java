package com.example.um2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InicioFragment extends Fragment {

    private RecyclerView universidadesDestacadas, cursosDestacados;
    private DatabaseReference mDatabaseUnis, mDatabaseCursos;
    private FirebaseRecyclerAdapter<InicioUnisDes, InicioActivity.UnisDesViewHolder> firebaseRecyclerAdapterUnis;
    private FirebaseRecyclerAdapter<InicioCursosDes, InicioActivity.CursosDesViewHolder> firebaseRecyclerAdapterCursos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el layout del fragmento (en lugar de setContentView)
        View rootView = inflater.inflate(R.layout.activity_inicio, container, false);

        // Configuración de los RecyclerViews
        universidadesDestacadas = rootView.findViewById(R.id.universidades_destacadas);
        cursosDestacados = rootView.findViewById(R.id.cursos_destacados);

        universidadesDestacadas.setHasFixedSize(true);
        universidadesDestacadas.setLayoutManager(new LinearLayoutManager(getContext()));

        cursosDestacados.setHasFixedSize(true);
        cursosDestacados.setLayoutManager(new LinearLayoutManager(getContext()));

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
                holder.setImg(getContext(), model.getImg());
                holder.setDire(model.getDire());
                holder.setTel(model.getTel());
            }

            @Override
            public InicioActivity.UnisDesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unis, parent, false);
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
                holder.setImg(getContext(), model.getImg());
                holder.setDire(model.getDire());
                holder.setTel(model.getTel());
            }

            @Override
            public InicioActivity.CursosDesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cursos, parent, false);
                return new InicioActivity.CursosDesViewHolder(view);
            }
        };

        cursosDestacados.setAdapter(firebaseRecyclerAdapterCursos);

        return rootView; // Devuelve la vista inflada
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapterUnis != null) {
            firebaseRecyclerAdapterUnis.startListening();
        }
        if (firebaseRecyclerAdapterCursos != null) {
            firebaseRecyclerAdapterCursos.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapterUnis != null) {
            firebaseRecyclerAdapterUnis.stopListening();
        }
        if (firebaseRecyclerAdapterCursos != null) {
            firebaseRecyclerAdapterCursos.stopListening();
        }
    }
}
