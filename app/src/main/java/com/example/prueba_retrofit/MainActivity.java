package com.example.prueba_retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DepartamentoAdapter adapter;
    private List<Departamento> departmentList = new ArrayList<>();
    private DatabaseHelper dbHelper; // Usamos SQLiteOpenHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewDepartments);
        dbHelper = new DatabaseHelper(this); // Inicializamos el helper

        // Configurar el adaptador personalizado con el listener para editar
        adapter = new DepartamentoAdapter(this, departmentList, new DepartamentoAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Departamento departamento) {
                // Abrir la actividad de edición pasando el ID Y EL NOMBRE ACTUAL
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("departmentId", departamento.getDepartmentId());
                intent.putExtra("departmentName", departamento.getDisplayName()); // Pasamos el nombre actual
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cargar datos cada vez que la actividad se muestra (incluyendo al volver de editar)
        loadData();
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            loadFromApi();
        } else {
            loadFromDatabase("Sin conexión. Mostrando SOLO tus ediciones guardadas.");
        }
    }

    private void loadFromApi() {
        MetMuseumApi api = RetrofitClient.getApi();
        Call<DepartamentoResponse> call = api.getDepartamentos();

        call.enqueue(new Callback<DepartamentoResponse>() {
            @Override
            public void onResponse(Call<DepartamentoResponse> call, Response<DepartamentoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Departamento> apiDepartments = response.body().getDepartamentos();

                    // IMPORTANTE: Ya NO guardamos automáticamente en la BD.
                    // Solo mostramos lo que viene de la API.
                    // dbHelper.insertAll(apiDepartments); <--- COMENTADO/ELIMINADO

                    // Mostrar datos de la API
                    updateList(apiDepartments);
                    // Toast.makeText(MainActivity.this, "Datos de API cargados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DepartamentoResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                loadFromDatabase("Fallo al conectar con API. Mostrando datos locales.");
            }
        });
    }

    private void loadFromDatabase(String message) {
        List<Departamento> localDepartments = dbHelper.getAll();

        if (localDepartments.isEmpty()) {
             Toast.makeText(this, "No hay datos guardados localmente.", Toast.LENGTH_LONG).show();
        } else {
             Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        updateList(localDepartments);
    }

    private void updateList(List<Departamento> newData) {
        departmentList.clear();
        departmentList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}