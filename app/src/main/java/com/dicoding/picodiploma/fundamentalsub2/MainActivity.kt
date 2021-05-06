package com.dicoding.picodiploma.fundamentalsub2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.fundamentalsub2.adapter.UserAdapter
import com.dicoding.picodiploma.fundamentalsub2.databinding.ActivityMainBinding
import com.dicoding.picodiploma.fundamentalsub2.model.UserItems
import com.dicoding.picodiploma.fundamentalsub2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private val list = ArrayList<UserItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.setListUser()

        mainViewModel.getListUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })

        //SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                searchView.clearFocus()
                mainViewModel.setSearchUser(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if(query.isEmpty()) {
                    showLoading(true)
                    mainViewModel.setListUser()
                }
                return false
            }
        })

        mainViewModel.getSearchUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(list)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter
        adapter.notifyDataSetChanged()
        showLoading(true)

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                moveIntent(data)
            }
        })
    }

    private fun moveIntent(user: UserItems) {
        val moveDetailUser = Intent(this, DetailUserActivity::class.java)
        moveDetailUser.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(moveDetailUser)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}