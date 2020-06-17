package com.example.submission_second.ui.search_team

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.submission_second.R
import com.example.submission_second.adapter.RecyclerViewSearchTeam
import com.example.submission_second.databinding.FragmentSearchTeamBinding
import com.example.submission_second.model.model.search_team.Team
import com.example.submission_second.module.Api
import com.example.submission_second.util.ViewModelFactory


class SearchTeam : Fragment(), RecyclerViewSearchTeam.OnClickTeam {

    private lateinit var searchView: SearchView
    private lateinit var binding: FragmentSearchTeamBinding
    private lateinit var viewModel: SearchTeamViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter = RecyclerViewSearchTeam(listOf(), this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ViewModelFactory { SearchTeamViewModel(Api.retrofitService) }
        binding = FragmentSearchTeamBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchTeamViewModel::class.java)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_view_bar, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_View)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.rvSearchViewTeam.visibility = View.GONE
                    binding.notFoundTeam.visibility = View.VISIBLE
                }
                showLoading()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.rvSearchViewTeam.visibility = View.GONE
                    binding.notFoundTeam.visibility = View.VISIBLE
                    return false
                }
                showLoading()
                binding.notFoundTeam.visibility = View.GONE
                binding.rvSearchViewTeam.visibility = View.VISIBLE
                sendQueryToViewModel(query)
                return false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.string_search)
        initRecyclerView()
        viewModel.getSearchList.observe(viewLifecycleOwner, Observer {
            it.let {
                hideLoading()
                sentDataToAdapter(it.teams)
            }
        })
    }

    private fun sentDataToAdapter(teams: List<Team>) {
        adapter.refreshData(teams)
    }

    private fun initRecyclerView() {
        binding.rvSearchViewTeam.adapter = adapter
    }

    private fun sendQueryToViewModel(query: String?) {
        viewModel.sendQueryToApi(query)
    }

    fun showLoading() {
        binding.progressBarTeam.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.progressBarTeam.visibility = View.INVISIBLE
    }

    override fun OnPressed(model: Team, position: Int) {
        navigateTodetail(model.idTeam)
    }

    private fun navigateTodetail(idteam : String) {
        val action = SearchTeamDirections.actionSearchTeamToTeamDetail(idteam)
        findNavController().navigate(action)
    }

}
