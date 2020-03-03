package com.example.submission_second.ui.search_view

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import com.example.submission_second.module.NetworkConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModelFragment : ViewModel() {

    val networkConfig = NetworkConfig()
    private val mCompositeDisposable = CompositeDisposable()

    private val _getSearchList = MutableLiveData<List<SearchMatchResponse.SearchData>>()
    val getSearchList: LiveData<List<SearchMatchResponse.SearchData>>
        get() = _getSearchList


    fun sendQueryToApi(query: String?) {
        mCompositeDisposable.add(
            networkConfig.apiService().getSearchMatchWithId(query!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchMatchResponse>() {
                    override fun onComplete() {
                    }

                    override fun onNext(response: SearchMatchResponse) {
                        if (response.event.isNullOrEmpty()) {

                        } else {
                            response.event.let { setResult(it) }
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun setResult(data: List<SearchMatchResponse.SearchData>) {
        _getSearchList.postValue(data)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}