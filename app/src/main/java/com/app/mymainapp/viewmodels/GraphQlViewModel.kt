package com.app.mymainapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mymainapp.LaunchListQuery
import com.app.mymainapp.baseresult.BaseResult
import com.app.mymainapp.baseresult.ResultWrapper
import com.app.mymainapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphQlViewModel @Inject constructor(
    private val mainRepository: AppRepository
) : ViewModel() {

    private val _listMutableLivedata = MutableLiveData<BaseResult<List<LaunchListQuery.Launch?>>>()
    val listLiveData: LiveData<BaseResult<List<LaunchListQuery.Launch?>>>
        get() = _listMutableLivedata


    fun fetchLiveData() = viewModelScope.launch {

        _listMutableLivedata.postValue(BaseResult.loading())

        when (val response =
            mainRepository.fetchLists()) {
            is ResultWrapper.Success ->
                _listMutableLivedata.postValue(
                    BaseResult.success(
                        response.data.launches.launches
                    )
                )

            is ResultWrapper.Failure ->
                _listMutableLivedata.postValue(
                    BaseResult.error(
                        response.message
                    )
                )
        }
    }
}