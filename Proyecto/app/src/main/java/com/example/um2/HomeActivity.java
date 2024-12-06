package com.example.um2;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Configura el Toolbar como ActionBar

        // Inicializa el DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Configura el ActionBarDrawerToggle para abrir/cerrar el menú lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configura el NavigationView para manejar las selecciones
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navListener);

        // Cargar el fragmento inicial
        loadFragment(new InicioFragment());

        // Habilitar el icono de las tres rayas en la barra de acción
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_foreground);

        // Asegúrate de que el ícono se alinee a la izquierda
        toolbar.setNavigationIcon(R.drawable.ic_menu_foreground);
        toolbar.setNavigationContentDescription("Menu");

// Esto asegurará que el ícono esté alineado más hacia la izquierda
        toolbar.setNavigationIcon(R.drawable.ic_menu_foreground);
    }

    private final NavigationView.OnNavigationItemSelectedListener navListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.page_1) {
                selectedFragment = new InicioFragment();
            } else if (item.getItemId() == R.id.page_3) {
                selectedFragment = new NotificacionesFragment();
            } else if (item.getItemId() == R.id.page_4) {
                selectedFragment = new ProfileActivity();
            } else if (item.getItemId() == R.id.page_5) {
                selectedFragment = new TestVocacionalActivity();
            } else if (item.getItemId() == R.id.page_6) {
                selectedFragment = new CursosFragment();
            } else if (item.getItemId() == R.id.page_7) {
                selectedFragment = new UniversidadesFragment();
            } else if (item.getItemId() == R.id.page_8) {
                selectedFragment = new CarrerasFragment();
            } else if (item.getItemId() == R.id.page_9) {
                selectedFragment = new DonacionesFragment();
            } else if (item.getItemId() == R.id.whatsapp_button) {
                // Acción para WhatsApp
                String whatsappUrl = "https://wa.me/5214491370279"; // Formato para WhatsApp
                Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl));
                startActivity(whatsappIntent);
                drawerLayout.closeDrawers();
                return true; // No es necesario cargar un fragmento
            } else if (item.getItemId() == R.id.phone_button) {
                // Acción para Llamar
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4491370279"));
                startActivity(phoneIntent);
                drawerLayout.closeDrawers();
                return true; // No es necesario cargar un fragmento
            }

            // Cargar el fragmento seleccionado (si aplica)
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            // Cerrar el DrawerLayout después de cualquier acción
            drawerLayout.closeDrawers();
            return true;
        }
    };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
        return false;
    }

}
