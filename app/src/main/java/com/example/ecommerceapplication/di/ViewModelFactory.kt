package com.example.ecommerceapplication.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(stateHandle: SavedStateHandle): T
}
//
//class ViewModelFactory<out V : ViewModel>(
//    private val viewModelFactory: ViewModelAssistedFactory<V>,
//    owner: SavedStateRegistryOwner,
//    defaultArgs: Bundle? = null
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
//        return viewModelFactory.create(handle) as T
//    }
//}

class ViewModelFactoryByInjection @Inject constructor(
    private val viewModelMap: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards ViewModelAssistedFactory<out ViewModel>>
) {
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return viewModelMap[modelClass]?.create(handle) as? T ?: throw IllegalStateException("Unknown ViewModel class")
            }
        }
    }
}

//@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
//@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
//@MapKey
//internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
