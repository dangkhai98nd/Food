package herbs.n.more.util

class Constant {

    companion object {
        const val SPLASH_TIME_OUT = 2000
        const val SPLASH_TIME_DELAY = 500

        const val NO_INTERNET = "2001"
        const val API_ERROR = "2002"
        const val FORGOT_PASS_OK = "1010"

        // Validation regex email and password
        const val EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                "{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
        const val PASSWORD_SPECIAL_REGEX = "^[a-zA-Z0-9@!\$#_\\d-]{8,20}\$"
        const val PASSWORD_VALIDATION_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z@!\$#_\\d-]{8,20}\$"
    }
}