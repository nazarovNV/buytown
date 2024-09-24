package com.buytown.ru

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.buytown.ru.screens.LoginScreen
import com.buytown.ru.screens.ProductsScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class LoginFragmentTest  : TestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginSuccess() {
        LoginScreen {
            emailEditText.typeText("1@mail.ru")
            passwordEditText.typeText("1")
            loginButton {
                isVisible()
                click()
            }

        }
        ProductsScreen {
            titleText.isVisible()
        }
    }

    @Test
    fun loginFailed() {
        LoginScreen {
            emailEditText.typeText("1@mail.ru")
            passwordEditText.typeText("12")
            loginButton {
                isVisible()
                click()
            }
            errorText.isVisible()
        }

    }
}