package com.example.submission_second.ui.search_view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.search_match.SearchMatchData
import com.example.submission_second.model.model.search_match.SearchMatchResponse
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelFragmentTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: DicodingDatabase

    @Mock
    private lateinit var viewModel: SearchViewModelFragment

    @Mock
    lateinit var observer: Observer<List<SearchMatchData>>


    private val string: String = "arsenal"

    private val dataTest =
        SearchMatchResponse(listOf(SearchMatchData("1234", "makan", "", "", "", "", "", "")))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModelFragment(database, apiService)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun sendQueryToApi() {
        Mockito.`when`(apiService.getSearchMatchWithId(string))
            .thenReturn(Observable.just(dataTest))
        viewModel.sendQueryToApi(string)
        viewModel.getSearchList.observeForever(observer)
        verify(apiService).getSearchMatchWithId(string)
    }
}