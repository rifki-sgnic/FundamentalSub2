package com.dicoding.picodiploma.fundamentalsub2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.fundamentalsub2.databinding.ItemRowUserBinding
import com.dicoding.picodiploma.fundamentalsub2.model.UserItems

class UserAdapter(private val listUser : ArrayList<UserItems>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<UserItems>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding : ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userItems: UserItems) {
            with(binding) {
                tvItemName.text = userItems.username
                Glide.with(imgItemAvatar.context)
                    .load(userItems.avatar)
                    .apply(RequestOptions().override(85, 85))
                    .into(imgItemAvatar)
                cardView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[adapterPosition]) }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItems)
    }
}