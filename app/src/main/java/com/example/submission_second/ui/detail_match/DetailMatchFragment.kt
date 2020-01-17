package com.example.submission_second.ui.detail_match


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_second.adapter.RecyclerViewDetailMatch
import com.example.submission_second.databinding.FragmentDetailMatchBinding
import com.example.submission_second.model.model.detail_match.DetailMatchResponse
import com.example.submission_second.ui.next_match.NextMatchFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class DetailMatchFragment : Fragment() {

    private lateinit var binding: FragmentDetailMatchBinding
    private lateinit var viewModel: DetailMatchViewModel
    private var leagueId: String = ""
    private var adapter = RecyclerViewDetailMatch(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(DetailMatchViewModel::class.java)
        binding = FragmentDetailMatchBinding.inflate(inflater, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            leagueId = NextMatchFragmentArgs.fromBundle(it).leagueId
        }
        storeLeagueId(leagueId)
        viewModel.getMatchDetail.observe(this, Observer {
            it.let {
                refreshData(it[0])
            }
        })
    }

    private fun refreshData(response: DetailMatchResponse.DetailMatchData) {

    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, true)
        binding.recyclerViewDetailMatch.layoutManager = layoutManager
        binding.recyclerViewDetailMatch.adapter = adapter
    }

    fun storeLeagueId(leagueId: String) {
        viewModel.fetchDetailMatch(leagueId)
    }

}
