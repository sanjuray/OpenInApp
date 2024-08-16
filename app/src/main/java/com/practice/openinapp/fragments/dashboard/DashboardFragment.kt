package com.practice.openinapp.fragments.dashboard

import com.practice.openinapp.MainActivity
import com.practice.openinapp.R
import com.practice.openinapp.base.BaseFragmentWithoutViewModel
import com.practice.openinapp.databinding.FragmentDashboardBinding
import com.practice.openinapp.fragments.dashboard.links.LinksFragment
import com.practice.openinapp.fragments.dashboard.others.NewExpandingFragment
import com.practice.openinapp.utils.CommonObjects


class DashboardFragment :
    BaseFragmentWithoutViewModel<FragmentDashboardBinding>(
        R.layout.fragment_dashboard
    ) {


    companion object{
        fun newInstance(): DashboardFragment{
            val fragment = DashboardFragment()
            return fragment
        }
    }

    override fun initUI() {
        try {
            (context as MainActivity).loadFragment(LinksFragment.newInstance(),binding)
        }catch (e: Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }

    override fun initOnClickListeners() {
        initNavigation()
    }

    private fun initNavigation(){
        try {
            binding.bnvQuickNavigationBottomNavigationView.background = null
            binding.bnvQuickNavigationBottomNavigationView.setOnItemSelectedListener { item ->
                if (item.itemId == R.id.links) {
                    (context as MainActivity).loadFragment(LinksFragment.newInstance(), binding)
//                    findNavController().navigate(R.id.action_dashboardFragment2_to_linksFragment3)
                } else {
                    val message = when (item.itemId) {
                        R.id.courses -> "Courses"
                        R.id.campaigns -> "Campaigns"
                        R.id.profile -> "Profile"
                        else -> {
                            "Probably Not"
                        }
                    }
                    (context as MainActivity).loadFragment(NewExpandingFragment.newInstance(message),binding)
//                    findNavController().navigate(
//                        R.id.action_dashboardFragment2_to_newExpandingFragment2,
//                        bundleOf(Constants.KEY_MESSAGE_FRAGMENTS to message)
//                    )
                }
                true
            }

            binding.fabNavigationAddFloatingActionButton.setOnClickListener{
                CommonObjects.showToast(requireContext(),"Add links here!")
            }
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

}