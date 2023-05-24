package com.example.mygithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.adapter.SectionPagerAdapter
import com.example.mygithubuser.UserDetailResponse
import com.example.mygithubuser.viewModel.UserDetailViewModel
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.databinding.ActivityUserDetailBinding
import com.example.mygithubuser.factory.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userDetailViewModel: UserDetailViewModel

    private lateinit var favUser: FavUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(EXTRA_USER)
        binding.tvUserName.text = user

        userDetailViewModel = obtainViewModel(this@UserDetailActivity)

        if (savedInstanceState == null) {
            if (user != null) {
                showLoading(true)
                userDetailViewModel.getUserDetail(user)
            }
        }

        userDetailViewModel.userDetail.observe(this) { userDetail ->
            setUserDetails(userDetail)
            favUser = FavUser(userDetail.login, userDetail.avatarUrl)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        user?.let {
            userDetailViewModel.checkIfUserExists(it).observe(this) { isExist ->
                if (isExist) {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        if (user != null) {
            sectionPagerAdapter.userName = user
        }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        userDetailViewModel = obtainViewModel(this@UserDetailActivity)

        binding.fab.setOnClickListener {
            userDetailViewModel.checkIfUserExists(favUser.username).observeOnce(this) { isExist ->
                if (isExist) {
                    userDetailViewModel.delete(favUser)
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                    Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    userDetailViewModel.insert(favUser)
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                    Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
        observe(owner, object : Observer<T> {
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this)
            }
        })
    }

    private fun setUserDetails(user: UserDetailResponse?) {
        if (user == null) {
            Toast.makeText(this, R.string.user_detail_error, Toast.LENGTH_SHORT).show()
        } else {
            with(binding) {
                Glide.with(this@UserDetailActivity)
                    .load(user.avatarUrl)
                    .into(ivUserAvatar)
                tvUserName.text = user.name
                tvUserUserName.text = user.login
                tvFollowers.text = user.followers.toString()
                tvFollowing.text = user.following.toString()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UserDetailViewModel::class.java]
    }

    companion object {
        const val EXTRA_USER = "user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )
    }
}