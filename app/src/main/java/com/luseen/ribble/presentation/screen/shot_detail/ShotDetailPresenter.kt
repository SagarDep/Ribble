package com.luseen.ribble.presentation.screen.shot_detail

import com.luseen.ribble.di.scope.PerActivity
import com.luseen.ribble.domain.interactor.ShotDetailInteractor
import com.luseen.ribble.domain.interactor.ShotLikeInteractor
import com.luseen.ribble.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject

/**
 * Created by Chatikyan on 05.08.2017.
 */
@PerActivity
class ShotDetailPresenter @Inject constructor(private val shotDetailInteractor: ShotDetailInteractor,
                                              private val shotLikeInteractor: ShotLikeInteractor)
    : ApiPresenter<ShotDetailContract.View>(), ShotDetailContract.Presenter {

    init {

    }

    override fun onRequestStart() {

    }

    override fun <T> onRequestSuccess(data: T) {

    }

    override fun onRequestError(errorMessage: String?) {

    }
}