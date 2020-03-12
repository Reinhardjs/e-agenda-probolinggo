package com.example.e_agendaprobolinggo.ui.home.customsearchutils;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_agendaprobolinggo.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// http://www.devexchanges.info/2016/03/modal-bottom-sheet-with-material-design.html
// http://vsjttyk.blogspot.com/2019/01/bottomsheetdialogfragment-view-becomes.html
// https://medium.com/@droidbyme/show-hide-password-in-edittext-in-android-c4c3db44f734
// https://medium.com/@OguzhanAlpayli/bottom-sheet-dialog-fragment-expanded-full-height-65b725c8309
// https://medium.com/better-programming/bottom-sheet-android-340703e114d2
// https://medium.com/@oshanm1/how-to-implement-a-search-dialog-using-full-screen-bottomsheetfragment-29ceb0af3d41 (best)
// https://medium.com/@marxallski/from-bottomsheetbehavior-to-anchorsheetbehavior-262ad7997286 (best)

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

    public void dismiss() {
        mDialog.cancel();
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                    true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, getResources().getDisplayMetrics());
        } else {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int getStatusbarHeight() {
        // reference from here : https://gist.github.com/hamakn/8939eb68a920a6d7a498
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        mDialog = dialog;

        View contentView = View.inflate(getContext(), R.layout.fragment_signin_dialog, null);
        dialog.setContentView(contentView);

        LinearLayout bottomSheet = contentView.findViewById(R.id.signinContainer);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) bottomSheet.getLayoutParams();
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels - getActionBarHeight() - getStatusbarHeight();
        bottomSheet.setLayoutParams(params);

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
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels, true);
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
