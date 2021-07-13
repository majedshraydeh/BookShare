package com.fikrabd.mehna_w_amal.home;

import com.fikrabd.mehna_w_amal.models.Category;
import com.fikrabd.mehna_w_amal.models.DefaultData;
import com.fikrabd.mehna_w_amal.models.Rating;
import com.fikrabd.mehna_w_amal.models.SubCategory;
import com.fikrabd.mehna_w_amal.models.User;

import java.util.List;


public interface MainInterface {

    interface MainView {


    }
    interface CategoryView {

        void allCategories(List<Category> categories);

        void noCategories();
    }

    interface SubCategoryView {

        void allSubCategory(List<SubCategory> subCategories);

        void noSubCategory();
        void onRegisterSuccess(User user);

        void onRegisterFailure(String message);

        void onRegisterError(String message);
    }

    interface PeopleView {

        void allPeople(List<User> subCategories);

        void noPeople();
    }

    interface UserDetailsView {

        void allUserDetails(User user);

        void noUserDetails();

        void rated(String message, Rating rating);
    }




    interface UpdateView {
        void onUpdateSuccess(User user,String message);

        void onUpdateFailure(String message);

        void onUpdateError(String message);

        void onStatusChanged(String message);

    }




    interface MainPresenterView
    {

        void getCategory();

        void getSubCategory(String categoryId);

        void getPeople(String subCategoryId);

        void createUser(User user);

        void userDetails(String userId);

        void rating(String userToRate , String rateValue);

        void changeStatus(String userId,String status);

        void updateUser(User user);
    }
}
