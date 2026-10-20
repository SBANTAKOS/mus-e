package com.example.model

import androidx.annotation.DrawableRes
import com.example.R

enum class ArtworkCategory(
    val displayNameFr: String,
    val displayNameEn: String,
    val displayNameAr: String
) {
    ALL("Tous les Chefs-d'œuvre", "All Masterpieces", "جميع التحف الفنية"),
    MIDDLE_EAST_ISLAMIC("Art d'Orient & Islam", "Middle East & Islamic Art", "فن الشرق الأوسط والحضارة الإسلامية"),
    PAINTINGS("Peintures & Toiles", "Paintings & Canvases", "اللوحات والرسومات"),
    SCULPTURES_3D("Sculptures 3D", "3D Sculptures", "منحوتات ثلاثية الأبعاد"),
    TOURS_360("Visites Virtuelles", "Virtual Tours", "جولات افتراضية"),
    RENAISSANCE("Renaissance", "Renaissance", "عصر النهضة"),
    IMPRESSIONISM("Impressionnisme", "Impressionism", "المدرسة الانطباعية"),
    ANTIQUITY("Antiquités Classiques", "Classical Antiquities", "الآثار الكلاسيكية"),
    SCHOOL_CE1("École CE1", "Grade 2 / CE1", "المستوى الابتدائي CE1"),
    SCHOOL_CM1("École CM1", "Grade 4 / CM1", "المستوى الابتدائي CM1"),
    SCHOOL_CM2("École CM2", "Grade 5 / CM2", "المستوى الابتدائي CM2");

    fun getDisplayName(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> displayNameEn
        AppLanguage.ARABIC -> displayNameAr
        AppLanguage.FRENCH -> displayNameFr
    }
}

enum class SculptureType(
    val titleFr: String,
    val titleEn: String,
    val titleAr: String,
    val periodFr: String,
    val periodEn: String,
    val periodAr: String,
    val materialDefault: String
) {
    ISLAMIC_ASTROLABE(
        "Astrolabe Astronomique de Damas",
        "Astronomical Astrolabe of Damascus",
        "أسطرلاب دمشق الفلكي الملكي",
        "Âge d'Or Islamique (~1208)",
        "Islamic Golden Age (~1208 CE)",
        "العصر الذهبي للحضارة الإسلامية (~1208 م)",
        "Laiton Ciselé & Or d'Alep"
    ),
    APOLLO_BUST(
        "Buste d'Apollon du Belvédère",
        "Bust of Belvedere Apollo",
        "تمثال نصفي لأبولو بلفيدير",
        "Grèce Antique (~330 av. J.-C.)",
        "Ancient Greece (~330 BCE)",
        "اليونان القديمة (~330 ق.م)",
        "Marbre Blanc de Paros"
    ),
    VENUS_APHRODITE(
        "Vénus Céleste d'Alexandrie",
        "Celestial Venus of Alexandria",
        "فينوس السماوية السكندرية",
        "Période Hellénistique (~130 av. J.-C.)",
        "Hellenistic Period (~130 BCE)",
        "العصر الهلنستي (~130 ق.م)",
        "Marbre de Carrare"
    ),
    THE_THINKER(
        "Le Penseur Polyédrique",
        "The Polyhedral Thinker",
        "المفكر متعدد الأوجه",
        "Hommage Moderne (~1902)",
        "Modern Homage (~1902)",
        "تحية للفن الحديث (~1902)",
        "Bronze Ciselé & Patine"
    ),
    WINGED_VICTORY(
        "Victoire Ailée de Samothrace",
        "Winged Victory of Samothrace",
        "تمثال نصر ساموثراس المجنح",
        "Sanctuaire des Grands Dieux (~190 av. J.-C.)",
        "Sanctuary of the Great Gods (~190 BCE)",
        "ملاذ الآلهة العظام (~190 ق.م)",
        "Marbre de Thasos"
    ),
    MOBIUS_CONTEMPORARY(
        "Ruban d'Infini Géométrique",
        "Geometric Infinity Ribbon",
        "شريط اللانهاية الهندسي",
        "Art Spatial Contemporain (2024)",
        "Contemporary Spatial Art (2024)",
        "الفن الفضائي المعاصر (2024)",
        "Or Impérial Poli"
    );

    val title: String get() = getTitle(AppLanguageManager.currentLanguage)
    val period: String get() = getPeriod(AppLanguageManager.currentLanguage)

    fun getTitle(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> titleEn
        AppLanguage.ARABIC -> titleAr
        AppLanguage.FRENCH -> titleFr
    }

    fun getPeriod(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> periodEn
        AppLanguage.ARABIC -> periodAr
        AppLanguage.FRENCH -> periodFr
    }
}

enum class SculptureMaterial(
    val displayNameFr: String,
    val displayNameEn: String,
    val displayNameAr: String,
    val baseColorHex: Long,
    val specularStrength: Float
) {
    DAMASCUS_BRASS("Laiton Damasquiné", "Damascened Brass", "نحاس دمشقي مذهب", 0xFFC9A042, 0.85f),
    LAPIS_LAZULI("Lapis-lazuli de Babylone", "Babylon Lapis Lazuli", "لازورد بابل الخزفي", 0xFF193B78, 0.65f),
    CARRARA_MARBLE("Marbre de Carrare", "Carrara Marble", "رخام كرارا الإيطالي", 0xFFEDECE8, 0.4f),
    IMPERIAL_GOLD("Or Impérial 24K", "Imperial 24K Gold", "ذهب إمبراطوري عيار 24", 0xFFE2B743, 0.9f),
    BRONZE_PATINA("Bronze & Patine", "Bronze & Patina", "برونز معتق عالي الجودة", 0xFF6D533B, 0.5f),
    GLOWING_WIREFRAME("Filaire Holographique", "Holographic Wireframe", "شبكة ثلاثية الأبعاد هولوغرافية", 0xFF64B5F6, 0.8f);

    val displayName: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> displayNameEn
        AppLanguage.ARABIC -> displayNameAr
        AppLanguage.FRENCH -> displayNameFr
    }
}

data class RoomHotspot(
    val id: String,
    val xNormalized: Float,
    val yNormalized: Float,
    val title: String,
    val era: String,
    val artworkId: String
)

data class VirtualRoom(
    val id: String,
    val name: String,
    val wingName: String,
    val floor: String,
    val description: String,
    @DrawableRes val panoramaRes: Int,
    val ambientMood: String,
    val hotspots: List<RoomHotspot>
)

data class Artwork(
    val id: String,
    val titleFr: String,
    val titleEn: String,
    val titleAr: String,
    val artistFr: String,
    val artistEn: String,
    val artistAr: String,
    val year: String,
    val periodFr: String,
    val periodEn: String,
    val periodAr: String,
    val mediumFr: String,
    val mediumEn: String,
    val mediumAr: String,
    val dimensions: String,
    val wingFr: String,
    val wingEn: String,
    val wingAr: String,
    val roomNumber: String,
    @DrawableRes val imageRes: Int,
    val descriptionFr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val curatorialNotesFr: String,
    val curatorialNotesEn: String,
    val curatorialNotesAr: String,
    val provenanceFr: String,
    val provenanceEn: String,
    val provenanceAr: String,
    val audioNarrationFr: String,
    val audioNarrationEn: String,
    val audioNarrationAr: String,
    val has3DModel: Boolean = false,
    val sculptureType: SculptureType? = null,
    val isFeatured: Boolean = false,
    val categories: List<ArtworkCategory>,
    val keywords: List<String> = emptyList(),
    val suitableGrades: List<String> = listOf("CE1", "CM1", "CM2")
) {
    // Backward-compatible properties defaulting to active language
    val title: String get() = getTitle(AppLanguageManager.currentLanguage)
    val artist: String get() = getArtist(AppLanguageManager.currentLanguage)
    val period: String get() = getPeriod(AppLanguageManager.currentLanguage)
    val medium: String get() = getMedium(AppLanguageManager.currentLanguage)
    val wing: String get() = getWing(AppLanguageManager.currentLanguage)
    val description: String get() = getDescription(AppLanguageManager.currentLanguage)
    val curatorialNotes: String get() = getCuratorialNotes(AppLanguageManager.currentLanguage)
    val provenance: String get() = getProvenance(AppLanguageManager.currentLanguage)

    fun getTitle(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> titleEn
        AppLanguage.ARABIC -> titleAr
        AppLanguage.FRENCH -> titleFr
    }

    fun getArtist(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> artistEn
        AppLanguage.ARABIC -> artistAr
        AppLanguage.FRENCH -> artistFr
    }

    fun getPeriod(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> periodEn
        AppLanguage.ARABIC -> periodAr
        AppLanguage.FRENCH -> periodFr
    }

    fun getMedium(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> mediumEn
        AppLanguage.ARABIC -> mediumAr
        AppLanguage.FRENCH -> mediumFr
    }

    fun getWing(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> wingEn
        AppLanguage.ARABIC -> wingAr
        AppLanguage.FRENCH -> wingFr
    }

    fun getDescription(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> descriptionEn
        AppLanguage.ARABIC -> descriptionAr
        AppLanguage.FRENCH -> descriptionFr
    }

    fun getCuratorialNotes(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> curatorialNotesEn
        AppLanguage.ARABIC -> curatorialNotesAr
        AppLanguage.FRENCH -> curatorialNotesFr
    }

    fun getProvenance(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> provenanceEn
        AppLanguage.ARABIC -> provenanceAr
        AppLanguage.FRENCH -> provenanceFr
    }

    fun getAudioNarration(lang: AppLanguage = AppLanguageManager.currentLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> audioNarrationEn
        AppLanguage.ARABIC -> audioNarrationAr
        AppLanguage.FRENCH -> audioNarrationFr
    }
}

object MuseumRepository {

    val virtualRooms: List<VirtualRoom> = listOf(
        VirtualRoom(
            id = "room_grand_gallery",
            name = "La Grande Galerie des Glaces & Maîtres",
            wingName = "Aile Sully - Salon Carré",
            floor = "Niveau 1",
            description = "Voûtes d'or, lustres de cristal et perspective infinie abritant les plus somptueuses toiles de la Renaissance italienne et flamande.",
            panoramaRes = R.drawable.img_grand_gallery,
            ambientMood = "Écho solennel & pas feutrés sur parquet ciré",
            hotspots = listOf(
                RoomHotspot("hs_1", 0.22f, 0.52f, "La Muse Céleste", "Renaissance", "art_muse"),
                RoomHotspot("hs_2", 0.50f, 0.46f, "Perspective des Maîtres", "Architecture Royale", "art_gallery"),
                RoomHotspot("hs_3", 0.78f, 0.54f, "Vénus Céleste", "Antiquité", "art_venus")
            )
        ),
        VirtualRoom(
            id = "room_sculpture_atrium",
            name = "Le Sanctuaire d'Apollon & Marbres Antiques",
            wingName = "Cour Puget & Antiquités",
            floor = "Rez-de-Chaussée",
            description = "Atrium baigné d'une verrière zénithale où la lumière naturelle caresse les contours des chefs-d'œuvre de la statuaire grecque et romaine.",
            panoramaRes = R.drawable.img_venus_sculpture,
            ambientMood = "Fontaine murmurante & sérénité minérale",
            hotspots = listOf(
                RoomHotspot("hs_4", 0.35f, 0.40f, "Vénus Aphrodite", "Hellénistique", "art_venus"),
                RoomHotspot("hs_5", 0.65f, 0.45f, "Buste d'Apollon 3D", "Art Grec", "art_apollo"),
                RoomHotspot("hs_6", 0.85f, 0.42f, "Astrolabe de Damas 3D", "Art Islamique", "art_islamic_astrolabe")
            )
        ),
        VirtualRoom(
            id = "room_impressionist_haven",
            name = "Le Pavillon des Étoiles & Impressionnistes",
            wingName = "Aile d'Orsay & Nymphéas",
            floor = "Niveau 2",
            description = "Espace intimiste aux cimaises bleu nuit mettant en valeur la vibration lumineuse des maîtres du plein air et de la nuit.",
            panoramaRes = R.drawable.img_impressionist_art,
            ambientMood = "Accords de harpe impressionniste & brise nocturne",
            hotspots = listOf(
                RoomHotspot("hs_7", 0.50f, 0.48f, "Nuit Vénitienne Étoilée", "Impressionnisme", "art_venetian_night")
            )
        )
    )

    val artworks: List<Artwork> = listOf(
        // 1. ISLAMIC ASTROLABE (Middle East & Islamic Art, with 3D model)
        Artwork(
            id = "art_islamic_astrolabe",
            titleFr = "L'Astrolabe Royal de Damas & d'Andalousie",
            titleEn = "The Royal Astrolabe of Damascus & Andalusia",
            titleAr = "أسطرلاب دمشق والأندلس الفلكي الملكي",
            artistFr = "Badr al-Din al-Dimashqi (Maître des Arts Célestes)",
            artistEn = "Badr al-Din al-Dimashqi (Master of Celestial Arts)",
            artistAr = "بدر الدين الدمشقي (صانع الآلات الفلكية)",
            year = "1208 après J.-C. (605 H)",
            periodFr = "Âge d'Or des Sciences & Arts Islamiques",
            periodEn = "Islamic Golden Age of Science & Arts",
            periodAr = "العصر الذهبي للعلوم والفنون الإسلامية",
            mediumFr = "Laiton coulé et ciselé, dorure au feu d'Alep, inscriptions coufiques et naskhi",
            mediumEn = "Cast & chased brass, Aleppo fire gilding, engraved Kufic and Naskh scripts",
            mediumAr = "نحاس مصبوب ومنقوش، مطلي بذهب حلب الخالص، نقوش كوفية ونسخية دقيقة",
            dimensions = "Diamètre 34 cm • Épaisseur 1.4 cm • Poids 2.8 kg",
            wingFr = "Département des Arts de l'Islam & Civilisations d'Orient",
            wingEn = "Department of Islamic Arts & Eastern Civilizations",
            wingAr = "جناح الفنون الإسلامية وحضارات الشرق",
            roomNumber = "Salle Al-Biruni (Salle 42)",
            imageRes = R.drawable.img_islamic_astrolabe,
            descriptionFr = "Véritable joyau de la civilisation arabo-islamique, cet astrolabe planisphérique réunit science céleste, virtuosité calligraphique et géométrie sacrée. Utilisé par les astronomes, navigateurs et savants de Damas, Bagdad et Cordoue pour cartographier les astres et mesurer le temps.",
            descriptionEn = "A masterpiece of Arab-Islamic civilization, this planispheric astrolabe merges celestial science, calligraphic virtuosity, and sacred geometry. Used by astronomers and navigators from Damascus, Baghdad, and Cordoba to map constellations, determine latitude, and measure solar time.",
            descriptionAr = "تحفة فريدة من روائع الحضارة العربية الإسلامية تجمع بين العلوم الفلكية وبراعة الخط العربي والهندسة الزخرفية الدقيقة. كان هذا الأسطرلاب المسطح رفيق علماء الفلك والرحالة في دمشق وبغداد وقرطبة لرصد مواقع النجوم وتحديد خطوط العرض وحساب التوقيت الشمسي بدقة متناهية.",
            curatorialNotesFr = "L'araignée ajourée (rete) comporte 29 pointes stellaires en forme de flammes et de motifs végétaux calligraphiques. Le trône (kursi) est orné d'entrelacs arabesques d'une finesse spectaculaire. Observez dans la vue 3D la graduation à 360 degrés gravée à la pointe diamant.",
            curatorialNotesEn = "The pierced openwork rete (spider) features 29 flame-shaped star pointers and delicate arabesque foliate tendrils. The kursi (throne) is engraved with magnificent interlaced arabesques. Explore the 3D model to inspect the 360-degree graduated rim etched with diamond-point tools.",
            curatorialNotesAr = "تتضمن شبكة العنكبوت المفرغة 29 مؤشراً نجمياً على شكل ألسنة لهب وزخارف نباتية بديعة. يزدان الكرسي العلوي بنقوش أرابيسك مفرغة متقنة للغاية. يمكنك عبر عارض المجسمات ثلاثي الأبعاد تفحص التدرجات المحفورة بدقة فائقة على حافة الأسطرلاب بزاوية 360 درجة.",
            provenanceFr = "Commandé par le sultan ayyoubide de Damas, conservé à l'observatoire royal puis transmis aux collections d'État.",
            provenanceEn = "Commissioned by the Ayyubid Sultan of Damascus, preserved in the royal observatory before entering national collections.",
            provenanceAr = "صُنِع بطلب من سلطان دمشق الأيوبي وحُفِظ في المرصد الفلكي الملكي قبل انتقاله إلى المجموعات الفنية العالمية.",
            audioNarrationFr = "Bienvenue devant l'Astrolabe Royal de Damas. Ce chef-d'œuvre incarne le génie de l'âge d'or islamique, où la science et la beauté ne faisaient qu'un. Grâce à ses disques interchangeables, cet instrument permettait de lire le ciel nocturne de Damas jusqu'à l'Andalousie.",
            audioNarrationEn = "Welcome to the Royal Astrolabe of Damascus. This extraordinary instrument embodies the genius of the Islamic Golden Age, where science and aesthetic beauty converged. With its interchangeable plates, astronomers could decipher constellations from Damascus all the way to Andalusia.",
            audioNarrationAr = "أهلاً بكم أمام أسطرلاب دمشق الفلكي الملكي. يجسد هذا الإنجاز عبقرية العصر الذهبي للحضارة الإسلامية حين تلاقت دقة العلوم مع سحر الفنون الزخرفية. كانت هذه الآلة المعقدة تتيح لعلماء الفلك قراءة صفحات السماء والنجوم من دمشق وبغداد وصولاً إلى غرناطة وقرطبة.",
            has3DModel = true,
            sculptureType = SculptureType.ISLAMIC_ASTROLABE,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.MIDDLE_EAST_ISLAMIC, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.SCHOOL_CM1, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CE1", "CM1", "CM2", "astrolabe", "islam", "damas", "andalousie", "etoiles", "orient", "moyen-orient", "astronomie", "or", "cuivre", "الشرق الأوسط", "أسطرلاب", "فن إسلامي", "ذهب", "نجوم", "فلك", "عرب"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 2. ALHAMBRA COURT OF THE LIONS & MUQARNAS (Middle East & Islamic Art)
        Artwork(
            id = "art_alhambra_court",
            titleFr = "La Cour des Lions & Muqarnas de l'Alhambra",
            titleEn = "Court of the Lions & Muqarnas of Alhambra",
            titleAr = "بهو السباع ومقرنصات قصر الحمراء",
            artistFr = "Architectes & Maîtres Artisans Nasrides de Grenade",
            artistEn = "Nasrid Architects & Master Artisans of Granada",
            artistAr = "مهندسو وبنّاؤو الدولة النصرية بغرناطة",
            year = "1370 – 1391 après J.-C.",
            periodFr = "Art Islamique Hispano-Mauresque (Andalousie)",
            periodEn = "Hispano-Moresque Islamic Art (Al-Andalus)",
            periodAr = "الفن الإسلامي الأندلسي (غرناطة)",
            mediumFr = "Stucs sculptés en nids d'abeille (muqarnas), marbre blanc de Macael, céramiques zellige polychromes",
            mediumEn = "Carved honeycomb muqarnas stucco, Macael white marble, polychrome zellige mosaic tiles",
            mediumAr = "مقرنصات جصية هندسية على شكل خلايا النحل، رخام ماكاييل الأبيض، فسيفساء زليج أندلسي ملون",
            dimensions = "Cour monumentale 35 m × 20 m • 124 colonnes de marbre",
            wingFr = "Galerie des Merveilles Architecturales d'Orient",
            wingEn = "Eastern Architectural Wonders Gallery",
            wingAr = "معرض روائع العمارة الشرقية والإسلامية",
            roomNumber = "Cour Nasride (Salle 38)",
            imageRes = R.drawable.img_alhambra_palace,
            descriptionFr = "Sommet absolu de l'architecture nasride en Andalousie, la Cour des Lions symbolise le jardin paradisiaque céleste. Douze lions de marbre supportent un bassin central d'où s'élancent quatre canaux d'eau vive représentant les quatre fleuves du Paradis.",
            descriptionEn = "The zenith of Nasrid architectural mastery in Moorish Spain, the Court of the Lions embodies the celestial paradise garden. Twelve marble lions support a central fountain basin from which four crystal channels flow, symbolizing the four rivers of paradise.",
            descriptionAr = "قمة الإبداع المعماري النصري في الأندلس الإسلامية، يجسد بهو السباع صورة الفردوس الأرضي بأسلوبه البديع. اثنا عشر أسداً منحوتاً من الرخام الصافي يحملون الحوض المركزي الذي تنبثق منه أربعة قنوات مائية ترمز لانهار الجنة الأربعة.",
            curatorialNotesFr = "Les arcades en plein cintre et stalactites de muqarnas créent un jeu d'ombre et de lumière continu. Les bandeaux d'épigraphie coufique et thuluth gravent dans la pierre des poèmes d'Ibn Zamrak et la devise 'Wa la Ghaliba illa Allah' (Nul vainqueur sinon Dieu).",
            curatorialNotesEn = "The slender colonnade and cascading muqarnas stalactites filter sunlight into delicate laces of shadow. Flowing Arabic calligraphic bands engrave verses of court poet Ibn Zamrak and the renowned motto 'Wa la Ghaliba illa Allah' (There is no victor but God).",
            curatorialNotesAr = "تخلق الأعمدة الرخامية الرشيقة وتدليات المقرنصات ستائر ضوئية ساحرة ترشح نور الشمس كأنه خيوط من ذهب. تزين الجدران أشرطة من الخطين الكوفي والثلث تضم أبيات الشاعر لسان الدين بن الخطيب وابن زمرك وشعار 'ولا غالب إلا الله'.",
            provenanceFr = "Palais Royal de l'Alhambra, Grenade. Patrimoine universel de l'humanité UNESCO.",
            provenanceEn = "Royal Alhambra Palace, Granada. UNESCO World Heritage Masterpiece.",
            provenanceAr = "قصر الحمراء الملكي، غرناطة، إسبانيا. تحفة التراث العالمي لليونسكو.",
            audioNarrationFr = "Entrez dans la féérie de l'Alhambra. Le murmure de l'eau sur le marbre blanc se mêle à la dentelle des stucs sculptés. Ici, la géométrie islamique transcende la matière pour faire de la cour un havre de paix spirituelle et de beauté absolue.",
            audioNarrationEn = "Step into the enchanted realm of the Alhambra. The gentle murmur of water across cool white marble blends with intricate carved stucco lace. Here, Islamic geometry transcends physical matter to create an oasis of serene contemplation and beauty.",
            audioNarrationAr = "ادخلوا إلى سحر قصر الحمراء في غرناطة. يتهادى خرير المياه الرقراقة فوق الرخام الأبيض ليتناغم مع نقوش الجص المزخرفة كالدانتيل. هنا تتحول الهندسة الإسلامية والخط العربي إلى واحة من السكينة الروحية والجمال الخالد.",
            has3DModel = false,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.MIDDLE_EAST_ISLAMIC, ArtworkCategory.TOURS_360, ArtworkCategory.SCHOOL_CM1, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CM1", "CM2", "CE1", "Alhambra", "Grenade", "Andalousie", "Espagne", "Islam", "palais", "lions", "muqarnas", "zellige", "stuc", "eau", "fontaine", "arabesque", "قصر الحمراء", "الأندلس", "غرناطة", "مقرنصات", "زليج", "شرق"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 3. ISHTAR GATE OF BABYLON (Middle East & Mesopotamia)
        Artwork(
            id = "art_ishtar_gate",
            titleFr = "La Porte d'Ishtar de Babylone Céleste",
            titleEn = "The Celestial Ishtar Gate of Babylon",
            titleAr = "بوابة عشتار البابلية العظيمة",
            artistFr = "Bâtisseurs & Émailleurs de l'Empire Néo-Babylonien",
            artistEn = "Builders & Enamel Masters of the Neo-Babylonian Empire",
            artistAr = "بنّاؤو وخزافو الإمبراطورية البابلية الحديثة",
            year = "~575 avant J.-C.",
            periodFr = "Mésopotamie Antique & Règne de Nabuchodonosor II",
            periodEn = "Ancient Mesopotamia & Reign of Nebuchadnezzar II",
            periodAr = "بلاد الرافدين القديمة وعهد الملك نبوخذ نصر الثاني",
            mediumFr = "Briques d'argile cuite à glaçure au lapis-lazuli, bas-reliefs de lions et mušḫuššu dorés",
            mediumEn = "Baked clay bricks with lapis lazuli cobalt glaze, golden relief lions and mušḫuššu dragons",
            mediumAr = "آجر طيني مفخور مطلي بالمينا اللازوردية الزرقاء، نقوش بارزة لأسود وتنانين المشخوشو الذهبية",
            dimensions = "Hauteur monumentale 14.5 m • Largeur 30 m",
            wingFr = "Département des Antiquités Orientales & Mésopotamie",
            wingEn = "Department of Near Eastern Antiquities & Mesopotamia",
            wingAr = "جناح الآثار الشرقية القديمة وبلاد الرافدين",
            roomNumber = "Grande Cour de Babylone (Salle 10)",
            imageRes = R.drawable.img_ishtar_gate,
            descriptionFr = "Huitième porte fortifiée de la cité légendaire de Babylone, dédiée à la déesse Ishtar. Ses briques vernissées d'un bleu cobalt hypnotique évoquent le lapis-lazuli sacré, tandis que des dizaines de lions dorés avancent en procession protectrice vers la Voie Processionnelle.",
            descriptionEn = "The grand eighth fortified gateway to the legendary city of Babylon, dedicated to goddess Ishtar. Its glazed bricks gleam with hypnotic cobalt blue evoking sacred lapis lazuli, while dozens of relief lions advance in majestic protective procession.",
            descriptionAr = "البوابة الثامنة المحصنة لمدينة بابل التاريخية العظيمة، المهداة للإلهة عشتار. تتلألأ جدرانها بآجر مزجج بلون أزرق كحلي أخّاذ يحاكي حجر اللازورد المقدس، وتصطف على جانبيها مواكب من الأسود الذهبية المهيبة التي تحرس طريق الموكب الملكي.",
            curatorialNotesFr = "L'émaillage babylonien au cobalt et à l'oxyde de cuivre a conservé sa brillance pendant plus de deux millénaires et demi. Les animaux mythologiques (lions d'Ishtar, taureaux d'Adad et dragons mušḫuššu de Mardouk) symbolisent la puissance impériale universelle.",
            curatorialNotesEn = "The Babylonian cobalt glaze technique has preserved its intense vitreous luster for over two and a half millennia. Mythological beasts—Ishtar's lions, Adad's bulls, and Marduk's dragon mušḫuššu—symbolized divine protection and imperial majesty.",
            curatorialNotesAr = "حافظ طلاء المينا البابلي المصنوع من الكوبالت وأكسيد النحاس على بريقه الأخّاذ لأكثر من ألفين وخمسمائة عام. تمثل الكائنات الرمزية المنحوتة (أسد عشتار، ثور أدد، وتنين مشخوشو الخاص بمردوخ) القوة الإلهية وحماية المدينة الخالدة.",
            provenanceFr = "Fouilles archéologiques de Babylone (Irak moderne). Chef-d'œuvre fondateur des civilisations du Moyen-Orient.",
            provenanceEn = "Excavated at ancient Babylon (modern Iraq). Foundational masterpiece of Near Eastern civilizations.",
            provenanceAr = "اكتشفت في موقع بابل التاريخي في العراق. إحدى أعظم تحف الشرق الأوسط القديم وعجائب الدنيا.",
            audioNarrationFr = "Contemplez la Porte d'Ishtar de Babylone. Sous le soleil de Mésopotamie, ce bleu lapis-lazuli étincelant annonçait aux voyageurs l'entrée dans la plus fabuleuse métropole de l'Antiquité orientale. Les lions rugissants semblent encore marcher sous vos yeux.",
            audioNarrationEn = "Gaze upon the Ishtar Gate of Babylon. Under the blazing Mesopotamian sun, this luminous lapis blue proclaimed entrance to the most fabulous metropolis of the ancient Near East. The golden relief lions stride forward in eternal power.",
            audioNarrationAr = "تأملوا بوابة عشتار الأسطورية في بابل. تحت شمس بلاد الرافدين المشرقة، كان هذا اللون الأزرق اللازوردي الساحر يعلن للقوافل والمسافرين دخولهم إلى أعظم عاصمة في الشرق القديم. تمشي الأسود البابلية الذهبية أمامكم في موكب خالد عبر العصور.",
            has3DModel = false,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.MIDDLE_EAST_ISLAMIC, ArtworkCategory.ANTIQUITY, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM1, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CE1", "CM1", "CM2", "Babylone", "Ishtar", "Mesopotamie", "Moyen-Orient", "lapis-lazuli", "briques", "lions", "empire", "archeologie", "bleu", "or", "بابل", "عشتار", "العراق", "الشرق القديم", "أسد", "آثار"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 4. STARRY VENETIAN NIGHT (Impressionism)
        Artwork(
            id = "art_venetian_night",
            titleFr = "Nuit Vénitienne Étoilée",
            titleEn = "Starry Venetian Night",
            titleAr = "ليلة البندقية المرصعة بالنجوم",
            artistFr = "Maître de l'Impressionnisme Céleste",
            artistEn = "Master of Celestial Impressionism",
            artistAr = "رائد المدرسة الانطباعية الكونية",
            year = "1889",
            periodFr = "Post-Impressionnisme",
            periodEn = "Post-Impressionism",
            periodAr = "ما بعد الانطباعية",
            mediumFr = "Huile sur toile de lin d'Arles",
            mediumEn = "Oil on Arles linen canvas",
            mediumAr = "زيت على قماش كتان خالص",
            dimensions = "73.7 cm × 92.1 cm",
            wingFr = "Aile d'Orsay",
            wingEn = "Orsay Wing",
            wingAr = "جناح أورسي للمتحف",
            roomNumber = "Salle 71",
            imageRes = R.drawable.img_impressionist_art,
            descriptionFr = "Une exploration vibrante du cosmos se reflétant dans les eaux calmes de la lagune vénitienne. Les volutes azurées et l'or des reflets célestes captent l'énergie mystique d'une nuit hors du temps.",
            descriptionEn = "A vibrant cosmic exploration reflected upon the serene waters of the Venetian lagoon. Azure swirls and shimmering gold starlight capture the mystical energy of a timeless nocturnal sky.",
            descriptionAr = "رحلة بصرية مدهشة في أعماق الفضاء تتلألأ انعكاساتها على مياه بحيرة البندقية الهادئة. تجسد الدوامات الزرقاء وأشعة النجوم الذهبية طاقة ليلية ساحرة تتجاوز حدود الزمان والمكان.",
            curatorialNotesFr = "Le travail au couteau et à la brosse épaisse (impasto) crée un relief tridimensionnel qui capte la lumière ambiante sous tous les angles de vue. Observez la pulsation chromatique entre l'outremer et le jaune de cadmium.",
            curatorialNotesEn = "Vigorous palette knife work and thick impasto create a tangible relief catching ambient gallery light from every viewpoint. Note the chromatic pulse between deep ultramarine and radiant cadmium gold.",
            curatorialNotesAr = "تخلق ضربات سكين الرسم والألوان الزيتية الكثيفة (الإمباستو) ملمساً بارزاً يعكس الإضاءة من كل الزوايا. لاحظوا التناغم اللوني بين الأزرق السماوي والأصفر الذهبي المشع.",
            provenanceFr = "Legs d'un mécène vénitien (1923). Restauré aux ateliers nationaux en 2018.",
            provenanceEn = "Bequest of a Venetian patron (1923). Conserved at National Studios in 2018.",
            provenanceAr = "إهداء من أحد رعاة الفنون في البندقية (1923). تم ترميمها بالكامل بأحدث التقنيات عام 2018.",
            audioNarrationFr = "Bienvenue devant la Nuit Vénitienne Étoilée. L'artiste a composé cette œuvre non comme une simple vue nocturne, mais comme un hymne vibrant à la lumière réfractée. Observez la cadence des touches de peinture, appliquée avec une vigueur presque musicale.",
            audioNarrationEn = "Welcome before the Starry Venetian Night. The painter conceived this work not as a plain nocturnal landscape, but as a vibrant hymn to refracted starlight. Notice the rhythmic cadence of the impasto brushstrokes.",
            audioNarrationAr = "أهلاً بكم أمام لوحة ليلة البندقية المرصعة بالنجوم. لم يرسم الفنان مجرد مشهد ليلي، بل صاغ سيمفونية لونية حية تحتفي بوهج النجوم الساطعة. تأملوا إيقاع ضربات الفرشاة الذي يفيض بحيوية موسيقية آسرة.",
            has3DModel = false,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.PAINTINGS, ArtworkCategory.IMPRESSIONISM, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CE1", "CM1", "CM2", "nuit", "étoiles", "lagune", "bleu", "jaune", "peinture", "couleur", "Monet", "Van Gogh", "impressionnisme", "ciel", "eau", "reflet", "impasto", "émotion", "نجوم", "ليل", "أزرق", "لوحة"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 5. VENUS APHRODITE (Antiquity, 3D model)
        Artwork(
            id = "art_venus",
            titleFr = "Vénus Céleste d'Alexandrie",
            titleEn = "Celestial Venus of Alexandria",
            titleAr = "فينوس السماوية السكندرية",
            artistFr = "Attribué à l'École de Praxitèle",
            artistEn = "Attributed to the School of Praxiteles",
            artistAr = "منسوب إلى مدرسة براكسيتيليس النحتية",
            year = "~130 avant J.-C.",
            periodFr = "Grèce Hellénistique",
            periodEn = "Hellenistic Greece",
            periodAr = "العصر الهلنستي اليوناني",
            mediumFr = "Marbre blanc de Carrare extrait des carrières de Paros",
            mediumEn = "White Parian marble from ancient quarries",
            mediumAr = "رخام باروس الأبيض الصافي المنحوت يدوياً",
            dimensions = "Hauteur 204 cm • Poids 980 kg",
            wingFr = "Département des Antiquités Grecques",
            wingEn = "Department of Greek Antiquities",
            wingAr = "جناح الآثار والمنحوتات اليونانية",
            roomNumber = "Rotonde de Mars",
            imageRes = R.drawable.img_venus_sculpture,
            descriptionFr = "Incarnation du canon de beauté classique et de la grâce féminine. Le contrapposto élégant et le drapé mouillé révèlent une virtuosité technique inégalée dans l'art de donner vie à la pierre brute.",
            descriptionEn = "An embodiment of classical beauty canon and feminine grace. Elegant contrapposto and translucent wet drapery display extraordinary mastery in breathing life into raw stone.",
            descriptionAr = "تجسيد لمثالية الجمال الكلاسيكي والرشاقة الإنسانية. تكشف وقفة الكونتراپوستو المتوازنة وثنيات الرداء الحريري عن براعة فائقة في تحويل الرخام الصلب إلى عمل ينبض بالحياة.",
            curatorialNotesFr = "La sculpture a conservé de microscopiques traces de pigments dorés sur le diadème, attestant que les marbres antiques étaient originellement parés de dorures somptueuses et de polychromie sacrée.",
            curatorialNotesEn = "Microscopic traces of gold pigment on the tiara demonstrate that ancient Greek marbles were originally richly gilded and polychrome.",
            curatorialNotesAr = "أظهر الفحص الدقيق وجود آثار مجهرية لذهب خالص على تاج الرأس، مما يؤكد أن التماثيل الرخامية القديمة كانت تزين بطلاء ذهبي وألوان زاهية فاخرة.",
            provenanceFr = "Exhumée lors des fouilles sous-marines de la baie d'Alexandrie en 1894.",
            provenanceEn = "Recovered during underwater excavations in Alexandria Bay in 1894.",
            provenanceAr = "اكتشفت أثناء أعمال التنقيب البحرية في خليج الإسكندرية عام 1894.",
            audioNarrationFr = "Vous contemplez la Vénus Céleste. Observez la courbe serpentine de sa hanche, ce que les maîtres de la Renaissance appelleront la ligne serpentine. Le marbre poli semble respirer sous l'éclairage zénithal.",
            audioNarrationEn = "You are gazing upon the Celestial Venus. Note the gentle serpentine curve of her posture, celebrated by Renaissance masters. The translucent marble seems almost alive under the museum spotlight.",
            audioNarrationAr = "أنتم تشاهدون الآن تمثال فينوس السماوية. لاحظوا انسيابية الخطوط الرشيقة التي أشاد بها فنانو عصر النهضة. يبدو الرخام الأبيض كأنه يتنفس تحت أضواء المتحف الهادئة.",
            has3DModel = true,
            sculptureType = SculptureType.VENUS_APHRODITE,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.ANTIQUITY, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM1),
            keywords = listOf("CE1", "CM1", "CM2", "Grèce", "Antiquité", "sculpture", "statue", "marbre", "Vénus", "Aphrodite", "déesse", "beauté", "contrapposto", "drapé", "mythologie", "رخام", "تمثال", "أفروديت"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 6. CELESTIAL MUSE (Renaissance)
        Artwork(
            id = "art_muse",
            titleFr = "La Muse Céleste en Velours d'Émeraude",
            titleEn = "The Celestial Muse in Emerald Velvet",
            titleAr = "الملهمة السماوية برداء المخمل الزمردي",
            artistFr = "Cercle de Léonard de Vinci",
            artistEn = "Circle of Leonardo da Vinci",
            artistAr = "دائرة ليوناردو دا فينشي الفنية",
            year = "1508",
            periodFr = "Haute Renaissance Italienne",
            periodEn = "High Italian Renaissance",
            periodAr = "عصر النهضة الإيطالية الذهبي",
            mediumFr = "Huile sur panneau de peuplier préparé au gesso",
            mediumEn = "Oil on gesso-prepared poplar wood panel",
            mediumAr = "ألوان زيتية على خشب الحور المحضر بعناية",
            dimensions = "65 cm × 51 cm",
            wingFr = "Aile Denon - Salon des Maîtres",
            wingEn = "Denon Wing - Masterpieces Salon",
            wingAr = "جناح دينون - قاعة كبار المعلمين",
            roomNumber = "Tribune d'Or",
            imageRes = R.drawable.img_renaissance_portrait,
            descriptionFr = "Portrait énigmatique d'une noble florentine drapée de velours émeraude et de broderies d'or. Le sfumato vaporeux estompe les contours des yeux et des lèvres, conférant au regard une mélancolie intemporelle.",
            descriptionEn = "Enigmatic portrait of a Florentine noblewoman draped in emerald velvet and gold embroidery. Vaporous sfumato softens contours of eyes and lips, lending her gaze an eternal, haunting depth.",
            descriptionAr = "لوحة غامضة لسيدة فلورنسية ترتدي رداءً من المخمل الزمردي المزين بالذهب. تذيب تقنية السفوماتو الدقيقة حدود العينين والابتسامة، مانحة النظرة سحراً أبدياً لا ينسى.",
            curatorialNotesFr = "L'analyse radiographique révèle trois couches préparatoires et un repentir sur la position des mains. Le voile transparent est exécuté avec pas moins de quatorze glacis d'une finesse de quelques microns.",
            curatorialNotesEn = "X-ray analysis reveals multiple preparatory underdrawings. The transparent veil is achieved through no fewer than fourteen ultra-thin glaze layers.",
            curatorialNotesAr = "أظهرت التحاليل بالأشعة السينية طبقات تحضيرية متعددة وتعديلات في وضعية اليدين. رُسِم الوشاح الشفاف بأكثر من أربع عشرة طبقة مجهرية بالغة الدقة.",
            provenanceFr = "Collection personnelle de la Maison Médicis, entrée au musée national en 1797.",
            provenanceEn = "Personal collection of the House of Medici, entered the national museum in 1797.",
            provenanceAr = "مجموعة أسرة ميديتشي الفنية في فلورنسا، أودعت بالمتحف الوطني عام 1797.",
            audioNarrationFr = "Approchez-vous du regard de la Muse Céleste. La technique du sfumato, inventée par Léonard de Vinci, permet cette transition impalpable entre l'ombre et la clarté. Elle semble vous observer où que vous vous déplaciez.",
            audioNarrationEn = "Approach the enigmatic gaze of the Celestial Muse. Leonardo's famous sfumato technique creates an imperceptible gradation between light and shadow, giving her eyes a living, haunting depth.",
            audioNarrationAr = "اقتربوا من نظرة الملهمة السماوية. تتيح تقنية السفوماتو التي ابتكرها دا فينشي هذا التدرج السلس بين الظل والنور، لتشعروا وكأن عينيها تتابعانكم أينما تحركتم.",
            has3DModel = false,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.PAINTINGS, ArtworkCategory.RENAISSANCE, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM1),
            keywords = listOf("CE1", "CM1", "CM2", "Renaissance", "portrait", "visage", "Léonard de Vinci", "sfumato", "velours", "émeraude", "yeux", "regard", "sourire", "Italie", "Florence", "دا فينشي", "نهضة", "بورتريه"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 7. APOLLO BUST (Classical Antiquity, 3D model)
        Artwork(
            id = "art_apollo",
            titleFr = "Buste d'Apollon du Belvédère",
            titleEn = "Bust of Belvedere Apollo",
            titleAr = "تمثال نصفي لأبولو بلفيدير",
            artistFr = "Léocharès (Original grec)",
            artistEn = "Leochares (Greek original master)",
            artistAr = "ليوخارس (النحات اليوناني القديم)",
            year = "~330 avant J.-C.",
            periodFr = "Art Grec Classique",
            periodEn = "Classical Greek Art",
            periodAr = "الفن اليوناني الكلاسيكي",
            mediumFr = "Marbre blanc sculpté au trépan et ciselure fine",
            mediumEn = "Carved white marble with fine chisel and drill work",
            mediumAr = "رخام أبيض منحوت بدقة متناهية وأزاميل كلاسيكية",
            dimensions = "Hauteur 62 cm • Poids 45 kg",
            wingFr = "Cour Carrée des Sculptures",
            wingEn = "Sculpture Courtyard",
            wingAr = "بهو المنحوتات الكبرى",
            roomNumber = "Salle Apollon",
            imageRes = R.drawable.img_venus_sculpture,
            descriptionFr = "Le dieu de la lumière, de la poésie et des arts dans toute sa majesté solaire. Les boucles de cheveux enchevêtrées et le port altier en font le modèle suprême de l'idéal esthétique antique.",
            descriptionEn = "The god of light, poetry, and arts in solar majesty. Cascading curls and noble carriage make this a quintessential model of classical antiquity's aesthetic ideal.",
            descriptionAr = "إله النور والشعر والفنون في كامل هيبته. تجعل الخصلات المتطايرة والتعبير الملكي الهادئ من هذا التمثال النموذج الأسمى للجمال الإغريقي المثالي.",
            curatorialNotesFr = "Considéré par l'historien Winckelmann au XVIIIe siècle comme le plus haut sommet jamais atteint par l'art humain.",
            curatorialNotesEn = "Considered by 18th-century art historian Winckelmann as the absolute summit of human artistic achievement.",
            curatorialNotesAr = "اعتبره مؤرخ الفنون فينكِلمان في القرن الثامن عشر أعلى قمة وصل إليها الإبداع الإنساني عبر التاريخ.",
            provenanceFr = "Découvert à Anzio vers 1489 dans les ruines d'une villa impériale.",
            provenanceEn = "Discovered at Anzio circa 1489 in the ruins of an imperial seaside villa.",
            provenanceAr = "اكتشف في أنزيو عام 1489 بين أطلال قصر روماني إمبراطوري قديم.",
            audioNarrationFr = "Le Buste d'Apollon incarne l'harmonie mathématique des proportions idéales. Utilisez notre vue 3D interactive pour inspecter les volumes du profil et l'effet de la lumière sur les différentes matières.",
            audioNarrationEn = "The Apollo bust embodies mathematical harmony and divine proportion. Use our interactive 3D inspection mode to examine the delicate carving of the curls and the interplay of light and shade.",
            audioNarrationAr = "يجسد تمثال أبولو التناغم الرياضي في النسب الجمالية. استخدموا عارض المنحوتات ثلاثي الأبعاد لاستكشاف تفاصيل الوجه وخصلات الشعر وتأثيرات الإضاءة على مختلف الخامات.",
            has3DModel = true,
            sculptureType = SculptureType.APOLLO_BUST,
            isFeatured = false,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.ANTIQUITY, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM1),
            keywords = listOf("CE1", "CM1", "CM2", "Apollon", "dieu", "lumière", "soleil", "buste", "marbre", "Grèce", "Antiquité", "sculpture", "statue", "أبولو", "تمثال", "يونان"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 8. GRAND GALLERY (Virtual Tour)
        Artwork(
            id = "art_gallery",
            titleFr = "La Grande Galerie des Glaces & Chefs-d'œuvre",
            titleEn = "The Grand Hall of Mirrors & Royal Galleries",
            titleAr = "قاعة المرايا الكبرى والمعارض الملكية",
            artistFr = "Architectes Royaux & Peintres du Roi",
            artistEn = "Royal Architects & Court Painters",
            artistAr = "مهندسو ورسامو البلاط الملكي",
            year = "1684",
            periodFr = "Baroque & Classicisme Français",
            periodEn = "French Baroque & Classicism",
            periodAr = "الباروك والكلاسيكية الفرنسية",
            mediumFr = "Architecture monumentale, stucs dorés & fresques marouflées",
            mediumEn = "Monumental architecture, gilded stuccoes, and vaulted frescoes",
            mediumAr = "عمارة صرحية مهيبة، جص مذهب وجداريات فنية سقفية",
            dimensions = "Longueur 73 m • 357 miroirs de cristal",
            wingFr = "Grand Corps Central",
            wingEn = "Central Grand Pavilion",
            wingAr = "الجناح المركزي الأكبر",
            roomNumber = "Galerie Majeure",
            imageRes = R.drawable.img_grand_gallery,
            descriptionFr = "Chef-d'œuvre absolu de l'architecture muséale royale, magnifié par ses voûtes peintes célébrant les arts et la philosophie. La lumière du crépuscule dore les miroirs et les cadres d'époque.",
            descriptionEn = "A crowning achievement of royal architecture, crowned with painted vaults celebrating arts and sciences. Evening light shimmers across hundreds of crystal mirrors and ornate gilded frames.",
            descriptionAr = "تحفة خالدة في العمارة الملكية المزدانة بأسقف جدارية تحتفي بالفنون والعلوم. ينعكس نور الغروب الساحر على مئات المرايا البلورية والإطارات المذهبة العريقة.",
            curatorialNotesFr = "Les lustres de cristal de Bohême et le parquet en marqueterie de chêne et d'ébène ont été entièrement restaurés selon les techniques de compagnonnage des XVIIe et XVIIIe siècles.",
            curatorialNotesEn = "Bohemian crystal chandeliers and oak-ebony marquetry parquet restored following traditional centuries-old gilding craftsmanship.",
            curatorialNotesAr = "تم ترميم الثريات البلورية الفاخرة والأرضيات الخشبية المعشقة باتباع التقاليد الحرفية التراثية بدقة تامة.",
            provenanceFr = "Palais Royal transformé en Musée des Arts universels.",
            provenanceEn = "Royal Palace converted into the Universal Museum of Arts.",
            provenanceAr = "قصر ملكي عريق تحول إلى صرح عالمي للفنون والحضارات الإنسانية.",
            audioNarrationFr = "Bienvenue dans la Grande Galerie. Ici, l'architecture elle-même devient une œuvre d'art totale. La scénographie a été pensée pour que chaque visiteur se sente transporté dans une époque de splendeur dorée.",
            audioNarrationEn = "Welcome to the Grand Gallery. Here, the architecture itself acts as a total work of art, framing masterpieces in a symphony of gold leaf, vaulted frescoes, and royal heritage.",
            audioNarrationAr = "أهلاً بكم في قاعة المرايا الكبرى. هنا تتحول العمارة ذاتها إلى عمل فني متكامل، يأخذكم في رحلة إلى عصور من الفخامة الملكية والجمال الخالد.",
            has3DModel = false,
            isFeatured = true,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.TOURS_360, ArtworkCategory.RENAISSANCE, ArtworkCategory.SCHOOL_CM1, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CM1", "CM2", "Versailles", "Louis XIV", "miroirs", "Baroque", "château", "or", "architecture", "Grande Galerie", "مرايا", "قصر", "عمارة"),
            suitableGrades = listOf("CM1", "CM2")
        ),

        // 9. THE THINKER (Modernism, 3D model)
        Artwork(
            id = "art_thinker",
            titleFr = "Le Penseur Polyédrique",
            titleEn = "The Polyhedral Thinker",
            titleAr = "المفكر متعدد الأوجه",
            artistFr = "Atelier de Sculpture d'Avant-Garde",
            artistEn = "Avant-Garde Sculpture Studio",
            artistAr = "محترف النحت الطليعي الحديث",
            year = "1902 – 1928",
            periodFr = "Modernisme & Symbolisme",
            periodEn = "Modernism & Symbolism",
            periodAr = "الحداثة والرمزية الفنية",
            mediumFr = "Fonte en bronze à la cire perdue, patine brune nuancée",
            mediumEn = "Lost-wax cast bronze with nuanced warm brown patina",
            mediumAr = "برونز مصبوب بتقنية الشمع المفقود بطلاء بني معتق",
            dimensions = "Hauteur 72 cm",
            wingFr = "Jardin des Sculptures & Pavillon Moderne",
            wingEn = "Sculpture Garden & Modern Pavilion",
            wingAr = "حديقة المنحوتات والجناح الحديث",
            roomNumber = "Salon de Bronze",
            imageRes = R.drawable.img_venus_sculpture,
            descriptionFr = "Figure musculeuse et méditative, incarnant l'effort titanesque de l'esprit humain confronté aux mystères de l'univers et à la création poétique.",
            descriptionEn = "A muscular, meditative figure embodying the heroic struggle of human thought wrestling with mysteries of the universe and poetic creation.",
            descriptionAr = "مجسم منحوت يجسد التأمل الإنساني العميق والجهد الفكري الجبار في استكشاف أسرار الكون والإبداع الفني.",
            curatorialNotesFr = "La tension musculaire concentrée dans le dos, les orteils crispés et le poing sous le menton expriment une puissance cérébrale en pleine ébullition.",
            curatorialNotesEn = "Muscular tension across the shoulders and the clenched fist beneath the chin portray mental power in vigorous motion.",
            curatorialNotesAr = "يعبر التوتر العضلي في الكتفين وقبضة اليد تحت الذقن عن عقل متقد يفكر بجهد وحماس فائقين.",
            provenanceFr = "Donation de la succession de l'artiste.",
            provenanceEn = "Gift from the artist's estate.",
            provenanceAr = "إهداء من تركة ورثة الفنان إلى المتحف الوطني.",
            audioNarrationFr = "Le Penseur n'est pas seulement un corps au repos, c'est un intellect en action héroïque. Dans notre visionneuse 3D, observez la tension anatomique du dos et des épaules.",
            audioNarrationEn = "The Thinker represents not merely physical stillness, but heroic mental effort. Rotate the 3D model to observe the powerful anatomy of the back and shoulders in dynamic tension.",
            audioNarrationAr = "المفكر ليس مجرد جسد في حالة سكون، بل هو فكر حي في أوج نشاطه. يمكنكم تدوير المجسم ثلاثي الأبعاد لملاحظة القوة التشريحية في الظهر والكتفين.",
            has3DModel = true,
            sculptureType = SculptureType.THE_THINKER,
            isFeatured = false,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CM1", "CM2", "Penseur", "sculpture", "bronze", "pensée", "philosophie", "statue", "المفكر", "برونز", "فلسفة"),
            suitableGrades = listOf("CM1", "CM2")
        ),

        // 10. WINGED VICTORY (Hellenistic, 3D model)
        Artwork(
            id = "art_winged_victory",
            titleFr = "Victoire Ailée de Samothrace",
            titleEn = "Winged Victory of Samothrace",
            titleAr = "تمثال نصر ساموثراس المجنح",
            artistFr = "Pythokritos de Rhodes",
            artistEn = "Pythokritos of Rhodes",
            artistAr = "بيثوكريتوس الرودسي",
            year = "~190 avant J.-C.",
            periodFr = "Hellénistique Triomphal",
            periodEn = "Triumphant Hellenistic",
            periodAr = "العصر الهلنستي النصراني",
            mediumFr = "Marbre blanc de Paros sur proue de navire en marbre de Lartos",
            mediumEn = "White Parian marble on grey Lartos marble ship prow",
            mediumAr = "رخام باروس الأبيض على مقدمة سفينة حربية حجرية",
            dimensions = "Hauteur totale 5.57 m",
            wingFr = "Escalier Daru",
            wingEn = "Daru Grand Staircase",
            wingAr = "سلم دارو العظيم",
            roomNumber = "Palier Triomphal",
            imageRes = R.drawable.img_venus_sculpture,
            descriptionFr = "Niké, la déesse de la victoire ailée, se posant sur la proue d'un navire de guerre. Le vent fouette ses voiles de marbre avec un réalisme dynamique et une puissance théâtrale à couper le souffle.",
            descriptionEn = "Nike, the winged goddess of victory, alights upon the prow of a warship. Ocean wind whips her marble drapery in dynamic, breathtaking theatrical grandeur.",
            descriptionAr = "نايكي، إلهة النصر المجنحة، تهبط على مقدمة سفينة حربية شامخة. تعصف الرياح بأرديتها الرخامية في مشهد درامي مذهل يفيض بالحركة والحيوية.",
            curatorialNotesFr = "L'asymétrie voulue de la statue a été conçue pour être contemplée depuis le trois-quarts gauche, alors que le visiteur gravit le grand escalier d'honneur.",
            curatorialNotesEn = "The intentional asymmetrical composition was planned to be viewed from the three-quarters left perspective as visitors ascend the monumental staircase.",
            curatorialNotesAr = "صُممت المنحوتة بانسيابية مميزة لتُشاهَد من زاوية مائلة جهة اليسار أثناء صعود الزوار عبر الدرج الشرفي الكبير.",
            provenanceFr = "Découverte sur l'île de Samothrace en 1863 par Charles Champoiseau.",
            provenanceEn = "Discovered on Samothrace island in 1863 by Charles Champoiseau.",
            provenanceAr = "اكتشفت في جزيرة ساموثراس عام 1863 بواسطة تشارلز شامبوازو.",
            audioNarrationFr = "La Victoire de Samothrace est sans doute la plus impressionnante mise en scène du musée. Les ailes déployées défient la gravité tandis que le drapé colle au corps comme trempé par l'écume marine.",
            audioNarrationEn = "The Winged Victory is arguably the museum's most dramatic spectacle. The outstretched wings defy gravity while the marble drapery clings to the body as if drenched by ocean spray.",
            audioNarrationAr = "يعد تمثال النصر المجنح من أروع معروضات المتحف على الإطلاق. تتحدى الأجنحة المنبسطة الجاذبية بينما يلتصق الرداء المنحوت بالجسد كما لو بلله رذاذ البحر المتطاير.",
            has3DModel = true,
            sculptureType = SculptureType.WINGED_VICTORY,
            isFeatured = false,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.ANTIQUITY, ArtworkCategory.SCHOOL_CE1, ArtworkCategory.SCHOOL_CM1),
            keywords = listOf("CE1", "CM1", "CM2", "Victoire", "ailes", "Samothrace", "déesse", "Niké", "vent", "marbre", "تمثال", "أجنحة", "نصر"),
            suitableGrades = listOf("CE1", "CM1", "CM2")
        ),

        // 11. MOBIUS RIBBON (Contemporary, 3D model)
        Artwork(
            id = "art_mobius",
            titleFr = "Ruban d'Infini Géométrique",
            titleEn = "Geometric Infinity Ribbon",
            titleAr = "شريط اللانهاية الهندسي",
            artistFr = "Studio d'Art Cinétique Éthéré",
            artistEn = "Ethereal Kinetic Art Studio",
            artistAr = "أستوديو الفنون الحركية الإثيرية المعاصرة",
            year = "2024",
            periodFr = "Art Contemporain & Paramétrique",
            periodEn = "Contemporary & Parametric Art",
            periodAr = "الفن المعاصر والبارامتري",
            mediumFr = "Alliage de titane doré et surfaces non-orientables",
            mediumEn = "Gilded titanium alloy and non-orientable topological surfaces",
            mediumAr = "سبائك تيتانيوم مذهبة وسطوح طوبولوجية متصلة",
            dimensions = "Hauteur 85 cm • Rayon 40 cm",
            wingFr = "Rotonde Futuriste",
            wingEn = "Futuristic Rotunda",
            wingAr = "القاعة المستقبلية الحديثة",
            roomNumber = "Salle Alpha",
            imageRes = R.drawable.img_app_icon_fg,
            descriptionFr = "Sculpture mathématique à une seule face continue et une seule arête, symbolisant l'immortalité de l'art à travers les cycles de l'histoire humaine.",
            descriptionEn = "A mathematical sculpture with a single continuous surface and boundary, symbolizing art's immortality across cycles of human civilization.",
            descriptionAr = "منحوتة رياضية فريدة ذات وجه وحيد متصل وحافة واحدة مستمرة، ترمز إلى خلود الفن والإبداع الإنساني عبر العصور المتعاقبة.",
            curatorialNotesFr = "Conçue par modélisation topologique, cette pièce capture la réflexion de la lumière dans un mouvement fluide ininterrompu.",
            curatorialNotesEn = "Engineered via parametric topology, this piece captures ambient light reflections in perpetual seamless flow.",
            curatorialNotesAr = "صممت عبر محاكاة رياضية هندسية لتعكس الإضاءة في تدفق بصري مستمر دون انقطاع.",
            provenanceFr = "Commande spéciale pour l'inauguration du Musée Digital Éthéré.",
            provenanceEn = "Special commission for the inauguration of Musée Éthéré.",
            provenanceAr = "صُنعت خصيصاً بمناسبة افتتاح متحف إيثيري الرقمي العالمي.",
            audioNarrationFr = "Le Ruban d'Infini est une merveille mathématique. En suivant sa surface du doigt, vous revenez au point de départ sans jamais franchir de bord. Une métaphore de la créativité humaine infinie.",
            audioNarrationEn = "The Mobius Ribbon is a mathematical marvel. Tracing its surface with your finger brings you back to the origin without crossing an edge, symbolizing infinite human creativity.",
            audioNarrationAr = "شريط اللانهاية هو معجزة رياضية بصرية. عند تتبع سطحه بإصبعك، تعود إلى نقطة البداية دون أن تقطع أي حافة خارجية، في رمزية معبرة عن لا نهائية الإبداع البشري.",
            has3DModel = true,
            sculptureType = SculptureType.MOBIUS_CONTEMPORARY,
            isFeatured = false,
            categories = listOf(ArtworkCategory.ALL, ArtworkCategory.SCULPTURES_3D, ArtworkCategory.SCHOOL_CM2),
            keywords = listOf("CM1", "CM2", "ruban", "infini", "mathématiques", "géométrie", "or", "شريط", "لانهاية", "هندسة", "معاصر"),
            suitableGrades = listOf("CM1", "CM2")
        )
    )
}
