package com.geekbrains.tests.presenter.details

import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times

class DetailsPresenterTest {

    private val presenter = DetailsPresenter()
    private val viewContract = mock(ViewDetailsContract::class.java)
    private var count = -123

    @Before
    fun setUp() {
        presenter.setCounter(count)
        presenter.onAttach(viewContract)
    }

    @Test
    fun test_onIncrement() {
        presenter.onIncrement()
        verify(viewContract, times(1))
            .setCount(++count)
    }

    @Test
    fun test_onDecrement() {
        presenter.onDecrement()
        verify(viewContract, times(1))
            .setCount(--count)
    }

    @Test
    fun test_onDetach() {
        presenter.onDetach()
        presenter.onDecrement()
        verify(viewContract, times(0))
            .setCount(10)
    }
}