package com.example.submission_second.ui.detail_league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.module.NetworkConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {
    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getData = MutableLiveData<List<LeagueDetailData>>()
    val getData: LiveData<List<LeagueDetailData>>
        get() = _getData

    fun getDetailLeagueData(leagueId: String) {
        mCompositeDisposable.add(
            networkConfig.apiService().getDetailLeagueWithId(leagueId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LeagueDetailResponse>() {
                    override fun onNext(response: LeagueDetailResponse) {
                        setResultLeagueList(response.leagues)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun setResultLeagueList(leagueDetailData: List<LeagueDetailData>) {
        _getData.postValue(leagueDetailData)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}