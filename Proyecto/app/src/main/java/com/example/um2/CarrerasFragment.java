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

public class CarrerasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.activity_carreras, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del RecyclerView
        RecyclerView mCarrerasList = view.findViewById(R.id.carreras);
        mCarrerasList.setHasFixedSize(true);
        mCarrerasList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración de Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Carreras");
        mDatabase.keepSynced(true);

        FirebaseRecyclerOptions<Carreras> options = new FirebaseRecyclerOptions.Builder<Carreras>()
                .setQuery(mDatabase, Carreras.class)
                .build();

        FirebaseRecyclerAdapter<Carreras, CarrerasActivity.CarrerasViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Carreras, CarrerasActivity.CarrerasViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(CarrerasActivity.CarrerasViewHolder holder, int position, Carreras model) {
                        holder.setTitle(model.getTitle());
                        holder.setDesc(model.getDesc());
                    }

                    @Override
                    public CarrerasActivity.CarrerasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carreras, parent, false);
                        return new CarrerasActivity.CarrerasViewHolder(view);
                    }
                };

        mCarrerasList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
}
