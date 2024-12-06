package com.example.um2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificacionesFragment extends Fragment {

    private RecyclerView mNotificacionesList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Notificaciones, NotificationsActivity.NotificacionesViewHolder> firebaseRecyclerAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_notifications, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotificacionesList = view.findViewById(R.id.notificaciones);
        mNotificacionesList.setHasFixedSize(true);
        mNotificacionesList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración de Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Notificaciones");
        mDatabase.keepSynced(true);

        FirebaseRecyclerOptions<Notificaciones> options = new FirebaseRecyclerOptions.Builder<Notificaciones>()
                .setQuery(mDatabase, Notificaciones.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notificaciones, NotificationsActivity.NotificacionesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(NotificationsActivity.NotificacionesViewHolder holder, int position, Notificaciones model) {
                holder.setDesc(model.getDesc());
                holder.setImg(requireContext(), model.getImg());

                // Configuramos el clic para eliminar la notificación
                holder.mView.findViewById(R.id.delete_button).setOnClickListener(v -> {
                    String notificationId = getRef(position).getKey();
                    deleteNotification(notificationId);
                });
            }

            @Override
            public NotificationsActivity.NotificacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificaciones, parent, false);
                return new NotificationsActivity.NotificacionesViewHolder(view);
            }
        };

        mNotificacionesList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void deleteNotification(String notificationId) {
        mDatabase.child(notificationId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Notificación eliminada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}
