package net.riadh.henri.injection

import net.riadh.henri.app.BASE_URL
import net.riadh.henri.network.BookApi
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.ui.book.BookListViewModel
import net.riadh.henri.ui.book.BookViewModel
import net.riadh.henri.util.ExceptionUtil
import net.riadh.henri.util.ExceptionUtilInterface
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object DependencyModules {

    val appModules = module {

        single { BookRepositoryImpl(get()) }

        single { ExceptionUtil(androidContext()) as ExceptionUtilInterface }

        viewModel { BookListViewModel(get() as BookRepositoryImpl, get()) }

        viewModel { BookViewModel(get()) }

        // provided web components
        single { createOkHttpClient() }

        // Fill property
        single { provideRetrofitInterface<BookApi>(createOkHttpClient(), BASE_URL) }

    }
}