package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.GitHubRepositoryCallback
import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val repository: RepositoryContract
) : PresenterSearchContract, GitHubRepositoryCallback {

    private var viewContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract?.let { view ->
            view.displayLoading(true)
            repository.searchGithub(searchQuery, this)
        }
    }

    override fun onAttach(viewContract: ViewContract) {
        this.viewContract = viewContract as ViewSearchContract
    }

    override fun onDetach() {
        viewContract = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.let { view ->
            view.displayLoading(false)
            if (response != null && response.isSuccessful) {
                val searchResponse = response.body()
                val searchResults = searchResponse?.searchResults
                val totalCount = searchResponse?.totalCount
                if (searchResults != null && totalCount != null) {
                    view.displaySearchResults(
                        searchResults,
                        totalCount
                    )
                } else {
                    view.displayError("Search results or total count are null")
                }
            } else {
                view.displayError("Response is null or unsuccessful")
            }
        }

    }

    override fun handleGitHubError() {
        viewContract?.let { view ->
            view.displayLoading(false)
            view.displayError()
        }

    }
}
