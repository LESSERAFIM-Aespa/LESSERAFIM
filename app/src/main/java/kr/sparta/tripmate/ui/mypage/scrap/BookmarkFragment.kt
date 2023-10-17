package kr.sparta.tripmate.ui.mypage.scrap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kr.sparta.tripmate.databinding.FragmentBookmarkBinding
import kr.sparta.tripmate.ui.scrap.ScrapDetail
import kr.sparta.tripmate.ui.viewmodel.home.FirstViewModel
import kr.sparta.tripmate.util.sharedpreferences.SharedPreferences

class BookmarkFragment : Fragment() {
    companion object {
        fun newInstance(): BookmarkFragment = BookmarkFragment()
    }
    private val bookmarkResults = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){}
    }

    private val scrapViewModel: FirstViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarkAdapter by lazy {
        BookmarkListAdapter(
            onItemClick = { model, position ->
                bookmarkResults.launch(ScrapDetail.newIntentForScrap(requireContext(), model))
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initViewModel() {
        with(scrapViewModel) {
            getFirstData(SharedPreferences.getUid(requireContext())).observe(viewLifecycleOwner){
                bookmarkAdapter.submitList(it)
            }
        }
    }

    private fun initView() = with(binding) {
      bookmarkRecyclerview.apply {
          layoutManager = GridLayoutManager(requireContext(),2)
          adapter = bookmarkAdapter
          setHasFixedSize(true)
      }
    }
}