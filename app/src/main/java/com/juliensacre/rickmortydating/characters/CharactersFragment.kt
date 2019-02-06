package com.juliensacre.rickmortydating.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.CharacterLite
import com.juliensacre.rickmortydating.util.*
import kotlinx.android.synthetic.main.fragment_characters_list.*
import kotlinx.android.synthetic.main.item_network_state.*

/**
 * With help of Ahmed Abd-Elmeged (https://github.com/Ahmed-Abdelmeged/PagingLibraryWithRxJava)
 */
class CharactersFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel : CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
        initSwipeToRefresh()

        (activity as AppCompatActivity).setSupportActionBar(toolbar_character_list)

        viewModel.charactersList.observe(this, Observer<PagedList<CharacterLite>> { adapter.submitList(it) })
        viewModel.getNetworkState().observe(this, Observer<NetworkState> { adapter.setNetworkState(it) })
    }

    /**
     * Warn the activity that character is selected
     */
    private fun characterClicked(characterId : Int){
        RxBus.publish(RxEvent.CharacterSelected(characterId))
    }

    private fun initAdapter(){
        adapter = CharactersAdapter({characterClicked(it!!.id)}){ viewModel.retry() }
        val gridLayoutManager = GridLayoutManager(context,calculateNumberOfColumn(activity!!))
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        viewModel.getRefreshState().observe(this, Observer { networkState ->
            if (adapter.currentList != null) {
                if (adapter.currentList!!.size > 0) {
                    swipeRefresh.isRefreshing = networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        })
        swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        network_state_include.visibility = View.VISIBLE
        //error message
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        swipeRefresh.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }
}