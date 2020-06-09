package com.example.submission_second.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.submission_second.adapter.RecyclerViewFavorite
import com.example.submission_second.adapter.TabLayoutDetailLeagueAdapter
import com.example.submission_second.databinding.FragmentFavoriteBinding
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.ui.favorite.favorite_match.FavoriteMatch
import com.example.submission_second.ui.favorite.favorite_team.TeamFavorite
import com.example.submission_second.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout


class FavoriteFragment : Fragment(), RecyclerViewFavorite.OnItemPressed {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    val adapter = RecyclerViewFavorite(listOf(), this)
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout : TabLayout
    private lateinit var tabLayoutDetailLeagueAdapter: TabLayoutDetailLeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ViewModelFactory { FavoriteViewModel(requireActivity()) }
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        initPagerAndTab();
//        initRecycler()
//        loadDataDatabase()
//        loadMessage()
    }

    private fun initPagerAndTab() {
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        tabLayoutDetailLeagueAdapter = TabLayoutDetailLeagueAdapter(childFragmentManager)
        tabLayoutDetailLeagueAdapter.addFragment(FavoriteMatch(),"match")
        tabLayoutDetailLeagueAdapter.addFragment(TeamFavorite(),"team")
        viewPager.adapter = tabLayoutDetailLeagueAdapter
        tabLayout.setupWithViewPager(viewPager,true)
    }


//    private fun initRecycler() {
//        binding.rvFavorite.adapter = adapter
//    }

    private fun loadMessage() {
        viewModel.getMessage.observe(
            viewLifecycleOwner,
            Observer { Toast.makeText(context, it, Toast.LENGTH_SHORT) })
    }

    private fun loadDataDatabase() {
        viewModel.getAllFavorite()
        viewModel.getDataFavorite.observe(viewLifecycleOwner, Observer {
            sentDataToAdapter(it)
        })
    }

    override fun onItemClick(model: EntityFavorite, position: Int) {
        navigateToDetail(model.idEvent)
    }

    private fun navigateToDetail(idEvent: String) {
        val action =
            FavoriteFragmentDirections.actionFavoriteFragment2ToDetailMatchFragment(idEvent)
        findNavController().navigate(action)
    }

    override fun deleteData(model: EntityFavorite) {
        viewModel.deleteFavorite(model.idEvent)
    }

    private fun sentDataToAdapter(response: List<EntityFavorite>) {
        adapter.refreshData(response)
    }
}
