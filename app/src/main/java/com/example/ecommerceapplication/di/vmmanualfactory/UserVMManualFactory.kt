package com.example.ecommerceapplication.di.vmmanualfactory

import androidx.lifecycle.SavedStateHandle
import com.example.data.session.SessionManager
import com.example.data.usecases.Authentication
import com.example.data.usecases.UserWishlist
import com.example.ecommerceapplication.di.ViewModelAssistedFactory
import com.example.ecommerceapplication.ui.user.UserViewModel
import javax.inject.Inject

class UserVMManualFactory @Inject constructor(
    private val authentication: Authentication,
    private val session: SessionManager,
    private val userWishlist: UserWishlist
) : ViewModelAssistedFactory<UserViewModel> {
    override fun create(stateHandle: SavedStateHandle): UserViewModel =
        UserViewModel(stateHandle, authentication, session, userWishlist)
}