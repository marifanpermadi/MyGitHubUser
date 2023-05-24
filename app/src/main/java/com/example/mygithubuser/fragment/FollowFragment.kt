package com.example.mygithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.ItemsItem
import com.example.mygithubuser.adapter.UserAdapter
import com.example.mygithubuser.databinding.FragmentFollowBinding
import com.example.mygithubuser.factory.ViewModelFactory
import com.example.mygithubuser.viewModel.UserDetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var userDetailViewModel: UserDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        val application = requireActivity().application
        userDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(application)
        )[UserDetailViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION) ?: 0
        val userName = arguments?.getString(ARG_USERNAME) ?: ""

        val userAdapter = UserAdapter(emptyList())
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.adapter = userAdapter

        if (position == 1) {
            showLoading(true)
            userName.let { userDetailViewModel.getFollowers(it) }
            userDetailViewModel.followers.observe(viewLifecycleOwner) {
                setUsers(it)
                showLoading(false)
            }
        } else {
            showLoading(true)
            userName.let { userDetailViewModel.getFollowings(it) }
            userDetailViewModel.following.observe(viewLifecycleOwner) {
                setUsers(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUsers(users: List<ItemsItem>) {

        val adapter = binding.rvFollow.adapter as UserAdapter
        adapter.listUsers = users
        adapter.notifyDataSetChanged()

    }


    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"

    }
}
