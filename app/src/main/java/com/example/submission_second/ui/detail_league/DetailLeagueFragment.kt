package com.example.submission_second.ui.detail_league

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.submission_second.databinding.FragmentDetailLeagueBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.submission_second.R
import com.example.submission_second.adapter.RecyclerViewNextmatchAdapter
import com.example.submission_second.adapter.RecyclerViewPreviousMatchAdapter
import com.example.submission_second.model.model.next_match.NextMatchResponse
import com.example.submission_second.model.model.previous_match.PreviousMatchResponse


class DetailLeagueFragment : Fragment(), RecyclerViewNextmatchAdapter.OnNextMatchPressed,
    RecyclerViewPreviousMatchAdapter.OnPreviousMatch {

    private var leagueId: String = ""
    private var leagueName: String = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailLeagueBinding
    private val adapterNextMatch = RecyclerViewNextmatchAdapter(listOf(), this)
    private val adapterPreviousMatch = RecyclerViewPreviousMatchAdapter(listOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(DetailViewModel::class.java)
        binding = FragmentDetailLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewNextMatch()
        initRecyclerViewPreviousMatch()
        arguments?.let {
            leagueId = DetailLeagueFragmentArgs.fromBundle(it).idLeague
            leagueName = DetailLeagueFragmentArgs.fromBundle(it).idTitleLeague
            (activity as AppCompatActivity).supportActionBar!!.title = leagueName
        }
        storeLeagueId(leagueId)
        viewModel.getDataLeague.observe(this, Observer {
            it?.let {
                binding.leagueInfo.text = it[0].strDescriptionEN
                loadImageGlide(it[0].strFanart1)
                showView()
            }
        })
        viewModel.getNextMatch.observe(this, Observer {
            it?.let {
                if (it.isNullOrEmpty()) {
                    binding.recyclerViewNextMatch.visibility = View.INVISIBLE
                } else {
                    passDataToAdapterNextMatch(it)
                }

            }
        })
        viewModel.getPreviousMatch.observe(this, Observer {
            it?.let {
                if (it.isNullOrEmpty()) {
                    binding.recyclerViewPreviousMatch.visibility = View.INVISIBLE
                } else {
                    passDataToAdapterPreviousMatch(it)
                }

            }
        })

    }

    private fun passDataToAdapterPreviousMatch(response: List<PreviousMatchResponse.PreviousMatchData>) {
        adapterPreviousMatch.refreshData(response)
    }

    private fun passDataToAdapterNextMatch(response: List<NextMatchResponse.Event>) {
        adapterNextMatch.refreshData(response)
    }

    private fun loadImageGlide(strFanart1: String) {
        if (strFanart1.isNullOrEmpty()) {
            binding.leagueImage.setImageResource(R.drawable.english_premier_league)
        } else {
            Glide.with(this).asBitmap().load(strFanart1)
                .apply {
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                }.into(binding.leagueImage)
        }
    }

    fun storeLeagueId(leagueId: String) {
        viewModel.getDetailLeagueData(leagueId)
        viewModel.getNextMatchData(leagueId)
        viewModel.getPreviousMatch(leagueId)
    }

    fun showView() {
        binding.detailLeagueLayout.visibility = View.VISIBLE
    }

    fun initRecyclerViewNextMatch() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewNextMatch.layoutManager = layoutManager
        binding.recyclerViewNextMatch.adapter = adapterNextMatch
    }

    fun initRecyclerViewPreviousMatch() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewPreviousMatch.layoutManager = layoutManager
        binding.recyclerViewPreviousMatch.adapter = adapterPreviousMatch
    }

    override fun onPressed(model: NextMatchResponse.Event, position: Int) {
        goToDetail(model.idEvent)
    }

    override fun onPressed(model: PreviousMatchResponse.PreviousMatchData, position: Int) {
        goToDetail(model.idEvent!!)
    }

    private fun goToDetail(leagueId: String) {
        val action = DetailLeagueFragmentDirections.actionLaunchDetailLeaguetoDetailMatch(leagueId)
        findNavController().navigate(action)
    }

}
