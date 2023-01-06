package com.example.contactlistproj;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//extender recycle adpater defenir um classe interna para no adapter do nosso recycle e mas antes temos de implementar alguns metodos
public class Contact_RecicycleviewAdapter extends RecyclerView.Adapter<Contact_RecicycleviewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Contato> contactList;

    public Contact_RecicycleviewAdapter(Context context, ArrayList<Contato> contactList) { //apanhar os valores das nossas variaveis
        this.context = context; //inflar layout
        this.contactList = contactList;
    }

    @NonNull
    @Override
    //vamos inflar nosso layout e dar aparencia a cada um das nossas coluna
    public Contact_RecicycleviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layoutcont, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Contact_RecicycleviewAdapter.MyViewHolder holder, int position) {
        //atribuimos valores a cada uma das linhas quandos elas voltam no ecra e tudo dependera da posicao do recycleview

        holder.Tvname.setText(contactList.get(position).getNome());
        holder.Tvnum.setText(contactList.get(position).getNumero());
        holder.Tvemail.setText(contactList.get(position).getEmail());

        /*      *//*  holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }*//*
        });*/
    }

    @Override
    public int getItemCount() {
        //quantos intem temos no total
        return contactList.size();
    }


    //esqueleto no nosso recycle
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        //vai apanhar as views do nosso recycle-view layout tipo um one create metodo
        Context context;
        TextView Tvname, Tvnum, Tvemail;
        View mview;

        public MyViewHolder(View itemView) {
            super(itemView);

            Tvname = itemView.findViewById(R.id.TV_name);
            Tvnum = itemView.findViewById(R.id.TV_num);
            Tvemail = itemView.findViewById(R.id.TV_email);
            mview = itemView;

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //obter o click
                    Intent moveToUpdateIntent = new Intent(context, MainActivity.class);
                    moveToUpdateIntent.putExtra("userData", "" + getLayoutPosition());
                    context.startActivity(moveToUpdateIntent);
                }
            });

        }

        @Override
        //menu context para quando quando  realizarmos um long click
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(0, itemView.getId(), 0, "Call").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @SuppressLint("QueryPermissionsNeeded")
                @Override
                //o primeiro item que aparece e o realizar chamada
                public boolean onMenuItemClick(MenuItem item) {
                    String phoneNumber = "tel:" + DB.contactList.get(getLayoutPosition()).getNumero();//vamos apanhar o numero referente ao icon do click
                    Intent CallIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
                    //context.startActivity(CallIntent);

                    if (null != CallIntent.resolveActivity(context.getPackageManager())) {
                        context.startActivity(CallIntent);
                        ((Activity) itemView.getContext()).startActivity(CallIntent);
                    } else {
                        Log.d("ImplicitIntents", "Can't handle this");
                    }
                    return false;
                }
            });


            menu.add(0, itemView.getId(), 0, "SMS").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String number = DB.contactList.get(getLayoutPosition()).getNumero();
                    Intent SMS = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
                    if (SMS.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(SMS);
                    } else {
                        Log.d("ImplicitIntents", "Can't handle this");
                    }


                    return false;
                }
            });

            menu.add(0, itemView.getId(), 0, "Remove").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(itemView.getContext(), "Remove " + DB.contactList.get(getLayoutPosition()).getNome(), Toast.LENGTH_SHORT).show();
                    DB.contactList.remove(getLayoutPosition());
                    return false;
                }
            });

            menu.add(0, itemView.getId(), 0, "Share").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String number = DB.contactList.get(getLayoutPosition()).getNumero();
                    Intent ShareIntent = new Intent(Intent.ACTION_SEND);
                    ShareIntent.putExtra(Intent.EXTRA_TEXT, number);
                    ShareIntent.setType("text/plain");
                    if (ShareIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(ShareIntent);
                    } else {
                        Log.d("ImplicitIntentts", "Can't handle this");
                    }
                    return false;
                }
            });
        }
    }
}
