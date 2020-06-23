package com.example.submission_second.ui.team_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.submission_second.databinding.FragmentTeamDetailBinding
import com.example.submission_second.db.DicodingDatabase
import com.example.submission_second.model.model.detail_team.Team
import com.example.submission_second.module.Api
import com.example.submission_second.util.ViewModelFactory


class TeamDetail : Fragment() {

    private var teamId: String? = ""
    private lateinit var viewModel: TeamDetailViewmodel
    private lateinit var binding: FragmentTeamDetailBinding
    private lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = DicodingDatabase.buildDatabase(requireActivity().applicationContext)
        viewModelFactory = ViewModelFactory { TeamDetailViewmodel(database!!, Api.retrofitService) }
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TeamDetailViewmodel::class.java)
        binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            teamId = TeamDetailArgs.fromBundle(it).leagueId
        }
        storeToViewModel(teamId)

        viewModel.getDetailTeam.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.model = it.teams[0]
                onClick(it.teams[0])
            }
        })

        viewModel.getMessage.observe(viewLifecycleOwner, Observer {
            it.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onClick(team: Team) {
        binding.favorite.setOnClickListener {
            viewModel.storeToDatabaseNextTeam(team)
        }
    }

    private fun storeToViewModel(teamId: String?) {
        viewModel.getListTeamData(teamId)
    }


}
