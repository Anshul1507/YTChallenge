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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private CustomTabsServiceConnection customTabsServiceConnection;
    private CustomTabsClient mClient;
    CustomTabsSession customTabsSession;
    private Context context;

    private Button loginbtn,signupbtn;
    private ImageView fbLogo,twitterLogo,youtubeLogo,linkedinLogo;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.button_login);
        signupbtn = findViewById(R.id.button_signup);

        fbLogo = findViewById(R.id.fb_image);
        twitterLogo = findViewById(R.id.twitter_image);
        youtubeLogo = findViewById(R.id.youtube_image);
        linkedinLogo = findViewById(R.id.linkedin_image);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);

            }
        });


        fbLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "https://www.facebook.com/HiteshChoudharyPage";
                //url = "https://goo.gl/dGV7B1";
                customTabLinking(url);
            }
        });

        twitterLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "https://twitter.com/hiteshdotcom";
                customTabLinking(url);
            }
        });

        youtubeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "https://www.youtube.com/user/hiteshitube";
                customTabLinking(url);
            }
        });

        linkedinLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "https://www.linkedin.com/in/hiteshchoudhary/";
                customTabLinking(url);
            }
        });

    }
    Boolean doubleBackPress = false;
    @Override
    public void onBackPressed() {
        if (doubleBackPress == true) {
            super.onBackPressed();
        } else {
            doubleBackPress = true;
            final RelativeLayout mainLayout = findViewById(R.id.main_layout);
            Snackbar.make(mainLayout, "Press back again to exit", Snackbar.LENGTH_LONG).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPress = false;
                }
            }, 2000);}
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
