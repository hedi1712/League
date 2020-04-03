package com.example.submission_second.ui.league_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission_second.api.ApiService
import com.example.submission_second.model.model.league_list.LeagueData
import com.example.submission_second.model.model.league_list.LeagueListResponse
import com.example.submission_second.ui.utils.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.never
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
class LeagueListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var viewModel: LeagueListViewModel

    private val string: String = "Soccer"

    private val dataTest = LeagueListResponse(listOf(LeagueData("1234", "makan", "", "", "")))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = LeagueListViewModel()

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getLeagueData() {
        Mockito.`when`(apiService.getAllLeagueData(string)).thenReturn(Observable.just(dataTest))
        viewModel.getLeagueData()
        verify(apiService, never()).getAllLeagueData(string)
    }
}