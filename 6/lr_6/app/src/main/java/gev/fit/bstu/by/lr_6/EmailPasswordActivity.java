package gev.fit.bstu.by.lr_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private String email = "";
    private String password = "";

    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btAuth).setOnClickListener(this);
        findViewById(R.id.btReg).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        switch(view.getId()) {
            case R.id.btAuth :
                if (!email.isEmpty() || !password.isEmpty()) {
                    signIn(email, password);
                }
                else {
                    Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btReg :
                if (!email.isEmpty() || !password.isEmpty()) {
                    signUp(email, password);
                }
                else {
                    Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(EmailPasswordActivity.this, "Registration successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(EmailPasswordActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(EmailPasswordActivity.this, "Authentication successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account) {
        if (account != null) {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
        else {

        }
    }
}