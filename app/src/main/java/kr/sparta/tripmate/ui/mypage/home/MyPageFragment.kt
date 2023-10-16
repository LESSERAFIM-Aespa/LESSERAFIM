package kr.sparta.tripmate.ui.mypage.home

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayoutMediator
import kr.sparta.tripmate.R
import kr.sparta.tripmate.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    companion object {
        fun newInstance(): MyPageFragment = MyPageFragment()
    }

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    private val adapter: MyPageTabLayoutAdapter by lazy {
        MyPageTabLayoutAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        mypageViewpager.adapter = adapter

        TabLayoutMediator(mypageTablayout, mypageViewpager) { tab, position ->
            tab.setText(adapter.getTitle(position))
        }.attach()

        mypageSettingButton.setOnClickListener {

        }

        // Edit 버튼클릭시 viewType변경
        fun updateEditType(isEditMode: Boolean) {
            if (isEditMode) {
                mypageEditmodeEdittextContainer.visibility = View.VISIBLE
                mypageEditmodeButtonContainer.visibility = View.VISIBLE
                mypageProfileContentTextview.visibility = View.GONE
            } else {
                mypageEditmodeEdittextContainer.visibility = View.GONE
                mypageEditmodeButtonContainer.visibility = View.GONE
                mypageProfileContentTextview.visibility = View.VISIBLE
            }
        }

        // Edit버튼 클릭
        mypageEditTextButton.setOnClickListener {
            // 이전입력되었던 텍스트 가져오기(있을경우)
            val beforeText = mypageProfileContentTextview.text
            mypageProfileContentEdittext.setText(beforeText)
            updateEditType(true)
        }

        // 확인 버튼
        mypageEditSubmitButton.setOnClickListener {
            // 입력된 Edittext
            val nowInputEdittext = mypageProfileContentEdittext.text.toString()
            // 입력된 EditText TextView에 입력
            mypageProfileContentTextview.text = nowInputEdittext
            // EditText 초기화
            mypageProfileContentEdittext.setText("")
            updateEditType(false)
        }

        // 취소 버튼
        mypageEditCancelButton.setOnClickListener {
            editCancelDialog(
                positive = {
                    updateEditType(false)
                },
                negative = {},
            )
        }

        // EditText Watcher
        mypageProfileContentEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                // 글자수 textView 업데이트
                mypageProfileCheckLengthTextview.text= "${p0?.length}/30"
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    // EditText 수정중 뒤로가기 클릭시
    private fun editCancelDialog(
        positive: () -> Unit,
        negative: () -> Unit
    ) {
        val listener = DialogInterface.OnClickListener { _, p1 ->
            when (p1) {
                DialogInterface.BUTTON_POSITIVE -> positive()

                DialogInterface.BUTTON_NEGATIVE -> negative()
            }
        }

        AlertDialog.Builder(requireContext()).apply {
            setTitle("작성취소")
            setMessage("정말 작성을 취소하시겠습니까?")
            setPositiveButton(R.string.mypage_edit_submit, listener)
            setNegativeButton(R.string.mypage_edit_cancel, listener)
        }
            .create()
            .show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}