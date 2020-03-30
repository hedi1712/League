package com.example.submission_second.ui.search_view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.model.model.search_match.SearchMatchData
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import com.example.submission_second.module.NetworkConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModelFragment(application: Context) : ViewModel() {

    private var database: DicodingDatabase? = null

    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getSearchList = MutableLiveData<List<SearchMatchData>>()
    val getSearchList: LiveData<List<SearchMatchData>>
        get() = _getSearchList

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

    init {
        database = DicodingDatabase.buildDatabase(application.applicationContext)
    }

    fun storeToDatabase(searchData: SearchMatchData) {
        val data = EntityFavorite(
            searchData.idEvent,
            searchData.strHomeTeam,
            searchData.strAwayTeam,
            searchData.dateEvent,
            searchData.intHomeScore,
            searchData.intAwayScore,
            2
        )
        mCompositeDisposable.add(
            database!!.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    fun sendQueryToApi(query: String?) {
        mCompositeDisposable.add(
            networkConfig.apiService().getSearchMatchWithId(query!!)
                .map { mapData(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<SearchMatchData>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: List<SearchMatchData>) {
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

    private fun mapData(response: SearchMatchResponse): List<SearchMatchData> {
        val searchData = mutableListOf<SearchMatchData>()
        for (i in response.event) {
            if (i.strSport.equals("Soccer")) {
                searchData.add(
                    SearchMatchData(
                        i.strLeague,
                        i.strSport,
                        i.idEvent,
                        i.dateEvent,
                        i.intAwayScore,
                        i.intHomeScore,
                        i.strAwayTeam,
                        i.strHomeTeam
                    )
                )
            }
        }
        return searchData
    }

    private fun setResult(data: List<SearchMatchData>) {
        _getSearchList.postValue(data)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}