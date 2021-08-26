package com.example.surfwidgetexample

/**
 * Модель факта про виджеты в Android 12
 *
 * @property title заголовок
 * @property text текст
 * @property opinion авторитетное мнение
 * @property url ссылка
 */
data class WidgetFact(
    val title: String,
    val text: String,
    val opinion: String,
    val url: String
)