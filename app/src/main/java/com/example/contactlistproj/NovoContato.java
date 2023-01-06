package com.example.contactlistproj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoContato extends AppCompatActivity {

    EditText nameEdt, phoneEdt, emailEdt;
    Button addContact, button, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        nameEdt = findViewById(R.id.idEdtName);
        phoneEdt = findViewById(R.id.idEdtPhoneNumber);
        emailEdt = findViewById(R.id.idEdtEmail);
        addContact = findViewById(R.id.AddContact);
        button = findViewById(R.id.button);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cance = new AlertDialog.Builder(NovoContato.this);
                cance.setTitle("O que deseja fazer:");
                cance.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Foi cancelado com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NovoContato.this, MainActivity.class));
                    }
                });//botoes
                cance.setPositiveButton("Continuar", null);
                cance.setCancelable(false);//click fora
                cance.create().show(); //mostrar o alert

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoContato.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Contact Room", Toast.LENGTH_SHORT).show();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoContato.this, MainActivity.class));
                addContact();

            }
        });
    }

    private void addContact() {
        String name = nameEdt.getText().toString();
        String phone = phoneEdt.getText().toString();
        String email = emailEdt.getText().toString();

        Contato contato = new Contato(name, phone, email);

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {
            Toast.makeText(NovoContato.this, "Please enter the data in all fields. ", Toast.LENGTH_SHORT).show();
        } else {
            DB.contactList.add(contato);
            Toast.makeText(getApplicationContext(), "seu contato foi guardado", Toast.LENGTH_SHORT).show();

        }
    }
}