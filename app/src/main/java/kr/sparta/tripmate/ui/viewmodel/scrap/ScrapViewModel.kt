package kr.sparta.tripmate.ui.viewmodel.scrap

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kr.sparta.tripmate.domain.model.ScrapModel
import kr.sparta.tripmate.domain.model.toScrapModel
import kr.sparta.tripmate.domain.usecase.GetSearchBlogUseCase
import kr.sparta.tripmate.util.sharedpreferences.SharedPreferences

class ScrapViewModel(private val searchBlog: GetSearchBlogUseCase) : ViewModel() {
    private val _scrapResult = MutableLiveData<List<ScrapModel>>()
    val scrapResult: LiveData<List<ScrapModel>> get() = _scrapResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun ScrapServerResults(q: String, context: Context) = viewModelScope.launch {
        kotlin.runCatching {
            // loading start
            _isLoading.value = true

            val result = searchBlog(q)
            val scrapItems = ArrayList<ScrapModel>()
            result.items?.let {
                for (i in it.indices) {
                    scrapItems.add(it[i].toScrapModel())
                }
                _scrapResult.value = scrapItems
                val scrap_Ref: DatabaseReference = Firebase.database.reference.child("UserData")
                    .child(SharedPreferences.getUid(context)).child("homeItems")
                scrap_Ref.setValue(scrapItems)
                // loading end
                _isLoading.value = false
            }
        }.onFailure {
            _isLoading.value = false
            Log.e("ScrapViewModel", it.message.toString())
        }
    }
}