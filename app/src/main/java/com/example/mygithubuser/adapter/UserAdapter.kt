package com.example.mygithubuser.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.ItemsItem
import com.example.mygithubuser.R
import com.example.mygithubuser.ui.UserDetailActivity
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(var listUsers: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        )


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val users = listUsers[position]
        Log.d("UserAdapter", "Binding user ${users.login} at position $position")
        viewHolder.tvUserName.text = users.login
        Glide.with(viewHolder.itemView.context)
            .load(users.avatarUrl)
            .circleCrop()
            .into(viewHolder.ivUserPhoto)

        viewHolder.itemView.setOnClickListener {
            val intentUserDetail =
                Intent(viewHolder.itemView.context, UserDetailActivity::class.java)
            intentUserDetail.putExtra(UserDetailActivity.EXTRA_USER, users.login)
            viewHolder.itemView.context.startActivity(intentUserDetail)
        }
    }

    override fun getItemCount() = listUsers.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        val ivUserPhoto: CircleImageView = view.findViewById(R.id.img_user_photo)
    }
}