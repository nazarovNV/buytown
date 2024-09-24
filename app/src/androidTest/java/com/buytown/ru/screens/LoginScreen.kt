package com.buytown.ru.screens

import com.buytown.ru.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton

object LoginScreen : KScreen<LoginScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val loginButton = KButton { withId(R.id.loginButton) }
    val emailEditText = KEditText { withId(R.id.emailEditText) }
    val passwordEditText = KEditText { withId(R.id.passwordEditText) }
    val errorText = KEditText { withId(R.id.errorText) }
}