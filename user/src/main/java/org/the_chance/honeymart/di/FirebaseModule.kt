package org.the_chance.honeymart.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.remote.network.FCMNotificationClickListener
import org.the_chance.honeymart.data.source.remote.network.FireBaseMessageService
import org.the_chance.honeymart.data.source.remote.network.FireBaseMsgServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FirebaseModule {
    @Singleton
    @Binds
    abstract fun bindFireBaseMsgService(fireBaseMsgService: FireBaseMsgServiceImpl): FireBaseMessageService
}

@Module
@InstallIn(SingletonComponent::class)
object NotificationHandler {
    @Singleton
    @Provides
    fun bindFCMNotificationClickListener(@ApplicationContext context: Context): FCMNotificationClickListener {
        return FCMNotificationClickListenerImp(context)
    }
}

