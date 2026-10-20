package com.example.billing

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

data class CulturalAd(
    val id: String,
    val sponsorName: String,
    val headline: String,
    val description: String,
    val callToAction: String,
    val badge: String = "MÉCÉNAT & ANNONCE CULTURELLE"
)

enum class PurchaseFlowState {
    IDLE,
    SELECTING,
    PROCESSING,
    SUCCESS,
    ERROR
}

class MembershipManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("museum_membership_prefs", Context.MODE_PRIVATE)

    var isVipPatron by mutableStateOf(prefs.getBoolean("is_vip_patron_unlocked", false))
        private set

    var purchaseState by mutableStateOf(PurchaseFlowState.IDLE)
        private set

    var showGooglePlayBillingDialog by mutableStateOf(false)

    val currentPrice = "4,99 $"
    val productId = "com.aistudio.musee.ethere.vip_lifetime"
    val productName = "Pass Mécène Perpétuel VIP"

    val culturalAds = listOf(
        CulturalAd(
            id = "ad_drouot",
            sponsorName = "Hôtel Drouot & Christie's Paris",
            headline = "Vente aux Enchères Exceptionnelle de Tableaux Anciens",
            description = "Découvrez plus de 120 toiles de maîtres de la Renaissance et du Siècle d'Or hollandais présentées sous le marteau.",
            callToAction = "Consulter le Catalogue"
        ),
        CulturalAd(
            id = "ad_fondation_cartier",
            sponsorName = "Fondation Cartier pour l'Art",
            headline = "Exposition : Sculptures Vivantes & Matières Brutes",
            description = "Rétrospective monumentale autour du marbre, du bronze et de la modélisation spatiale contemporaine.",
            callToAction = "Réserver un Billet"
        ),
        CulturalAd(
            id = "ad_sothebys",
            sponsorName = "Sotheby's Geneva & Monaco",
            headline = "Vente Royale : Or Impérial & Trésors d'Antiquités",
            description = "Pièces uniques de joaillerie royale, statuettes hellénistiques et manuscrits enluminés.",
            callToAction = "Explorer la Vente"
        )
    )

    var currentAdIndex by mutableStateOf(0)

    fun nextAd() {
        currentAdIndex = (currentAdIndex + 1) % culturalAds.size
    }

    val activeAd: CulturalAd
        get() = culturalAds[currentAdIndex]

    fun openPurchaseFlow() {
        purchaseState = PurchaseFlowState.SELECTING
        showGooglePlayBillingDialog = true
    }

    fun dismissPurchaseFlow() {
        showGooglePlayBillingDialog = false
        purchaseState = PurchaseFlowState.IDLE
    }

    suspend fun executeGooglePlayPurchase(): Boolean {
        purchaseState = PurchaseFlowState.PROCESSING
        // Realistic Google Play In-App Billing network verification simulation
        delay(1400)
        isVipPatron = true
        prefs.edit().putBoolean("is_vip_patron_unlocked", true).apply()
        purchaseState = PurchaseFlowState.SUCCESS
        delay(1200)
        showGooglePlayBillingDialog = false
        purchaseState = PurchaseFlowState.IDLE
        return true
    }

    suspend fun restorePurchases(): Boolean {
        purchaseState = PurchaseFlowState.PROCESSING
        delay(1000)
        val wasUnlocked = prefs.getBoolean("is_vip_patron_unlocked", false)
        purchaseState = if (wasUnlocked) PurchaseFlowState.SUCCESS else PurchaseFlowState.IDLE
        delay(800)
        showGooglePlayBillingDialog = false
        return wasUnlocked
    }

    fun debugToggleVip() {
        isVipPatron = !isVipPatron
        prefs.edit().putBoolean("is_vip_patron_unlocked", isVipPatron).apply()
    }
}
