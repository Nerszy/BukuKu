package com.dicoding.bukuku

import androidx.lifecycle.*
import com.dicoding.bukuku.response.BooksItem
import com.dicoding.bukuku.tools.ApiConfig
import retrofit2.*

class RecommendViewModel : ViewModel() {
    private val recommendBooks = ArrayList<BooksItem>()
    private val _listRecommendBook = MutableLiveData<ArrayList<BooksItem>>()
    val listRecommendBook: LiveData<ArrayList<BooksItem>> = _listRecommendBook

    fun getRecommendBook(lifecycleOwner: LifecycleOwner, books: RecommendRequest) {
        val detailViewModel = ViewModelProvider(lifecycleOwner as ViewModelStoreOwner)[DetailBookViewModel::class.java]

        val mediatorLiveData = MediatorLiveData<List<BooksItem>>()

        mediatorLiveData.addSource(detailViewModel.book) { book ->
            book?.let {
                recommendBooks.add(book)
                mediatorLiveData.value = recommendBooks
            }
        }

        mediatorLiveData.observe(lifecycleOwner) { recommendedBooks ->
            // Dapatkan buku rekomendasi yang diperbarui dan lakukan tindakan yang sesuai
            _listRecommendBook.value = ArrayList(recommendedBooks)
        }

        ApiConfig.getApiServicesRecoomend().recommendBook(books).enqueue(
            object : Callback<RecommendResponse> {
                override fun onResponse(
                    call: Call<RecommendResponse>,
                    response: Response<RecommendResponse>
                ) {
                    if (response.isSuccessful) {
                        val recommendedBooks = response.body()?.recommendations ?: emptyList()

                        recommendedBooks.forEach { bookId ->
                            detailViewModel.getBookById(bookId)
                        }
                    }
                }

                override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                    // Do nothing
                }
            }
        )
    }
}

