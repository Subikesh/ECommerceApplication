package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.roomdb.EcommerceDatabase
import com.example.data.roomdb.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {
    private val database: EcommerceDatabase = Room.databaseBuilder(context, EcommerceDatabase::class.java, "demo-db").build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): EcommerceDatabase {
        return database
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