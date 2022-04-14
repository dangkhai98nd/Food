package com.test.food.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.test.food.BR
import com.test.food.R
import com.test.food.databinding.ActivityMainBinding
import com.test.food.ui.base.BaseActivity
import com.test.food.ui.fragments.book.BookFragment
import com.test.food.ui.fragments.favorites.FavoritesFragment
import com.test.food.ui.fragments.home.HomeFragment
import com.test.food.ui.fragments.profile.ProfileFragment
import com.test.food.ui.fragments.search.SearchFragment
import com.test.food.util.FragmentContentPagerAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), KodeinAware {

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    override val layoutId: Int
        get() = R.layout.activity_main
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: MainViewModel
        get() = ViewModelProvider(this, factory)[MainViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialViewPager()
        TabLayoutMediator(
            mViewDataBinding!!.tabLayout,
            mViewDataBinding!!.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.setCustomView(R.layout.tab_item)
                    tab.customView?.findViewById<ImageView>(R.id.iv_tab_item)
                        ?.setImageResource(R.drawable.ic_outline_home_24)
                    tab.customView?.findViewById<TextView>(R.id.tv_tab_item)
                        ?.setText(R.string.word_home)
                }
                1 -> {
                    tab.setCustomView(R.layout.tab_item)
                    tab.customView?.findViewById<ImageView>(R.id.iv_tab_item)
                        ?.setImageResource(R.drawable.ic_round_search_24)
                    tab.customView?.findViewById<TextView>(R.id.tv_tab_item)
                        ?.setText(R.string.word_search)
                }
                2 -> {
                    tab.view.isEnabled = false
                }
                3 -> {
                    tab.setCustomView(R.layout.tab_item)
                    tab.customView?.findViewById<ImageView>(R.id.iv_tab_item)
                        ?.setImageResource(R.drawable.ic_round_favorite_border_24)
                    tab.customView?.findViewById<TextView>(R.id.tv_tab_item)
                        ?.setText(R.string.word_favorites)
                }
                4 -> {
                    tab.setCustomView(R.layout.tab_item)
                    tab.customView?.findViewById<ImageView>(R.id.iv_tab_item)
                        ?.setImageResource(R.drawable.ic_outline_account_circle_24)
                    tab.customView?.findViewById<TextView>(R.id.tv_tab_item)
                        ?.setText(R.string.word_profile)
                }
            }
        }.attach()
    }

    private fun initialViewPager() {
        with(mViewDataBinding!!.viewPager) {
            adapter = FragmentContentPagerAdapter(supportFragmentManager, lifecycle).also {
                it.addFragment(HomeFragment())
                it.addFragment(SearchFragment())
                it.addFragment(BookFragment())
                it.addFragment(FavoritesFragment())
                it.addFragment(ProfileFragment())
            }
            mViewDataBinding?.cvBook?.setOnClickListener {
                setCurrentItem(2, true)
            }
            val child = getChildAt(0)
            if (child is RecyclerView) {
                child.setOverScrollMode(View.OVER_SCROLL_NEVER)
            }
        }

    }
}
