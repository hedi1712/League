package com.example.submission_second.ui.detail_league

import android.util.EventLog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.module.NetworkConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {
    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getDetailLeague = MutableLiveData<List<LeagueDetailData>>()
    val getDataLeague: LiveData<List<LeagueDetailData>>
        get() = _getDetailLeague

    private val _getNextMatch = MutableLiveData<List<NextMatchResponse.Event>>()
    val getNextMatch: LiveData<List<NextMatchResponse.Event>>
        get() = _getNextMatch

    private val _getPreviousMatch = MutableLiveData<List<PreviousMatchResponse.PreviousMatchData>>()
    val getPreviousMatch: LiveData<List<PreviousMatchResponse.PreviousMatchData>>
        get() = _getPreviousMatch

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

    fun getNextMatchData(leagueId: String) {
        mCompositeDisposable.add(
            networkConfig.apiService().getNextMatchWithId(leagueId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<NextMatchResponse>() {
                    override fun onNext(response: NextMatchResponse) {
                        listNextMatch(response.events)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                    }

                })
        )
    }

    fun getPreviousMatch(leagueId: String) {
        mCompositeDisposable.add(
            networkConfig.apiService().getPreviousMatchWithId(leagueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PreviousMatchResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(response: PreviousMatchResponse) {
                        listPreviousMatch(response.events)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun listNextMatch(events: List<NextMatchResponse.Event>) {
        _getNextMatch.postValue(events)
    }

    fun setResultLeagueList(leagueDetailData: List<LeagueDetailData>) {
        _getDetailLeague.postValue(leagueDetailData)
    }

    fun listPreviousMatch(leagueDetailData: List<PreviousMatchResponse.PreviousMatchData>) {
        _getPreviousMatch.postValue(leagueDetailData)
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}