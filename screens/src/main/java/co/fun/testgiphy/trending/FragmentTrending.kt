package co.`fun`.testgiphy.trending

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.`fun`.testgiphy.ErrorState
import co.`fun`.testgiphy.ShowTrendingState
import co.`fun`.testgiphy.ViewState
import co.`fun`.testgiphy.data.Loading
import co.`fun`.testgiphy.BaseFragment
import co.`fun`.testgiphy.PaginationScrollListener
import co.`fun`.testgiphy.injectViewModel
import co.`fun`.testgiphy.ErrorHandlerDelegate
import co.`fun`.testgiphy.Navigation
import co.`fun`.testgiphy.ResourceUtils
import co.`fun`.testgiphy.SnackbarConfig
import co.`fun`.testgiphy.search.R
import co.`fun`.testgiphy.view.TrendingViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_trending.*
import javax.inject.Inject


class FragmentTrending : BaseFragment() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var errorHandlerDelegate: ErrorHandlerDelegate

    @Inject
    lateinit var resourceUtils: ResourceUtils

    override var layoutId: Int =
        R.layout.fragment_trending

    private lateinit var trendingViewModel: TrendingViewModel

    private lateinit var trendingAdapter: TrendingAdapter

    private var isLoading = false

    init {

        onAttach {
            trendingViewModel = injectViewModel()
        }

        onViewCreated {
            activity?.let { ctx ->
                tbFragmentTrending.run {
                    background = resourceUtils.getPrimaryDarkColor().run(::ColorDrawable)
                    inflateMenu(R.menu.menu_trending)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.menu_trending_search -> {
                                navigation.goToSearchFragmentFrom(this@FragmentTrending)
                                true
                            }
                            else -> super.onOptionsItemSelected(menuItem)
                        }
                    }
                }

                val linearLayoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                val paginationListener = object : PaginationScrollListener(linearLayoutManager) {
                    override fun loadMoreItems() {
                        trendingAdapter.addLoading()
                        val offset = trendingAdapter.itemCount
                        fetchItems(offset)
                    }

                    override fun isLoading(): Boolean = isLoading
                }
                rvFragmentTrending.run {
                    trendingAdapter = TrendingAdapter()
                        .apply {
                        addSingleItem(Loading())
                    }
                    layoutManager = linearLayoutManager
                    itemAnimator = SlideInUpAnimator()
                    adapter = trendingAdapter
                    addOnScrollListener(paginationListener)
                }
                fetchItems()
            }
        }
    }

    private fun fetchItems(offset: Int = 0) {
        isLoading = true
        trendingViewModel.getItems(offset)
            .observe(this@FragmentTrending, trendingFragmentObserver)
    }

    private val trendingFragmentObserver by lazy {
        Observer<ViewState> { state ->
            when (state) {
                is ErrorState -> handleError(state.throwable)
                is ShowTrendingState -> addItemsToRecyclerView(state)
                else -> throw IllegalArgumentException("unknown state: ${state::class.java.simpleName}")
            }
        }
    }

    private fun addItemsToRecyclerView(state: ShowTrendingState) {
        trendingAdapter.removeLoading()
        isLoading = false
        trendingAdapter.add(state.items)
    }

    private fun handleError(throwable: Throwable) =
        errorHandlerDelegate.showErrorMessage(
            rootFragmentTrending,
            SnackbarConfig(
                throwable = throwable,
                actionTitle = resourceUtils.getString(android.R.string.ok)
            )
        )

}