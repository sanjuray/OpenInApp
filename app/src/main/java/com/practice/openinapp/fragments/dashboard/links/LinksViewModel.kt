package com.practice.openinapp.fragments.dashboard.links

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.practice.openinapp.base.BaseViewModel
import com.practice.openinapp.R
import com.practice.openinapp.model_class.LinkOfAPI
import com.practice.openinapp.model_class.Stats
import com.practice.openinapp.model_class.TopLinks
import com.practice.openinapp.utils.CommonObjects
import com.practice.openinapp.utils.NetworkUtils
import com.practice.openinapp.utils.UserMetaData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LinksViewModel: BaseViewModel<Any>() {

    private lateinit var xValues: List<String>
    private var fromDuration = 0
    private var toDuration = 0
    private var maxYValue = 0.0

    private lateinit var entries: List<Entry>

    /**
     *  0 -> Top Links
     *  1 -> Recent Links
     */
    private var linksTypeState = -1
    private var userName = ""
    private lateinit var statList: List<Stats>

    private lateinit var _topLinks: TopLinks

    val topLinks: MutableLiveData<TopLinks> = MutableLiveData()

    private suspend fun getAPITopLinks(): TopLinks{
        return withContext(viewModelScope.coroutineContext){
            var topLink: TopLinks? = null
            try {
                NetworkUtils.makeNetworkCall(NetworkUtils.openInAppService.getLinks(),
                    onErrorCallback = {
                        CommonObjects.errorLogs(it)
                    }){
                    topLink = it as TopLinks
                }
            }catch (e: Exception){
                CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
            }
            topLink!!
        }
    }

    fun setFromDuration(from: Int){
        fromDuration = from
    }

    fun setToDuration(to: Int){
        toDuration = to
    }

    fun getFromDuration() = fromDuration

    fun getToDuration() = toDuration

    fun getXValues() = xValues

    fun getMaxYValue() = maxYValue.toFloat()

    fun getEntries(from:Int, to: Int) = entries.subList(from,to)

    fun init(linksTypeState: Int, userMetaData: UserMetaData) {
        try {
            setLinkTypeState(linksTypeState)
            userName = userMetaData.USER_NAME
            initAPICall()
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }

    private fun initAPICall(){
        viewModelScope.launch {
            try {
                val call = async {
                    getAPITopLinks()
                }
                _topLinks = call.await()
                initLists()
                topLinks.value = _topLinks
            }catch (e: Exception){
                CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
            }
        }
    }

    private fun initLists(){
        setStatList()
        setEntriesForGraph()
    }

    private fun setEntriesForGraph(){
        try {
            CommonObjects.errorLogs(_topLinks.data.overallUrlChart.toString())
            val chartData = _topLinks.data.overallUrlChart
            xValues = chartData.keys.toList()
            val tempEntries = mutableListOf<Entry>()
            var i = 0
            chartData.forEach{ entry ->
                tempEntries.add(Entry(i.toFloat(),entry.value.toFloat()))
                maxYValue = maxYValue.coerceAtLeast(entry.value)
                i += 1
            }
            toDuration = tempEntries.size - 1
            entries = tempEntries
        }catch (e: Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }

    private fun setStatList(){
        try {
            statList = listOf(
                Stats(R.drawable.ic_today_clicks,"Today's clicks",_topLinks.today_clicks.toString()),
                Stats(R.drawable.ic_top_location,"Top Location",_topLinks.top_location),
                Stats(R.drawable.ic_top_source, "Top source", _topLinks.top_source),
                Stats(R.drawable.ic_best_time, "Best Time", _topLinks.startTime)
            )
        }catch (e: Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }

    fun getUserName() = userName

    fun getStatList() = statList

    fun setLinkTypeState(linksTypeState: Int){
        this.linksTypeState = linksTypeState
    }

    //get links
    fun getLinks(): List<LinkOfAPI>{
        return when(linksTypeState){
            0 -> getTopLinks()
            else -> getRecentLinks()
        }
    }
    //top links
    private fun getTopLinks():List<LinkOfAPI>{
        return _topLinks.data.topLinks
    }

    //recent links
    private fun getRecentLinks(): List<LinkOfAPI>{
        return _topLinks.data.recentLinks
    }

    //all links
//    fun getAllLinks(): List<Link>{
//        return listOf()
//    }
}