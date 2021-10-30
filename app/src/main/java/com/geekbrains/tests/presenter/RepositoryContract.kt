package com.geekbrains.tests.presenter

import com.geekbrains.tests.repository.GitHubRepositoryCallback

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: GitHubRepositoryCallback
    )

}