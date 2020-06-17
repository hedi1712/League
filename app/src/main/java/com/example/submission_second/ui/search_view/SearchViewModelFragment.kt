package com.example.submission_second.ui.search_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.model.model.search_match.SearchMatchData
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModelFragment(private val database: DicodingDatabase, private val api: ApiService) :
    ViewModel() {


    private val mCompositeDisposable = CompositeDisposable()

    private val _getSearchList = MutableLiveData<SearchMatchResponse>()
    val getSearchList: LiveData<SearchMatchResponse>
        get() = _getSearchList

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

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
            database.favoriteDao().insertFavorite(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    fun sendQueryToApi(query: String?) {
        mCompositeDisposable.add(
            api.getSearchMatchWithId(query!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchMatchResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: SearchMatchResponse) {
                        setResult(response)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun setResult(data: SearchMatchResponse) {
        _getSearchList.postValue(data)
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}