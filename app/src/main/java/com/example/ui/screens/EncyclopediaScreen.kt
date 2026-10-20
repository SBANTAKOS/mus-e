package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.ArtMovement
import com.example.model.ArtTechnique
import com.example.model.EncyclopediaRepository
import com.example.model.MasterBiography
import com.example.model.QuizQuestion
import com.example.model.SchoolDossier
import com.example.model.SchoolGrade
import com.example.model.SchoolRepository
import com.example.model.TimelineMilestone
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurface
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily

enum class EncyclopediaTab(val labelFr: String, val labelEn: String, val labelAr: String) {
    SCHOOL_RESOURCES("École CE1 • CM1 • CM2", "School Grades • CE1 • CM1 • CM2", "المناهج المدرسية للأطفال"),
    MOVEMENTS("Mouvements & Époques", "Art Movements & Eras", "المدارس الفنية والحركات"),
    TECHNIQUES("Glossaire des Techniques", "Techniques Glossary", "قاموس التقنيات والأساليب"),
    BIOGRAPHIES("Maîtres Illustres", "Master Biographies", "سير كبار المعلمين"),
    TIMELINE("Frise Chronologique", "Historical Timeline", "الخط الزمني التاريخي");

    val label: String get() = getLabel(AppLanguageManager.currentLanguage)

    fun getLabel(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> labelEn
        AppLanguage.ARABIC -> labelAr
        AppLanguage.FRENCH -> labelFr
    }
}

@Composable
fun EncyclopediaScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(EncyclopediaTab.SCHOOL_RESOURCES) }
    var searchQuery by remember { mutableStateOf("") }
    var schoolGradeFilter by remember { mutableStateOf(SchoolGrade.ALL_GRADES) }
    val currentLang = AppLanguageManager.currentLanguage

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .testTag("encyclopedia_screen")
    ) {
        // Top Header
        Surface(
            color = Color(0xFF0F1017),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "CODEX & ENCYCLOPEDIA OF ARTS"
                                AppLanguage.ARABIC -> "الموسوعة الفنية والمدارس العالمية"
                                AppLanguage.FRENCH -> "CODEX & ENCYCLOPÉDIE DES ARTS"
                            },
                            fontFamily = CinzelFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "Educational Resources & Universal Heritage"
                                AppLanguage.ARABIC -> "موارد تعليمية وتاريخ الفنون العالمية"
                                AppLanguage.FRENCH -> "Ressources Pédagogiques & Savoir Universel"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF8E919E)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "Search concepts, techniques, masters, school dossiers..."
                                AppLanguage.ARABIC -> "ابحث عن مفهوم، تقنية، معلم فني، ملف مدرسي..."
                                AppLanguage.FRENCH -> "Rechercher un concept, mot-clé, niveau scolaire..."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B6E7E)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Recherche",
                            tint = MuseumGoldPrimary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("search_encyclopedia_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MuseumGoldPrimary,
                        unfocusedBorderColor = MuseumObsidianBorder,
                        focusedContainerColor = MuseumObsidianSurface,
                        unfocusedContainerColor = MuseumObsidianSurface,
                        cursorColor = MuseumGoldPrimary,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Primary Category Tabs
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(EncyclopediaTab.values()) { tab ->
                        val isSelected = selectedTab == tab
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 12.sp,
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MuseumGoldPrimary,
                                selectedLabelColor = MuseumObsidian,
                                containerColor = MuseumObsidianSurfaceElevated,
                                labelColor = Color(0xFFC7C9D6)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) MuseumGoldPrimary else MuseumObsidianBorder,
                                selectedBorderColor = MuseumGoldPrimary
                            )
                        )
                    }
                }

                // If on SCHOOL_RESOURCES tab, display Grade Level sub-filter
                if (selectedTab == EncyclopediaTab.SCHOOL_RESOURCES) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(SchoolGrade.values()) { grade ->
                            val isSelected = schoolGradeFilter == grade
                            FilterChip(
                                selected = isSelected,
                                onClick = { schoolGradeFilter = grade },
                                label = {
                                    Text(
                                        text = grade.label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF1E3A5F),
                                    selectedLabelColor = Color(0xFF90CAF9),
                                    containerColor = Color(0xFF131722),
                                    labelColor = Color(0xFF9EA3B5)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) Color(0xFF4A90E2) else Color(0xFF232838),
                                    selectedBorderColor = Color(0xFF4A90E2)
                                )
                            )
                        }
                    }
                }
            }
        }

        // Encyclopedic Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (selectedTab) {
                EncyclopediaTab.SCHOOL_RESOURCES -> {
                    val filtered = SchoolRepository.dossiers.filter { dossier ->
                        val matchesGrade = schoolGradeFilter == SchoolGrade.ALL_GRADES || dossier.grade == schoolGradeFilter
                        val matchesSearch = searchQuery.isBlank() ||
                            dossier.title.contains(searchQuery, ignoreCase = true) ||
                            dossier.theme.contains(searchQuery, ignoreCase = true) ||
                            dossier.keywords.any { it.contains(searchQuery, ignoreCase = true) } ||
                            dossier.grade.code.equals(searchQuery.trim(), ignoreCase = true)

                        matchesGrade && matchesSearch
                    }

                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF151C2C),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF283A5A)),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = Color(0xFF90CAF9),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "ESPACE PÉDAGOGIQUE ÉCOLE PRIMAIRE",
                                        fontFamily = CinzelFontFamily,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF90CAF9),
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "Fiches d'activités, méthodologie d'exposé, vocabulaire et quiz interactifs adaptés aux programmes scolaires officiels (CE1, CM1, CM2).",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFCCD2E3),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    items(filtered) { dossier ->
                        SchoolDossierCard(dossier)
                    }
                }
                EncyclopediaTab.MOVEMENTS -> {
                    val filtered = EncyclopediaRepository.artMovements.filter {
                        searchQuery.isBlank() ||
                            it.name.contains(searchQuery, ignoreCase = true) ||
                            it.summary.contains(searchQuery, ignoreCase = true) ||
                            it.majorMasters.any { m -> m.contains(searchQuery, ignoreCase = true) }
                    }
                    items(filtered) { movement ->
                        ArtMovementCard(movement)
                    }
                }
                EncyclopediaTab.TECHNIQUES -> {
                    val filtered = EncyclopediaRepository.artTechniques.filter {
                        searchQuery.isBlank() ||
                            it.term.contains(searchQuery, ignoreCase = true) ||
                            it.definition.contains(searchQuery, ignoreCase = true) ||
                            it.pioneerArtist.contains(searchQuery, ignoreCase = true)
                    }
                    items(filtered) { technique ->
                        ArtTechniqueCard(technique)
                    }
                }
                EncyclopediaTab.BIOGRAPHIES -> {
                    val filtered = EncyclopediaRepository.masterBiographies.filter {
                        searchQuery.isBlank() ||
                            it.name.contains(searchQuery, ignoreCase = true) ||
                            it.movement.contains(searchQuery, ignoreCase = true) ||
                            it.biography.contains(searchQuery, ignoreCase = true)
                    }
                    items(filtered) { biography ->
                        MasterBiographyCard(biography)
                    }
                }
                EncyclopediaTab.TIMELINE -> {
                    val filtered = EncyclopediaRepository.timelineMilestones.filter {
                        searchQuery.isBlank() ||
                            it.title.contains(searchQuery, ignoreCase = true) ||
                            it.movement.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                    }
                    items(filtered) { milestone ->
                        TimelineMilestoneCard(milestone)
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolDossierCard(dossier: SchoolDossier) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("school_dossier_${dossier.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2C3246))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Grade & Cycle Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (dossier.grade) {
                        SchoolGrade.CE1 -> Color(0xFF1E3A5F)
                        SchoolGrade.CM1 -> Color(0xFF382A1B)
                        SchoolGrade.CM2 -> Color(0xFF243B2E)
                        else -> Color(0xFF2C2F40)
                    },
                    border = androidx.compose.foundation.BorderStroke(
                        0.8.dp,
                        when (dossier.grade) {
                            SchoolGrade.CE1 -> Color(0xFF64B5F6)
                            SchoolGrade.CM1 -> MuseumGoldPrimary
                            SchoolGrade.CM2 -> Color(0xFF81C784)
                            else -> Color(0xFF9FA2B4)
                        }
                    )
                ) {
                    Text(
                        text = "${dossier.grade.label} • ${dossier.grade.ageRange}",
                        style = MaterialTheme.typography.labelSmall,
                        color = when (dossier.grade) {
                            SchoolGrade.CE1 -> Color(0xFF90CAF9)
                            SchoolGrade.CM1 -> MuseumGoldLight
                            SchoolGrade.CM2 -> Color(0xFFA5D6A7)
                            else -> Color.White
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Déplier",
                        tint = MuseumGoldLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dossier.title,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = MuseumGoldLight
            )

            Text(
                text = "Thème : ${dossier.theme}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9E9FA8),
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dossier.introduction,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD6D8E4),
                lineHeight = 20.sp
            )

            // Keywords bar
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                dossier.keywords.take(4).forEach { kw ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF141724)
                    ) {
                        Text(
                            text = "#$kw",
                            fontSize = 9.sp,
                            color = Color(0xFF8E919E),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Expanded Curriculum Section, Questions, Vocabulary and Interactive Quiz
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MuseumObsidianBorder)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Curriculum goal
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF131826),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF263554)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "PROGRAMME SCOLAIRE OFFICIEL :",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF90CAF9),
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dossier.curriculumProgram,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFC0C7DC),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CE QU'IL FAUT RETENIR :",
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    dossier.keyPoints.forEach { point ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text(text = "• ", color = MuseumGoldPrimary, fontWeight = FontWeight.Bold)
                            Text(
                                text = point,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFD0D3DF),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Observation questions for class
                    Text(
                        text = "QUESTIONS D'OBSERVATION POUR LA CLASSE :",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF7EB0D5),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    dossier.questionsForObservation.forEachIndexed { i, q ->
                        Text(
                            text = "${i + 1}. $q",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFB5BAC9),
                            modifier = Modifier.padding(vertical = 2.dp),
                            lineHeight = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Vocabulary box
                    Text(
                        text = "BOÎTE À MOTS (VOCABULAIRE) :",
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    dossier.vocabularyWords.forEach { (word, def) ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "$word : ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MuseumGoldLight,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = def,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB0B3C4)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Suggested activity / exposé
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF282315),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "ACTIVITÉ OU EXPOSÉ CONSEILLÉ :",
                                style = MaterialTheme.typography.labelSmall,
                                color = MuseumGoldPrimary,
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dossier.suggestedActivity,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE4E4EB),
                                lineHeight = 17.sp
                            )
                        }
                    }

                    // Interactive Mini-Quiz
                    if (dossier.quiz.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "QUIZ INTERACTIF DU JEUNE CONSERVATEUR :",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF81C784),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        dossier.quiz.forEach { quizQuestion ->
                            InteractiveQuizItem(quizQuestion)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveQuizItem(question: QuizQuestion) {
    var selectedOption by remember { mutableIntStateOf(-1) }
    val isAnswered = selectedOption != -1

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF10131E),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262A3B)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = question.question,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            question.options.forEachIndexed { index, option ->
                val isCorrect = index == question.correctOptionIndex
                val isSelected = selectedOption == index

                val optionBg = when {
                    !isAnswered -> Color(0xFF1B1F2D)
                    isSelected && isCorrect -> Color(0xFF1B3B24)
                    isSelected && !isCorrect -> Color(0xFF3B1E1E)
                    isCorrect -> Color(0xFF1B3B24)
                    else -> Color(0xFF161924)
                }

                val optionBorder = when {
                    !isAnswered -> Color(0xFF2B3045)
                    isSelected && isCorrect -> Color(0xFF4CAF50)
                    isSelected && !isCorrect -> Color(0xFFE57373)
                    isCorrect -> Color(0xFF4CAF50)
                    else -> Color(0xFF232738)
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = optionBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, optionBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(enabled = !isAnswered) {
                            selectedOption = index
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${('A' + index)}. ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = MuseumGoldPrimary
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD6D8E4),
                            modifier = Modifier.weight(1f),
                            fontSize = 11.sp
                        )
                        if (isAnswered && isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF81C784),
                                modifier = Modifier.size(16.dp)
                            )
                        } else if (isAnswered && isSelected && !isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color(0xFFE57373),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            if (isAnswered) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = question.explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA5D6A7),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun ArtMovementCard(movement: ArtMovement) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF282315),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary)
                ) {
                    Text(
                        text = movement.dateRange,
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MuseumGoldLight
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movement.name,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MuseumGoldLight
            )

            Text(
                text = movement.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD0D2DE),
                modifier = Modifier.padding(top = 4.dp),
                lineHeight = 20.sp
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MuseumObsidianBorder)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "ANALYSE HISTORIQUE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movement.fullEssay,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFC0C2D0),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Maîtres Majeurs : ${movement.majorMasters.joinToString(", ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MuseumGoldLight,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Techniques Clés : ${movement.keyTechniques.joinToString(" • ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8E919E)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtTechniqueCard(technique: ArtTechnique) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = technique.term,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MuseumGoldLight
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1E212E)
                ) {
                    Text(
                        text = technique.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF7EB0D5),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = technique.definition,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD6D8E4),
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF141620),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "COMMENT ÇA MARCHE EN ATELIER :",
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = technique.howItWorks,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFB0B3C4),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pionnier : ${technique.pioneerArtist}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF8E919E)
            )
            Text(
                text = "Exemple dans le musée : ${technique.iconicExample}",
                style = MaterialTheme.typography.bodySmall,
                color = MuseumGoldLight
            )
        }
    }
}

@Composable
fun MasterBiographyCard(biography: MasterBiography) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = biography.name,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MuseumGoldLight
                    )
                    Text(
                        text = "${biography.lifeSpan} • ${biography.nationality}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8E919E)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF282315)
                ) {
                    Text(
                        text = biography.movement.split(" ").take(2).joinToString(" "),
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = biography.biography,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD6D8E4),
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Révolution Artistique :",
                style = MaterialTheme.typography.labelSmall,
                color = MuseumGoldPrimary,
                letterSpacing = 1.sp
            )
            Text(
                text = biography.contribution,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB0B3C4),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Famous quote banner
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF141620),
                border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF2B2E3E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = biography.famousQuote,
                        fontFamily = PlayfairFontFamily,
                        fontSize = 13.sp,
                        color = MuseumGoldLight,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineMilestoneCard(milestone: TimelineMilestone) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Milestone Year Badge & Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(80.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF282315),
                border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary)
            ) {
                Text(
                    text = milestone.yearRange,
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MuseumGoldPrimary,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .width(1.5.dp)
                    .height(60.dp)
                    .background(Color(0xFF383A4C))
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Content
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = milestone.title,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MuseumGoldLight
                )
                Text(
                    text = milestone.movement.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF7EB0D5),
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = milestone.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFC5C7D4),
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Œuvre emblématique : ${milestone.keyArtwork}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MuseumGoldPrimary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
