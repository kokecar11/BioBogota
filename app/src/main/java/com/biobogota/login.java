package com.biobogota;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.biobogota.Common.Common;
import com.biobogota.Model.User;

import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class login extends AppCompatActivity {

    private Button btn_mudderSignIn,btn_mudderRegister;
    private RelativeLayout rootLayout;
    private CardView loginLayout;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    List<AuthUI.IdpConfig> providers;

    ProgressDialog progressDialog;

    private static  final int  MY_REQUEST_CODE = 7117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference(Common.user_md_tbl);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
        );

        showSignInOptions();

        btn_mudderRegister = findViewById(R.id.Btn_MudderRg);
        btn_mudderSignIn = findViewById(R.id.Btn_MudderLg);
        rootLayout = findViewById(R.id.rootLayout);
        loginLayout = findViewById(R.id.loginLayout);



        btn_mudderRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        btn_mudderSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });


    }


    private void showSignInOptions(){
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.mitema)
                .build(),MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse res = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                //se obtiene el usuario
                FirebaseUser userfire = FirebaseAuth.getInstance().getCurrentUser();
                //
                User user = new User();
                user.setEmail(userfire.getEmail());
                user.setName(userfire.getDisplayName());

                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(rootLayout,"¡Registro Completado!.",Snackbar.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Snackbar.make(rootLayout,"¡Registro Fallido!. "+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(this,""+userfire.getEmail(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this,MainActivity.class));

                finish();

            }else{
                Toast.makeText(this,""+res.getError().getMessage(),Toast.LENGTH_SHORT);
            }
        }
    }

    private void showRegisterDialog() {


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
       /* dialog.setTitle("Registro MuDDER");
        dialog.setMessage("Por favor use su Email para registrarse.");*/

        LayoutInflater inflater = LayoutInflater.from(this);
        View registrer_layout = inflater.inflate(R.layout.layout_register,null);



        final EditText edt_EmailMudder = registrer_layout.findViewById(R.id.ET_EmailMudder);
        final EditText edt_NameMudder = registrer_layout.findViewById(R.id.ET_NameMudder);
        final EditText edt_PhoneMudder = registrer_layout.findViewById(R.id.ET_PhoneMudder);
        final EditText edt_PassMudder = registrer_layout.findViewById(R.id.ET_PassMudder);

        dialog.setView(registrer_layout);

        dialog.setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

                if (TextUtils.isEmpty(edt_EmailMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese un Email.",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(edt_NameMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese un Nombre.",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(edt_PhoneMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese un numero de Celular.",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(edt_PassMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese una Contraseña.",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (edt_PassMudder.getText().toString().length() < 6){

                    Snackbar.make(rootLayout,"La Contraseña es muy corta.",Snackbar.LENGTH_SHORT).show();
                    return;

                }

                mAuth.createUserWithEmailAndPassword(edt_EmailMudder.getText().toString(),edt_PassMudder.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(edt_EmailMudder.getText().toString());
                                user.setName(edt_NameMudder.getText().toString());
                                user.setPhone(edt_PhoneMudder.getText().toString());
                                user.setPassword(edt_PassMudder.getText().toString());


                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootLayout,"¡Registro Completado!.",Snackbar.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Snackbar.make(rootLayout,"¡Registro Fallido!. "+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"¡Fallido!. "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });


        dialog.show();


    }

    private void showLoginDialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
       /* dialog.setTitle("Iniciar Sesión");
        dialog.setMessage("Por favor use su Email para Ingresar.");*/

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login,null);

        final EditText edt_EmailMudder = login_layout.findViewById(R.id.ET_EmailMudder);
        final EditText edt_PassMudder = login_layout.findViewById(R.id.ET_PassMudder);

        dialog.setView(login_layout);

        dialog.setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

                btn_mudderSignIn.setEnabled(false);


                if (TextUtils.isEmpty(edt_EmailMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese un Email.",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(edt_PassMudder.getText().toString())){

                    Snackbar.make(rootLayout,"Porfavor Ingrese una Contraseña.",Snackbar.LENGTH_SHORT).show();
                    return;

                }

                if (edt_PassMudder.getText().toString().length() < 6){

                    Snackbar.make(rootLayout,"La Contraseña es muy corta.",Snackbar.LENGTH_SHORT).show();
                    return;

                }

                final AlertDialog waitingdialog = new SpotsDialog.Builder().setContext(login.this).setTheme(R.style.AlertDialogCustom).build();
                waitingdialog.show();


                mAuth.signInWithEmailAndPassword(edt_EmailMudder.getText().toString(),edt_PassMudder.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingdialog.dismiss();
                                startActivity(new Intent(login.this,MainActivity.class));
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingdialog.dismiss();
                        Snackbar.make(rootLayout,"Sesión Fallida. "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        btn_mudderSignIn.setEnabled(true);
                    }
                });

            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();

    }

}
