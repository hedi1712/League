package com.example.submission_second.ui.league_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.api.ApiService
import com.example.submission_second.model.model.league_list.LeagueListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LeagueListViewModel(private val api : ApiService) : ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _getData = MutableLiveData<LeagueListResponse>()
    val getData: LiveData<LeagueListResponse>
        get() = _getData

    fun getLeagueData() {
        mCompositeDisposable.add(
            api.getAllLeagueData("Soccer").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LeagueListResponse>() {
                    override fun onNext(response: LeagueListResponse) {
                        setResultLeagueList(response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    fun setResultLeagueList(leagueListResponse: LeagueListResponse) {
        _getData.postValue(leagueListResponse)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}
