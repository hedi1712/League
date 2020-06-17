package com.example.submission_second.ui.detail_league

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.model.model.classment.ClassmentResponse
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.list_of_competition_team.ListOfTeamCompetitionResponse
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.util.toddMMyyyy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailLeagueViewModel(private val database: DicodingDatabase, private val api: ApiService) :
    ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()

    private val _getDetailLeague = MutableLiveData<LeagueDetailResponse>()
    val getDataLeague: LiveData<LeagueDetailResponse>
        get() = _getDetailLeague

    private val _getNextMatch = MutableLiveData<NextMatchResponse>()
    val getNextMatch: LiveData<NextMatchResponse>
        get() = _getNextMatch

    private val _getPreviousMatch =
        MutableLiveData<PreviousMatchResponse>()
    val getPreviousMatch: LiveData<PreviousMatchResponse>
        get() = _getPreviousMatch

    private val _getClassement = MutableLiveData<ClassmentResponse>()
    val getClassment: LiveData<ClassmentResponse>
        get() = _getClassement

    private val _getTeamLeague = MutableLiveData<ListOfTeamCompetitionResponse>()
    val getTeamLeague: LiveData<ListOfTeamCompetitionResponse>
        get() = _getTeamLeague

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

    fun getDetailLeagueData(leagueId: String) {
        mCompositeDisposable.add(
            api.getDetailLeagueWithId(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LeagueDetailResponse>() {
                    override fun onNext(response: LeagueDetailResponse) {
                        setResultLeagueList(response)
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
            api.getNextMatchWithId(leagueId)
//                .map { transformNextData(it) }
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<NextMatchResponse>() {
                    override fun onNext(response: NextMatchResponse) {
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

    fun getPreviousMatch(leagueId: String) {
        mCompositeDisposable.add(
            api.getPreviousMatchWithId(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<PreviousMatchResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: PreviousMatchResponse) {
                        ProgressBar.INVISIBLE
                        response.let { listPreviousMatch(it) }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun getClassmentLeague(leagueId: String) {
        mCompositeDisposable.add(
            api.getListClassmentWithId(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ClassmentResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: ClassmentResponse) {
                        response.let { listClassment(it) }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun getListTeamLeague(leagueId: String) {
        mCompositeDisposable.add(
            api.getListTeamWithId(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ListOfTeamCompetitionResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: ListOfTeamCompetitionResponse) {
                        response.let { listTeamLeague(it) }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
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
            database.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
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
            database.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    fun listNextMatch(events: NextMatchResponse) {
        _getNextMatch.postValue(events)
    }

    fun listPreviousMatch(leagueDetailData: PreviousMatchResponse) {
        _getPreviousMatch.postValue(leagueDetailData)
    }

    fun listClassment(events: ClassmentResponse) {
        _getClassement.postValue(events)
    }

    fun listTeamLeague(events: ListOfTeamCompetitionResponse) {
        _getTeamLeague.postValue(events)
    }

    fun setResultLeagueList(leagueDetailData: LeagueDetailResponse) {
        _getDetailLeague.postValue(leagueDetailData)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}