package com.harbourspace.unsplash.ui.legacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.databinding.ActivityMainBinding
import com.harbourspace.unsplash.ui.legacy.fragments.AboutFragment
import com.harbourspace.unsplash.ui.legacy.fragments.FavouritesFragment
import com.harbourspace.unsplash.ui.legacy.fragments.MainFragment
import com.harbourspace.unsplash.utils.EXTRA_TAB_SELECTED

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selectedTab = 0
    private val fragments: List<Fragment> by lazy {
        setup()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val selectedTabId = savedInstanceState?.getInt(EXTRA_TAB_SELECTED) ?: R.id.action_home

        setupBottomBarActions(selectedTabId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(EXTRA_TAB_SELECTED, binding.bnvNavigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setup(): List<Fragment> {
        val main = MainFragment()
        val favourites = FavouritesFragment()
        val about = AboutFragment()

        return listOf(main, favourites, about)
    }

    private fun setupBottomBarActions(tab: Int) {
        binding.bnvNavigation.setOnItemSelectedListener { item ->
            val index: Int = when (item.itemId) {
                R.id.action_home -> 0
                R.id.action_favourites -> 1
                R.id.action_about -> 2
                else -> 0
            }

            switchFragment(index)
            selectedTab = index

            return@setOnItemSelectedListener true
        }

        binding.bnvNavigation.selectedItemId = tab
    }

    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = "${fragments[index]::class.java}"

        // if the fragment has not yet been added to the container, add it first
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            transaction.add(R.id.fcv_container, fragments[index], tag)

        } else {
            if (fragments[index] === supportFragmentManager.findFragmentByTag(tag)) {
                fragments[index].onResume()

            } else {
                transaction.replace(R.id.fcv_container, fragments[index], tag)
            }
        }

        transaction.hide(fragments[selectedTab])
        transaction.show(fragments[index])
        transaction.commit()
    }
}