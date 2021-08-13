package com.app.bookshares.register;


import android.util.Log;

import com.app.bookshares.R;
import com.app.bookshares.app.App;
import com.app.bookshares.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterPresenter implements RegisterInterface.RegisterPresenter {

    private static final String TAG = "FirebaseServices";
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private RegisterInterface.RegisterView registerView;


    public RegisterPresenter(RegisterInterface.RegisterView registerView) {
        this.registerView = registerView;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void createUser(User user,String password) {

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail().trim(), password.trim())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        if(firebaseAuth.getCurrentUser()!=null)
                        {
                            user.setUser_id(firebaseAuth.getCurrentUser().getUid());
                            addNewUser(user);
                        }

                    } else {

                        registerView.onRegisterFailure(Objects.requireNonNull(task.getException()).getLocalizedMessage());

                    }

                })
                .addOnFailureListener(e -> {
                    registerView.onRegisterFailure(e.getLocalizedMessage());

                });
    }


    public void addNewUser(User user) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("user_id", user.getUser_id());
        newUser.put("user_name", user.getUser_name());
        newUser.put("email", user.getEmail().trim());
        newUser.put("phone_number", user.getPhone_number());
        newUser.put("image", "");


        firebaseFirestore.collection(App.getAppContext().getString(R.string.users)).document(user.getUser_id())
                .set(newUser)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    registerView.onRegisterSuccess(App.getAppContext().getString(R.string.successfully_sign_up));

                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing document", e);

                    registerView.onRegisterFailure(App.getAppContext().getString(R.string.error_sign_up));


                });
    }

}
