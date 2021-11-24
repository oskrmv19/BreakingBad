package com.oskr19.breakingbad.modules.characters.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oskr19.breakingbad.R
import com.oskr19.breakingbad.core.Constants.EMTPY
import com.oskr19.breakingbad.core.presentation.BaseFragment
import com.oskr19.breakingbad.core.presentation.Event
import com.oskr19.breakingbad.databinding.FragmentCharactersListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersListFragment : BaseFragment(), CharacterItemListener {

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<CharactersViewModel>()

    @Inject
    internal lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        setHasOptionsMenu(true)
        setTitle(getString(R.string.title))
        setSubTitle(EMTPY)

        adapter.listener = this
        binding.charactersRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CharactersListFragment.adapter
        }
        binding.swipeRefresh.setOnRefreshListener(::fetch)
    }

    private fun observeViewModel() {
        viewModel.status.observe(viewLifecycleOwner, ::observeStatus)
        viewModel.characters.observe(viewLifecycleOwner, { value ->
            value?.let {
                adapter.setData(it)
                binding.swipeRefresh.isRefreshing = false
                setSubTitle(getString(R.string.subtitle).format(it.size))
            }
        })
        viewModel.fetchFromLocal()
    }

    private fun fetch() {
        viewModel.fetchFromRemote(adapter.itemCount)
    }

    override fun onItemClick(position: Int) {
        val action = CharactersListFragmentDirections.listToDetailAction(adapter.getItem(position))
        binding.charactersRecycler.findNavController().navigate(action)
    }

    override fun setFavorite(position: Int, isFavorite: Boolean) {
        viewModel.setFavorite(adapter.getItem(position).charId, isFavorite)
        adapter.getItem(position).isFavorite = isFavorite.not()
        adapter.notifyItemChanged(position)
    }

    override fun onLoading() {
        super.onLoading()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onFinished() {
        super.onFinished()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDisconnected() {
        super.onDisconnected()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onError(event: Event?) {
        super.onError(event)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.action_search)
        val sv = item.actionView as SearchView
        val txtSearch = sv.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        txtSearch.setHintTextColor(Color.LTGRAY)
        txtSearch.hint = getString(R.string.search_title)

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                _binding?.swipeRefresh?.isEnabled = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                _binding?.swipeRefresh?.isEnabled = true
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }
}