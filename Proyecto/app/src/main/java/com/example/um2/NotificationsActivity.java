package com.example.um2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView mNotificacionesList;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Notificaciones, NotificationsActivity.NotificacionesViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mDatabase = FirebaseDatabase.getInstance().getReference("UniversityMatch_Notificaciones");
        mDatabase.keepSynced(true);

        mNotificacionesList = findViewById(R.id.notificaciones);
        mNotificacionesList.setHasFixedSize(true);
        mNotificacionesList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Notificaciones> options = new FirebaseRecyclerOptions.Builder<Notificaciones>()
                .setQuery(mDatabase, Notificaciones.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notificaciones, NotificationsActivity.NotificacionesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(NotificationsActivity.NotificacionesViewHolder holder, int position, Notificaciones model) {
                holder.setDesc(model.getDesc());
                holder.setImg(getApplicationContext(), model.getImg());

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
                Toast.makeText(this, "Notificación eliminada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public static class NotificacionesViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public NotificacionesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImg(Context ctx, String img) {
            ImageView postImage = mView.findViewById(R.id.post_image);
            Picasso.get().load(img).into(postImage);
        }

        public void setDesc(String desc) {
            TextView postDesc = mView.findViewById(R.id.post_desc);
            postDesc.setText(desc);
        }
    }
}
