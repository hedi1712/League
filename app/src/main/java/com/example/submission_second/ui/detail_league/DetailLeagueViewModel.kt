package com.example.submission_second.ui.detail_league

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.module.NetworkConfig
import com.example.submission_second.util.toddMMyyyy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailLeagueViewModel(private val database: DicodingDatabase) : ViewModel() {

    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getDetailLeague = MutableLiveData<List<LeagueDetailData>>()
    val getDataLeague: LiveData<List<LeagueDetailData>>
        get() = _getDetailLeague

    private val _getNextMatch = MutableLiveData<List<Event>>()
    val getNextMatch: LiveData<List<Event>>
        get() = _getNextMatch

    private val _getPreviousMatch =
        MutableLiveData<List<PreviousMatchData>>()
    val getPreviousMatch: LiveData<List<PreviousMatchData>>
        get() = _getPreviousMatch

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

    fun getDetailLeagueData(leagueId: String) {
        mCompositeDisposable.add(
            networkConfig.apiService().getDetailLeagueWithId(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
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
            networkConfig.apiService().getNextMatchWithId(leagueId)
                .map { transformNextData(it) }
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Event>>() {
                    override fun onNext(response: List<Event>) {
                        ProgressBar.INVISIBLE
                        response.let { listNextMatch(response) }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                    }

                })
        )
    }

    private fun transformNextData(response: NextMatchResponse): List<Event> {
        val nextMatchData = mutableListOf<Event>()
        for (i in response.events) {
            nextMatchData.add(
                Event(
                    i.idEvent,
                    i.dateEvent.toddMMyyyy(),
                    i.intAwayScore,
                    i.intHomeScore,
                    i.strAwayTeam,
                    i.strHomeTeam,
                    i.strLeague,
                    i.strTime
                )
            )
        }
        return nextMatchData
    }

    fun getPreviousMatch(leagueId: String) {
        mCompositeDisposable.add(
            networkConfig.apiService().getPreviousMatchWithId(leagueId)
                .map { transformPreviousData(it) }
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<PreviousMatchData>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: List<PreviousMatchData>) {
                        ProgressBar.INVISIBLE
                        response.let { listPreviousMatch(it) }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun transformPreviousData(response: PreviousMatchResponse): List<PreviousMatchData> {
        val previousMatchData = mutableListOf<PreviousMatchData>()
        for (i in response.events) {
            previousMatchData.add(
                PreviousMatchData(
                    i.idEvent,
                    i.dateEvent.toddMMyyyy(),
                    i.intAwayScore,
                    i.intHomeScore,
                    i.strAwayTeam,
                    i.strHomeTeam,
                    i.strLeague,
                    i.strTime
                )
            )
        }
        return previousMatchData
    }


    fun storeToDatabaseNextMatch(searchData: Event) {
        val data = EntityFavorite(
            searchData.idEvent,
            searchData.strHomeTeam,
            searchData.strAwayTeam,
            searchData.dateEvent,
            searchData.intHomeScore,
            searchData.intAwayScore,
            1
        )
        mCompositeDisposable.add(
            database!!.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    fun storeToDatabasePreviousMatch(searchData: PreviousMatchData) {
        val data = EntityFavorite(
            searchData.idEvent,
            searchData.strHomeTeam,
            searchData.strAwayTeam,
            searchData.dateEvent,
            searchData.intHomeScore,
            searchData.intAwayScore,
            3
        )
        mCompositeDisposable.add(
            database!!.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    private fun listNextMatch(events: List<Event>) {
        _getNextMatch.postValue(events)
    }

    fun setResultLeagueList(leagueDetailData: List<LeagueDetailData>) {
        _getDetailLeague.postValue(leagueDetailData)
    }

    fun listPreviousMatch(leagueDetailData: List<PreviousMatchData>) {
        _getPreviousMatch.postValue(leagueDetailData)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}