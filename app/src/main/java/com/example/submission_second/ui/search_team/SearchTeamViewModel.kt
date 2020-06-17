package com.example.submission_second.ui.search_team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.api.ApiService
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import com.example.submission_second.model.model.search_team.SearchTeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchTeamViewModel(private val api: ApiService) : ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()

    private val _getSearchList = MutableLiveData<SearchTeamResponse>()
    val getSearchList: LiveData<SearchTeamResponse>
        get() = _getSearchList

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage


    fun sendQueryToApi(query: String?) {
        mCompositeDisposable.add(
            api.getSearchListTeam(query!!)
//                .map { mapData(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchTeamResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: SearchTeamResponse) {
                        setResult(response)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun setResult(data: SearchTeamResponse) {
        _getSearchList.postValue(data)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}