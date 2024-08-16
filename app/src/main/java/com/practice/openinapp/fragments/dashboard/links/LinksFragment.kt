package com.practice.openinapp.fragments.dashboard.links

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayout
import com.practice.openinapp.base.BaseFragment
import com.practice.openinapp.MainActivity
import com.practice.openinapp.R
import com.practice.openinapp.databinding.FragmentLinksBinding
import com.practice.openinapp.fragments.settings.SettingsFragment
import com.practice.openinapp.utils.CommonObjects
import com.practice.openinapp.utils.Constants
import com.practice.openinapp.utils.UserMetaData


class LinksFragment :
    BaseFragment<FragmentLinksBinding, LinksViewModel>(
        R.layout.fragment_links
    ){

    private lateinit var linksSectionAdapter: LinksSectionAdapter
    private lateinit var statsAdapter: StatsAdapter

    private lateinit var tabLayout: TabLayout

    override val viewModel: LinksViewModel by lazy {
        ViewModelProvider(this)[LinksViewModel::class.java]
    }

    companion object{
        private val maxLabelCountX = 5
        private val maxLabelCountY = 4
        fun newInstance(): LinksFragment{
            val fragment = LinksFragment()
            return fragment
        }
    }

    override fun initViewModel(){
        try {
            /**
             * this gives the scope for scalability
             */
            viewModel.init(0, UserMetaData)
            viewModel.topLinks.observe(this){
                initRecyclerView()
                initLineChart()
            }
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    override fun initUI() {
        setGreetingsText()

        initTabLayout()

        initOnClickListeners()
    }

    private fun setGreetingsText(){
        binding.tvGreetingsTextView.text = CommonObjects.getGreeting()
        binding.tvUserNameTextView.text = viewModel.getUserName()
    }

    private fun initLineChart(){
        try {
            val lineChart = binding.lcOverviewLineChart

            val description = Description()
            description.text = ""
            description.setPosition(0f, 0f)
            lineChart.description = description
            lineChart.axisRight.setDrawLabels(false)

            val from = viewModel.getFromDuration()
            val to = viewModel.getToDuration() + 1

            val xValues = viewModel.getXValues()

            val durationText = "${xValues[from]} - ${xValues[to-1]}"
            binding.tvdurationIndicatorTextView.text = durationText

            val xAxis = lineChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
            xAxis.labelCount = maxLabelCountX
            xAxis.granularity = 1f

            val yAxis = lineChart.axisLeft
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = viewModel.getMaxYValue()
            yAxis.labelCount = maxLabelCountY

            val dataSet = LineDataSet(viewModel.getEntries(from,to), null)
            dataSet.color = Constants.BLUE
            dataSet.lineWidth = 3f

            val lineData = LineData(dataSet)

            lineChart.data = lineData

            lineChart.invalidate()
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    private fun initTabLayout(){
        try {
            tabLayout = binding.tlLinksTabLayout

            tabLayout.addTab(tabLayout.newTab().setText("Top Links"))
            tabLayout.addTab(tabLayout.newTab().setText("Recent Links"))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewModel.setLinkTypeState(tab!!.position)
                    initRecyclerViewAdapter()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }catch (e: Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }

    private fun initRecyclerView(){
        binding.rvStatsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        statsAdapter = StatsAdapter(
            viewModel.getStatList(),
            callback = {
                /**
                 * add functionality if required
                 */
            }
        )
        binding.rvStatsRecyclerView.adapter = statsAdapter

        binding.rvLinksRecyclerView.layoutManager = LinearLayoutManager(context)
        linksSectionAdapter = LinksSectionAdapter(
                viewModel.getLinks(),
                callback = {
                    (context as MainActivity).copyToClipboard(it)
                }
        )
        binding.rvLinksRecyclerView.adapter = linksSectionAdapter
    }

    private fun initRecyclerViewAdapter(){
        linksSectionAdapter.setLinkList(viewModel.getLinks())
    }

    override fun initOnClickListeners() {
        try {
            binding.ibSettingsImageButton.setOnClickListener {
                (context as MainActivity).loadFragment(SettingsFragment.newInstance())
                //            findNavController().navigate(R.id.action_linksFragment3_to_settingsFragment4)
            }

            binding.llDurationSelectorLinearLayout.setOnClickListener {
                CommonObjects.showDurationSelectionDialog(
                    requireContext(),
                    "Select Duration",
                    viewModel.getXValues(),
                    viewModel.getFromDuration(),
                    viewModel.getToDuration(),
                    "Confirm",
                    "Cancel",
                ) { from, to ->
                    viewModel.setFromDuration(from)
                    viewModel.setToDuration(to)
                    initLineChart()
                }
            }

            binding.llViewAnalyticsLinearLayout.setOnClickListener {
                CommonObjects.showToast(requireContext(), "Feature Coming Soon...")
            }

            binding.ibSearchImageButton.setOnClickListener {
                CommonObjects.showToast(requireContext(), "Coming Soon...")
            }

            binding.llViewAllLinksLinearLayout.setOnClickListener {
                CommonObjects.showToast(requireContext(), "Coming Soon...")
            }

            binding.llContactLinearLayout.setOnClickListener {
                (context as MainActivity).onClickWhatsapp(viewModel.topLinks.value!!.support_whatsapp_number)
            }

            binding.llFAQLinearLayout.setOnClickListener {
                val url = "https://openinapp.com/faq"
                CommonObjects.openInBrowserIntent(requireContext(), url)
            }
        }catch (e: Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }

}