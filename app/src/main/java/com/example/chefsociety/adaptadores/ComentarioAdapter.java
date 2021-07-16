package com.example.chefsociety.adaptadores;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chefsociety.R;
import com.example.chefsociety.actividades.CatalogoActivity;
import com.example.chefsociety.clases.Categoria;
import com.example.chefsociety.clases.Comentario;
import com.loopj.android.http.Base64;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder>{
    private List<Comentario> lista_comentarios;

    public ComentarioAdapter(List<Comentario> lista_comentarios) {
        this.lista_comentarios =lista_comentarios;
    }
    @NonNull
    @NotNull
    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario,parent,false);
        // devolver la vista
        return new ComentarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ComentarioAdapter.ViewHolder holder, int position) {
        Comentario comentario= lista_comentarios.get(position);
        holder.nombres.setText(comentario.getC_nombres());
        holder.apellidos.setText(comentario.getC_apellidos());
        holder.comentario.setText(comentario.getC_comentario());
        holder.fecha.setText(comentario.getC_fecha());
        holder.hora.setText(comentario.getC_hora());
        String imagen = comentario.getC_imagen_usuario();
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        holder.Foto_usuario.setImageBitmap(bitmap);
        holder.item_comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(v.getContext(),"ID: "+ lista_comentarios.get(position).getC_idusuario(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_comentarios.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_comentarios;
        TextView nombres,apellidos,comentario,fecha,hora;
        ImageView Foto_usuario;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene de item_comentario xml
            item_comentarios=itemView.findViewById(R.id.cv_item_comentarios);
            nombres=itemView.findViewById(R.id.com_nombres);
            apellidos=itemView.findViewById(R.id.com_apellidos);
            comentario=itemView.findViewById(R.id.com_comentario);
            fecha=itemView.findViewById(R.id.com_fecha);
            hora=itemView.findViewById(R.id.com_hora);
            Foto_usuario=itemView.findViewById(R.id.ivItem_com_imagen_usuario);
        }
    }
}
