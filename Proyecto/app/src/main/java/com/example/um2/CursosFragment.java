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

public class CursosFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.activity_cursos, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del RecyclerView
        RecyclerView mCursosList = view.findViewById(R.id.cursos);
        mCursosList.setHasFixedSize(true);
        mCursosList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración de Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Cursos");
        mDatabase.keepSynced(true);

        FirebaseRecyclerOptions<Cursos> options = new FirebaseRecyclerOptions.Builder<Cursos>()
                .setQuery(mDatabase, Cursos.class)
                .build();

        FirebaseRecyclerAdapter<Cursos, CursosActivity.CursosViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Cursos, CursosActivity.CursosViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(CursosActivity.CursosViewHolder holder, int position, Cursos model) {
                        holder.setTitle(model.getTitle());
                        holder.setDesc(model.getDesc());
                        holder.setImg(requireContext(), model.getImg());
                        holder.setDire(model.getDire());
                        holder.setTel(model.getTel());
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
}
