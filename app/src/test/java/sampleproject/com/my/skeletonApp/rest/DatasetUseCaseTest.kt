package sampleproject.com.my.skeletonApp.rest

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import sampleproject.com.my.skeletonApp.core.util.SchedulerProvider
import sampleproject.com.my.skeletonApp.rest.model.SampleDataResponse
import sampleproject.com.my.skeletonApp.util.TestSchedulers

class DatasetUseCaseTest {
    private lateinit var useCase: DatasetUseCase
    private val mockGateway: DataSetRepository = mock()
    private lateinit var schedulers: SchedulerProvider

    @Before
    fun setUp() {
        schedulers = TestSchedulers()
        useCase = DatasetUseCase(schedulers, mockGateway)
    }

    @Test
    fun `get the list of articles`() {

        //Given
        val domainItems = listOf<SampleDataResponse>(
            SampleDataResponse(
                userId = 1,
                id = 1,
                title = "unt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "uia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
            )
        )
        Mockito.`when`(mockGateway.getData()).thenReturn(Single.just(domainItems))

        //when

        val testObserver = useCase.execute().test()

        //should

        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(domainItems)

        Mockito.verify(mockGateway).getData()

    }
}