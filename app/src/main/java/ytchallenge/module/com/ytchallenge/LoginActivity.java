package ytchallenge.module.com.ytchallenge;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private TextView signUpAppeal;
    private EditText username,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fab = findViewById(R.id.fab_login);
        signUpAppeal = findViewById(R.id.text_signUpAppeal);
        username = findViewById(R.id.editText_signup_1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login Button", Toast.LENGTH_SHORT).show();
            }
        });

        signUpAppeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(signUpIntent);
                finish();
            }
        });

    }


}
