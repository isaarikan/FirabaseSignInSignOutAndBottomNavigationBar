package quiz.live.online.arksoft.com.onlinequiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import quiz.live.online.arksoft.com.onlinequiz.Model.User;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNewUser,edtNewPassword,edtNewEmail;//Sign up
    MaterialEditText edtUser,edtPassword;//Sign in

    Button btnSignUp,btnSignIn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        users=firebaseDatabase.getReference("Users");

        edtUser=(MaterialEditText)findViewById(R.id.edtUserName);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);

        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnSignUp=(Button)findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }


        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUser.getText().toString(),edtPassword.getText().toString());
                
            }
        });




    }

    private void signIn(final String user, final String string1) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){

                    if(!user.isEmpty()){
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(string1)) {


                            Intent homeActivity = new Intent(MainActivity.this, Home.class);
                            startActivity(homeActivity);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Enter Your Username", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "User Now Exists", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater layoutInflater=this.getLayoutInflater();
        View sign_up_layout=layoutInflater.inflate(R.layout.sign_up_layout,null);

        edtNewUser=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user=new User(edtNewUser.getText().toString(),edtNewPassword.getText().toString(),edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            else {
                                users.child(user.getUserName()).setValue(user);

                            Toast.makeText(MainActivity.this, "User Registration Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                dialogInterface.dismiss();


            }
        });
        alertDialog.show();
    }
}
