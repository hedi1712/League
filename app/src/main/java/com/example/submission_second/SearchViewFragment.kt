package com.example.submission_second


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 */
class SearchViewFragment : Fragment() {

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search_view, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
    }

}
