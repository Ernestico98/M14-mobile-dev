package com.harbourspace.unsplash.ui.legacy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.UnsplashViewModel
import com.harbourspace.unsplash.databinding.FragmentCollectionsBinding
import com.harbourspace.unsplash.ui.legacy.DetailsActivity
import com.harbourspace.unsplash.ui.legacy.adapters.MainCollectionsAdapter
import com.harbourspace.unsplash.utils.EXTRA_UNSPLASH_IMAGE_URL

class MainCollectionsFragment : Fragment() {

    private lateinit var unsplashViewModel: UnsplashViewModel
    private lateinit var binding: FragmentCollectionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiComponents()
    }

    private fun setupUiComponents() {
        unsplashViewModel = ViewModelProvider(requireActivity())[UnsplashViewModel::class.java]

        binding.rvContainer.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MainCollectionsAdapter(emptyList()) {
                openDetailsActivity(it)
            }
        }

        unsplashViewModel.unsplashCollections.observe(requireActivity()) {
            val adapter = binding.rvContainer.adapter as MainCollectionsAdapter
            adapter.submitList(it)
        }

        unsplashViewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), R.string.main_unable_to_fetch_images, Toast.LENGTH_SHORT).show()
        }

        unsplashViewModel.fetchCollections()
    }

    private fun openDetailsActivity(url: String) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(EXTRA_UNSPLASH_IMAGE_URL, url)

        startActivity(intent)
    }
}