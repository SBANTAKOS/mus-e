package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated

/**
 * Privacy Policy and Legal Terms Modal compliant with Google Play Store policies
 * and Google AdMob requirements. Trilingual: English, French, and Arabic.
 */
@Composable
fun PrivacyPolicyModal(
    onDismiss: () -> Unit
) {
    val lang = AppLanguageManager.currentLanguage

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, MuseumObsidianBorder, RoundedCornerShape(24.dp))
                .testTag("privacy_policy_modal"),
            color = DarkBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF282315),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = MuseumGoldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = when (lang) {
                                    AppLanguage.FRENCH -> "Politique & Mentions"
                                    AppLanguage.ARABIC -> "الخصوصية والشروط القانونية"
                                    else -> "Privacy & Legal Notice"
                                },
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MuseumGoldLight
                            )
                            Text(
                                text = when (lang) {
                                    AppLanguage.FRENCH -> "Conformité Google Play & AdMob"
                                    AppLanguage.ARABIC -> "متوافق مع سياسات Google Play"
                                    else -> "Google Play & AdMob Compliance"
                                },
                                fontSize = 11.sp,
                                color = Color(0xFF8E92A4)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("privacy_policy_close_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = MuseumObsidianBorder)
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        PolicySectionCard(
                            icon = Icons.Default.Lock,
                            title = when (lang) {
                                AppLanguage.FRENCH -> "1. Respect Absolu de la Vie Privée"
                                AppLanguage.ARABIC -> "١. احترام كامل للخصوصية والبيانات"
                                else -> "1. Absolute Privacy & Local Data"
                            },
                            body = when (lang) {
                                AppLanguage.FRENCH -> "L'application Musée Éthéré ne requiert aucune inscription obligatoire et ne collecte, ne stocke ni ne transmet aucune donnée personnelle nominative sur des serveurs tiers. Vos œuvres favorites, vos visites 360°, vos progrès éducatifs et vos notes restent 100% sur votre appareil."
                                AppLanguage.ARABIC -> "لا يتطلب تطبيق Musée Éthéré أي تسجيل إجباري، ولا يجمع أو يخزن أو ينقل أي بيانات شخصية إلى خوادم خارجية. تظل أعمالك المفضلة وجولاتك الافتراضية ونتائجك التعليمية محفوظة محلياً بالكامل على جهازك."
                                else -> "Musée Éthéré requires no mandatory account registration and does not collect, sell, or transmit personal identity data to external servers. Your saved artworks, 360° tour history, educational scores, and curator notes remain strictly on your device."
                            }
                        )
                    }

                    item {
                        PolicySectionCard(
                            icon = Icons.Default.Security,
                            title = when (lang) {
                                AppLanguage.FRENCH -> "2. Publicités & Google AdMob"
                                AppLanguage.ARABIC -> "٢. الإعلانات وخدمة Google AdMob"
                                else -> "2. Advertising & Google AdMob"
                            },
                            body = when (lang) {
                                AppLanguage.FRENCH -> "Pour soutenir la numérisation des œuvres et l'accès gratuit pour les étudiants, l'application intègre le SDK officiel Google Mobile Ads (AdMob). AdMob peut traiter l'identifiant publicitaire (AAID) et des données de diagnostic technique conformément aux règles de Google. Les annonces peuvent être totalement désactivées via le Pass VIP Mécène."
                                AppLanguage.ARABIC -> "لدعم رقمنة الأعمال الفنية وتوفير المحتوى مجاناً للطلاب، يستخدم التطبيق حزمة Google Mobile Ads (AdMob) الرسمية. قد تستخدم AdMob معرف الإعلانات (AAID) وفقاً لسياسات Google. يمكن تعطيل الإعلانات كلياً عبر اشتراك العضوية المميزة VIP."
                                else -> "To support the digital preservation of artworks and free access for students, the app integrates the official Google Mobile Ads (AdMob) SDK. AdMob may process advertising identifiers (AAID) and diagnostic data according to Google's Privacy Policies. All ads can be completely removed through the VIP Patron Pass."
                            }
                        )
                    }

                    item {
                        PolicySectionCard(
                            icon = Icons.Default.Public,
                            title = when (lang) {
                                AppLanguage.FRENCH -> "3. Droits Culturels & Patrimoine"
                                AppLanguage.ARABIC -> "٣. التراث الثقافي وحقوق الملكية"
                                else -> "3. Cultural Heritage & Educational Fair Use"
                            },
                            body = when (lang) {
                                AppLanguage.FRENCH -> "Les chefs-d'œuvre présentés (peintures, bas-reliefs, astrolabes islamiques et sculptures antiques) appartiennent au domaine public mondial ou sont reproduits à des fins d'éducation, de recherche et de vulgarisation artistique conformément aux accords de valorisation du patrimoine."
                                AppLanguage.ARABIC -> "التحف الفنية المعروضة (اللوحات والمنحوتات والأسطرلاب الإسلامي وبوابة عشتار) تعود إلى الملكية العامة العالمية أو يتم عرضها لأغراض تعليمية وثقافية وفقاً لقواعد نشر التراث الإنساني."
                                else -> "The featured masterpieces (classical paintings, ancient sculptures, Islamic astrolabes, and Babylonian reliefs) belong to the global public domain or are presented under educational fair-use principles to promote international art appreciation."
                            }
                        )
                    }

                    item {
                        PolicySectionCard(
                            icon = Icons.Default.Policy,
                            title = when (lang) {
                                AppLanguage.FRENCH -> "4. Autorisations & Capteurs"
                                AppLanguage.ARABIC -> "٤. الأذونات واستخدام المستشعرات"
                                else -> "4. Permissions & Device Sensors"
                            },
                            body = when (lang) {
                                AppLanguage.FRENCH -> "L'application utilise uniquement l'accès réseau pour le chargement des ressources culturelles et les vibrations pour le retour haptique 3D. Aucune autorisation intrusive (caméra, microphone, localisation géographique précise) n'est requise."
                                AppLanguage.ARABIC -> "يستخدم التطبيق اتصال الإنترنت فقط لتحميل الموارد الثقافية والاهتزاز للملاحظات اللمسية أثناء تدوير المجسمات 3D. لا نطلب أي أذونات حساسة كالكاميرا أو الميكروفون أو الموقع الجغرافي الدقيق."
                                else -> "The app only requests standard Internet access to load online cultural resources and device vibration for subtle haptic feedback during 3D sculpture interaction. Zero sensitive permissions (camera, microphone, precise GPS) are required."
                            }
                        )
                    }

                    item {
                        PolicySectionCard(
                            icon = Icons.Default.Gavel,
                            title = when (lang) {
                                AppLanguage.FRENCH -> "5. Contact & Support Éditeur"
                                AppLanguage.ARABIC -> "٥. التواصل والدعم الفني"
                                else -> "5. Publisher & Contact"
                            },
                            body = when (lang) {
                                AppLanguage.FRENCH -> "Éditeur: Studio Musée Éthéré\nVersion: 1.0 (Production)\nPour toute question relative aux données ou aux droits: contact@musee-ethere.art"
                                AppLanguage.ARABIC -> "الناشر: استوديو Musée Éthéré\nالإصدار: 1.0 (نسخة متجر Google Play)\nللتواصل والاستفسارات: contact@musee-ethere.art"
                                else -> "Publisher: Musée Éthéré Studio\nVersion: 1.0 (Release Build)\nData inquiries & support: contact@musee-ethere.art"
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Footer confirmation badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF131622),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = when (lang) {
                                AppLanguage.FRENCH -> "Conforme aux normes RGPD, CCPA et Google Play Developer Policies."
                                AppLanguage.ARABIC -> "متوافق مع معايير RGPD و CCPA وسياسات مطوري Google Play."
                                else -> "Compliant with GDPR, CCPA, and Google Play Developer Policies."
                            },
                            fontSize = 10.5.sp,
                            color = Color(0xFFB0B4C8),
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PolicySectionCard(
    icon: ImageVector,
    title: String,
    body: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(0.7.dp, MuseumObsidianBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MuseumGoldPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.5.sp,
                    color = MuseumGoldLight
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCBCFD8),
                fontSize = 11.5.sp,
                lineHeight = 16.sp
            )
        }
    }
}
