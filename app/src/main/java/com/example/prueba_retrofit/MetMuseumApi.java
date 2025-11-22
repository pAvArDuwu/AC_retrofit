package com.example.prueba_retrofit; // Declara el paquete al que pertenece la clase

import retrofit2.Call; // Importa la clase Call de Retrofit para realizar llamadas asíncronas
import retrofit2.http.GET; // Importa la anotación GET de Retrofit para especificar una solicitud GET

public interface MetMuseumApi { // Define la interfaz MetMuseumApi

    @GET("public/collection/v1/departments") // Anotación que indica que este método realiza una solicitud GET a la ruta especificada
    Call<DepartamentoResponse> getDepartamentos(); // Define un método que devuelve un objeto Call con una respuesta de tipo DepartamentoResponse


}
