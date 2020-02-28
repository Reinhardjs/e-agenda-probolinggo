package com.example.e_agendaprobolinggo.base;


public class BasePresenter<V> {
//    public V view;
//    protected NetworkApi networkApi;
//    private CompositeDisposable compositeSubscription;
//    private Subscriber subscriber;
//
//    public void attachView(V view){
//        this.view = view;
//        networkApi = NetworkClient.getRetrofit().create(NetworkApi.class);
//    }
//
//    public void dettachView() {
//        this.view = null;
//        onUnsubscriber();
//    }
//
//    private void onUnsubscriber() {
//        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()){
//            compositeSubscription.unsubscribe();
//            Log.e("TAG", "onUnsubscribe: ");
//        }
//    }
//
//    protected void addSubscibe(Observable observable, Subscriber subscriber){
//        this.subscriber = subscriber;
//        if (compositeSubscription == null){
//            compositeSubscription = new CompositeSubscription();
//        }
//        compositeSubscription.add(observable
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(observable));
//    }
}
