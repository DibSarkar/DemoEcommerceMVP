package com.app.demoopencartapp.shared.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();
}