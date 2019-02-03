package com.juliensacre.rickmortydating.characters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juliensacre.rickmortydating.R
import com.juliensacre.rickmortydating.data.CharacterLite
import com.juliensacre.rickmortydating.util.NetworkState

/**
 * With help of Ahmed Abd-Elmeged (https://github.com/Ahmed-Abdelmeged/PagingLibraryWithRxJava)
 */
class CharactersAdapter(val onClick: (CharacterLite?) -> Unit, private val retryCallback: () -> Unit) : PagedListAdapter<CharacterLite,RecyclerView.ViewHolder>(CharacterDiffCallback) {

    private var networkState: NetworkState? = null

    //Wanted for the paging list
    // check the item to avoid duplication
    companion object {
        val CharacterDiffCallback = object : DiffUtil.ItemCallback<CharacterLite>() {
            override fun areItemsTheSame(oldItem: CharacterLite, newItem: CharacterLite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterLite, newItem: CharacterLite): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var items: List<CharacterLite> = emptyList()

    fun loadItem(newItems : List<CharacterLite>){
        items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_character -> CharactersViewHolder.create(parent)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_character -> {
                (holder as CharactersViewHolder).bindTo(getItem(position))
                holder.itemView.setOnClickListener { onClick(getItem(position)) }
            }
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_character
        }
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

}