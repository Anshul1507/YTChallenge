package ytchallenge.module.com.ytchallenge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Button signupbtn;
    private ImageView googlesign,fbsign;

    private CustomTabsServiceConnection customTabsServiceConnection;
    private CustomTabsClient mClient;
    CustomTabsSession customTabsSession;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this.getApplicationContext();

        signupbtn = findViewById(R.id.button_signup);
        googlesign = findViewById(R.id.google_signup);
        fbsign = findViewById(R.id.fb_signup);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "Signed Up", Toast.LENGTH_SHORT).show();
            }
        });

        googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTabLinking("https://goo.gl/dGV7B1");
            }
        });

        fbsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTabLinking("https://goo.gl/JdykTg");
            }
        });



    }

    public void customTabLinking(String url){
        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //pre-warning means to fast the surfing
                mClient = customTabsClient;
                mClient.warmup(0L);
                customTabsSession = mClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mClient = null;
            }
        };
        CustomTabsClient.bindCustomTabsService(context,"com.android.chrome",customTabsServiceConnection);
        Uri uri = Uri.parse(url);

        //Create an Intent Builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        //Begin Customizing
        //Set Toolbar Colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(context,R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));

        //Set Start and Exit Animations
        intentBuilder.setStartAnimations(context,android.R.anim.slide_out_right,android.R.anim.slide_in_left);
        intentBuilder.setExitAnimations(context,android.R.anim.slide_in_left,android.R.anim.slide_out_right);

        //build custom tabs intent
        CustomTabsIntent customTabsIntent =  intentBuilder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intentBuilder.setShowTitle(true);
        intentBuilder.enableUrlBarHiding();

        //launch the url
        customTabsIntent.launchUrl(context,uri);

    }
}
