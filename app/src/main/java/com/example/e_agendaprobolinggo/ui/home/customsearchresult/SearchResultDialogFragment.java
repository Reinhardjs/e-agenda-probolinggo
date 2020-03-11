package com.example.e_agendaprobolinggo.ui.home.customsearchresult;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_agendaprobolinggo.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// http://www.devexchanges.info/2016/03/modal-bottom-sheet-with-material-design.html
// http://vsjttyk.blogspot.com/2019/01/bottomsheetdialogfragment-view-becomes.html
// https://medium.com/@droidbyme/show-hide-password-in-edittext-in-android-c4c3db44f734
// https://medium.com/@OguzhanAlpayli/bottom-sheet-dialog-fragment-expanded-full-height-65b725c8309
// https://medium.com/better-programming/bottom-sheet-android-340703e114d2

public class SearchResultDialogFragment extends BottomSheetDialogFragment {

    Button btnSignin;
    EditText etEmail, etPassword;
    CheckBox passwordSeek;
    Dialog mDialog;

    SearchResultDialogFragment.SigninCallback mSigninCallback;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                // androidx should use: com.google.android.material.R.id.design_bottom_sheet
                FrameLayout bottomSheet = (FrameLayout) dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        mDialog = dialog;

        View contentView = View.inflate(getContext(), R.layout.fragment_signin_dialog, null);
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
            bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        }

        initView(contentView);
        initViewListener();
    }

    private void initView(View parent){
        btnSignin = parent.findViewById(R.id.btnSignin);
        etEmail = parent.findViewById(R.id.etEmail);
        etPassword = parent.findViewById(R.id.etPassword);
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

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                mSigninCallback.onSigninSubmitted(email, password);
            }
        });
    }

    public void setSigninCallback(SearchResultDialogFragment.SigninCallback callback) {
        mSigninCallback = callback;
    }

    public interface SigninCallback {

        void onSigninSubmitted(String email, String password);

    }
}
