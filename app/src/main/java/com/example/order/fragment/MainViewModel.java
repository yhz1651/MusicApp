package com.example.order.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> progress;

    public LiveData<String> getProgress()
    {
        if (progress == null)
        {
            progress = new MutableLiveData<>();
        }
        return progress;
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        progress = null;
    }
}