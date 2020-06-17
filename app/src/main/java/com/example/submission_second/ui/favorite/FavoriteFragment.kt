package com.example.submission_second.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.submission_second.adapter.TabLayoutDetailLeagueAdapter
import com.example.submission_second.databinding.FragmentFavoriteBinding
import com.example.submission_second.ui.favorite.favorite_match.FavoriteMatch
import com.example.submission_second.ui.favorite.favorite_team.TeamFavorite
import com.google.android.material.tabs.TabLayout


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var tabLayoutDetailLeagueAdapter: TabLayoutDetailLeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        initPagerAndTab()
    }

    private fun initPagerAndTab() {
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        tabLayoutDetailLeagueAdapter = TabLayoutDetailLeagueAdapter(childFragmentManager)
        tabLayoutDetailLeagueAdapter.addFragment(FavoriteMatch(), "match")
        tabLayoutDetailLeagueAdapter.addFragment(TeamFavorite(), "team")
        viewPager.adapter = tabLayoutDetailLeagueAdapter
        tabLayout.setupWithViewPager(viewPager, true)
    }
}
