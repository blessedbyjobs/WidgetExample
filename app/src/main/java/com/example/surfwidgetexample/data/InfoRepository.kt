package com.example.surfwidgetexample.data

import android.content.Context

class InfoRepository(
    private val context: Context
) {

    /**
     * @return модель факта
     */
    fun getData(appWidgetId: Int): WidgetFact {
        val sp = SharedPrefHelper(appWidgetId)

        val currentCount = sp.getCount()
        sp.updateCount(currentCount)
        return getFactByCount(currentCount)
    }

    private fun getFactByCount(counter: Int): WidgetFact {
        return factList[counter]
    }

    private val factList = listOf(
        WidgetFact(
            title = "Закругления",
            text = "Добавлено закругление краев у background. Раз теперь виджеты будут скругленными, надо обратить внимание, чтобы контент не обрезался.",
            opinion = "- \"Сделаем вид, что здесь что-то оригинальное\"",
            url = "https://developer.android.com/about/versions/12/features/widgets#rounded-corner"
        ),
        WidgetFact(
            title = "Поддержка цветов темы",
            text = "Динамические цвета теперь можно использовать для любого компонента виджета.",
            opinion = "- \"Можно делать красоту\"",
            url = "https://developer.android.com/about/versions/12/features/widgets#dynamic-colors"
        ),
        WidgetFact(
            title = "Настройка виджета",
            text = "При долгом тапе на виджет мы как и раньше можем его настроить, но добавилась кнопка редактирования (если такая возможность включена в настройках).",
            opinion = "- \"Больше правок богу правок\"",
            url = "https://developer.android.com/about/versions/12/features/widgets#reconfigure-widgets"
        ),
        WidgetFact(
            title = "Дополнен список вьюх",
            text = "Добавлена поддержка чекбоксов, свичей и радиокнопок.",
            opinion = "- \"На пути к iOS\"",
            url = "https://developer.android.com/guide/topics/appwidgets#CreatingLayout"
        )
    )

    inner class SharedPrefHelper(private val appWidgetId: Int) {

        private val COUNT_KEY = "count"
        private val SHARED_PREF_FILE = "com.example.surfwidgetexample"

        private val prefs = context.getSharedPreferences(SHARED_PREF_FILE, 0)
        private val prefEditor = prefs.edit()

        /**
         * Получить номер факта для отображения из [SharedPreferences]
         */
        fun getCount(): Int {
            return prefs.getInt(COUNT_KEY + appWidgetId, 0)
        }

        /**
         * Обновить номер и записать его в [SharedPreferences]
         */
        fun updateCount(count: Int) {
            if (count == factList.size - 1) {
                prefEditor.putInt(COUNT_KEY + appWidgetId, 0)
            } else {
                prefEditor.putInt(COUNT_KEY + appWidgetId, count + 1)
            }
            prefEditor.apply()
        }
    }
}