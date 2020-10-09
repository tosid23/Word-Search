package com.kaus.wordsearch.utilities.base_classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.kaus.wordsearch.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    private val parentJob = Job()
    private val defaultCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val ioCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val mainCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    protected val defaultScope = CoroutineScope(defaultCoroutineContext)
    protected val ioScope = CoroutineScope(ioCoroutineContext)
    protected val mainScope = CoroutineScope(mainCoroutineContext)

    val workManager = WorkManager.getInstance(App.instance)
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val showMessageInSnackBar: MutableLiveData<String> = MutableLiveData()
    val closeFragmentLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val emptyStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
}