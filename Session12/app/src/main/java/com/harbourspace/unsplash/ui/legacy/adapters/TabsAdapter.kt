package com.harbourspace.unsplash.ui.legacy.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.harbourspace.unsplash.ui.legacy.fragments.MainCollectionsFragment
import com.harbourspace.unsplash.ui.legacy.fragments.MainImagesFragment

class TabsAdapter(
    fragmentManager : FragmentManager,
    private val count: Int,
    lifecycle: Lifecycle) :

    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MainImagesFragment()
            else -> MainCollectionsFragment()
        }
    }
}