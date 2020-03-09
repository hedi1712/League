package com.example.submission_second.ui.detail_match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.detail_match.*
import com.example.submission_second.module.NetworkConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailMatchViewModel : ViewModel() {
    private val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getMatchDetail = MutableLiveData<List<DetailMatchResponse.DetailMatchData>>()
    val getMatchDetail: LiveData<List<DetailMatchResponse.DetailMatchData>>
        get() = _getMatchDetail

    private val _getBadgeHome = MutableLiveData<List<TeamHome>>()
    val getBadgeHome: LiveData<List<TeamHome>>
        get() = _getBadgeHome

    private val _getBadgeAway = MutableLiveData<List<TeamAway>>()
    val getBadgeAway: LiveData<List<TeamAway>>
        get() = _getBadgeAway

    fun fetchDetailMatch(leagueId: String) {
        mCompositeDisposable.addAll(
            networkConfig.apiService().getDetailMatchWithId(leagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DetailMatchResponse>() {
                    override fun onNext(response: DetailMatchResponse) {
                        liveDataMatchDetail(response.detailMatchData)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun fetchBadgeHome(leagueId: String) {
        mCompositeDisposable.addAll(
            networkConfig.apiService().getBadgeHomeWithId(leagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GetBadgeHomeTeamResponse>() {
                    override fun onNext(response: GetBadgeHomeTeamResponse) {
                        getBadgeHome(response)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun fetchBadgeAway(leagueId: String) {
        mCompositeDisposable.addAll(
            networkConfig.apiService().getBadgeAwayWithId(leagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GetBadgeAwayTeamResponse>() {
                    override fun onNext(response: GetBadgeAwayTeamResponse) {
                        getBadgeAway(response)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun liveDataMatchDetail(response: List<DetailMatchResponse.DetailMatchData>) {
        _getMatchDetail.postValue(response)
    }


    private fun getBadgeHome(response: GetBadgeHomeTeamResponse) {
        return _getBadgeHome.postValue(response.teams)
    }

    private fun getBadgeAway(response: GetBadgeAwayTeamResponse) {
        return _getBadgeAway.postValue(response.teams)
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }


}