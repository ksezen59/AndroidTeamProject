package com.example.atakansoztekin.team_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    public static EditText etName;
    EditText etPassword;
    Button btnlogin,btnadd,btnUpdate;
    Users c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(getApplicationContext());

        etName = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.login);
        btnUpdate = (Button) findViewById(R.id.change_pass);
        btnadd = (Button) findViewById(R.id.addUser);
    }

    public void update(View view){
        if(etName.getText().toString().isEmpty()) {
            if(etPassword.getText().toString().isEmpty()) {
                showMessage("Enter required fields");
            }
            else{
                showMessage("Enter Username");
            }

        }
        else {
            if(etPassword.getText().toString().isEmpty()) {
                showMessage("Enter Password");
            }
            else {
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();

                Users c = new Users(name, password);
                int r = db.updateContact(c);

                if (r > 0) {
                    showMessage("User is updated!");
                    clearValues();
                } else {
                    showMessage("Error!");
                }
            }
        }
        }

    public void getContact(View view){
        if(etName.getText().toString().isEmpty()) {
            if(etPassword.getText().toString().isEmpty()) {
                showMessage("Enter required fields");
            }
            else{
                showMessage("Enter Username");
            }

        }
        else {
            if(etPassword.getText().toString().isEmpty()) {
                showMessage("Enter Password");
            }
            else {
                String name = etName.getText().toString();
                c = db.getContact(name);

                if (etName.getText().toString().equals(c.getName().toString())) {
                    if (etPassword.getText().toString().equals(c.getPassword().toString())) {
                        Intent intent = new Intent(view.getContext(), activity_guess.class);
                        view.getContext().startActivity(intent);
                    } else {
                        showMessage("Wrong Password");
                    }
                } else {
                    showMessage("Wrong User");
                }
            }
        }
    }

    public void insert(View view) {
        if (etName.getText().toString().isEmpty()) {
            if (etPassword.getText().toString().isEmpty()) {
                showMessage("Enter required fields");
            } else {
                showMessage("Enter Username");
            }

        } else {
            if (etPassword.getText().toString().isEmpty()) {
                showMessage("Enter Password");
            } else {
                String name = etName.getText().toString();
                String phone = etPassword.getText().toString();

                Users c = new Users(name, phone);
                long r = db.addContact(c);

                if (r > 0) {
                    showMessage("User is inserted!");
                    clearValues();
                } else {
                    showMessage("Error!");
                }
            }

        }
    }

    private void clearValues(){
        etName.setText("");
        etPassword.setText("");
    }

    private void showMessage(String value){
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
    }
}
