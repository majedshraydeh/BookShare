package com.app.bookshare.register;


import com.app.bookshare.models.User;

public interface RegisterInterface {

    interface RegisterView {
        void onRegisterSuccess(String message);

        void onRegisterFailure(String message);



    }



    interface RegisterPresenter {
        void createUser(User user,String password);

    }
}
