package com.gkhn.imagesearch.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkhn.imagesearch.R
import com.gkhn.imagesearch.databinding.FragmentMainBinding
import com.gkhn.imagesearch.view.adapter.ImageLoadStateAdapter
import com.gkhn.imagesearch.view.adapter.ImagePagingAdapter
import com.gkhn.imagesearch.view.viewModel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()

    @Inject
    lateinit var imagePagingAdapter: ImagePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view.context)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                val searchItem = menu.findItem(R.id.search_action)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        setSearchQuery(p0.toString())
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        return true
                    }
                })

                searchView.setOnCloseListener {
                    if (viewModel.lastQuery != ""){
                        setSearchQuery()
                    }
                    false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        subscribeToObservers()
        initOnClick()
    }

    private fun initRecyclerView(context: Context){
        binding.apply {
            rcvMain.setHasFixedSize(true)
            rcvMain.layoutManager = LinearLayoutManager(context)
            rcvMain.adapter = imagePagingAdapter.withLoadStateFooter(
                ImageLoadStateAdapter { imagePagingAdapter.retry() }
            )
        }

        imagePagingAdapter.addLoadStateListener {
            binding.apply {
                progressBarMain.isVisible = it.source.refresh is LoadState.Loading
                rcvMain.isVisible = it.source.refresh is LoadState.NotLoading
                txtErrorMessageMain.isVisible = it.source.refresh is LoadState.Error
                btnRetryMain.isVisible = it.source.refresh is LoadState.Error
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.images.observe(viewLifecycleOwner, {
            imagePagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setSearchQuery()
    }

    private fun setSearchQuery(queryString: String = ""){
        binding.rcvMain.scrollToPosition(0)
        viewModel.searchImage(queryString)
    }

    private fun initOnClick() {
        binding.btnRetryMain.setOnClickListener{
            imagePagingAdapter.retry()
        }
    }
}