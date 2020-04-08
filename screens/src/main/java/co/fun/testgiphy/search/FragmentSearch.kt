@file:Suppress("MoveLambdaOutsideParentheses")

package co.`fun`.testgiphy.search

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.`fun`.testgiphy.*
import co.`fun`.testgiphy.view.EmptyItem
import co.`fun`.testgiphy.view.SearchViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


class FragmentSearch : BaseFragment() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var errorHandlerDelegate: ErrorHandlerDelegate

    @Inject
    lateinit var resourceUtils: ResourceUtils

    override var layoutId: Int =
        R.layout.fragment_search

    private val hintString by lazy { resourceUtils.getString(R.string.fragment_search_edittext_hint) }

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter

    init {
        onAttach {
            searchViewModel = injectViewModel()
        }

        onViewCreated {
            activity?.let { ctx ->
                tbFragmentSearch.run {
                    background = resourceUtils.getPrimaryDarkColor().run(::ColorDrawable)
                    navigationIcon = resourceUtils.getDrawable(R.drawable.ic_arrow_back_white_24dp)
                    setNavigationOnClickListener {
                        getSystemService(ctx, InputMethodManager::class.java)
                            ?.hideSoftInputFromWindow(etSearchToolbar.windowToken, 0)
                        navigation.goBackToTrending(this@FragmentSearch)
                        if (etSearchToolbar.text.isNullOrBlank()) etSearchToolbar.hint = hintString
                    }
                }

                etSearchToolbar.run {
                    SimpleTextWatcher(::observeQuery)
                        .run(this::addTextChangedListener)
                    onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                        v as EditText
                        if (hasFocus && v.text.toString().isNotEmpty()) {
                            v.hint = ""
                        } else {
                            v.hint = hintString
                        }
                    }
                }

                rvFragmentSearch.run {
                    searchAdapter =
                        SearchAdapter()
                    layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                    itemAnimator = SlideInUpAnimator()
                    adapter = searchAdapter
                }
            }
        }
    }

    private fun observeQuery(query: CharSequence) =
        query
            .takeIf { query.isNotEmpty() && it.length > 3 }
            ?.let { sequence ->
                searchAdapter.addLoading()
                searchViewModel.getSearchResult(
                    sequence,
                    offset = searchAdapter.itemCount
                )
                    .observe(this@FragmentSearch, Observer<ViewState> { state ->
                        when (state) {
                            is ErrorState -> handleError(state.throwable)
                            is EmptyState -> searchAdapter.replace(listOf(EmptyItem()))
                            is ShowSearchResultsState -> searchAdapter.replace(state.items)
                            else -> throw IllegalArgumentException("unknown state: ${state::class.java.simpleName}")
                        }
                    })
            }
            ?: run { if (query.isEmpty()) etSearchToolbar.hint = hintString }

    private fun handleError(throwable: Throwable) =
        errorHandlerDelegate.showErrorMessage(
            rootFragmentSearch,
            SnackbarConfig(
                throwable = throwable,
                actionTitle = resourceUtils.getString(android.R.string.ok)
            )
        )

}