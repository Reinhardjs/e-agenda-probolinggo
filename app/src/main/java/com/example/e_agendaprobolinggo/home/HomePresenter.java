package com.example.e_agendaprobolinggo.home;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    public HomePresenter(HomeContract.View view){
        mView = view;
    }

}
