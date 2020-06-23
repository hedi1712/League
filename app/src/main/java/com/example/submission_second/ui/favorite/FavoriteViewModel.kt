package com.example.submission_second.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.db.entity.EntityTeam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(context: Context) : ViewModel() {

    private var database: DicodingDatabase? = null
    private val mCompositeDisposable = CompositeDisposable()

    private val _getDataFavorite = MutableLiveData<List<EntityFavorite>>()
    val getDataFavorite: LiveData<List<EntityFavorite>>
        get() = _getDataFavorite

    private val _getDataTeamFavorite = MutableLiveData<List<EntityTeam>>()
    val getDataTeamFavorite: LiveData<List<EntityTeam>>
        get() = _getDataTeamFavorite

    private val _getMessage = MutableLiveData<String>()
    val getMessage: LiveData<String>
        get() = _getMessage

    init {
        database = DicodingDatabase.buildDatabase(context.applicationContext)
    }


    fun getAllFavorite() {
        mCompositeDisposable.addAll(
            database!!.favoriteDao().getListClub()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<EntityFavorite>>() {
                    override fun onComplete() {
                        _getMessage.value = "Data Berhasil Diload"
                    }

                    override fun onNext(data: List<EntityFavorite>) {
                        _getDataFavorite.value = data
                    }

                    override fun onError(e: Throwable) {
                        _getMessage.value = "Data Gagal Diload"
                    }
                })
        )
    }

    fun deleteFavorite(idEvent: String) {
        mCompositeDisposable.addAll(
            database!!.favoriteDao().deleteMatchEvent(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "data favorite Berhasil dihapus" },
                    { _getMessage.value = "data favorite gagal dihapus" })
        )
    }

    fun getTeamFavorite() {
        mCompositeDisposable.addAll(database!!.teamDao().getTeamFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<EntityTeam>>() {
                    override fun onComplete() {
                        _getMessage.value = "Data Berhasil Diload"
                    }

                    override fun onNext(data: List<EntityTeam>) {
                        _getDataTeamFavorite.value = data
                    }

                    override fun onError(e: Throwable) {
                        _getMessage.value = "Data Gagal Diload"
                    }
                })
        )
    }

    fun deleteTeamFavorite(idEvent: String) {
        mCompositeDisposable.addAll(
            database!!.teamDao().deleteTeamFavorite(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _getMessage.value = "data favorite Berhasil dihapus" },
                    { _getMessage.value = "data favorite gagal dihapus" })
        )
    }



    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}
