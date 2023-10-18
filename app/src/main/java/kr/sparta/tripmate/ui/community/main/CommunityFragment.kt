package kr.sparta.tripmate.ui.community.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.sparta.tripmate.databinding.FragmentCommunityBinding
import kr.sparta.tripmate.ui.community.CommunityWriteActivity

class CommunityFragment : Fragment() {
    private var _binding : FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val adapter by lazy {
        CommunityListAdapter()
    }

    // Write a message to the database
    private val database = Firebase.database
    private val communityRef = database.getReference("CommunityData")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, CommunityWriteActivity::class.java)
            startActivity(intent)
        }


        val user = auth.currentUser
        user?.let {
            val uid = it.uid
            communityRef
                .child(uid)
                .setValue(
                    CommunityModel(
                        id, "이미지", "제목", "닉네임", "프로필", "1800", "1500"
                    )
                )
        }





        initview()
    }

    private fun initview()=with(binding) {
        communityMainRecyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
