package com.fikrabd.mehna_w_amal.change_password;

/**
 * Created by Saif M Jaradat on 14/2/2021.
 */
public interface ChangePasswordInterface {

    interface ChangePasswordView {
        void passwordChanged(String message);

        void passwordNotChanged(String message);

        void errorOccurred(String message);
    }

    interface ChangePasswordPresenter {
        void changePassword(String currentPassword , String newPassword);
    }

}
