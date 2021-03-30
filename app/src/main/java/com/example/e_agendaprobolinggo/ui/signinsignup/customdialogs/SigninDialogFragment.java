package com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_agendaprobolinggo.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// http://www.devexchanges.info/2016/03/modal-bottom-sheet-with-material-design.html
// http://vsjttyk.blogspot.com/2019/01/bottomsheetdialogfragment-view-becomes.html
// https://medium.com/@droidbyme/show-hide-password-in-edittext-in-android-c4c3db44f734

public class SigninDialogFragment extends BottomSheetDialogFragment {

    Button btnSignin;
    EditText etEmail, etPassword;
    CheckBox passwordSeek;
    Dialog mDialog;

    SigninDialogFragment.SigninCallback mSigninCallback;
    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    public void dismiss() {
        mDialog.cancel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        mDialog = dialog;

        View contentView = View.inflate(getContext(), R.layout.fragment_signin_dialog, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(Color.TRANSPARENT);
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppTheme_BottomSheetDialogTheme);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            // bottomSheetBehavior.setHideable(true);
            // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.addBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        initView(contentView);
        initViewListener();
    }

    private void initView(View parent) {
        btnSignin = parent.findViewById(R.id.btnSignin);
        etEmail = parent.findViewById(R.id.etEmail);
        etPassword = parent.findViewById(R.id.etPassword);
        passwordSeek = parent.findViewById(R.id.passwordSeek);
    }

    private void initViewListener() {
        passwordSeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // show password
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // hide password
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btnSignin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            mSigninCallback.onSigninSubmitted(email, password);
        });
    }

    public void setSigninCallback(SigninDialogFragment.SigninCallback callback) {
        mSigninCallback = callback;
    }

    public interface SigninCallback {

        void onSigninSubmitted(String email, String password);

    }
}
