package com.dicoding.picodiploma.fundamentalsub2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.fundamentalsub2.adapter.SectionsPagerAdapter
import com.dicoding.picodiploma.fundamentalsub2.databinding.ActivityDetailUserBinding
import com.dicoding.picodiploma.fundamentalsub2.model.UserItems
import com.dicoding.picodiploma.fundamentalsub2.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        //custom toolbar
        var title = binding.include.title
        binding.include.toolbar.background = null
        var back = binding.include.navBack
        back.setOnClickListener{
            navigateUpTo(parentActivityIntent)
        }

        val user = intent.getParcelableExtra<UserItems>(EXTRA_USER) as UserItems

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        user.username?.let { mainViewModel.setDetailUser(it) }

        mainViewModel.getDetailUser().observe(this, { userItems ->
            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(userItems.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(profileImg)
                tvProfileUsername.text = userItems.username
                tvProfileName.text = userItems.name
                tvProfileLocation.text = userItems.location
                tvProfileCompany.text = userItems.company
                tvFollowersCount.text = userItems.followers.toString()
                tvFollowingCount.text = userItems.following.toString()
                tvRepositoryCount.text = userItems.repository.toString()
            }
            showLoading(false)
            title.text = userItems.username
        })

        //ViewPager2
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.username
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}