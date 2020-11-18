package goforfit.com.sportsaddakotlin.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import goforfit.com.sportsaddakotlin.models.FragmentModel

class FragmentsAdapter(val fm:FragmentManager):FragmentPagerAdapter(fm){
    val fragmentsArrayList = ArrayList<FragmentModel>()


    override fun getItem(position: Int): Fragment {
        return fragmentsArrayList[position].fragment
    }

    override fun getCount(): Int {
        return fragmentsArrayList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsArrayList[position].fragmentTitle
    }

    fun addFragment(fragment:Fragment,title:String){
        fragmentsArrayList.add(FragmentModel(title,fragment))
    }
}