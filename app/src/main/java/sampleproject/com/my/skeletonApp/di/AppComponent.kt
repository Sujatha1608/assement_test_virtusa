package sampleproject.com.my.skeletonApp.di

import sampleproject.com.my.skeletonApp.AppApplication
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import sampleproject.com.my.skeletonApp.di.modules.*
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class),
    (ActivityBuilder::class), (NetworkModule::class),
    (ViewModelModules::class),(DomainModules::class)])

    interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppApplication)
}