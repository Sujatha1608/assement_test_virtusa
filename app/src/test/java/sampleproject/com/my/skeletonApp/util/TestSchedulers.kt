package sampleproject.com.my.skeletonApp.util

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import sampleproject.com.my.skeletonApp.core.util.SchedulerProvider

class TestSchedulers() : SchedulerProvider {

    override val observeOn: Scheduler
        get() = Schedulers.trampoline()

    override val subscribeOn: Scheduler
        get() = Schedulers.trampoline()

    override val newThread: Scheduler
        get() = Schedulers.trampoline()
}
