package com.example.submission_second.ui.team_detail

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.submission_second.R
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityTeam
import com.example.submission_second.model.model.detail_team.DetailTeamResponse
import com.example.submission_second.model.model.detail_team.Team
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TeamDetailViewmodel(
    private val database: DicodingDatabase,
    private val apiService: ApiService
) : ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()

    private val _getDetailTeam = MutableLiveData<DetailTeamResponse>()
    val getDetailTeam: LiveData<DetailTeamResponse>
        get() = _getDetailTeam

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

    fun getListTeamData(leagueId: String?) {
        mCompositeDisposable.add(
            apiService.getDetailTeam(leagueId)
                .doOnSubscribe { ProgressBar.VISIBLE }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DetailTeamResponse>() {
                    override fun onNext(response: DetailTeamResponse) {
                        setResultDetailTeam(response)
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private fun setResultDetailTeam(response: DetailTeamResponse) {
        _getDetailTeam.postValue(response)
    }


    fun storeToDatabaseNextTeam(team: Team) {
        val data = EntityTeam(team.idTeam,team.strDescriptionEN,team.strTeamBadge,team.strTeam)
        mCompositeDisposable.add(database.teamDao().insertTeam(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "Success Save In Database" },
                    { _getMessage.value = "Failed Save In Database" })
        )
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

}