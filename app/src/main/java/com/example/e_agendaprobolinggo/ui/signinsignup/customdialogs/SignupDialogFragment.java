package com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.request.Register;
import com.example.e_agendaprobolinggo.model.response.DataRole;
import com.example.e_agendaprobolinggo.model.response.RoleResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SignupDialogFragment extends BottomSheetDialogFragment implements SignupContract.View {

    private Button btnSignup;
    private EditText etNama, etEmail, etPassword, etJabatan, etOpd;
    private CheckBox passwordSeek;
    private AutoCompleteTextView tvRole;
    SignupCallback mSignupCallback;
    private Dialog mDialog;
    private ArrayList<String> listRole = new ArrayList<>();
    private List<DataRole> listRoleData;
    private DataRole roleData;

    private SignupContract.Presenter mPresenter;

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

        View contentView = View.inflate(getContext(), R.layout.fragment_signup_dialog, null);
        dialog.setContentView(contentView);

        ((View) contentView.getParent()).setBackgroundColor(Color.TRANSPARENT);
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppTheme_BottomSheetDialogTheme);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.addBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        initView(contentView);
        initViewListener();

        mPresenter = new SignupPresenter(this);
        mPresenter.getRoleUser();

    }

    private void initSpinner(ArrayList<String> roles) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, roles);
        tvRole.setAdapter(spinnerAdapter);

        tvRole.setOnClickListener(view -> tvRole.showDropDown());

        tvRole.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                tvRole.showDropDown();
            }
        });

        tvRole.setOnItemClickListener((adapterView, view, i, l) -> roleData = listRoleData.get(i));
    }

    private void initView(View parent) {
        btnSignup = parent.findViewById(R.id.btnSignup);
        etNama = parent.findViewById(R.id.etNama);
        etEmail = parent.findViewById(R.id.etEmail);
        etPassword = parent.findViewById(R.id.etPassword);
        etJabatan = parent.findViewById(R.id.etJabatan);
        etOpd = parent.findViewById(R.id.etOpd);
        passwordSeek = parent.findViewById(R.id.passwordSeek);
        tvRole = parent.findViewById(R.id.tv_role);
        tvRole.setInputType(0);
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

        btnSignup.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String jabatan = etJabatan.getText().toString();
            String opd = etOpd.getText().toString();
            String tingkatan = tvRole.getText().toString();

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || opd.isEmpty() || tingkatan.isEmpty()) {
                Toast.makeText(getContext(), "Lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                Register register = new Register(nama, email, password, jabatan, opd, 0, Integer.parseInt(roleData.getId()));
                mSignupCallback.onSignupSubmitted(register);
            }

        });
    }

    public void setSignupCallback(SignupCallback callback) {
        mSignupCallback = callback;
    }

    @Override
    public void populateRoleUser(RoleResponse roleResponse) {
        listRole.clear();
        listRoleData = roleResponse.getData();
        for (DataRole role : listRoleData) {
            listRole.add(role.getTingkatan());
        }
        initSpinner(listRole);
    }

    @Override
    public void showPopulateFailure(String message) {

    }

    public interface SignupCallback {

        void onSignupSubmitted(Register register);

    }

}
