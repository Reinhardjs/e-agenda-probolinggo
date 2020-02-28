package com.example.e_agendaprobolinggo.signinsignup.customdialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.body.User;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SignupDialogFragment extends BottomSheetDialogFragment {

    Button btnSignup;
    EditText etNama, etEmail, etPassword, etJabatan, etOpd;
    CheckBox passwordSeek;
    SignupCallback mSignupCallback;
    Dialog mDialog;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

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

    public void dismiss(){
        mDialog.cancel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        mDialog = dialog;

        View contentView = View.inflate(getContext(), R.layout.fragment_signup_dialog, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppTheme_BottomSheetDialogTheme);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            // bottomSheetBehavior.setHideable(true);
            // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        initView(contentView);
        initViewListener();
    }

    private void initView(View parent){
        btnSignup = parent.findViewById(R.id.btnSignup);
        etNama = parent.findViewById(R.id.etNama);
        etEmail = parent.findViewById(R.id.etEmail);
        etPassword = parent.findViewById(R.id.etPassword);
        etJabatan = parent.findViewById(R.id.etJabatan);
        etOpd = parent.findViewById(R.id.etOpd);
        passwordSeek = parent.findViewById(R.id.passwordSeek);
    }

    private void initViewListener() {
        passwordSeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // show password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnSignup.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String jabatan = etJabatan.getText().toString();
            String opd = etOpd.getText().toString();

            User user = new User();
            user.setNama(nama);
            user.setEmail(email);
            user.setPassword(password);
            user.setJabatan(jabatan);
            user.setOpd(opd);

            mSignupCallback.onSignupSubmitted(user);
        });
    }

    public void setSignupCallback(SignupCallback callback){
        mSignupCallback = callback;
    }

    public interface SignupCallback {

        void onSignupSubmitted(User user);

    }

}
