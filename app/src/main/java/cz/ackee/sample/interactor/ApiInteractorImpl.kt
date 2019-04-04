package cz.ackee.sample.interactor

import cz.ackee.ackroutine.CoroutineOAuthManager
import cz.ackee.ackroutine.core.OAuthCredentials
import cz.ackee.sample.model.SampleItem
import cz.ackee.sample.model.rest.ApiDescription
import cz.ackee.sample.model.rest.AuthApiDescription
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Implementation of api
 */
class ApiInteractorImpl(
    private val oAuthManager: CoroutineOAuthManager,
    private val apiDescription: ApiDescription,
    private val authApiDescription: AuthApiDescription
) : ApiInteractor {

    override fun login(name: String, password: String): Deferred<OAuthCredentials> {
        return GlobalScope.async(start = CoroutineStart.LAZY) {
            (authApiDescription.login(name, password).await()).also {
                oAuthManager.saveCredentials(it)
            }
        }
    }

    override fun getData(): Deferred<List<SampleItem>> = apiDescription.getData()

    override fun logout(): Deferred<Unit> = authApiDescription.logout()
}
