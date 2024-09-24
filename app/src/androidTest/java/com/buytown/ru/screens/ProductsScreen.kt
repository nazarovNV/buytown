package com.buytown.ru.screens

import com.buytown.ru.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KTextView

object ProductsScreen : KScreen<ProductsScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val titleText = KTextView { withId(R.id.titleText) }


}