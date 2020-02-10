package com.example.submission_second.ui.league_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_second.adapter.RecyclerViewListLeague
import com.example.submission_second.databinding.FragmentHomeClubBinding
import com.example.submission_second.model.model.league_list.LeagueData

class LeagueFragment : Fragment(), RecyclerViewListLeague.OnListLeaguePressedListener {

    private lateinit var binding: FragmentHomeClubBinding
    private lateinit var viewModel: LeagueViewModel
    private val adapter = RecyclerViewListLeague(listOf(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(LeagueViewModel::class.java)
        binding = FragmentHomeClubBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getLeagueData()
        viewModel.getData.observe(this, Observer {
            it.let {
                refreshList(it.leagues)
            }

        })
        onFindClick()
    }

    override fun onListLeaguePressed(leagueList: LeagueData, position: Int) {
        passingListData(leagueList.idLeague, leagueList.strLeague)
    }

    fun refreshList(leagues: List<LeagueData>) {
        adapter.refreshData(leagues)
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration =
            DividerItemDecoration(binding.rvClub.context, layoutManager.orientation)
        binding.rvClub.layoutManager = layoutManager
        binding.rvClub.adapter = adapter
        binding.rvClub.addItemDecoration(dividerItemDecoration)
    }

    fun passingListData(idLeague: String, leagueName: String) {
        goToDetailLeague(idLeague, leagueName)
    }

    private fun goToDetailLeague(idLeague: String, leagueName: String) {
        val action =
            LeagueFragmentDirections.actionLaunchTodetailLeagueFragment(idLeague, leagueName)
        findNavController().navigate(action)
    }

    fun onFindClick() {
        binding.findMatch.setOnClickListener {
            val action = LeagueFragmentDirections.actionActionLaunchSearchView()
            findNavController().navigate(action)
        }
    }
}
