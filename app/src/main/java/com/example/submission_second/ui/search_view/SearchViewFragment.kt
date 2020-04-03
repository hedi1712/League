package com.example.submission_second.ui.search_view


import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.submission_second.R
import com.example.submission_second.adapter.RecyclerSearch
import com.example.submission_second.databinding.FragmentSearchViewBinding
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.search_match.SearchMatchData
import com.example.submission_second.util.ViewModelFactory


class SearchViewFragment : Fragment(), RecyclerSearch.Onclick {

    private lateinit var searchView: SearchView
    private lateinit var binding: FragmentSearchViewBinding
    private lateinit var viewModel: SearchViewModelFragment
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    val adapter = RecyclerSearch(listOf(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.string_search)
        initRecyclerView()
        viewModel.getSearchList.observe(viewLifecycleOwner, Observer {
            it.let {
                hideLoading()
                sentDataToAdapter(it)
            }
        })
        viewModel.getMessage.observe(viewLifecycleOwner, Observer {
            it.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = DicodingDatabase.buildDatabase(activity!!.applicationContext)
        viewModelFactory = ViewModelFactory { SearchViewModelFragment(database!!) }
        binding = FragmentSearchViewBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModelFragment::class.java)
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
                    binding.rvSearchView.visibility = GONE
                    binding.notFound.visibility = VISIBLE
                }
                showLoading()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.rvSearchView.visibility = GONE
                    binding.notFound.visibility = VISIBLE
                    return false
                }
                showLoading()
                binding.notFound.visibility = GONE
                binding.rvSearchView.visibility = VISIBLE
                sendQueryToViewModel(query)
                return false
            }
        })
    }

    private fun sentDataToAdapter(response: List<SearchMatchData>) {
        adapter.refreshData(response)
    }

    fun initRecyclerView() {
        binding.rvSearchView.adapter = adapter
    }

    private fun sendQueryToViewModel(query: String?) {
        viewModel.sendQueryToApi(query)
    }


    private fun passingDataToDetail(leagueId: String) {
        val binding =
            SearchViewFragmentDirections.actionLaunchSearchViewToDetailMatchFragment(leagueId)
        findNavController().navigate(binding)
    }

    fun showLoading() {
        binding.progressBar.visibility = VISIBLE
    }

    fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onClick(searchData: SearchMatchData, position: Int) {
        passingDataToDetail(searchData.idEvent)
    }

    override fun favoriteToDatabase(searchData: SearchMatchData) {
        viewModel.storeToDatabase(searchData)
    }
}
