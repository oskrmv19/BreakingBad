package com.oskr19.breakingbad.modules.characters.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.oskr19.breakingbad.R
import com.oskr19.breakingbad.core.presentation.loadImage
import com.oskr19.breakingbad.databinding.CharacterItemBinding
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), Filterable {

    private val characters = arrayListOf<CharacterDTO>()
    private val filteredList: MutableList<CharacterDTO> = mutableListOf()
    lateinit var listener: CharacterItemListener

    fun setData(list: List<CharacterDTO>) {
        characters.clear()
        characters.addAll(list)
        notifyDataSetChanged()
        filter.filter("")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = filteredList.size

    fun getItem(position: Int) = filteredList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.view.characterItemNameTxt.text = character.name
        holder.view.characterItemNicknameTxt.text = character.nickname
        holder.view.characterItemImg.loadImage(character.img)

        val icon = if (character.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        holder.view.characterItemFavoriteImg.background = ResourcesCompat.getDrawable(holder.view.root.context.resources, icon, null)

        holder.view.characterItemFavoriteImg.setOnClickListener {
            listener.setFavorite(position, character.isFavorite)
        }

        holder.view.characterItemLayout.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    inner class ViewHolder(var view: CharacterItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun getFilter() = characterFilter

    private val characterFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<CharacterDTO> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(characters)
            } else {
                val filterPattern = constraint.toString().lowercase()
                for (item in characters) {
                    if (item.name.lowercase().contains(filterPattern)
                        || item.nickname.lowercase().contains(filterPattern)
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredList.clear()
            filteredList.addAll(results.values as List<CharacterDTO>)
            notifyDataSetChanged()
        }
    }
}