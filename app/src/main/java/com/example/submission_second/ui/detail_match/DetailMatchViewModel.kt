package com.example.submission_second.ui.detail_match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.model.model.detail_match.DetailMatchResponse
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

    private fun liveDataMatchDetail(response: List<DetailMatchResponse.DetailMatchData>) {
        _getMatchDetail.postValue(response)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }


}