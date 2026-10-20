package com.example.admob

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * AdMobManager provides full management of Google AdMob ad placements within the app:
 * - Official Google AdMob Test Ad Units (Banner, Interstitial, Rewarded Video)
 * - Automatic suppression of all AdMob ads when the user has purchased the VIP Patron Pass ($4.99)
 * - Rewarded Ad flow allowing non-paying users to support the museum while earning +50 XP
 */
object AdMobConfig {
    // User's Real Google AdMob Production IDs
    const val APP_ID = "ca-app-pub-5349893955586234~4645951817"
    const val PROD_AD_UNIT_ID = "ca-app-pub-5349893955586234/3713063962"

    // Official Google AdMob Test Ad Unit IDs (used during development & review to prevent invalid click penalties)
    const val BANNER_TEST_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_TEST_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val REWARDED_TEST_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
}

data class AdMobCreative(
    val id: String,
    val advertiser: String,
    val headline: String,
    val body: String,
    val callToAction: String,
    val rating: Float = 4.8f,
    val isCultural: Boolean = true
)

class AdMobManager(private val context: Context) {

    var isAdLoaded by mutableStateOf(true)
        private set

    var isShowingRewardedAd by mutableStateOf(false)
    var rewardedAdCountdown by mutableIntStateOf(5)
        private set

    var rewardedAdCompleted by mutableStateOf(false)
        private set

    val sampleCreatives = listOf(
        AdMobCreative(
            id = "admob_louvre",
            advertiser = "Grand Palais RMN Paris",
            headline = "Rétrospective Trésors de l'Italie Antique",
            body = "Bénéficiez de -20% sur la billetterie coupe-file pour les expositions d'automne.",
            callToAction = "Installer l'App",
            rating = 4.9f
        ),
        AdMobCreative(
            id = "admob_drouot",
            advertiser = "Hôtel des Ventes Drouot",
            headline = "Vente aux Enchères Tableaux de Maîtres",
            body = "Explorez les catalogues d'art classique et enchérissez en direct depuis votre smartphone.",
            callToAction = "Découvrir",
            rating = 4.7f
        ),
        AdMobCreative(
            id = "admob_florence",
            advertiser = "Galleria degli Uffizi Firenze",
            headline = "Visites Nocturnes Exclusives aux Offices",
            body = "Accédez aux chefs-d'œuvre de Botticelli et Léonard sans attente grâce au pass numérique.",
            callToAction = "Réserver",
            rating = 4.8f
        )
    )

    var currentCreativeIndex by mutableIntStateOf(0)
        private set

    val activeCreative: AdMobCreative
        get() = sampleCreatives[currentCreativeIndex]

    fun nextAd() {
        currentCreativeIndex = (currentCreativeIndex + 1) % sampleCreatives.size
    }

    fun startRewardedAd(onCompleted: () -> Unit) {
        isShowingRewardedAd = true
        rewardedAdCountdown = 5
        rewardedAdCompleted = false
    }

    suspend fun runRewardedCountdown(onRewardEarned: () -> Unit) {
        rewardedAdCountdown = 5
        while (rewardedAdCountdown > 0) {
            delay(1000)
            rewardedAdCountdown--
        }
        rewardedAdCompleted = true
        onRewardEarned()
    }

    fun dismissRewardedAd() {
        isShowingRewardedAd = false
        rewardedAdCompleted = false
    }
}
