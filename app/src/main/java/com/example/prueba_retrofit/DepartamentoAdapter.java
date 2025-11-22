package com.example.prueba_retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DepartamentoAdapter extends ArrayAdapter<Departamento> {

    private Context context;
    private List<Departamento> departamentos;
    private OnEditClickListener onEditClickListener;

    // Interfaz para manejar el clic en editar
    public interface OnEditClickListener {
        void onEditClick(Departamento departamento);
    }

    public DepartamentoAdapter(@NonNull Context context, List<Departamento> list, OnEditClickListener listener) {
        super(context, 0, list);
        this.context = context;
        this.departamentos = list;
        this.onEditClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_departamento, parent, false);

        Departamento currentDepartamento = departamentos.get(position);

        // Asignamos el nombre del departamento
        TextView name = listItem.findViewById(R.id.textViewName);
        name.setText(currentDepartamento.getDisplayName());

        // Asignamos el ID al círculo
        TextView idText = listItem.findViewById(R.id.textViewId);
        idText.setText(String.valueOf(currentDepartamento.getDepartmentId()));

        ImageButton editButton = listItem.findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(currentDepartamento);
                }
            }
        });

        return listItem;
    }
}