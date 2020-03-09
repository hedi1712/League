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

class LeagueListFragment : Fragment(), RecyclerViewListLeague.OnListLeaguePressedListener {

    private lateinit var binding: FragmentHomeClubBinding
    private lateinit var viewModel: LeagueListViewModel
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
        viewModel = ViewModelProviders.of(activity!!).get(LeagueListViewModel::class.java)
        binding = FragmentHomeClubBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideData()
        initRecyclerView()
        viewModel.getLeagueData()
        viewModel.getData.observe(this, Observer {
            it.let {
                showData()
                refreshList(it.leagues)
            }

        })
        onFindClick()
    }

    override fun onListLeaguePressed(leagueList: LeagueData, position: Int) {
        passingListData(leagueList.idLeague, leagueList.strLeague,leagueList.strBadge)
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

    fun passingListData(idLeague: String, leagueName: String, leagueUrl: String?) {
        goToDetailLeague(idLeague, leagueName, leagueUrl!!)
    }

    private fun goToDetailLeague(
        idLeague: String,
        leagueName: String,
        leagueUrl: String?
    ) {
        val action =
            LeagueListFragmentDirections.actionLaunchTodetailLeagueFragment(idLeague, leagueName,leagueUrl)
        findNavController().navigate(action)
    }

    fun onFindClick() {
        binding.findMatch.setOnClickListener {
            val action = LeagueListFragmentDirections.actionActionLaunchSearchView()
            findNavController().navigate(action)
        }
    }

    fun showData() {
        binding.showData = true
        binding.progressBar.visibility = View.INVISIBLE
    }

    fun hideData() {
        binding.showData = false
        binding.progressBar.visibility = View.VISIBLE
    }
}
