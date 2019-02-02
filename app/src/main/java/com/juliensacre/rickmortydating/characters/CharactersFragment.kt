package com.juliensacre.rickmortydating.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.util.NetworkState
import com.juliensacre.rickmortydating.util.RxBus
import com.juliensacre.rickmortydating.util.RxEvent
import com.juliensacre.rickmortydating.util.Status
import kotlinx.android.synthetic.main.fragment_characters_list.*
import kotlinx.android.synthetic.main.item_network_state.*
import timber.log.Timber

/**
 * With help of Ahmed Abd-Elmeged (https://github.com/Ahmed-Abdelmeged/PagingLibraryWithRxJava)
 */
class CharactersFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel : CharactersViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val adapter = CharactersAdapter({characterClicked(it!!.id)}){
            viewModel.retry() //similar to click listener
        }
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = adapter

        viewModel.charactersList.observe(this, Observer { adapter.submitList(it) })
        viewModel.getNetworkState().observe(this, Observer<NetworkState> { adapter.setNetworkState(it) })
    }

    private fun characterClicked(characterId : Int){
        Timber.i("click on item id: $characterId")
        RxBus.publish(RxEvent.CharacterSelected(characterId))
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        viewModel.getRefreshState().observe(this, Observer { networkState ->
            if (charactersAdapter.currentList != null) {
                if (charactersAdapter.currentList!!.size > 0) {
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