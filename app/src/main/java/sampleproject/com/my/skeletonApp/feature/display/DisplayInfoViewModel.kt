package sampleproject.com.my.skeletonApp.feature.display

import androidx.lifecycle.MutableLiveData
import sampleproject.com.my.skeletonApp.AppPreference
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.reactivex.rxjava3.kotlin.subscribeBy
import sampleproject.com.my.skeletonApp.core.event.SingleLiveEvent
import sampleproject.com.my.skeletonApp.rest.DatasetUseCase
import javax.inject.Inject


class DisplayInfoViewModel @Inject constructor( private val dataSettUseCase: DatasetUseCase, private val appPreference: AppPreference): ViewModel() {



    lateinit var callBack: ViewModelCallBack
    val isRecyclerviewUpdate = MutableLiveData<Boolean>(false)
    val errorEvent = MutableLiveData<String>()
    val loadingDialogEvent = SingleLiveEvent<Boolean>()

    var dataResultInfo: MutableList<DataResultResponse> = mutableListOf()
    val list = mutableListOf<DataResultResponse>()

    init {
//        setUserName()
    }
    interface ViewModelCallBack {
        fun updateRecyclerView(update: Boolean)

    }
    public fun setUserName() {
        loadingDialogEvent.postValue(true)
        dataSettUseCase.execute()
            .subscribeBy(
                onSuccess = {
                    Timber.d { "api $it" }
                    loadingDialogEvent.postValue(false)
                    for(i in it){
                        val model = DataResultResponse(i.title,i.body)
                        list.add(model)
                        if (dataResultInfo.isNotEmpty()) {
                            dataResultInfo.clear()
                        }
                        dataResultInfo.addAll(list)
                        isRecyclerviewUpdate.value=true
//                        callBack.updateRecyclerView(true)
                    }

                },
                onError = { e ->
                    errorEvent.postValue(e.localizedMessage ?: e.message ?: "Unknown error")
                    loadingDialogEvent.postValue(false)

                }
            )
    }
}