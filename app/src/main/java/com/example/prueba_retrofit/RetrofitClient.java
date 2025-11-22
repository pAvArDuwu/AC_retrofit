package com.example.prueba_retrofit; // Declara el paquete al que pertenece la clase

import retrofit2.Retrofit; // Importa la clase Retrofit
import retrofit2.converter.gson.GsonConverterFactory; // Importa el convertidor Gson para Retrofit

public class RetrofitClient { // Define la clase RetrofitClient

    // URL base para las solicitudes a la API del Met Museum
    private static final String BASE_URL = "https://collectionapi.metmuseum.org/";

    // Método estático para obtener una instancia de la interfaz de la API
    public static MetMuseumApi getApi(){
        // Crea una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Establece la URL base para las solicitudes
                .addConverterFactory(GsonConverterFactory.create()) // Agrega un convertidor para analizar las respuestas JSON usando Gson
                .build(); // Construye la instancia de Retrofit
        // Crea e devuelve una implementación de la interfaz MetMuseumApi
        return retrofit.create(MetMuseumApi.class);
    }
}
