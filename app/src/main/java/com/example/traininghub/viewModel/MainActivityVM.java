package com.example.traininghub.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;

public class MainActivityVM extends ViewModel {
    private MainFragment mainFragment;
    private MyCoursesFragment myCoursesFragment;
    private AccountFragment accountFragment;




    public MainFragment getMainFragment() {
        if (mainFragment==null){
            Log.d("FRAGG", "getMainFragment: null");

        }else {
            Log.d("FRAGG", "getMainFragment: cache");
        }
        return mainFragment==null?mainFragment=new MainFragment():mainFragment;
    }



    public MyCoursesFragment getMyCoursesFragment() {
        return myCoursesFragment==null?myCoursesFragment=new MyCoursesFragment():myCoursesFragment;
    }

    public void setMyCoursesFragment(MyCoursesFragment myCoursesFragment) {
        this.myCoursesFragment = myCoursesFragment;
    }

    public AccountFragment getAccountFragment() {
        return accountFragment==null?accountFragment=new AccountFragment():accountFragment;
    }

    public void setAccountFragment(AccountFragment accountFragment) {
        this.accountFragment = accountFragment;
    }
}
