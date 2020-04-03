package com.example.submission_second.ui.detail_league

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.ui.utils.FakeData
import com.nhaarman.mockitokotlin2.never
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
internal class DetailLeagueViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: DicodingDatabase

    @Mock
    private lateinit var viewModel: DetailLeagueViewModel


    private val idleague: String = ""

    private val fakeDataNext = FakeData.nextMatch()[0]
    private val fakeDataPrevious = FakeData.previousMatch()[0]
    private val fakeDataDetailLeague = FakeData.detailLeague()[0]

    private val dataNext = NextMatchResponse(listOf(Event("", "", "", "", "", "", "", "")))

    private val dataPrevious =
        PreviousMatchResponse(listOf(PreviousMatchData("", "", "", "", "", "", "", "")))
    private val dataDetail = LeagueDetailResponse(listOf(LeagueDetailData("", "", "")))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailLeagueViewModel(database)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }


    @Test
    fun getDetailLeagueData() {
        Mockito.lenient().`when`(apiService.getDetailLeagueWithId(idleague))
            .thenReturn(Observable.just(dataDetail))
        viewModel.getDetailLeagueData(idleague)
        verify(apiService, never()).getDetailLeagueWithId(idleague)
    }

    @Test
    fun getNextMatchData() {
        Mockito.lenient().`when`(apiService.getNextMatchWithId(fakeDataNext.idEvent))
            .thenReturn(Observable.just(dataNext))
        viewModel.getDetailLeagueData(fakeDataNext.idEvent)
        verify(apiService, never()).getNextMatchWithId(fakeDataNext.idEvent)
    }

    @Test
    fun getPreviousMatch() {
        Mockito.lenient().`when`(apiService.getPreviousMatchWithId(fakeDataNext.idEvent))
            .thenReturn(Observable.just(dataPrevious))
        viewModel.getDetailLeagueData(fakeDataPrevious.idEvent)
        verify(apiService, never()).getPreviousMatchWithId(fakeDataPrevious.idEvent)
    }
}