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

public class UniversidadesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.activity_universidades, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del RecyclerView
        RecyclerView mUnisList = view.findViewById(R.id.universidades);
        mUnisList.setHasFixedSize(true);
        mUnisList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración de Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch");
        mDatabase.keepSynced(true);

        FirebaseRecyclerOptions<Unis> options = new FirebaseRecyclerOptions.Builder<Unis>()
                .setQuery(mDatabase, Unis.class)
                .build();

        FirebaseRecyclerAdapter<Unis, UniversidadesActivity.UnisViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Unis, UniversidadesActivity.UnisViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(UniversidadesActivity.UnisViewHolder holder, int position, Unis model) {
                        holder.setTitle(model.getTitle());
                        holder.setDesc(model.getDesc());
                        holder.setImg(requireContext(), model.getImg());
                        holder.setDire(model.getDire());
                        holder.setTel(model.getTel());
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
}

