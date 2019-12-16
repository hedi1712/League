package com.example.submission_second.ui.detail_league

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.submission_second.databinding.FragmentDetailLeagueBinding
import androidx.appcompat.app.AppCompatActivity




class DetailLeagueFragment : Fragment() {

    private var leagueId: String = ""
    private var leagueName: String = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailLeagueBinding

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
        arguments?.let {
            leagueId = DetailLeagueFragmentArgs.fromBundle(it).idLeague
            leagueName = DetailLeagueFragmentArgs.fromBundle(it).idTitleLeague
            (activity as AppCompatActivity).supportActionBar!!.setTitle(leagueName)
        }

        storeLeagueId(leagueId)
        viewModel.getData.observe(this, Observer {
            it?.let {
                binding.leagueInfo.text = it[0].strDescriptionEN
                loadImageGlide(it[0].strFanart1)
            }
        })

    }

    @SuppressLint("CheckResult")
    private fun loadImageGlide(strFanart1: String) {
        Glide.with(this).asBitmap().load(strFanart1).into(binding.leagueImage)
    }

    fun storeLeagueId(leagueId: String) {
        viewModel.getDetailLeagueData(leagueId)
    }

}