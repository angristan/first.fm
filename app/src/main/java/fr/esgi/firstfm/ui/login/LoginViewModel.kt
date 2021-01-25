package fr.esgi.firstfm.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.firstfm.R
import fr.esgi.firstfm.data.LoginRepository
import fr.esgi.firstfm.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(activity: LoginActivity, username: String, password: String) {
        // can be launched in a separate asynchronous job
        CoroutineScope(IO).launch {
            val result = loginRepository.login(activity, username, password)

            withContext(Main) {
                if (result is Result.Success) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = result.data.userId))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }

        }
    }

    fun loginDataChanged(username: String, password: String) {
        Log.v("firstfm", "oui")
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}