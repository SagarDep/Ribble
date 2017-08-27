package com.luseen.ribble.presentation.screen.home

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import com.luseen.ribble.R
import com.luseen.ribble.presentation.base_mvp.base.BaseActivity
import com.luseen.ribble.presentation.screen.auth.AuthActivity
import com.luseen.ribble.presentation.screen.shot_root.ShotRootFragment
import com.luseen.ribble.presentation.screen.user_following.UserFollowingFragment
import com.luseen.ribble.presentation.screen.user_likes.UserLikesFragment
import com.luseen.ribble.presentation.widget.navigation_view.NavigationItem
import com.luseen.ribble.presentation.widget.navigation_view.NavigationItemSelectedListener
import com.luseen.ribble.utils.lock
import com.luseen.ribble.utils.showToast
import com.luseen.ribble.utils.start
import com.luseen.ribble.utils.unlock
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject
import com.luseen.ribble.presentation.widget.navigation_view.NavigationId as Id

class HomeActivity : BaseActivity<HomeContract.View, HomeContract.Presenter>(), HomeContract.View,
        NavigationItemSelectedListener {

    @Inject
    protected lateinit var homePresenter: HomePresenter

    @Inject
    protected lateinit var fragmentManager: FragmentManager

    override fun initPresenter(): HomeContract.Presenter = homePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

        val navigatorState = presenter.getNavigatorState()
        if (navigatorState != null)
            navigator.restore(navigatorState)
    }

    override fun onDestroy() {
        presenter.saveNavigatorState(navigator.getState())
        super.onDestroy()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navView.navigationItemSelectListener = this
    }

    override fun onDrawerLocked() {
        arcImage.setImageResource(R.drawable.ic_menu_send)
        arcView.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onDrawerUnlocked() {
        arcImage.setImageResource(R.drawable.equal)
        arcView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun openShotFragment() {
        goTo(ShotRootFragment::class)
    }

    override fun openLoginActivity() {
        start {
            AuthActivity::class.java
        }
        showToast("Logged out")
        finish()
    }

    override fun onNonRegistryFragmentOpen(tag: Id) {
        drawerLayout.lock()
        presenter.handleDrawerLock()
    }

    override fun onNonRegistryFragmentClose() {
        drawerLayout.unlock()
        presenter.handleDrawerUnLock()
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
            else -> super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: NavigationItem) {
        when (item.id) {
            Id.SHOT -> {
                goTo(ShotRootFragment::class)
            }
            Id.USER_LIKES -> {
                goTo(UserLikesFragment::class)
            }
            Id.FOLLOWING -> {
                goTo(UserFollowingFragment::class)
            }
            Id.ABOUT -> {

            }
            Id.LOG_OUT -> {
                presenter.logOut()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
    }
}