package com.example.prueba_retrofit; // Declara el paquete al que pertenece la clase

import com.google.gson.annotations.SerializedName; // Importa la anotación SerializedName de Gson para mapear claves JSON a campos de Java

import java.util.List; // Importa la interfaz List

public class  DepartamentoResponse{ // Define la clase DepartamentoResponse

    @SerializedName("departments") // Especifica que el campo 'departamentos' corresponde a la clave 'departments' en el JSON
    private List<Departamento> departamentos; // Declara una lista de objetos Departamento

    public List<Departamento> getDepartamentos() { // Método público para obtener la lista de departamentos
        return departamentos; // Devuelve la lista de departamentos
    }
}