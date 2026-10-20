package com.example.model

data class ArtMovement(
    val id: String,
    val nameFr: String,
    val nameEn: String,
    val nameAr: String,
    val eraFr: String,
    val eraEn: String,
    val eraAr: String,
    val dateRange: String,
    val summaryFr: String,
    val summaryEn: String,
    val summaryAr: String,
    val fullEssayFr: String,
    val fullEssayEn: String,
    val fullEssayAr: String,
    val keyTechniques: List<String>,
    val majorMasters: List<String>,
    val iconicWorks: List<String>
) {
    val name: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> nameEn
        AppLanguage.ARABIC -> nameAr
        AppLanguage.FRENCH -> nameFr
    }

    val era: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> eraEn
        AppLanguage.ARABIC -> eraAr
        AppLanguage.FRENCH -> eraFr
    }

    val summary: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> summaryEn
        AppLanguage.ARABIC -> summaryAr
        AppLanguage.FRENCH -> summaryFr
    }

    val fullEssay: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> fullEssayEn
        AppLanguage.ARABIC -> fullEssayAr
        AppLanguage.FRENCH -> fullEssayFr
    }
}

data class ArtTechnique(
    val id: String,
    val termFr: String,
    val termEn: String,
    val termAr: String,
    val categoryFr: String,
    val categoryEn: String,
    val categoryAr: String,
    val originEra: String,
    val definitionFr: String,
    val definitionEn: String,
    val definitionAr: String,
    val howItWorksFr: String,
    val howItWorksEn: String,
    val howItWorksAr: String,
    val iconicExample: String,
    val pioneerArtist: String
) {
    val term: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> termEn
        AppLanguage.ARABIC -> termAr
        AppLanguage.FRENCH -> termFr
    }

    val category: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> categoryEn
        AppLanguage.ARABIC -> categoryAr
        AppLanguage.FRENCH -> categoryFr
    }

    val definition: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> definitionEn
        AppLanguage.ARABIC -> definitionAr
        AppLanguage.FRENCH -> definitionFr
    }

    val howItWorks: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> howItWorksEn
        AppLanguage.ARABIC -> howItWorksAr
        AppLanguage.FRENCH -> howItWorksFr
    }
}

data class MasterBiography(
    val id: String,
    val nameFr: String,
    val nameEn: String,
    val nameAr: String,
    val lifeSpan: String,
    val nationalityFr: String,
    val nationalityEn: String,
    val nationalityAr: String,
    val movementFr: String,
    val movementEn: String,
    val movementAr: String,
    val biographyFr: String,
    val biographyEn: String,
    val biographyAr: String,
    val contributionFr: String,
    val contributionEn: String,
    val contributionAr: String,
    val famousQuote: String
) {
    val name: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> nameEn
        AppLanguage.ARABIC -> nameAr
        AppLanguage.FRENCH -> nameFr
    }

    val nationality: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> nationalityEn
        AppLanguage.ARABIC -> nationalityAr
        AppLanguage.FRENCH -> nationalityFr
    }

    val movement: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> movementEn
        AppLanguage.ARABIC -> movementAr
        AppLanguage.FRENCH -> movementFr
    }

    val biography: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> biographyEn
        AppLanguage.ARABIC -> biographyAr
        AppLanguage.FRENCH -> biographyFr
    }

    val contribution: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> contributionEn
        AppLanguage.ARABIC -> contributionAr
        AppLanguage.FRENCH -> contributionFr
    }
}

data class TimelineMilestone(
    val yearRange: String,
    val titleFr: String,
    val titleEn: String,
    val titleAr: String,
    val movement: String,
    val descriptionFr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val keyArtwork: String
) {
    val title: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> titleEn
        AppLanguage.ARABIC -> titleAr
        AppLanguage.FRENCH -> titleFr
    }

    val description: String get() = when (AppLanguageManager.currentLanguage) {
        AppLanguage.ENGLISH -> descriptionEn
        AppLanguage.ARABIC -> descriptionAr
        AppLanguage.FRENCH -> descriptionFr
    }
}

object EncyclopediaRepository {

    val artMovements: List<ArtMovement> = listOf(
        // 1. ISLAMIC GOLDEN AGE & ORIENTAL ARTS (Middle East & Islamic World)
        ArtMovement(
            id = "mov_islamic_golden_age",
            nameFr = "Arts de l'Islam & Âge d'Or Céleste",
            nameEn = "Arts of Islam & Islamic Golden Age",
            nameAr = "الفنون والحضارة الإسلامية (العصر الذهبي)",
            eraFr = "Civilisation Arabo-Musulmane & Orient",
            eraEn = "Arab-Islamic Civilization & Near East",
            eraAr = "الحضارة العربية الإسلامية والشرق الأوسط",
            dateRange = "VIIIe – XVIe Siècle (750 – 1550)",
            summaryFr = "Géométrie sacrée, arabesques végétales, calligraphie d'or et maîtrise vertigineuse des métaux, de l'astronomie et de l'architecture.",
            summaryEn = "Sacred geometry, flowing arabesques, golden calligraphy, and peerless mastery of astronomy, metallurgy, and architecture.",
            summaryAr = "الهندسة المقدسة، الزخارف النباتية (الأرابيسك)، الخط العربي المذهب، والريادة الاستثنائية في علوم الفلك والمعادن والعمارة الصرحية.",
            fullEssayFr = "L'art islamique est l'un des phénomènes culturels et esthétiques les plus éclatants de l'histoire humaine. De Cordoue à Bagdad, du Caire à Samarcande, les maîtres artisans ont banni la copie servile pour inventer un langage universel fondé sur la perfection mathématique des entrelacs géométriques (girih), l'arabesque continue symbolisant l'infinité divine, et la calligraphie coufique ou thuluth sublimant l'écriture. Les instruments scientifiques comme les astrolabes de Damas, les céramiques lustrées et les coupoles à muqarnas de l'Alhambra témoignent d'une synthèse magistrale entre raison scientifique et contemplation mystique.",
            fullEssayEn = "Islamic art represents one of the most brilliant aesthetic achievements in world history. Stretching from Cordoba to Baghdad, from Cairo to Samarkand, master artisans forged a universal visual language rooted in geometric perfection, infinite curvilinear arabesques, and sublime Arabic calligraphy. Precision scientific instruments such as the Damascus astrolabes, radiant metallic lusterware, and cascading honeycomb muqarnas vaults in the Alhambra reflect an extraordinary harmony between scientific inquiry and sacred beauty.",
            fullEssayAr = "تمثل الفنون الإسلامية واحدة من أبهى المحطات الجمالية والحضارية في تاريخ البشرية. من قرطبة وغرناطة في الأندلس إلى بغداد ودمشق والقاهرة وسمرقند، ابتكر الفنانون والمهندسون لغة بصرية متفردة قائمة على الهندسة الرياضية المحكمة، والزخارف النباتية اللانهائية (الأرابيسك)، والخط العربي الذي تحول إلى أرقى أشكال التعبير الفني. كما تجلت هذه العبقرية في صناعة الأدوات الفلكية المعقدة كالأسطرلاب، والزليج الملون، والمقرنصات الجصية المعمارية.",
            keyTechniques = listOf("Arabesques & Girih", "Calligraphie Coufique & Thuluth", "Damasquinage au fil d'or", "Muqarnas (alvéoles)", "Zellige polychrome"),
            majorMasters = listOf("Badr al-Din al-Dimashqi", "Ismail al-Jazari", "Mimar Sinan", "Kamal ud-Din Behzad"),
            iconicWorks = listOf("Astrolabe Royal de Damas", "La Cour des Lions de l'Alhambra", "La Mosquée Bleue", "Le Coran Bleu de Kairouan")
        ),

        // 2. MESOPOTAMIA & BABYLON (Ancient Middle East)
        ArtMovement(
            id = "mov_mesopotamian",
            nameFr = "Mésopotamie & Splendeur de Babylone",
            nameEn = "Ancient Mesopotamia & Babylonian Splendor",
            nameAr = "حضارة بلاد الرافدين وبابل العظيمة",
            eraFr = "Orient Ancien & Berceau des Villes",
            eraEn = "Ancient Near East & Cradle of Cities",
            eraAr = "الشرق الأدنى القديم ومهد الحضارات الأولى",
            dateRange = "IIIe millénaire – VIe siècle av. J.-C.",
            summaryFr = "Naissance de l'écriture, ziggourats monumentales, briques émaillées au lapis-lazuli et bas-reliefs d'animaux mythologiques.",
            summaryEn = "Invention of cuneiform script, monumental ziggurats, lapis-lazuli glazed bricks, and sacred mythological animal reliefs.",
            summaryAr = "اختراع الكتابة المسمارية، الزقورات الصرحية، الآجر المزجج باللازورد الأزرق، ونقوش الكائنات الأسطورية المقدسة.",
            fullEssayFr = "Entre le Tigre et l'Euphrate, la Mésopotamie a vu naître les premières cités-États, la roue et l'écriture cunéiforme. L'art mésopotamien culmine sous l'Empire Néo-Babylonien de Nabuchodonosor II, où la cité de Babylone se pare de remparts étincelants de briques émaillées couleur cobalt. La Porte d'Ishtar et la Voie Processionnelle, gardées par des lions rugissants et des dragons mušḫuššu dorés, affirmaient aux yeux du monde la toute-puissance impériale et la protection des dieux.",
            fullEssayEn = "Nestled between the Tigris and Euphrates rivers, Mesopotamia fostered humanity's first metropolises, the wheel, and cuneiform writing. Mesopotamian art achieved its crowning brilliance during the Neo-Babylonian Empire under Nebuchadnezzar II, when Babylon gleamed with cobalt-glazed brick ramparts. The Ishtar Gate and the Processional Way, guarded by fierce golden relief lions and dragon mušḫuššu, stood as unmatched triumphs of ancient Near Eastern engineering.",
            fullEssayAr = "بين نهري دجلة والفرات، شهدت بلاد الرافدين فجر الحضارة الإنسانية باختراع الكتابة المسمارية والعجلة والمدن الأولى. وبلغ الفن البابلي أوجه في عهد نبوخذ نصر الثاني، حيث تحولت بابل إلى مدينة الأحلام بأسوارها اللامعة من الآجر المزجج باللون الأزرق الملكي. وقد شكلت بوابة عشتار وطريق الموكب المهيب، المحروسان بالأسود الذهبية والتنانين، أعظم صرح هندسي وفني في العالم القديم.",
            keyTechniques = listOf("Briques cuites émaillées", "Bas-reliefs d'animaux sacrés", "Ciselure sur stèle de basalte", "Cire perdue sumérienne"),
            majorMasters = listOf("Maîtres Émailleurs de Babylone", "Sculpteurs Sumériens d'Ur", "Architectes des Ziggourats"),
            iconicWorks = listOf("La Porte d'Ishtar", "Le Code de Hammurabi", "L'Étendard d'Ur", "La Voie Processionnelle")
        ),

        // 3. RENAISSANCE
        ArtMovement(
            id = "mov_renaissance",
            nameFr = "Haute Renaissance Italienne",
            nameEn = "High Italian Renaissance",
            nameAr = "عصر النهضة الإيطالية الذهبي",
            eraFr = "Époque Moderne",
            eraEn = "Modern Era",
            eraAr = "العصر الحديث المبكر",
            dateRange = "1490 – 1527",
            summaryFr = "Apogée de l'équilibre classique, de la perspective géométrique et de l'humanisme universel.",
            summaryEn = "Culmination of classical harmony, geometric perspective, and humanistic universality.",
            summaryAr = "ذروة التوازن الكلاسيكي، المنظور الهندسي الرياضي، والفلسفة الإنسانية الشاملة.",
            fullEssayFr = "La Haute Renaissance marque le moment où l'art occidental atteint une maîtrise technique absolue du dessin, de l'anatomie et de la perspective linéaire découverte par Brunelleschi. Rejetant les conventions hiératiques du Moyen Âge, les maîtres florentins et romains placent l'être humain au centre de la création, animé par un idéal d'harmonie néoplatonicienne. Les compositions triangulaires rigoureuses, la modulation atmosphérique du sfumato et l'élévation spirituelle caractérisent cette ère sans égale.",
            fullEssayEn = "The High Renaissance marks the period when Western art achieved total mastery of anatomical drawing, linear perspective, and atmospheric gradation. Placing humanity at the center of creation, masters created compositions of supreme Neoplatonic balance and emotional resonance.",
            fullEssayAr = "يمثل عصر النهضة الإيطالية ذروة التمكن البصري والتشريحي والمنظور الخطي الدقيق. وضع فنانو فلورنسا وروما الإنسان في قلب الكون ضمن تناغم جمالي فريد، تميز بالتركيبات الهرمية المدروسة وظلال السفوماتو الهوائية الناعمة.",
            keyTechniques = listOf("Sfumato", "Perspective centrale", "Clair-obscur naissant", "Composition pyramidale"),
            majorMasters = listOf("Léonard de Vinci", "Michel-Ange", "Raphaël", "Giorgione"),
            iconicWorks = listOf("La Joconde", "Le Plafond de la Chapelle Sixtine", "L'École d'Athènes")
        ),

        // 4. CLASSICAL ANTIQUITY
        ArtMovement(
            id = "mov_antiquity",
            nameFr = "Antiquité Classique & Hellénisme",
            nameEn = "Classical Antiquity & Hellenism",
            nameAr = "العصر الكلاسيكي والهلنستي الإغريقي",
            eraFr = "Grèce & Rome Antiques",
            eraEn = "Ancient Greece & Rome",
            eraAr = "اليونان وروما القديمة",
            dateRange = "Ve siècle av. J.-C. – IIe siècle ap. J.-C.",
            summaryFr = "Canon des proportions divines, quête de la grâce éternelle et maîtrise du marbre blanc.",
            summaryEn = "Canon of divine proportions, pursuit of eternal grace, and mastery of white marble.",
            summaryAr = "مقياس النسب الذهبية الإلهية، البحث عن الرشاقة الخالدة، وبراعة نحت الرخام الأبيض.",
            fullEssayFr = "L'art grec classique, fondé par Polyclète et Phidias, établit les proportions mathématiques du corps idéal à travers le nombre d'or et le contrapposto (bascule des hanches rompant la rigidité). La période hellénistique qui lui succède insuffle une expressivité théâtrale échevelée, des drapés tourbillonnants et un pathétique sublime, culminant dans des chefs-d'œuvre comme la Victoire de Samothrace et la Vénus d'Alexandrie.",
            fullEssayEn = "Classical Greek sculpture established mathematical canons of ideal bodily proportion through the golden ratio and contrapposto. The subsequent Hellenistic era injected dramatic theatricality, swirling wind-whipped draperies, and emotional pathos.",
            fullEssayAr = "أرست المنحوتات اليونانية الكلاسيكية النسب الهندسية المثالية للجسد الإنساني من خلال النسبة الذهبية ووقفة الكونتراپوستو المتوازنة، قبل أن يضيف العصر الهلنستي درامية مسرحية وأردية متطايرة في الريح مفعمة بالحركة والقوة.",
            keyTechniques = listOf("Contrapposto", "Drapé mouillé", "Sculpture au trépan", "Fonte de bronze à la cire perdue"),
            majorMasters = listOf("Praxitèle", "Phidias", "Polyclète", "Lysippe", "Léocharès"),
            iconicWorks = listOf("Vénus de Milo", "Discobole", "Victoire de Samothrace", "Apollon du Belvédère")
        ),

        // 5. IMPRESSIONISM
        ArtMovement(
            id = "mov_impressionism",
            nameFr = "L'Impressionnisme & Lumière Pure",
            nameEn = "Impressionism & Pure Light",
            nameAr = "المدرسة الانطباعية وسحر الضوء",
            eraFr = "XIXe Siècle",
            eraEn = "19th Century",
            eraAr = "القرن التاسع عشر",
            dateRange = "1874 – 1886",
            summaryFr = "Révolution du plein air, capture de l'instant fugitif et décomposition chromatique de la lumière.",
            summaryEn = "Plein-air revolution, seizing transient atmospheric moments, and optical color vibration.",
            summaryAr = "ثورة الرسم في الهواء الطلق، التقاط اللحظات الهاربة، وتفكيك أطياف الضوء الطبيعي.",
            fullEssayFr = "En rejetant les salons officiels académiques en 1874, les Impressionnistes bouleversent l'histoire de l'art mondial. Travaillant en plein air grâce à l'invention des tubes de peinture souples, ils peignent non pas l'objet en soi, mais l'impression optique fugace produite par la lumière sur la rétine.",
            fullEssayEn = "Rejecting official academic salons in 1874, Impressionists transformed art history. Painting outdoors with newly invented portable paint tubes, they captured not static objects, but the transient optical vibrations of light reflecting upon water, foliage, and sky.",
            fullEssayAr = "أحدث رواد الانطباعية ثورة جذرية في الفن بتم ردهم على القواعد الأكاديمية الصارمة. بالخروج للرسم في الطبيعة الحرة، ركزوا على تسجيل انطباع العين المباشر وتلألؤ الضوء فوق مياه الأنهار وأمواج البحار.",
            keyTechniques = listOf("Pleinairisme", "Touche divisée", "Ombres colorées", "Peinture alla prima"),
            majorMasters = listOf("Claude Monet", "Pierre-Auguste Renoir", "Edgar Degas", "Camille Pissarro"),
            iconicWorks = listOf("Impression, soleil levant", "Nymphéas", "Bal du moulin de la Galette")
        )
    )

    val artTechniques: List<ArtTechnique> = listOf(
        // 1. MUQARNAS
        ArtTechnique(
            id = "tech_muqarnas",
            termFr = "Les Muqarnas (Voûtes en nids d'abeille)",
            termEn = "Muqarnas (Honeycomb Vaulting)",
            termAr = "المقرنصات المعمارية (خلايا النحل الهندسية)",
            categoryFr = "Architecture Islamique & Stuc",
            categoryEn = "Islamic Architecture & Stucco",
            categoryAr = "العمارة والزخرفة الإسلامية",
            originEra = "Moyen-Orient & Perse (~Xe Siècle)",
            definitionFr = "Éléments architecturaux tridimensionnels composés de niches et corniches imbriquées en encorbellement, créant une transition fluide entre murs rectangulaires et dômes célestes.",
            definitionEn = "Three-dimensional architectural corbelling of interlocking niches, forming a smooth geometric transition between square walls and domed ceilings.",
            definitionAr = "عنصر معماري زخرفي ثلاثي الأبعاد يتكون من طبقات متراكبة من التجاويف والمشكاوات المفرغة، يؤمن انتقالاً هندسياً ساحراً بين الجدران المستقيمة والقباب الدائرية.",
            howItWorksFr = "Les artisans sculptent le plâtre fin ou assemblent des blocs de pierre selon une grille géométrique stricte. La lumière rasante illumine les alvéoles, donnant l'illusion que la voûte est suspendue sans pesanteur.",
            howItWorksEn = "Carved from fine gypsum plaster or cut stone along geometric grids, muqarnas catch cascading ambient light, creating the impression of a weightless starry vault.",
            howItWorksAr = "تُنحت المقرنصات في الجص الأبيض أو الحجر وفق معادلات هندسية دقيقة؛ وعندما يتخللها النور تبدو القبة وكأنها تسبح في الهواء دون أي ثقل مادي.",
            iconicExample = "Les voûtes enchanteresses de la Salle des Abencérages et de la Cour des Lions à l'Alhambra de Grenade.",
            pioneerArtist = "Maîtres d'œuvre nasrides et persans."
        ),

        // 2. DAMASCENING & METAL INLAY
        ArtTechnique(
            id = "tech_damascening",
            termFr = "Le Damasquinage & Ciselure",
            termEn = "Damascening & Gold Inlay",
            termAr = "التكفيت والدمشقة (تطعيم المعادن بالذهب)",
            categoryFr = "Métallurgie d'Art & Ciselure",
            categoryEn = "Art Metallurgy & Chasing",
            categoryAr = "حرف المعادن الفاخرة والنقش",
            originEra = "Damas & Proche-Orient Antique",
            definitionFr = "Art d'incruster des fils d'or ou d'argent pur dans des incisions gravées sur la surface d'un métal plus dur comme le laiton, le fer ou le bronze.",
            definitionEn = "The craft of inlaying pure gold or silver threads into finely grooved channels etched on harder metals like brass, iron, or bronze.",
            definitionAr = "فن حرفي راقٍ يقوم على حفر أخاديد دقيقة في المعادن الصلبة كالنحاس أو الحديد وتطعيمها بأسلاك وخيوط من الذهب الخالص والفضة اللامعة.",
            howItWorksFr = "L'artisan trace au burin un motif d'arabesque ou de calligraphie avec des entailles en queue-d'aronde. Le fil précieux y est inséré puis martelé à froid pour faire corps avec la masse.",
            howItWorksEn = "The craftsman chisels dovetail grooves into the metal substrate. Fine gold wire is then laid into the channels and cold-hammered until securely embedded, followed by polishing.",
            howItWorksAr = "يقوم الصانع بنقر خطوط وزخارف مجهرية بإزميل ذي رأس ماسي، ثم يدخل أسلاك الذهب ويطرقها على البارد لتلتحم بالسطح قبل صقله وتلميعه.",
            iconicExample = "L'Astrolabe Royal de Damas et les bassins impériaux mamlouks et ayyoubides.",
            pioneerArtist = "Maîtres dinandiers et ciseleurs de Damas, Mossoul et Alep."
        ),

        // 3. ARABIC CALLIGRAPHY (Kufic & Thuluth)
        ArtTechnique(
            id = "tech_calligraphy_islamic",
            termFr = "Calligraphie Arabe Coufique & Thuluth",
            termEn = "Arabic Calligraphy (Kufic & Thuluth)",
            termAr = "فن الخط العربي (الكوفي والثلث)",
            categoryFr = "Épigraphie & Art Sacré",
            categoryEn = "Epigraphy & Sacred Art",
            categoryAr = "فنون الخط والكتابة التراثية",
            originEra = "Koufa, Bagdad & Le Caire (VIIe – Xe Siècle)",
            definitionFr = "Art souverain de la belle écriture élevée au rang de chef-d'œuvre visuel, régie par des proportions mathématiques strictes fondées sur le point losangé du calame.",
            definitionEn = "The supreme art of beautiful script elevated into monument-defining visual art, governed by rigorous mathematical proportions based on the reed pen's diamond dot.",
            definitionAr = "أرقى الفنون البصرية في الثقافة الإسلامية، حيث يخضع رسم الحروف لقواعد هندسية صارمة وميزان دقيق يعتمد على نقطة قلم القصب (القطة).",
            howItWorksFr = "Le calligraphe taille un calame en roseau et dose le noir de fumée. Le style Coufique privilégie les lignes horizontales majestueuses et géométriques, tandis que le Thuluth déploie des courbes monumentales royales.",
            howItWorksEn = "Carved reed pens (qalam) apply soot-based ink onto parchment or stone. Kufic features bold geometric angularity, while Thuluth unfurls majestic curves across palaces and manuscripts.",
            howItWorksAr = "يبرى قلم البوص بعناية فائقة؛ ويتميز الخط الكوفي بهندسته الصرحية وزواياه الشامخة، بينما ينفرد خط الثلث بمرونته الملوكية وتداخل حروفه المهيب على جدران القصور والتحف.",
            iconicExample = "Les bandeaux épigraphiques de l'Alhambra et les inscriptions astronomiques des astrolabes.",
            pioneerArtist = "Ibn Muqla, Ibn al-Bawwab et Yaqut al-Musta'simi."
        ),

        // 4. SFUMATO
        ArtTechnique(
            id = "tech_sfumato",
            termFr = "Le Sfumato",
            termEn = "Sfumato Technique",
            termAr = "تقنية السفوماتو (التظليل الدخاني)",
            categoryFr = "Peinture à l'Huile & Nuances",
            categoryEn = "Oil Painting & Nuance",
            categoryAr = "الرسم الزيتي وتدرج الظلال",
            originEra = "Haute Renaissance (Léonard de Vinci, ~1500)",
            definitionFr = "Technique picturale consistant à noyer les contours dans une vapeur vaporeuse et insaisissable pour créer un modelé d'une infinie douceur.",
            definitionEn = "Pictorial technique softening contours into smoke-like gradations to produce subtle atmospheric depth and lifelike volume.",
            definitionAr = "تقنية تصويرية بارعة تعتمد على تذويب وتشتيت الحدود الفاصلة للرسم في تدرجات دخانية ناعمة للغاية تمنح البشرة والعيون سحراً ينبض بالحياة.",
            howItWorksFr = "Application de multiples glacis transparents de quelques microns d'épaisseur chacun, séchés patiemment.",
            howItWorksEn = "Superposition of dozens of micro-thin translucent glazes, eliminating brushstrokes.",
            howItWorksAr = "وضع عشرات الطبقات الشفافة الرقيقة جداً من الزيت الملون دون ترك أي أثر لضربات الفرشاة.",
            iconicExample = "Le regard et le sourire de la Joconde et de La Muse Céleste en Velours.",
            pioneerArtist = "Léonard de Vinci."
        ),

        // 5. CONTRAPPOSTO
        ArtTechnique(
            id = "tech_contrapposto",
            termFr = "Le Contrapposto",
            termEn = "Contrapposto Pose",
            termAr = "وقفة الكونتراپوستو المتوازنة",
            categoryFr = "Sculpture & Statuaire",
            categoryEn = "Sculpture & Statuary",
            categoryAr = "فن النحت والتماثيل",
            originEra = "Grèce Antique classique (~450 av. J.-C.)",
            definitionFr = "Pose sculpturale où le poids du corps repose principalement sur une seule jambe, créant une courbe dynamique en 'S' le long de la colonne.",
            definitionEn = "Sculptural pose where body weight rests primarily on one leg, shifting hips and shoulders into an organic S-curve.",
            definitionAr = "وضعية نحتية يرتكز فيها ثقل الجسد على ساق واحدة، مما يحدث انحناءً حيوياً انسيابياً على شكل حرف S في العمود الفقري يكسر جمود الحجر.",
            howItWorksFr = "La jambe d'appui tendue soulève la hanche, compensée par l'inclinaison des épaules.",
            howItWorksEn = "The engaged leg raises the hip while shoulders counter-balance in dynamic tension.",
            howItWorksAr = "يرتفع مفصل الورك فوق الساق الحاملة بينما تنحدر الأكتاف في الاتجاه المعاكس لتحقيق توازن ديناميكي.",
            iconicExample = "La Vénus d'Alexandrie et le Buste d'Apollon du Belvédère.",
            pioneerArtist = "Polyclète et Praxitèle."
        )
    )

    val masterBiographies: List<MasterBiography> = listOf(
        // 1. ISMAIL AL-JAZARI
        MasterBiography(
            id = "bio_al_jazari",
            nameFr = "Badi' al-Zaman Al-Jazari",
            nameEn = "Ismail al-Jazari (Father of Robotics)",
            nameAr = "بديع الزمان الجزري (عالم الحيل الميكانيكية)",
            lifeSpan = "1136 – 1206 après J.-C.",
            nationalityFr = "Mésopotamienne & Islamique (Diyar Bakr)",
            nationalityEn = "Mesopotamian & Islamic (Diyar Bakr)",
            nationalityAr = "عالم ومهندس مسلم من بلاد الرافدين",
            movementFr = "Ingénierie Mécanique & Arts Célestes de l'Islam",
            movementEn = "Islamic Mechanical Engineering & Celestial Arts",
            movementAr = "الهندسة الميكانيكية والفنون العلمية الإسلامية",
            biographyFr = "Polymathe, ingénieur mécanique et inventeur visionnaire au service des princes artuqides de Haute Mésopotamie. Il est l'auteur du célèbre 'Livre de la connaissance des procédés mécaniques ingénieux', où il décrit plus de 50 automates, horloges à eau monumentales et mécanismes à vilebrequin qui ont révolutionné la technologie mondiale.",
            biographyEn = "Visionary polymath, mechanical engineer, and craftsman serving the Artuqid court of upper Mesopotamia. Renowned for authoring 'The Book of Knowledge of Ingenious Mechanical Devices', cataloging over 50 automated machines, monumental water clocks, and crankshaft mechanisms centuries ahead of his time.",
            biographyAr = "عالم موسوعي ومهندس ميكانيكي عبقري عاش في ديار بكر شمال بلاد الرافدين. صاحب أثمن مخطوطة هندسية في التاريخ 'الجامع بين العلم والعمل النافع في صناعة الحيل'، حيث اخترع عمود الكامات والمضخات المائية والساعات الفلكية الرائعة كساعة الفيل.",
            contributionFr = "Invention du vilebrequin moderne, perfectionnement des astrolabes et automates hydrauliques programmables.",
            contributionEn = "Invention of the modern crankshaft, hydraulic automations, and pioneering celestial astrolabe instruments.",
            contributionAr = "ابتكار عمود الكرنك والمكابس الهيدروليكية، وتطوير الساعات المائية والآلات الفلكية الدقيقة.",
            famousQuote = "« L'art et la science s'unissent pour donner vie à ce qui semblait inerte. »"
        ),

        // 2. MIMAR SINAN
        MasterBiography(
            id = "bio_sinan",
            nameFr = "Mimar Koca Sinan",
            nameEn = "Mimar Sinan (Grand Imperial Architect)",
            nameAr = "معمار سنان باشا (كبير المهندسين المعماريين)",
            lifeSpan = "1488 – 1588 après J.-C.",
            nationalityFr = "Ottomane & Méditerranéenne (Istanbul)",
            nationalityEn = "Ottoman & Mediterranean (Istanbul)",
            nationalityAr = "المهندس المعماري الأكبر للدولة العثمانية",
            movementFr = "Architecture Classique Islamique & Coupoles Monumentales",
            movementEn = "Classical Islamic Architecture & Monumental Domes",
            movementAr = "العمارة الإسلامية الكلاسيكية وهندسة القباب الصرحية",
            biographyFr = "Considéré comme l'un des plus grands architectes de tous les temps, Mimar Sinan a conçu plus de 300 monuments impériaux, dont les chefs-d'œuvre de la Mosquée Süleymaniye à Istanbul et de la Mosquée Selimiye à Edirne. Sa maîtrise des calculs de résistance aux séismes et des proportions acoustiques demeure légendaire.",
            biographyEn = "Celebrated as one of history's greatest architects, Mimar Sinan engineered over 300 monuments, including the iconic Süleymaniye in Istanbul and Selimiye in Edirne. His mastery of earthquake-resistant structural engineering and acoustic geometry remains legendary.",
            biographyAr = "أحد أعظم بناة التاريخ الإنساني قاطبة. شيد على مدار قرن من العطاء أكثر من 300 صرح معماري خالد كجامع السليمانية في إسطنبول وجامع السليمية في أدرنة، وبلغت هندسته لمقاومة الزلازل وتوزيع الصوت كمالاً إعجازياً.",
            contributionFr = "Perfectionnement des dômes monumentaux sur pendentifs et des minarets élancés défiant les lois de la gravité.",
            contributionEn = "Perfection of monumental domes on pendentive arches and needle minarets harmonizing vast interior spaces.",
            contributionAr = "رفع القباب الهائلة فوق الأقواس والمثلثات الكروية وتصميم المآذن الرشيقة التي تعانق السماء.",
            famousQuote = "« Le chef-d'œuvre d'un bâtisseur est le sanctuaire qui réconcilie l'espace et la lumière. »"
        ),

        // 3. LEONARDO DA VINCI
        MasterBiography(
            id = "bio_leonardo",
            nameFr = "Léonard de Vinci",
            nameEn = "Leonardo da Vinci",
            nameAr = "ليوناردو دا فينشي",
            lifeSpan = "1452 – 1519",
            nationalityFr = "Italienne (Vinci / Florence / Amboise)",
            nationalityEn = "Italian (Vinci / Florence / Amboise)",
            nationalityAr = "إيطالي (فلورنسا / ميلانو)",
            movementFr = "Haute Renaissance Italienne",
            movementEn = "High Italian Renaissance",
            movementAr = "عصر النهضة الإيطالية",
            biographyFr = "Polymathe de génie universel : peintre, anatomiste, ingénieur, sculpteur et philosophe de la nature. Il élève la peinture au rang de science suprême.",
            biographyEn = "Universal genius, painter, anatomist, and engineer who elevated painting into a supreme philosophical science.",
            biographyAr = "عبقري عصر النهضة الشامل، رسام وعالم تشريح ومهندس وفيلسوف رفع التصوير إلى مرتبة العلوم العليا.",
            contributionFr = "Invention du sfumato, perspective atmosphérique, et étude anatomique du regard.",
            contributionEn = "Invention of sfumato, atmospheric perspective, and scientific anatomy in art.",
            contributionAr = "ابتكار تقنية التظليل الدخاني (السفوماتو) والمنظور الهوائي العميق.",
            famousQuote = "« Les détails font la perfection, et la perfection n'est pas un détail. »"
        ),

        // 4. CLAUDE MONET
        MasterBiography(
            id = "bio_monet",
            nameFr = "Claude Monet",
            nameEn = "Claude Monet",
            nameAr = "كلود مونيه",
            lifeSpan = "1840 – 1926",
            nationalityFr = "Française (Paris / Giverny)",
            nationalityEn = "French (Paris / Giverny)",
            nationalityAr = "فرنسي (باريس / جيفرني)",
            movementFr = "Impressionnisme",
            movementEn = "Impressionism",
            movementAr = "المدرسة الانطباعية",
            biographyFr = "Chef de file de l'Impressionnisme, obsédé par les variations de la lumière naturelle au fil des heures.",
            biographyEn = "Leader of Impressionism, obsessed with capturing the elusive transformations of natural sunlight.",
            biographyAr = "رائد الحركة الانطباعية الذي كرس حياته لمطاردة انعكاسات النور والضوء على سطح الماء والطبيعة.",
            contributionFr = "Création des séries picturales et libération de la couleur pure.",
            contributionEn = "Serial painting method and liberation of vibrant optical color.",
            contributionAr = "ابتكار السلاسل الفنية التكرارية وتحرير اللون الصافي من قيود الخط.",
            famousQuote = "« Je peins pour capturer l'air, la lumière et la vibration des choses. »"
        )
    )

    val timelineMilestones: List<TimelineMilestone> = listOf(
        TimelineMilestone("~575 av. J.-C.", "La Porte d'Ishtar de Babylone", "The Ishtar Gate of Babylon", "بوابة عشتار البابلية العظيمة", "Mésopotamie Antique", "Briques d'argile émaillées au lapis-lazuli et couronnement de l'Empire de Nabuchodonosor II.", "Glazed cobalt lapis bricks celebrate the imperial capital of Nebuchadnezzar II.", "تحفة الآجر المزجج باللازورد الأزرق في عهد الملك نبوخذ نصر الثاني.", "Porte d'Ishtar de Babylone"),
        TimelineMilestone("~450 av. J.-C.", "L'Âge d'Or d'Athènes", "Golden Age of Classical Athens", "العصر الذهبي لأثينا الكلاسيكية", "Grèce Classique", "Phidias érige le Parthénon et Polyclète codifie le canon anatomique parfait.", "Phidias builds the Parthenon and Polykleitos codifies anatomical harmony.", "بناء البارثينون وتدوين النسب الرياضية للجمال البشري.", "Buste d'Apollon du Belvédère"),
        TimelineMilestone("1208 ap. J.-C.", "L'Âge d'Or de l'Astrolabe de Damas", "Golden Age of Damascus Astrolabe", "عصر أسطرلاب دمشق الذهبي", "Sciences & Arts de l'Islam", "Badr al-Din al-Dimashqi achève son astrolabe royal en laiton ciselé et or d'Alep.", "Badr al-Din al-Dimashqi completes the royal astronomical astrolabe in gilded brass.", "إتمام أسطرلاب دمشق الفلكي الملكي بالنحاس المنقوش وذهب حلب.", "Astrolabe Royal de Damas"),
        TimelineMilestone("1370 – 1391", "L'Apothéose de l'Alhambra", "Apogee of the Alhambra Palace", "ذروة إبداع قصر الحمراء بغرناطة", "Art Hispano-Mauresque", "Édification de la Cour des Lions et de ses voûtes de stalactites muqarnas sous les Nasrides.", "Construction of the Court of the Lions with cascading honeycomb muqarnas.", "تشييد بهو السباع وقباب المقرنصات الخالدة في غرناطة الأندلسية.", "La Cour des Lions de l'Alhambra"),
        TimelineMilestone("1503 – 1508", "Triomphe de la Renaissance", "Triumph of High Renaissance", "انتصار عصر النهضة الإيطالية", "Renaissance", "Léonard peint la Joconde et crée le sfumato ; Michel-Ange commence la Sixtine.", "Leonardo invents sfumato; Michelangelo begins the Sistine Chapel.", "ابتكار تقنية السفوماتو الدخانية وتصوير روائع الفن العالمي.", "La Muse Céleste en Velours"),
        TimelineMilestone("1684", "L'Apothéose Royale de Versailles", "Royal Grandeur of Versailles", "فخامة قصر فرساي وقاعة المرايا", "Baroque Français", "Inauguration de la Grande Galerie des Glaces, manifeste de l'art total européen.", "Inauguration of the Grand Hall of Mirrors, milestone of Baroque opulence.", "افتتاح قاعة المرايا الكبرى كأعظم نموذج للعمارة الباروكية الملكية.", "La Grande Galerie des Glaces"),
        TimelineMilestone("1889", "La Nuit des Étoiles & L'Impasto", "Starry Night & Expressive Impasto", "ليلة النجوم وتقنية الإمباستو الكثيف", "Post-Impressionnisme", "Révolution de l'empâtement chromatique et des ciels stellaires infinis.", "Thick impasto technique and cosmic starlight revolutionize modern painting.", "ثورة الألوان الزيتية الكثيفة وسحر الليالي المرصعة بالنجوم.", "Nuit Vénitienne Étoilée"),
        TimelineMilestone("2024 – 2026", "L'Ère du Musée Digital & de la 3D", "Era of Digital Museum & 3D", "عصر المتحف الرقمي والانغماس 3D", "Art Spatial Contemporain", "Numérisation stéréoscopique, jumeaux 3D interactifs et audioguides multilingues universels.", "Stereoscopic 3D inspection, digital twin masterpieces, and universal trilingual guides.", "المجسمات التفاعلية ثلاثية الأبعاد والجولات الافتراضية للجميع.", "Ruban d'Infini Géométrique")
    )
}
