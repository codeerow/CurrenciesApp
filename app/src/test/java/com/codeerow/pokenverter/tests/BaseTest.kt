package com.codeerow.pokenverter.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codeerow.pokenverter.core.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito


@RunWith(JUnit4::class)
abstract class BaseTest : KoinTest {

    @get:Rule
    val reactivexRule = RxImmediateSchedulerRule()

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }
}