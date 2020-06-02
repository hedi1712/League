package com.example.submission_second.ui.detail_league

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.submission_second.api.ApiService
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.league_detail.LeagueDetailData
import com.example.submission_second.model.model.league_detail.LeagueDetailResponse
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse
import com.example.submission_second.ui.utils.FakeData
import com.example.submission_second.ui.utils.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
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
class DetailLeagueViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var database: DicodingDatabase

    @Mock
    private lateinit var viewModel: DetailLeagueViewModel

    @Mock
    private val observerDetailLeague: Observer<LeagueDetailResponse> = mock()

    @Mock
    private val observergetNextMatch: Observer<NextMatchResponse> = mock()

    @Mock
    private val observergetPreviousMatch: Observer<PreviousMatchResponse> = mock()

    private val idleague: String = ""

    private val fakeDataNext = FakeData.nextMatch()[0]
    private val fakeDataPrevious = FakeData.previousMatch()[0]

    private val dataNext = NextMatchResponse(listOf(Event("", "", "", "", "", "", "", "")))

    private val dataPrevious =
        PreviousMatchResponse(listOf(PreviousMatchData("", "", "", "", "", "", "", "")))
    private val dataDetail = LeagueDetailResponse(listOf(LeagueDetailData("", "", "")))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailLeagueViewModel(database, apiService)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }

    @Test
    fun getDetailLeagueData() {
        Mockito.lenient().`when`(apiService.getDetailLeagueWithId(idleague))
            .thenReturn(Observable.just(dataDetail))
        with(viewModel) {
            getDetailLeagueData(idleague)
            setResultLeagueList(dataDetail)
            getDataLeague.observeForever(observerDetailLeague)
            Assert.assertEquals(dataDetail, getDataLeague.value)
        }
    }

    @Test
    fun getNextMatchData() {
        Mockito.lenient().`when`(apiService.getNextMatchWithId(idleague))
            .thenReturn(Observable.just(dataNext))
        with(viewModel) {
            viewModel.getNextMatchData(fakeDataNext.idEvent)
            listNextMatch(dataNext)
            getNextMatch.observeForever(observergetNextMatch)
            Assert.assertEquals(dataNext, getNextMatch.value)
        }
    }

    @Test
    fun getPreviousMatch() {
        Mockito.lenient().`when`(apiService.getPreviousMatchWithId(fakeDataNext.idEvent))
            .thenReturn(Observable.just(dataPrevious))
        with(viewModel) {
            getPreviousMatch(fakeDataPrevious.idEvent)
            listPreviousMatch(dataPrevious)
            getPreviousMatch.observeForever(observergetPreviousMatch)
            Assert.assertEquals(dataPrevious, getPreviousMatch.value)
        }
    }
}