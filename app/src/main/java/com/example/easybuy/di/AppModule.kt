package com.example.easybuy.di

import android.content.Context
import com.example.easybuy.feature.Auth.SigninWithAnIntent
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSignInClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    fun provideSigninWithAnIntent(
        @ApplicationContext context: Context,
        signInClient: SignInClient
    ): SigninWithAnIntent {
        return SigninWithAnIntent(context, signInClient)
    }
}