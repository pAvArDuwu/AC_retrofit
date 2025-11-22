package com.example.prueba_retrofit;

public class Departamento {

    private int departmentId;
    private String displayName;

    // Constructor vacío
    public Departamento() {
    }

    // Constructor con parámetros
    public Departamento(int departmentId, String displayName) {
        this.departmentId = departmentId;
        this.displayName = displayName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}