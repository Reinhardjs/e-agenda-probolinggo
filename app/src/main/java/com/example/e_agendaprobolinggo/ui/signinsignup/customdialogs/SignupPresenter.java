package com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs;

import com.example.e_agendaprobolinggo.model.response.RoleResponse;

public class SignupPresenter implements SignupContract.Presenter {

    private final SignupContract.View mView;
    private final SignupContract.Interactor mInteractor;

    public SignupPresenter(SignupContract.View view) {
        mView = view;
        mInteractor = new SignupInteractor();
    }

    @Override
    public void getRoleUser() {
        mInteractor.requestRoleUser(new SignupContract.RoleUserCallback() {
            @Override
            public void onRoleRequestCompleted(RoleResponse roleResponse) {
                mView.populateRoleUser(roleResponse);
            }

            @Override
            public void onRoleRequestFailure(String message) {
                mView.showPopulateFailure(message);
            }
        });
    }
}
