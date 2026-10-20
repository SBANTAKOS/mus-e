package com.example.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class AppLanguage(
    val code: String,
    val title: String,
    val shortLabel: String,
    val flag: String,
    val subtitle: String
) {
    ENGLISH(
        code = "en",
        title = "English",
        shortLabel = "EN",
        flag = "🇺🇸",
        subtitle = "Digital Art Gallery & 3D Immersion"
    ),
    ARABIC(
        code = "ar",
        title = "العربية",
        shortLabel = "AR",
        flag = "🇦🇪",
        subtitle = "معرض الفنون الرقمي والانغماس ثلاثي الأبعاد"
    ),
    FRENCH(
        code = "fr",
        title = "Français",
        shortLabel = "FR",
        flag = "🇫🇷",
        subtitle = "Galerie Virtuelle & Immersion 3D"
    );

    val isRtl: Boolean get() = this == ARABIC
}

object AppLanguageManager {
    var currentLanguage by mutableStateOf(AppLanguage.ENGLISH)
        private set

    fun setLanguage(lang: AppLanguage) {
        currentLanguage = lang
    }

    fun cycleNextLanguage(): AppLanguage {
        val next = when (currentLanguage) {
            AppLanguage.ENGLISH -> AppLanguage.ARABIC
            AppLanguage.ARABIC -> AppLanguage.FRENCH
            AppLanguage.FRENCH -> AppLanguage.ENGLISH
        }
        currentLanguage = next
        return next
    }
}
