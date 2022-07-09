package com.example.ecommerceapplication.di

import com.example.data.di.PreferenceModule
import com.example.data.di.RoomModule
import com.example.ecommerceapplication.ui.cart.CartFragment
import com.example.ecommerceapplication.ui.cart.CheckoutFragment
import com.example.ecommerceapplication.ui.cart.OrderFragment
import com.example.ecommerceapplication.ui.home.HomeFragment
import com.example.ecommerceapplication.ui.home.SearchFragment
import com.example.ecommerceapplication.ui.home.products.CategoryFragment
import com.example.ecommerceapplication.ui.product.ProductFragment
import com.example.ecommerceapplication.ui.user.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferenceModule::class, AppModule::class, RoomModule::class])
interface AppComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: UserFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignupFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: WishlistFragment)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: CheckoutFragment)
    fun inject(fragment: CartFragment)
    fun inject(fragment: OrderFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: CategoryFragment)
}