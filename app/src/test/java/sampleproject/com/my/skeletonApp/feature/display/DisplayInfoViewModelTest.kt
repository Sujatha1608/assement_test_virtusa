package sampleproject.com.my.skeletonApp.feature.display

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import sampleproject.com.my.skeletonApp.AppPreference
import sampleproject.com.my.skeletonApp.rest.DatasetUseCase
import sampleproject.com.my.skeletonApp.rest.model.SampleDataResponse


@RunWith(MockitoJUnitRunner::class)
class DisplayInfoViewModelTest {
    @Mock
    private lateinit var datasetUseCase: DatasetUseCase

    private lateinit var displayInfoViewModel: DisplayInfoViewModel

    @Mock
    private lateinit var appPreference: AppPreference

    //to update the value of mutableLive data instantly
    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);
        this.displayInfoViewModel = DisplayInfoViewModel(
            datasetUseCase, appPreference
        )
    }

    @Test
    @Throws(Exception::class)
    fun `Given trending repo list, When call repo list, Should update repositoriesList`() {

        // Given

        val domainItems = listOf<SampleDataResponse>(
            SampleDataResponse(
                userId = 1,
                id = 1,
                title = "unt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "uia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
            )
        )

        val dataResult = listOf<DataResultResponse>(
            DataResultResponse(
                title = "unt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "uia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
            )
        )
        Mockito.`when`(datasetUseCase.execute()).thenReturn(Single.just(domainItems))

        // When

        displayInfoViewModel.setUserName()

        // Should
        assertThat(displayInfoViewModel.list, Is.`is`(dataResult))
        //to check the size of the list
        val expectedValue = 1// 2 ids

        assertNotNull(displayInfoViewModel.list)

        assertEquals(expectedValue, displayInfoViewModel.list.size)
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, When load repo list with error, Should update error`() {

        // Given

        val error = RuntimeException("Error emission")
        Mockito.`when`(datasetUseCase.execute()).thenReturn(Single.error(error))

        // When

        displayInfoViewModel.setUserName()

        // Should

        assertThat(displayInfoViewModel.errorEvent.value!!, Is.`is`(error.message))
    }

    @Test
    @Throws(Exception::class)
    fun `Given unknown error emission, When load repo list with unknown error, Should update error`() {

        // Given

        val error = "Unknown error"
//        Mockito.`when`(application.getString(ArgumentMatchers.anyInt())).thenReturn(error)
        Mockito.`when`(datasetUseCase.execute()).thenReturn(Single.error(RuntimeException()))

        // When

        displayInfoViewModel.setUserName()

        // Should

        assertThat(displayInfoViewModel.errorEvent.value!!, Is.`is`(error))
    }


}