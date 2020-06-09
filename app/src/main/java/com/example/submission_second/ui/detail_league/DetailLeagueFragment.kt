package com.example.submission_second.ui.detail_league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.submission_second.R
import com.example.submission_second.adapter.RecyclerViewNextmatchAdapter
import com.example.submission_second.adapter.RecyclerViewPreviousMatchAdapter
import com.example.submission_second.adapter.TabLayoutDetailLeagueAdapter
import com.example.submission_second.databinding.FragmentDetailLeagueBinding
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.next_match.Event
import com.example.submission_second.model.model.previous_match.PreviousMatchData
import com.example.submission_second.module.Api
import com.example.submission_second.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout


class DetailLeagueFragment : Fragment(), RecyclerViewNextmatchAdapter.OnNextMatchPressed,
    RecyclerViewPreviousMatchAdapter.OnPreviousMatch {

    private var leagueId: String = ""
    private var leagueName: String = ""
    private lateinit var viewModel: DetailLeagueViewModel
    private lateinit var binding: FragmentDetailLeagueBinding
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapterNextMatch = RecyclerViewNextmatchAdapter(listOf(), this)
    private val adapterPreviousMatch = RecyclerViewPreviousMatchAdapter(listOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = DicodingDatabase.buildDatabase(activity!!.applicationContext)
        viewModelFactory =
            ViewModelFactory { DetailLeagueViewModel(database!!, Api.retrofitService) }
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailLeagueViewModel::class.java)
        binding = FragmentDetailLeagueBinding.inflate(inflater, container, false)
        viewModel.getMessage.observe(viewLifecycleOwner, Observer {
            it.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideData()
        initRecyclerViewNextMatch()
        initRecyclerViewPreviousMatch()
        arguments?.let {
            leagueId = DetailLeagueFragmentArgs.fromBundle(it).idLeague
            leagueName = DetailLeagueFragmentArgs.fromBundle(it).idTitleLeague
            (activity as AppCompatActivity).supportActionBar!!.title = leagueName
        }
        storeLeagueId(leagueId)
        viewModel.getDataLeague.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.leagueInfo.text = it.leagues[0].strDescriptionEN
                loadImageGlide(it.leagues[0].strBadge)
            }
        })
        viewModel.getNextMatch.observe(viewLifecycleOwner, Observer {
            it?.let {
                showData()
                if (it.events.isNullOrEmpty()) {
                    binding.recyclerViewNextMatch.visibility = View.INVISIBLE
                } else {
                    passDataToAdapterNextMatch(it.events)
                }

            }
        })
        viewModel.getPreviousMatch.observe(viewLifecycleOwner, Observer {
            it?.let {
                showData()
                if (it.events.isNullOrEmpty()) {
                    binding.recyclerViewPreviousMatch.visibility = View.INVISIBLE
                } else {
                    passDataToAdapterPreviousMatch(it.events)
                }

            }
        })
    }

    private fun loadImageGlide(strFanart1: String?) {
        if (strFanart1.isNullOrEmpty()) {
            binding.leagueImage.setImageResource(R.drawable.english_premier_league)
        } else {
            Glide.with(this).asBitmap().load(strFanart1)
                .apply {
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                }.into(binding.leagueImage)
        }
    }


    private fun passDataToAdapterPreviousMatch(response: List<PreviousMatchData>) {
        adapterPreviousMatch.refreshData(response)
    }

    private fun passDataToAdapterNextMatch(response: List<Event>) {
        adapterNextMatch.refreshData(response)
    }

    private fun storeLeagueId(leagueId: String) {
        viewModel.getDetailLeagueData(leagueId)
        viewModel.getNextMatchData(leagueId)
        viewModel.getPreviousMatch(leagueId)
    }

    private fun initRecyclerViewNextMatch() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewNextMatch.layoutManager = layoutManager
        binding.recyclerViewNextMatch.adapter = adapterNextMatch
    }

    private fun initRecyclerViewPreviousMatch() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewPreviousMatch.layoutManager = layoutManager
        binding.recyclerViewPreviousMatch.adapter = adapterPreviousMatch
    }

    override fun onPressed(model: Event, position: Int) {
        goToDetail(model.idEvent)
    }

    override fun favoriteTeam(model: Event) {
        viewModel.storeToDatabaseNextMatch(model)
    }

    override fun onPressed(model: PreviousMatchData, position: Int) {
        goToDetail(model.idEvent)
    }

    override fun onFavorite(model: PreviousMatchData) {
        viewModel.storeToDatabasePreviousMatch(model)
    }

    private fun goToDetail(leagueId: String) {
        val action = DetailLeagueFragmentDirections.actionLaunchDetailMatchFragment(leagueId)
        findNavController().navigate(action)
    }

    private fun showData() {
        binding.showData = true
        binding.progressBar.visibility = View.GONE
    }

    private fun hideData() {
        binding.showData = false
        binding.progressBar.visibility = View.VISIBLE
    }


}
