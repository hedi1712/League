package com.example.submission_second.ui.favorite.favorite_team

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
import com.example.submission_second.adapter.RecylerViewTeamFavorite
import com.example.submission_second.databinding.FragmentTeamFavoriteBinding
import com.example.submission_second.db.entity.EntityTeam
import com.example.submission_second.ui.favorite.FavoriteFragmentDirections
import com.example.submission_second.ui.favorite.FavoriteViewModel
import com.example.submission_second.util.ViewModelFactory

class TeamFavorite : Fragment(), RecylerViewTeamFavorite.OnclickDelete {

    private lateinit var binding: FragmentTeamFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    val adapter = RecylerViewTeamFavorite(listOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ViewModelFactory { FavoriteViewModel(requireActivity()) }
        binding = FragmentTeamFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecylerView()
        loadFromDatabase()
        loadMessage()
    }

    private fun loadFromDatabase() {
        viewModel.getTeamFavorite()
        viewModel.getDataTeamFavorite.observe(viewLifecycleOwner, Observer {
            sentDataToAdapter(it)
        })
    }

    private fun sentDataToAdapter(it: List<EntityTeam>) {
        adapter.refreshData(it)
    }

    private fun initRecylerView() {
        binding.recyclerview.adapter = adapter
    }

    override fun onDetail(model: EntityTeam, position: Int) {
        actionToDetail(model.idTeam)
    }

    override fun onDelete(model: EntityTeam) {
        viewModel.deleteTeamFavorite(model.idTeam)
    }

    fun actionToDetail(idTeam: String) {
        val action = FavoriteFragmentDirections.actionFavoriteFragment2ToTeamDetail(idTeam)
        findNavController().navigate(action)
    }

    private fun loadMessage() {
        viewModel.getMessage.observe(
            viewLifecycleOwner,
            Observer { Toast.makeText(context, it, Toast.LENGTH_SHORT) })
    }
}
