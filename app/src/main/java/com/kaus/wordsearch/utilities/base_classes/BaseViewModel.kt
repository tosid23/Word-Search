package com.kaus.wordsearch.utilities.base_classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.kaus.wordsearch.App

open class BaseViewModel : ViewModel() {
    val workManager = WorkManager.getInstance(App.instance)
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val showMessageInSnackBar: MutableLiveData<String> = MutableLiveData()
    val closeFragmentLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val emptyStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
}