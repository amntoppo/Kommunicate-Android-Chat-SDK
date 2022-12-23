package kommunicate.io.sample.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.kommunicate.Kommunicate;
import io.kommunicate.app.R;
import io.kommunicate.callbacks.KMLogoutHandler;
import kommunicate.io.sample.MainActivity;

public class TemporaryActivity extends AppCompatActivity {
    Button launchZendesk;
    Button logout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_temporary);
        launchZendesk = findViewById(R.id.launch_zendesk);
        logout = findViewById(R.id.logout_button);

        launchZendesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kommunicate.openZendeskChat(TemporaryActivity.this);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kommunicate.logout(TemporaryActivity.this, new KMLogoutHandler() {
                    @Override
                    public void onSuccess(Context context) {
                        Intent intent = new Intent(TemporaryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });
            }
        });
    }
}
