package com.app.mymainapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mymainapp.baseresult.BaseResult
import com.app.mymainapp.models.TestApiResponseModel
import com.app.mymainapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by Jishnu P Dileep on 25-05-2021 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: AppRepository
) : ViewModel() {

    private val _res = MutableLiveData<BaseResult<List<TestApiResponseModel>>>()
    val res: LiveData<BaseResult<List<TestApiResponseModel>>>
        get() = _res

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch {
        _res.postValue(BaseResult.loading(null))
        mainRepository.getPosts().let {
            if (it.isSuccessful) {
                _res.postValue(BaseResult.success(it.body()))
            } else {
                _res.postValue(BaseResult.error(it.errorBody().toString(), null))
            }
        }
    }
}