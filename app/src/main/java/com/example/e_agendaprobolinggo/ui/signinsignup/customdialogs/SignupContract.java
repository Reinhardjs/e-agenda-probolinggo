package com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs;

import com.example.e_agendaprobolinggo.model.response.RoleResponse;

public interface SignupContract {

    interface RoleUserCallback {
        void onRoleRequestCompleted(RoleResponse roleResponse);

        void onRoleRequestFailure(String message);
    }

    interface View {
        void populateRoleUser(RoleResponse roleResponse);

        void showPopulateFailure(String message);
    }

    interface Interactor {
        void requestRoleUser(RoleUserCallback roleUserCallback);
    }

    interface Presenter {
        void getRoleUser();
    }
}
