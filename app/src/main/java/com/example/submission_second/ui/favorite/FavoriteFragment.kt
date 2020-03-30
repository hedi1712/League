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
import com.example.submission_second.adapter.RecyclerViewFavorite
import com.example.submission_second.databinding.FragmentFavoriteBinding
import com.example.submission_second.db.entity.EntityFavorite
import com.example.submission_second.util.ViewModelFactory


class FavoriteFragment : Fragment(), RecyclerViewFavorite.OnItemPressed {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    val adapter = RecyclerViewFavorite(listOf(), this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ViewModelFactory { FavoriteViewModel(activity!!) }
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        loadDataDatabase()
        loadMessage()

    }

    private fun initRecycler() {
        binding.rvFavorite.adapter = adapter
    }

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
