package com.example.ecommerceapplication.di.module

import android.content.Context
import androidx.room.Room
import com.example.data.roomdb.EcommerceDatabase
import com.example.data.roomdb.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providesEcommerceDatabase(@ApplicationContext context: Context): EcommerceDatabase {
        return Room.databaseBuilder(context, EcommerceDatabase::class.java, "demo-db").build()
    }

    @Singleton
    @Provides
    fun providesUserDao(database: EcommerceDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun providesProductDao(database: EcommerceDatabase): ProductDao {
        return database.productDao()
    }

    @Singleton
    @Provides
    fun providesCategoryDao(database: EcommerceDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun providesWishlistDao(database: EcommerceDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Singleton
    @Provides
    fun providesShoppingCartDao(database: EcommerceDatabase): ShoppingCartDao {
        return database.cartDao()
    }

    @Singleton
    @Provides
    fun providesOrdersDao(database: EcommerceDatabase): OrderDao {
        return database.orderDao()
    }


//    @Singleton
//    @Provides
//    fun productRepository(productDao: ProductDao?): ProductRepository {
//        return ProductDataSource(productDao)
//    }
}