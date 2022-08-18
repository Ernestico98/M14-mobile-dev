package com.harbourspace.unsplash.ui.legacy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.databinding.FragmentMainBinding
import com.harbourspace.unsplash.ui.legacy.adapters.TabsAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiComponents()
    }

    private fun setupUiComponents() {
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tl_container)
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.main_tab_images)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.main_tab_collections)))

        val adapter = TabsAdapter(
            fragmentManager = childFragmentManager,
            count = tabLayout.tabCount,
            lifecycle = lifecycle
        )

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.vp_container)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //Do nothing
            }
        })
    }
}