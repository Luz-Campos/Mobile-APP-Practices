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

public class DonacionesFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.activity_donaciones, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del RecyclerView
        RecyclerView mDonacionesList = view.findViewById(R.id.donaciones);
        mDonacionesList.setHasFixedSize(true);
        mDonacionesList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración de Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Donaciones");
        mDatabase.keepSynced(true);

        FirebaseRecyclerOptions<Donaciones> options = new FirebaseRecyclerOptions.Builder<Donaciones>()
                .setQuery(mDatabase, Donaciones.class)
                .build();

        FirebaseRecyclerAdapter<Donaciones, DonacionesActivity.DonacionesViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Donaciones, DonacionesActivity.DonacionesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(DonacionesActivity.DonacionesViewHolder holder, int position, Donaciones model) {
                        holder.setTitle(model.getTitle());
                        holder.setDesc(model.getDesc());
                        holder.setImg(requireContext(), model.getImg());
                        holder.setMarca(model.getMarca());
                        holder.setColor(model.getColor());
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
}
