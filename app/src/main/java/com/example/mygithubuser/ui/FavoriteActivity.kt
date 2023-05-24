package com.example.mygithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.viewModel.FavUserViewModel
import com.example.mygithubuser.ItemsItem
import com.example.mygithubuser.adapter.UserAdapter
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.factory.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favUserViewModel by viewModels<FavUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = UserAdapter(emptyList())
        binding.rvUsers.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.setHasFixedSize(true)

        favUserViewModel.getAllFavUser().observe(this) { users: List<FavUser>? ->
            val items = arrayListOf<ItemsItem>()
            users?.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding.rvUsers.adapter = UserAdapter(items)
        }

    }
}