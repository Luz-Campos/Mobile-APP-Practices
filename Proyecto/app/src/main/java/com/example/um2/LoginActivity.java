package com.example.um2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button logonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar componentes de la UI
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        logonButton = findViewById(R.id.logon);

        logonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verificar que los campos no estén vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Intentar hacer login
                login(username, password);
            }
        });
    }

    private void login(final String username, final String password) {
        // Referencia a la base de datos de Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Buscar al usuario en la base de datos
        mDatabase.orderByChild("userName").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Verificar si el usuario existe
                if (dataSnapshot.exists()) {
                    // El usuario existe, verificamos la contraseña
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Obtener el valor de la contraseña, asegurándonos de convertirlo a String
                        Object storedPasswordObject = snapshot.child("password").getValue();

                        if (storedPasswordObject != null) {
                            String storedPassword = storedPasswordObject.toString();  // Convertir a String
                            if (storedPassword.equals(password)) {

                                // Redirigir a la HomeActivity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }
                }

                // Si el usuario no existe o la contraseña es incorrecta
                Toast.makeText(LoginActivity.this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
