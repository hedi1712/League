package com.example.submission_second.ui.detail_match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.submission_second.R
import com.example.submission_second.databinding.FragmentDetailMatchBinding
import com.example.submission_second.module.Api
import com.example.submission_second.util.ViewModelFactory
import com.example.submission_second.util.toddMMyyyy


class DetailMatchFragment : Fragment() {
    private lateinit var binding: FragmentDetailMatchBinding
    private lateinit var viewModel: DetailMatchViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private var leagueId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ViewModelFactory { DetailMatchViewModel(Api.retrofitService) }
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailMatchViewModel::class.java)
        binding = FragmentDetailMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            leagueId = DetailMatchFragmentArgs.fromBundle(arguments!!).leagueId
        }
        hideData()
        showLoading()
        storeLeagueId(leagueId)
        viewModel.getMatchDetail.observe(viewLifecycleOwner, Observer {
            it.let {
                showData()
                hideLoading()
                binding.leagueTitle.text = it[0].strLeague
                binding.dateMatch.text = it[0].dateEvent.toddMMyyyy()
                binding.timeMatch.text = it[0].strTime
                binding.homeClubMatch.text = it[0].strHomeTeam
                binding.awayClubMatch.text = it[0].strAwayTeam
                binding.homeScore.text = it[0].intHomeScore
                binding.awayScore.text = it[0].intAwayScore
                binding.homeScorer.text = it[0].strHomeGoalDetails
                binding.awayScorer.text = it[0].strAwayGoalDetails
                binding.homeGoalkeeper.text = it[0].strHomeLineupGoalkeeper
                binding.homeDefender.text = it[0].strHomeLineupDefense
                binding.homeMidfielder.text = it[0].strHomeLineupMidfield
                binding.homeForward.text = it[0].strHomeLineupForward
                binding.awayGoalKeeper.text = it[0].strAwayLineupGoalkeeper
                binding.awayDefender.text = it[0].strAwayLineupDefense
                binding.awayMidfielder.text = it[0].strAwayLineupMidfield
                binding.awayForward.text = it[0].strAwayLineupForward
                getBadgeHome(it[0].idHomeTeam)
                getBadgeAway(it[0].idAwayTeam)
            }
        })

        viewModel.getBadgeHome.observe(viewLifecycleOwner, Observer {
            it.let {
                showData()
                hideLoading()
                loadImageGlideHome(it[0].strTeamBadge)
            }
        })

        viewModel.getBadgeAway.observe(viewLifecycleOwner, Observer {
            it.let {
                showData()
                hideLoading()
                loadImageGlideAways(it[0].strTeamBadge)
            }
        })

    }

    private fun getBadgeHome(idAwayTeam: String) {
        viewModel.fetchBadgeHome(idAwayTeam)
    }

    private fun getBadgeAway(idHomeTeam: String) {
        viewModel.fetchBadgeAway(idHomeTeam)
    }

    private fun storeLeagueId(leagueId: String) {
        hideData()
        showLoading()
        viewModel.fetchDetailMatch(leagueId)
    }

    private fun showData() {
        binding.showData = true
    }

    private fun hideData() {
        binding.showData = false
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun loadImageGlideHome(url: String?) {
        if (url.isNullOrEmpty()) {
            binding.homeTeam.setImageResource(R.drawable.english_premier_league)
        } else {
            Glide.with(this).asBitmap().load(url)
                .apply {
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                }.into(binding.homeTeam)
        }
    }

    private fun loadImageGlideAways(url: String?) {
        if (url.isNullOrEmpty()) {
            binding.awayTeam.setImageResource(R.drawable.english_premier_league)
        } else {
            Glide.with(this).asBitmap().load(url)
                .apply {
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                }.into(binding.awayTeam)
        }
    }
}
