package com.applozic.mobicomkit.uiwidgets.kommunicate.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.applozic.mobicomkit.uiwidgets.AlCustomizationSettings;
import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicommons.ApplozicService;

import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.utils.KmAppSettingPreferences;

public class KmThemeHelper implements KmCallback {

    private static KmThemeHelper kmThemeHelper;
    private Context context;
    private KmAppSettingPreferences appSettingPreferences;
    private AlCustomizationSettings alCustomizationSettings;
    private int primaryColor = -1;
    private int secondaryColor = -1;
    private int sentMessageBackgroundColor = -1;
    private int sendButtonBackgroundColor = -1;
    private int sentMessageBorderColor = -1;
    private int messageStatusIconColor = -1;
    private int toolbarTitleColor = -1;
    private int toolbarSubtitleColor = -1;

    public static KmThemeHelper getInstance(Context context, AlCustomizationSettings alCustomizationSettings) {
        if (kmThemeHelper == null) {
            kmThemeHelper = new KmThemeHelper(context, alCustomizationSettings);
        }
        return kmThemeHelper;
    }

    private KmThemeHelper(Context context, AlCustomizationSettings alCustomizationSettings) {
        this.context = ApplozicService.getContext(context);
        this.alCustomizationSettings = alCustomizationSettings;
        appSettingPreferences = KmAppSettingPreferences.getInstance();
        appSettingPreferences.setCallback(this);
    }

    public int parseColorWithDefault(String color, int defaultColor) {
        try {
            return Color.parseColor(color);
        } catch (Exception invalidColorException) {
            return defaultColor;
        }
    }

    public int getPrimaryColor() {
        if (primaryColor == -1) {
            primaryColor = parseColorWithDefault(appSettingPreferences.getPrimaryColor(), context.getResources().getColor(R.color.applozic_theme_color_primary));
        }
        return primaryColor;
    }

    public int getToolbarTitleColor() {
        if (toolbarTitleColor == -1) {
            toolbarTitleColor = parseColorWithDefault(alCustomizationSettings.getToolbarTitleColor(), context.getResources().getColor(R.color.toolbar_title_color));
        }
        return toolbarTitleColor;
    }

    public int getToolbarSubtitleColor() {
        if (toolbarSubtitleColor == -1) {
            toolbarSubtitleColor = parseColorWithDefault(alCustomizationSettings.getToolbarSubtitleColor(), context.getResources().getColor(R.color.toolbar_subtitle_color));
        }
        return toolbarSubtitleColor;
    }

    public int getSentMessageBackgroundColor() {
        if (sentMessageBackgroundColor == -1) {
            String colorStr = alCustomizationSettings.getSentMessageBackgroundColor();

            if (TextUtils.isEmpty(colorStr)) {
                colorStr = appSettingPreferences.getPrimaryColor();
            }
            sentMessageBackgroundColor = parseColorWithDefault(colorStr, context.getResources().getColor(R.color.applozic_theme_color_primary));
        }
        return sentMessageBackgroundColor;
    }

    public int getSentMessageBorderColor() {
        if (sentMessageBorderColor == -1) {
            String colorStr = alCustomizationSettings.getSentMessageBorderColor();

            if (TextUtils.isEmpty(colorStr)) {
                colorStr = appSettingPreferences.getPrimaryColor();
            }
            sentMessageBorderColor = parseColorWithDefault(colorStr, context.getResources().getColor(R.color.applozic_theme_color_primary));
        }
        return sentMessageBorderColor;
    }

    public int getSendButtonBackgroundColor() {
        if (sendButtonBackgroundColor == -1) {
            String colorStr = alCustomizationSettings.getSendButtonBackgroundColor();

            if (TextUtils.isEmpty(colorStr)) {
                colorStr = appSettingPreferences.getPrimaryColor();
            }
            sendButtonBackgroundColor = parseColorWithDefault(colorStr, context.getResources().getColor(R.color.applozic_theme_color_primary));
        }
        return sendButtonBackgroundColor;
    }

    public int getMessageStatusIconColor() {
        if (messageStatusIconColor == -1) {
            String colorStr = alCustomizationSettings.getMessageStatusIconColor();

            if (TextUtils.isEmpty(colorStr)) {
                colorStr = appSettingPreferences.getPrimaryColor();
            }

            messageStatusIconColor = parseColorWithDefault(colorStr, context.getResources().getColor(R.color.message_status_icon_colors));
        }
        return messageStatusIconColor;
    }

    public int getSecondaryColor() {
        if (secondaryColor == -1) {
            secondaryColor = parseColorWithDefault(appSettingPreferences.getSecondaryColor(), context.getResources().getColor(R.color.applozic_theme_color_primary_dark));
        }
        return secondaryColor;
    }

    public static void clearInstance() {
        kmThemeHelper = null;
    }

    @Override
    public void onSuccess(Object message) {
        if (message instanceof String) {
            switch ((String) message) {
                case KmAppSettingPreferences.CLEAR_THEME_INSTANCE:
                    clearInstance();
            }
        }
    }

    @Override
    public void onFailure(Object error) { }
}
