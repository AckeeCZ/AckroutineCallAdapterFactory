package cz.ackee.sample.login

import cz.ackee.sample.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Presenter for login screen
 */
class LoginPresenter {

    private val apiInteractor = App.diContainer.apiInteractor
    private var view: ILoginView? = null

    fun login(name: String, password: String) {
        // for demo purposes only, ideally use scope of a viewModel
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.IO) {
                    apiInteractor.login(name, password).await()
                }

                onLoggedIn()
            } catch (e: Exception) {
                onErrorHappened(e)
            }
        }
    }

    fun onViewAttached(view: ILoginView) {
        this.view = view
    }

    fun onViewDetached() {
        this.view = null
    }

    private fun onLoggedIn() {
        view?.openDetail()
    }

    private fun onErrorHappened(throwable: Throwable) {
        throwable.printStackTrace()
    }
}