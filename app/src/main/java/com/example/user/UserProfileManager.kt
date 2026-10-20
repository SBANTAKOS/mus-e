package com.example.user

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class UserLevel(
    val levelNumber: Int,
    val titleFr: String,
    val titleEn: String,
    val minXp: Int,
    val maxXp: Int,
    val badgeColor: Color,
    val perks: List<String>
) {
    NOVICE(
        levelNumber = 1,
        titleFr = "Novice Curieux",
        titleEn = "Curious Novice",
        minXp = 0,
        maxXp = 150,
        badgeColor = Color(0xFFCD7F32), // Bronze
        perks = listOf(
            "Accès aux collections permanentes",
            "Audioguide binaural standard",
            "Résolution HD standard"
        )
    ),
    AMATEUR(
        levelNumber = 2,
        titleFr = "Amateur Éclairé",
        titleEn = "Enlightened Enthusiast",
        minXp = 151,
        maxXp = 450,
        badgeColor = Color(0xFFC0C0C0), // Silver
        perks = listOf(
            "Commentaires critiques d'experts débloqués",
            "Résolution Ultra-HD 4K débloquée",
            "Accès aux quiz pédagogiques CE1-CM2",
            "Shader 3D Bronze Antique"
        )
    ),
    CONNAISSEUR(
        levelNumber = 3,
        titleFr = "Connaisseur Érudit",
        titleEn = "Erudite Connoisseur",
        minXp = 451,
        maxXp = 900,
        badgeColor = Color(0xFFE5C378), // Vermeil Gold
        perks = listOf(
            "Visites 360° en mode Nuit & Atmosphère",
            "Shader 3D Marbre d'Or Impérial",
            "Téléchargement des fiches scolaires PDF",
            "Badge vérifié sur le Salon Privé"
        )
    ),
    CONSERVATEUR(
        levelNumber = 4,
        titleFr = "Conservateur Associé",
        titleEn = "Associate Curator",
        minXp = 901,
        maxXp = 1600,
        badgeColor = Color(0xFFFFD700), // Royal Gold
        perks = listOf(
            "Mode Restauration & Analyse aux rayons X",
            "Accès prioritaire aux nouvelles acquisitions",
            "Certificat d'Érudition Muséale officiel",
            "Fréquence des annonces réduite de 50%"
        )
    ),
    GRAND_MECENE(
        levelNumber = 5,
        titleFr = "Grand Mécène Illustre",
        titleEn = "Illustrious Grand Patron",
        minXp = 1601,
        maxXp = 3000,
        badgeColor = Color(0xFF64B5F6), // Diamond Azure
        perks = listOf(
            "Nom gravé au Panthéon des Mécènes Éthéré",
            "Toutes les fonctionnalités Master débloquées",
            "Gravure d'honneur dorée sur toutes les visites",
            "Invitation aux vernissages virtuels privés"
        )
    );

    companion object {
        fun fromXp(xp: Int): UserLevel {
            return when {
                xp >= GRAND_MECENE.minXp -> GRAND_MECENE
                xp >= CONSERVATEUR.minXp -> CONSERVATEUR
                xp >= CONNAISSEUR.minXp -> CONNAISSEUR
                xp >= AMATEUR.minXp -> AMATEUR
                else -> NOVICE
            }
        }
    }
}

enum class CulturalActivityType(
    val xpValue: Int,
    val labelFr: String,
    val icon: ImageVector
) {
    VIEW_ARTWORK(15, "Contemplation HD", Icons.Default.Collections),
    EXPLORE_3D(25, "Manipulation Sculpture 3D", Icons.Default.ViewInAr),
    VIRTUAL_TOUR(30, "Exploration Visite 360°", Icons.Default.Videocam),
    LISTEN_AUDIO(20, "Écoute Audioguide", Icons.Default.Headphones),
    ADD_FAVORITE(10, "Ajout au Salon Privé", Icons.Default.Star),
    STUDY_ENCYCLOPEDIA(15, "Recherche Pédagogique", Icons.Default.School),
    WATCH_REWARDED_AD(50, "Mécénat Sponsor AdMob", Icons.Default.AutoAwesome),
    DAILY_VISIT(40, "Visite Quotidienne", Icons.Default.WorkspacePremium)
}

data class ActivityHistoryItem(
    val id: String,
    val title: String,
    val xpEarned: Int,
    val formattedTime: String,
    val type: CulturalActivityType
)

data class UserProfileData(
    val userId: String,
    val displayName: String,
    val email: String,
    val isGoogleAuthenticated: Boolean,
    val memberNumber: String,
    val joinDate: String,
    val avatarResName: String = "avatar_statue",
    val patronHonorific: String = "Mécène des Arts"
)

class UserProfileManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("musee_user_profile_prefs", Context.MODE_PRIVATE)

    // Current authenticated user state
    var isUserSignedIn by mutableStateOf(prefs.getBoolean("user_is_signed_in", true))
        private set

    var displayName by mutableStateOf(prefs.getString("user_display_name", "Chan Ayoub") ?: "Chan Ayoub")
        private set

    var userEmail by mutableStateOf(prefs.getString("user_email", "chan.ayoub@gmail.com") ?: "chan.ayoub@gmail.com")
        private set

    var isGoogleAccount by mutableStateOf(prefs.getBoolean("user_is_google", true))
        private set

    var memberNumber by mutableStateOf(prefs.getString("user_member_number", "ETH-2026-8849") ?: "ETH-2026-8849")
        private set

    var joinDate by mutableStateOf(prefs.getString("user_join_date", "19 Octobre 2026") ?: "19 Octobre 2026")
        private set

    var patronHonorific by mutableStateOf(prefs.getString("user_honorific", "Mécène & Conservateur d'Honneur") ?: "Mécène & Conservateur d'Honneur")
        private set

    // Activity & Gamification State
    var totalXp by mutableIntStateOf(prefs.getInt("user_total_xp", 520)) // Starts at Level 3 Connaisseur
        private set

    var artworksContemplatedCount by mutableIntStateOf(prefs.getInt("stat_artworks_count", 14))
        private set

    var sculpturesRotatedCount by mutableIntStateOf(prefs.getInt("stat_sculptures_count", 6))
        private set

    var virtualToursCount by mutableIntStateOf(prefs.getInt("stat_tours_count", 4))
        private set

    var audioListenedCount by mutableIntStateOf(prefs.getInt("stat_audio_count", 9))
        private set

    var encyclopediaLookupsCount by mutableIntStateOf(prefs.getInt("stat_encyclopedia_count", 12))
        private set

    var streakDays by mutableIntStateOf(prefs.getInt("stat_streak_days", 3))
        private set

    // Notification toast when XP is earned
    var latestXpGainedNotification by mutableStateOf<String?>(null)
        private set

    // Activity log (in-memory + top entries)
    var recentActivities by mutableStateOf<List<ActivityHistoryItem>>(
        listOf(
            ActivityHistoryItem(
                id = "act_1",
                title = "Contemplation du tableau La Nuit Vénitienne en Ultra-HD",
                xpEarned = 15,
                formattedTime = "Il y a 5 min",
                type = CulturalActivityType.VIEW_ARTWORK
            ),
            ActivityHistoryItem(
                id = "act_2",
                title = "Rotation 3D & éclairage de la Vénus d'Aphrodite",
                xpEarned = 25,
                formattedTime = "Il y a 18 min",
                type = CulturalActivityType.EXPLORE_3D
            ),
            ActivityHistoryItem(
                id = "act_3",
                title = "Recherche Pédagogique Cycle 3 (CM1/CM2) - Renaissance",
                xpEarned = 15,
                formattedTime = "Il y a 1h",
                type = CulturalActivityType.STUDY_ENCYCLOPEDIA
            ),
            ActivityHistoryItem(
                id = "act_4",
                title = "Visite panoramique 360° de la Grande Galerie Napoléon",
                xpEarned = 30,
                formattedTime = "Aujourd'hui",
                type = CulturalActivityType.VIRTUAL_TOUR
            )
        )
    )
        private set

    val currentLevel: UserLevel
        get() = UserLevel.fromXp(totalXp)

    val currentLevelProgress: Float
        get() {
            val level = currentLevel
            if (level == UserLevel.GRAND_MECENE) return 1f
            val span = (level.maxXp - level.minXp).toFloat()
            val currentInLevel = (totalXp - level.minXp).coerceAtLeast(0).toFloat()
            return (currentInLevel / span).coerceIn(0f, 1f)
        }

    val xpToNextLevel: Int
        get() {
            val level = currentLevel
            return if (level == UserLevel.GRAND_MECENE) 0 else (level.maxXp - totalXp).coerceAtLeast(0)
        }

    fun logActivity(activityType: CulturalActivityType, itemTitle: String = "") {
        val earned = activityType.xpValue
        totalXp += earned

        when (activityType) {
            CulturalActivityType.VIEW_ARTWORK -> artworksContemplatedCount++
            CulturalActivityType.EXPLORE_3D -> sculpturesRotatedCount++
            CulturalActivityType.VIRTUAL_TOUR -> virtualToursCount++
            CulturalActivityType.LISTEN_AUDIO -> audioListenedCount++
            CulturalActivityType.STUDY_ENCYCLOPEDIA -> encyclopediaLookupsCount++
            else -> {}
        }

        saveStats()

        val timeStr = SimpleDateFormat("HH:mm", Locale.FRANCE).format(Date())
        val detail = if (itemTitle.isNotBlank()) " : $itemTitle" else ""
        val newEntry = ActivityHistoryItem(
            id = "act_${System.currentTimeMillis()}",
            title = "${activityType.labelFr}$detail",
            xpEarned = earned,
            formattedTime = timeStr,
            type = activityType
        )

        recentActivities = (listOf(newEntry) + recentActivities).take(15)
        latestXpGainedNotification = "+$earned XP • ${activityType.labelFr}"
    }

    fun dismissXpNotification() {
        latestXpGainedNotification = null
    }

    fun signInWithGoogle(name: String, email: String) {
        displayName = name
        userEmail = email
        isUserSignedIn = true
        isGoogleAccount = true
        memberNumber = "ETH-GOOG-${(1000..9999).random()}"
        saveProfile()
        logActivity(CulturalActivityType.DAILY_VISIT, "Connexion réussie avec Google")
    }

    fun signInAsCustom(name: String, email: String, honorific: String) {
        displayName = name.ifBlank { "Mécène Anonyme" }
        userEmail = email.ifBlank { "visiteur@musee-ethere.fr" }
        patronHonorific = honorific
        isUserSignedIn = true
        isGoogleAccount = false
        memberNumber = "ETH-MEC-${(1000..9999).random()}"
        saveProfile()
        logActivity(CulturalActivityType.DAILY_VISIT, "Identification Mécène enregistrée")
    }

    fun signOut() {
        displayName = "Visiteur Invité"
        userEmail = "non-identifié"
        isUserSignedIn = false
        isGoogleAccount = false
        saveProfile()
    }

    fun claimDailyBonus() {
        logActivity(CulturalActivityType.DAILY_VISIT, "Bonus Quotidien de Mécénat")
        streakDays++
        prefs.edit().putInt("stat_streak_days", streakDays).apply()
    }

    fun recordRewardedAdWatched() {
        logActivity(CulturalActivityType.WATCH_REWARDED_AD, "Vidéo Sponsorisée AdMob complétée")
    }

    private fun saveProfile() {
        prefs.edit()
            .putBoolean("user_is_signed_in", isUserSignedIn)
            .putString("user_display_name", displayName)
            .putString("user_email", userEmail)
            .putBoolean("user_is_google", isGoogleAccount)
            .putString("user_member_number", memberNumber)
            .putString("user_join_date", joinDate)
            .putString("user_honorific", patronHonorific)
            .apply()
    }

    private fun saveStats() {
        prefs.edit()
            .putInt("user_total_xp", totalXp)
            .putInt("stat_artworks_count", artworksContemplatedCount)
            .putInt("stat_sculptures_count", sculpturesRotatedCount)
            .putInt("stat_tours_count", virtualToursCount)
            .putInt("stat_audio_count", audioListenedCount)
            .putInt("stat_encyclopedia_count", encyclopediaLookupsCount)
            .apply()
    }
}
