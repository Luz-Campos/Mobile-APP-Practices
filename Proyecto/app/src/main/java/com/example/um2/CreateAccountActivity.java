package com.example.um2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText userName, email, password, dateOfBirth;
    private Button logoncreate;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Inicializar las vistas
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        dateOfBirth = findViewById(R.id.date);
        logoncreate = findViewById(R.id.logoncreate);

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Usuarios");

        logoncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del formulario
                String usernameText = userName.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String dateOfBirthText = dateOfBirth.getText().toString().trim();

                // Verificar que no haya campos vacíos
                if (usernameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || dateOfBirthText.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear un nuevo usuario
                Usuario nuevoUsuario = new Usuario(usernameText, emailText, passwordText, dateOfBirthText);

                // Obtener un ID único para el nuevo usuario
                String userId = usersRef.push().getKey(); // Esto genera un nuevo ID automáticamente

                // Guardar los datos del usuario en la base de datos
                if (userId != null) {
                    usersRef.child(userId).setValue(nuevoUsuario)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(CreateAccountActivity.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                                // Redirigir al HomeActivity
                                startActivity(new Intent(CreateAccountActivity.this, HomeActivity.class));
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CreateAccountActivity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Error al generar el ID del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
