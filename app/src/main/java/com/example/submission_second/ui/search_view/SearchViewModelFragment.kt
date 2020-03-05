package com.example.submission_second.ui.search_view

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.search_match.SearchData
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import com.example.submission_second.module.NetworkConfig
import com.example.submission_second.util.toddMMyyyy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchViewModelFragment : ViewModel() {

    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getSearchList = MutableLiveData<List<SearchData>>()
    val getSearchList: LiveData<List<SearchData>>
        get() = _getSearchList


    fun sendQueryToApi(query: String?) {
        mCompositeDisposable.add(
            networkConfig.apiService().getSearchMatchWithId(query!!)
                .map { mapData(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<SearchData>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: List<SearchData>) {
                        if (response.isNullOrEmpty()) {
                        } else {
                            response.let { setResult(it) }
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun mapData(response: SearchMatchResponse): List<SearchData> {
        val searchData = mutableListOf<SearchData>()
        for (i in response.event) {
            searchData.add(
                SearchData(
                    i.dateEvent.toddMMyyyy(),
                    i.dateEventLocal,
                    i.idAPIfootball,
                    i.idAwayTeam,
                    i.idEvent,
                    i.idHomeTeam,
                    i.idLeague,
                    i.idSoccerXML,
                    i.intAwayScore,
                    i.intAwayShots,
                    i.intHomeScore,
                    i.intHomeShots,
                    i.intRound,
                    i.intSpectators,
                    i.strAwayFormation,
                    i.strAwayGoalDetails,
                    i.strAwayLineupDefense,
                    i.strAwayLineupForward,
                    i.strAwayLineupGoalkeeper,
                    i.strAwayLineupMidfield,
                    i.strAwayLineupSubstitutes,
                    i.strAwayRedCards,
                    i.strAwayTeam,
                    i.strAwayYellowCards,
                    i.strBanner,
                    i.strCircuit,
                    i.strCity,
                    i.strCountry,
                    i.strDate,
                    i.strDescriptionEN,
                    i.intHomeScore,
                    i.strEvent,
                    i.strEventAlternate,
                    i.strFanart,
                    i.strFilename,
                    i.strHomeFormation,
                    i.strHomeGoalDetails,
                    i.strHomeLineupDefense,
                    i.strHomeLineupForward,
                    i.strHomeLineupGoalkeeper,
                    i.strHomeLineupMidfield,
                    i.strHomeLineupSubstitutes,
                    i.strHomeRedCards,
                    i.strHomeTeam,
                    i.strHomeYellowCards,
                    i.strLeague,
                    i.strLocked,
                    i.strMap,
                    i.strPoster,
                    i.strResult,
                    i.strSeason,
                    i.strSport,
                    i.strTVStation.toString(),
                    i.strThumb,
                    i.strTimeLocal,
                    i.strTweet1,
                    i.strTweet2,
                    i.strTweet3,
                    i.strVideo
                )
            )
        }
       return searchData
    }

    private fun setResult(data: List<SearchData>) {
        _getSearchList.postValue(data)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}