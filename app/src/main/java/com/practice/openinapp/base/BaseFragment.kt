package com.practice.openinapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.practice.openinapp.utils.CommonObjects

abstract class BaseFragment<VB: ViewBinding,VM: BaseViewModel<Any>>(
    private val layoutId: Int,
): Fragment() {

    lateinit var binding: VB
    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            binding = DataBindingUtil.inflate(
                inflater,
                layoutId, container, false
            )
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            init()
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    private fun init(){
        initViewModel()
        initUI()
        initOnClickListeners()
    }
    abstract fun initViewModel()

    abstract fun initUI()

    abstract fun initOnClickListeners()

}

abstract class BaseFragmentWithoutViewModel<VB: ViewBinding>(
    private val layoutId: Int,
): Fragment() {

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            binding = DataBindingUtil.inflate(
                inflater,
                layoutId, container, false
            )
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            init()
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    private fun init(){
        initUI()
        initOnClickListeners()
    }

    abstract fun initUI()

    abstract fun initOnClickListeners()

}

