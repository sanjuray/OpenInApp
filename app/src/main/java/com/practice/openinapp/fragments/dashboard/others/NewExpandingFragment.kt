package com.practice.openinapp.fragments.dashboard.others

import android.os.Bundle
import com.practice.openinapp.R
import com.practice.openinapp.base.BaseFragmentWithoutViewModel
import com.practice.openinapp.databinding.FragmentNewExpandingBinding
import com.practice.openinapp.utils.Constants

class NewExpandingFragment :
    BaseFragmentWithoutViewModel<FragmentNewExpandingBinding>(
        R.layout.fragment_new_expanding
    ) {

    companion object{
        fun newInstance(msg: String): NewExpandingFragment {
            val args = Bundle()
            args.putString(Constants.KEY_MESSAGE_FRAGMENTS,msg)
            val fragment = NewExpandingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initUI() {
        val msg = "Hello from ${arguments?.getString(Constants.KEY_MESSAGE_FRAGMENTS)} fragment"
        binding.tvFragmentIdentificationTextView.text = msg
    }

    override fun initOnClickListeners() {
        /**
         * no use of it in this fragment
         */
    }

}