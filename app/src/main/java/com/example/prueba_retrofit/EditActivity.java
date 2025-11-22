package com.example.prueba_retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonSave;
    private int departmentId;
    private String initialName; // Nombre original recibido de la API
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextName = findViewById(R.id.editTextDepartmentName);
        buttonSave = findViewById(R.id.buttonSave);

        dbHelper = new DatabaseHelper(this);

        // Recuperar ID y Nombre pasados desde MainActivity
        if (getIntent().hasExtra("departmentId")) {
            departmentId = getIntent().getIntExtra("departmentId", -1);
        }
        if (getIntent().hasExtra("departmentName")) {
            initialName = getIntent().getStringExtra("departmentName");
        }

        loadDepartmentData();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDepartment();
            }
        });
    }

    private void loadDepartmentData() {
        // Primero intentamos cargar de la base de datos (datos editados)
        Departamento localData = dbHelper.getDepartmentById(departmentId);
        
        if (localData != null) {
            // Si existe en BD, mostramos el nombre editado
            editTextName.setText(localData.getDisplayName());
        } else {
            // Si no existe en BD, mostramos el nombre original de la API
            editTextName.setText(initialName);
        }
    }

    private void saveDepartment() {
        String newName = editTextName.getText().toString();
        if (!newName.isEmpty()) {
            // Creamos el objeto con el ID y el nuevo nombre
            Departamento departamento = new Departamento(departmentId, newName);
            
            // Guardamos (inserta si no existe, actualiza si ya existe)
            dbHelper.insertOrUpdate(departamento);
            
            Toast.makeText(this, "Guardado en favoritos/editados", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
        }
    }
}