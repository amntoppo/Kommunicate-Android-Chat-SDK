package kommunicate.io.sample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.uiwidgets.kommunicate.views.KmToast;
import com.applozic.mobicomkit.uiwidgets.kommunicate.widgets.KmChatWidget;
import com.applozic.mobicomkit.uiwidgets.kommunicate.widgets.KmChatWidgetConfig;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.ApplozicClient;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import io.kommunicate.KmConversationHelper;
import io.kommunicate.KmException;
import io.kommunicate.app.BuildConfig;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.users.KMUser;
import io.kommunicate.Kommunicate;
import io.kommunicate.app.R;
import io.kommunicate.callbacks.KMLoginHandler;

public class MainActivity extends AppCompatActivity {

    EditText mUserId, mPassword;
    AppCompatButton loginButton, visitorButton;
    LinearLayout layout;
    boolean exit = false;
    public static final String APP_ID = BuildConfig.APP_ID;
    KmChatWidget floatingView;
    private static final String INVALID_APP_ID = "INVALID_APPLICATIONID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Kommunicate.init(this, APP_ID);

        layout = (LinearLayout) findViewById(R.id.footerSnack);
        mUserId = (EditText) findViewById(R.id.userId_editText);
        mPassword = (EditText) findViewById(R.id.password_editText);
        loginButton = (AppCompatButton) findViewById(R.id.btn_signup);
        visitorButton = findViewById(R.id.btn_login_as_visitor);

        KMUser user = new KMUser();
        user.setUserId("aman");
        Kommunicate.login(MainActivity.this, user, new KMLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context)
            {
                //getUn

                KmChatWidgetConfig config = new KmChatWidgetConfig.Builder()
                        .setPaddingLeft(200)
                        .setPaddingTop(200)
                        .setPaddingRight(130)
                        .setPaddingBottom(140)
                        .setLauncherSize(250)
                        .setMovable(true)
                        .build();
                floatingView = new KmChatWidget(MainActivity.this);
                floatingView.show();
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

            }
        });



        TextView txtViewPrivacyPolicy = (TextView) findViewById(R.id.txtPrivacyPolicy);
        txtViewPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isPlaceHolderAppId()) {
                        return;
                    }
                    final String mUserIdText = mUserId.getText().toString().trim();
                    String mPasswordText = mPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(mUserIdText) || mUserId.getText().toString().trim().length() == 0) {
                        KmToast.error(getBaseContext(), R.string.enter_user_id, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle("Logging in..");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    initLoginData(mUserId.getText().toString().trim(), mPassword.getText().toString().trim(), progressDialog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        visitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isPlaceHolderAppId()) {
//                    return;
//                }
//                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setTitle("Logging in..");
//                progressDialog.setMessage("Please wait...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                Kommunicate.init(MainActivity.this, APP_ID);
//                Kommunicate.loginAsVisitor(MainActivity.this, new KMLoginHandler() {
//                    @Override
//                    public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//                        finish();
//                        progressDialog.dismiss();
//                        Kommunicate.openConversation(context, null);
//                    }
//
//                    @Override
//                    public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//                        progressDialog.dismiss();
//                        createLoginErrorDialog(registrationResponse, exception);
//                    }
//                });
//                FloatingViewConfig config = new FloatingViewConfig.Builder()
//                        .setPaddingLeft(12)
//                        .setPaddingTop(13)
//                        .setPaddingRight(13)
//                        .setPaddingBottom(14)
//                        .setDisplayHeight(90)
//                        .setDisplayWidth(90)
//                        //.setMovable(false)
//                        .build();
//                FloatingView floatingView = new FloatingView(MainActivity.this, R.layout.view_floating, config);
                //FloatingView floatingView = new FloatingView(MainActivity.this);
                //floatingView.showOverlayActivity();
//                floatingView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                        Kommunicate.loginAsVisitor(MainActivity.this, new KMLoginHandler() {
//                    @Override
//                    public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//                        finish();
//                        //progressDialog.dismiss();
//                        Kommunicate.openConversation(context, null);
//                    }
//
//                    @Override
//                    public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//                        //progressDialog.dismiss();
//                        createLoginErrorDialog(registrationResponse, exception);
//                    }
//                });
//                    }
//                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(floatingView != null) {
            Log.e("floating", "desotry");
            floatingView.hide();
        }
    }


    public boolean isPlaceHolderAppId() {
        if (Kommunicate.PLACEHOLDER_APP_ID.equals(APP_ID)) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setCancelable(true);
            dialogBuilder.setMessage(Utils.getString(this, R.string.invalid_app_id_error));
            dialogBuilder.show();
            return true;
        }
        return false;
    }

    public String getInvalidAppIdError(RegistrationResponse registrationResponse) {
        if (registrationResponse != null) {
            if (registrationResponse.getMessage() != null && INVALID_APP_ID.equals(registrationResponse.getMessage())) {
                return getString(R.string.invalid_app_id_error);
            } else {
                return registrationResponse.getMessage();
            }
        }
        return "";
    }

    public void createLoginErrorDialog(RegistrationResponse registrationResponse, Exception exception) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        StringBuilder message = new StringBuilder(getString(R.string.some_error_occured));
        if (registrationResponse != null) {
            if (!TextUtils.isEmpty(getInvalidAppIdError(registrationResponse))) {
                message.append(" : ");
                message.append(getInvalidAppIdError(registrationResponse));
            }
        } else if (exception != null) {
            message.append(" : ");
            message.append(exception.getMessage());
        }

        dialogBuilder.setMessage(message.toString());
        dialogBuilder.show();
    }

    public void showSnackBar(int resId) {
        Snackbar.make(layout, resId,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onBackPressed() {

        if (exit) {
            finish();
        } else {
            KmToast.success(this, R.string.press_back_exit, Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }

    }

    public void initLoginData(String userId, String password, final ProgressDialog progressDialog) {

        final KMUser user = new KMUser();
        user.setUserId(userId);
        user.setApplicationId(APP_ID);

        if (!TextUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        Kommunicate.login(MainActivity.this, user, new KMLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                if (KMUser.RoleType.USER_ROLE.getValue().equals(registrationResponse.getRoleType())) {
                    ApplozicClient.getInstance(context).hideActionMessages(true).setMessageMetaData(null);
                } else {
                    Map<String, String> metadata = new HashMap<>();
                    metadata.put("skipBot", "true");
                    ApplozicClient.getInstance(context).hideActionMessages(false).setMessageMetaData(metadata);
                }
                Log.e("floatingview", String.valueOf(new AppContactService(context).getContactById("aman").getUnreadCount()));
                try {
                    KmConversationHelper.openConversation(context, true, null, new KmCallback() {
                        @Override
                        public void onSuccess(Object message) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            finish();
                        }

                        @Override
                        public void onFailure(Object error) {

                        }
                    });
                } catch (KmException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                createLoginErrorDialog(registrationResponse, exception);
            }
        });
    }
}
