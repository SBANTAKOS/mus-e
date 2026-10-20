package com.example.model

enum class SchoolGrade(val code: String, val label: String, val cycle: String, val ageRange: String) {
    CE1("CE1", "Cours Élémentaire 1 (CE1)", "Cycle 2", "7 – 8 ans"),
    CM1("CM1", "Cours Moyen 1 (CM1)", "Cycle 3", "9 – 10 ans"),
    CM2("CM2", "Cours Moyen 2 (CM2)", "Cycle 3", "10 – 11 ans"),
    ALL_GRADES("TOUS", "Tous Niveaux Primaires", "Cycle 2 & 3", "7 – 11 ans")
}

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class SchoolDossier(
    val id: String,
    val grade: SchoolGrade,
    val title: String,
    val theme: String,
    val curriculumProgram: String,
    val introduction: String,
    val keyPoints: List<String>,
    val questionsForObservation: List<String>,
    val vocabularyWords: Map<String, String>,
    val suggestedActivity: String,
    val relatedArtworkIds: List<String>,
    val quiz: List<QuizQuestion>,
    val keywords: List<String>
)

object SchoolRepository {

    val dossiers: List<SchoolDossier> = listOf(
        // ==================== CE1 DOSSIERS ====================
        SchoolDossier(
            id = "dossier_ce1_colors",
            grade = SchoolGrade.CE1,
            title = "La Magie des Couleurs & des Ciels Étoilés",
            theme = "Arts Plastiques & Éveil Visuel",
            curriculumProgram = "Cycle 2 (CE1) : Exprimer des émotions par la couleur, distinguer couleurs chaudes et froides, observer un paysage imaginaire.",
            introduction = "Dans un tableau, les couleurs racontent des histoires secrètes ! Les peintres utilisent le bleu et le violet pour le calme de la nuit, et le jaune ou l'or pour la chaleur des étoiles. Quand tu regardes 'La Nuit Vénitienne Étoilée', tes yeux voyagent dans un tourbillon de lumière.",
            keyPoints = listOf(
                "Les couleurs froides (bleu, vert, violet) évoquent l'eau calme, la fraîcheur et le mystère de la nuit.",
                "Les couleurs chaudes (jaune, orange, or) brillent comme le soleil, les lanternes et la joie.",
                "Le peintre utilise de la peinture très épaisse qui s'appelle 'l'impasto' : la peinture fait de petites vagues sur la toile que l'on peut presque sentir !",
                "En mélangeant du bleu et du jaune, on obtient du vert comme les reflets dans la lagune."
            ),
            questionsForObservation = listOf(
                "Combien d'étoiles dorées brille dans le ciel bleu ?",
                "De quelle couleur sont les reflets de la lumière sur l'eau ?",
                "Si tu pouvais entrer dans ce tableau, ferais-tu du bruit ou marcherais-tu sur la pointe des pieds ?"
            ),
            vocabularyWords = mapOf(
                "Couleur chaude" to "Couleur qui rappelle le feu et le soleil (jaune, rouge, orange).",
                "Couleur froide" to "Couleur qui rappelle l'eau, la glace et la nuit (bleu, vert, violet).",
                "Reflet" to "Image renvoyée par une surface brillante comme un miroir ou l'eau.",
                "Touche de pinceau" to "La trace que laisse le pinceau plein de peinture sur la toile."
            ),
            suggestedActivity = "Dessine ta propre nuit magique avec des craies grasses bleues et peins de grosses étoiles dorées ou jaunes au centre !",
            relatedArtworkIds = listOf("art_venetian_night"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_ce1_1",
                    question = "De quelle couleur est le ciel de la nuit dans le tableau de la lagune ?",
                    options = listOf("Vert pomme", "Bleu nuit avec des tourbillons d'or", "Tout noir sans aucune lumière", "Rose bonbon"),
                    correctOptionIndex = 1,
                    explanation = "Bravo ! Le peintre a mélangé du bleu profond et de l'or pour faire briller les étoiles !"
                ),
                QuizQuestion(
                    id = "q_ce1_2",
                    question = "Comment appelle-t-on le jaune, le rouge et l'orange ?",
                    options = listOf("Des couleurs froides", "Des couleurs invisibles", "Des couleurs chaudes", "Des couleurs tristes"),
                    correctOptionIndex = 2,
                    explanation = "Exactement ! Elles réchauffent le tableau comme un rayon de soleil !"
                )
            ),
            keywords = listOf("CE1", "couleur", "peinture", "bleu", "jaune", "étoile", "nuit", "palette", "ciel", "émotion", "dessin", "primaire", "froid", "chaud")
        ),
        SchoolDossier(
            id = "dossier_ce1_portrait",
            grade = SchoolGrade.CE1,
            title = "Les Visages et les Émotions dans le Portrait",
            theme = "Le Portrait & l'Autoportrait",
            curriculumProgram = "Cycle 2 (CE1) : Reconnaître un portrait, observer les expressions du visage, comprendre les détails d'un vêtement ancien.",
            introduction = "Avant l'invention des appareils photo et des téléphones, les rois et les personnes célèbres demandaient aux peintres de faire leur portrait. Regarde attentivement le visage de 'La Muse Céleste' : que ressent-elle ? Est-elle triste, sereine ou mystérieuse ?",
            keyPoints = listOf(
                "Un portrait est la représentation d'une personne réelle ou imaginaire.",
                "Les yeux et la bouche sont les deux parties les plus importantes pour deviner l'émotion d'un personnage.",
                "Ses vêtements sont en velours vert émeraude avec des broderies en fils d'or véritable.",
                "Le peintre a estompé les contours du visage pour donner l'impression que la dame respire doucement."
            ),
            questionsForObservation = listOf(
                "Où regarde la Muse ? Regarde-t-elle vers toi ou vers le lointain ?",
                "Porte-t-elle des bijoux ? Peux-tu trouver son diadème et son collier ?",
                "Quelle couleur domine dans sa robe royale ?"
            ),
            vocabularyWords = mapOf(
                "Portrait" to "Image d'une personne qui montre son visage et souvent son buste.",
                "Émeraude" to "Une pierre précieuse d'une magnifique couleur verte brillante.",
                "Sfumato" to "Mot italien qui veut dire 'en fumée' : les contours sont flous et doux."
            ),
            suggestedActivity = "Prends un miroir et dessine ton autoportrait en choisissant une émotion : joyeux, étonné ou pensif !",
            relatedArtworkIds = listOf("art_muse"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_ce1_3",
                    question = "Qu'est-ce qu'un portrait en peinture ?",
                    options = listOf("Un dessin d'une voiture", "Une image qui montre le visage d'une personne", "Une assiette de fruits", "Une carte géographique"),
                    correctOptionIndex = 1,
                    explanation = "Super ! Un portrait sert à garder le souvenir du visage d'une personne !"
                )
            ),
            keywords = listOf("CE1", "portrait", "visage", "yeux", "muse", "robe", "émeraude", "velours", "expression", "autoportrait", "regard")
        ),

        // ==================== CM1 DOSSIERS ====================
        SchoolDossier(
            id = "dossier_cm1_antiquity",
            grade = SchoolGrade.CM1,
            title = "L'Antiquité Grecque : Dieux, Déesses et Marbres Blancs",
            theme = "Histoire & Statuaire Antique",
            curriculumProgram = "Cycle 3 (CM1) : Les traces du passé antique, la citoyenneté à Athènes, les mythes de l'Olympe et les canons de beauté classiques.",
            introduction = "Dans la Grèce antique, les sculpteurs sculptaient d'immenses blocs de marbre blanc pour rendre hommage aux dieux de l'Olympe. Découvre la déesse Aphrodite (Vénus) et le dieu Apollon : pourquoi ces statues semblaient-elles prêtes à se mettre en mouvement ?",
            keyPoints = listOf(
                "Apollon est le dieu grec de la lumière, de la poésie, de la musique et des arts.",
                "Aphrodite (appelée Vénus par les Romains) est la déesse de la beauté, de l'amour et de la mer.",
                "Le marbre de Paros et de Carrare est une pierre naturelle blanche très pure extraite de carrières souterraines.",
                "Les statues grecques étaient à l'origine peintes de couleurs vives et dorées, effacées par les siècles !",
                "La pose avec une hanche inclinée s'appelle le contrapposto : le corps ne semble plus figé comme une colonne."
            ),
            questionsForObservation = listOf(
                "Observe la pose de Vénus : sur quelle jambe repose tout son poids ?",
                "Comment le sculpteur a-t-il réussi à donner au marbre l'aspect d'un tissu souple et fluide ?",
                "Pourquoi les statues antiques n'ont-elles souvent plus de bras lorsqu'on les retrouve dans des fouilles archéologiques ?"
            ),
            vocabularyWords = mapOf(
                "Mythologie" to "Ensemble des récits sacrés et des légendes sur les dieux et héros antiques.",
                "Contrapposto" to "Terme italien désignant la posture naturelle où le poids repose sur une seule jambe.",
                "Bas-relief" to "Sculpture qui se détache faiblement d'un fond plat en pierre.",
                "Archéologie" to "Science qui étudie les vestiges du passé enfouis dans la terre ou sous la mer."
            ),
            suggestedActivity = "Modèle avec de l'argile ou de la pâte à modeler un buste antique et invente son nom de dieu grec !",
            relatedArtworkIds = listOf("art_venus", "art_apollo"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm1_1",
                    question = "De quelle déesse la sculpture de marbre blanc 'Vénus' est-elle l'incarnation ?",
                    options = listOf("Athéna la déesse de la guerre", "Aphrodite déesse de la beauté et de l'amour", "Artémis la chasseresse", "Déméter déesse des récoltes"),
                    correctOptionIndex = 1,
                    explanation = "Exactement ! Les Grecs l'appelaient Aphrodite et les Romains la nommaient Vénus !"
                ),
                QuizQuestion(
                    id = "q_cm1_2",
                    question = "Les statues antiques étaient-elles toutes blanches à l'époque des Grecs ?",
                    options = listOf("Oui, le marbre ne supporte pas la peinture", "Non, elles étaient peintes de couleurs éclatantes et de dorures", "Elles étaient en bois noirci", "Elles étaient transparentes"),
                    correctOptionIndex = 1,
                    explanation = "Très bonne réponse ! Des microscopes modernes ont révélé des pigments dorés et rouges vifs sur les statues antiques !"
                )
            ),
            keywords = listOf("CM1", "Antiquité", "Grèce", "Rome", "marbre", "Vénus", "Apollon", "mythologie", "Olympe", "contrapposto", "sculpture", "archéologie", "drapé")
        ),
        SchoolDossier(
            id = "dossier_cm1_renaissance",
            grade = SchoolGrade.CM1,
            title = "Léonard de Vinci & la Renaissance : Quand la Science Éclaire l'Art",
            theme = "L'Époque Moderne & la Renaissance",
            curriculumProgram = "Cycle 3 (CM1) : Les grandes découvertes, la Renaissance artistique, l'humanisme et l'héritage de François Ier à Chambord.",
            introduction = "Au XVIe siècle, l'Italie puis la France vivent une renaissance extraordinaire. Les artistes étudient l'anatomie du corps humain, inventent la perspective mathématique et utilisent la science pour créer des illusions d'espace saisissantes.",
            keyPoints = listOf(
                "Léonard de Vinci n'était pas seulement peintre : il a dessiné des machines volantes, des ponts et étudié le corps humain.",
                "La perspective permet de donner l'impression de profondeur sur une feuille ou une toile entièrement plate.",
                "Le roi François Ier a invité Léonard de Vinci en France au château du Clos Lucé près d'Amboise.",
                "Le sfumato permet de fondre les contours dans l'air comme de la brume."
            ),
            questionsForObservation = listOf(
                "Où se trouve la ligne d'horizon dans un paysage de la Renaissance ?",
                "Pourquoi les objets lointains paraissent-ils plus petits et plus bleutés ?",
                "Comment la lumière sculpte-t-elle les pommettes et le menton de la Muse ?"
            ),
            vocabularyWords = mapOf(
                "Perspective" to "Technique géométrique pour représenter le monde en trois dimensions sur une surface plane.",
                "Point de fuite" to "Le point vers lequel convergent toutes les lignes de fuite sur la ligne d'horizon.",
                "Humanisme" to "Mouvement d'idées qui met l'homme et la connaissance au centre du monde.",
                "Mécène" to "Personne riche qui protège et finance les artistes pour créer des chefs-d'œuvre."
            ),
            suggestedActivity = "Prends une règle et trace une route qui rétrécit jusqu'à un point de fuite sur ta feuille : tu viens de créer une perspective !",
            relatedArtworkIds = listOf("art_muse", "art_gallery"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm1_3",
                    question = "Quel célèbre roi de France a fait venir Léonard de Vinci en France ?",
                    options = listOf("Louis XIV", "François Ier", "Charlemagne", "Napoléon"),
                    correctOptionIndex = 1,
                    explanation = "Bravo ! François Ier aimait tellement Léonard de Vinci qu'il l'a logé au château du Clos Lucé !"
                )
            ),
            keywords = listOf("CM1", "Renaissance", "Léonard de Vinci", "François Ier", "perspective", "point de fuite", "humanisme", "sfumato", "XVIe siècle", "sciences", "Italie", "France")
        ),

        // ==================== CM2 DOSSIERS ====================
        SchoolDossier(
            id = "dossier_cm2_louisxiv",
            grade = SchoolGrade.CM2,
            title = "Versailles & La Grande Galerie : L'Art au Service du Roi Soleil",
            theme = "La Monarchie Absolue & le XVIIe Siècle",
            curriculumProgram = "Cycle 3 (CM2) : Louis XIV et la monarchie absolue, le château de Versailles, l'architecture baroque et classique, les symboles du pouvoir royal.",
            introduction = "Louis XIV a fait de Versailles le centre du monde. La Grande Galerie des Glaces mesure plus de 73 mètres de long et comporte 357 miroirs qui reflètent la lumière des jardins royaux. Comment l'art baroque et classique glorifiait-il la puissance royale ?",
            keyPoints = listOf(
                "À l'époque de Louis XIV, fabriquer de grands miroirs était un secret d'État très cher détenu par Venise.",
                "Les miroirs de la galerie renvoient la lumière du soleil couchant pour rappeler que le Roi est 'le Roi Soleil'.",
                "Le plafond est entièrement peint par Charles Le Brun avec des scènes d'allégories et de victoires militaires.",
                "L'art classique français recherche l'ordre, la symétrie, les colonnes et la majesté monumentale."
            ),
            questionsForObservation = listOf(
                "Combien d'arches de fenêtres font face aux arches de miroirs dans la galerie ?",
                "Quels matériaux précieux remarques-tu sur les murs (stucs, dorures à la feuille, marbres colorés) ?",
                "Quel effet produit le reflet de la lumière sur le parquet de bois ciré ?"
            ),
            vocabularyWords = mapOf(
                "Galerie des Glaces" to "Grande salle d'apparat du château de Versailles ornée de 357 miroirs.",
                "Allégorie" to "Représentation d'une idée abstraite (comme la Paix ou la Victoire) sous la forme d'un personnage.",
                "Feuille d'or" to "Pellicule d'or martelée très finement appliquée sur les sculptures pour les faire briller.",
                "Monarchie absolue" to "Régime politique où le roi détient tous les pouvoirs par droit divin."
            ),
            suggestedActivity = "Fais un plan schématique de la Galerie des Glaces en plaçant les fenêtres à gauche et les miroirs à droite.",
            relatedArtworkIds = listOf("art_gallery"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm2_1",
                    question = "Pourquoi Louis XIV a-t-il choisi le soleil comme emblème personnel ?",
                    options = listOf("Parce qu'il aimait la plage", "Parce que le soleil donne la vie, brille partout et ne se couche jamais", "Parce qu'il portait des lunettes de soleil", "Parce que son armure était jaune"),
                    correctOptionIndex = 1,
                    explanation = "Exact ! Le soleil était le symbole d'Apollon, apportant l'ordre, la lumière et la chaleur sur tout le royaume !"
                )
            ),
            keywords = listOf("CM2", "Versailles", "Louis XIV", "Roi Soleil", "Galerie des Glaces", "miroirs", "Baroque", "XVIIe siècle", "château", "monarchie", "or", "pouvoir")
        ),
        SchoolDossier(
            id = "dossier_cm2_impressionism",
            grade = SchoolGrade.CM2,
            title = "La Révolution Impressionniste : Peindre la Lumière en Plein Air",
            theme = "Le XIXe Siècle & les Révolutions Artistiques",
            curriculumProgram = "Cycle 3 (CM2) : La révolution industrielle, les chemins de fer, la rupture des Impressionnistes avec les académies officielles.",
            introduction = "Au XIXe siècle, une révolution transforme la peinture : les Impressionnistes (Monet, Renoir, Degas) sortent des ateliers sombres pour peindre dehors. Grâce à l'invention des tubes de peinture et des trains pour aller à la campagne, ils peignent sur le vif.",
            keyPoints = listOf(
                "Avant 1874, les peintres devaient peindre des sujets mythologiques ou historiques très stricts pour être acceptés au Salon officiel.",
                "L'invention du tube de peinture souple permet enfin d'emporter ses couleurs dans la nature sans qu'elles sèchent !",
                "Les impressionnistes juxtaposent des petites touches de couleur pure au lieu de les mélanger sur la palette : c'est l'œil qui fait le mélange !",
                "Monet peint plusieurs fois la même meule de foin ou la cathédrale de Rouen pour montrer que la lumière change à chaque heure."
            ),
            questionsForObservation = listOf(
                "Dans la 'Nuit Vénitienne Étoilée', observe les touches de peinture : sont-elles lisses ou épaisses et vigoureuses ?",
                "De quelle couleur sont les ombres sur l'eau ? Pourquoi ne sont-elles pas simplement noires ?",
                "Quelle sensation de mouvement te procure le ciel en tourbillon ?"
            ),
            vocabularyWords = mapOf(
                "Pleinairisme" to "Fait de peindre directement à l'extérieur face au motif naturel.",
                "Impasto" to "Peinture appliquée en couche très épaisse créant un relief palpable.",
                "Touche divisée" to "Petits traits de pinceau posés les uns à côté des autres sans être fondus.",
                "Rétine" to "Membrane au fond de l'œil qui capte la lumière et les couleurs."
            ),
            suggestedActivity = "Fiche d'exposé CM2 : Rédige une biographie de Claude Monet en expliquant pourquoi sa toile 'Impression, soleil levant' a donné son nom à tout le mouvement.",
            relatedArtworkIds = listOf("art_venetian_night"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm2_2",
                    question = "Quelle invention technique du XIXe siècle a permis aux impressionnistes de peindre en plein air ?",
                    options = listOf("L'ordinateur portable", "Le tube de peinture souple en étain", "L'appareil photo numérique", "L'hélicoptère"),
                    correctOptionIndex = 1,
                    explanation = "Exactement ! Auparavant, la peinture séchait dans des vessies de porc très fragiles. Le tube de peinture a libéré les artistes !"
                ),
                QuizQuestion(
                    id = "q_cm2_3",
                    question = "Pourquoi les impressionnistes peignaient-ils rapidement avec des petites touches ?",
                    options = listOf("Parce qu'ils étaient en retard pour le dîner", "Pour capturer la lumière fugitive avant que le soleil ne tourne", "Parce qu'ils n'avaient pas de gros pinceaux", "Pour faire des économies"),
                    correctOptionIndex = 1,
                    explanation = "Superbe réponse ! La lumière change toutes les minutes dans la nature : il fallait peindre l'instant fugace !"
                )
            ),
            keywords = listOf("CM2", "Impressionnisme", "Monet", "Van Gogh", "lumière", "XIXe siècle", "plein air", "tube de peinture", "couleur", "impasto", "révolution industrielle", "vapeur")
        ),
        SchoolDossier(
            id = "dossier_cm2_methodology",
            grade = SchoolGrade.CM2,
            title = "Méthodologie Histoire des Arts : Comment Analyser un Tableau pour un Exposé",
            theme = "Méthode d'Analyse d'Œuvre (Cycle 3 / Collège)",
            curriculumProgram = "Cycle 3 (CM2) : Histoire des Arts — Décrire une œuvre, identifier le cartel d'exposition, analyser les plans (1er plan, arrière-plan) et la composition.",
            introduction = "Devant un tableau ou une sculpture, un critique d'art ou un élève qui prépare un exposé suit une méthode en 4 étapes simples : Présenter (le Cartel), Décrire (les plans), Analyser (les couleurs et la lumière) et Interpréter (le message de l'artiste).",
            keyPoints = listOf(
                "Étape 1 : Le Cartel — Titre, nom de l'artiste, date de création, technique/médium (ex: huile sur toile) et dimensions réelles.",
                "Étape 2 : Les Plans — Premier plan (au plus près de nous), second plan (au milieu), arrière-plan (au fond).",
                "Étape 3 : Les Lignes de force — Lignes horizontales (calme), lignes verticales (majesté), lignes diagonales (action et drame).",
                "Étape 4 : La Lumière — D'où vient la source lumineuse ? Y a-t-il des contrastes forts (clair-obscur) ?"
            ),
            questionsForObservation = listOf(
                "Trouve le cartel d'une œuvre du musée : quel est son support ?",
                "Sur 'La Muse Céleste', où se situe le personnage par rapport à l'arrière-plan ?",
                "Quel est le détail le plus éclairé du tableau ?"
            ),
            vocabularyWords = mapOf(
                "Cartel" to "Petite plaque placée à côté d'une œuvre indiquant son auteur, son titre et sa date.",
                "Lignes de force" to "Lignes directrices invisibles qui guident le regard du spectateur dans la composition.",
                "Premier plan" to "La partie de l'œuvre située le plus près de l'observateur.",
                "Médium" to "Le matériau utilisé pour créer l'œuvre (huile, marbre, bronze, pastel, aquarelle)."
            ),
            suggestedActivity = "Choisis ton œuvre préférée dans le musée et rédige sa 'Fiche d'identité d'artiste' pour la présenter à ta classe !",
            relatedArtworkIds = listOf("art_muse", "art_venus", "art_venetian_night", "art_gallery"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm2_4",
                    question = "Que contient le cartel placé à côté d'une œuvre dans un musée ?",
                    options = listOf("Le menu de la cafétéria du musée", "Le titre, l'artiste, l'époque, la technique et les dimensions", "Le prix de vente du musée", "Le numéro de téléphone du gardien"),
                    correctOptionIndex = 1,
                    explanation = "Exactement ! Le cartel donne toutes les informations d'identité officielles de l'œuvre d'art !"
                )
            ),
            keywords = listOf("CM2", "méthode", "analyse", "cartel", "plans", "composition", "exposé", "histoire des arts", "école", "vocabulaire", "lignes de force", "critique")
        ),
        // ==================== MIDDLE EAST & ISLAMIC ART DOSSIERS ====================
        SchoolDossier(
            id = "dossier_ce1_orient_patterns",
            grade = SchoolGrade.CE1,
            title = "Les Étoiles d'Or & Merveilles de Babylone",
            theme = "Arts de l'Orient Ancien & Couleurs d'Émail",
            curriculumProgram = "Cycle 2 (CE1) : Découvrir des œuvres de civilisations lointaines, identifier des animaux protecteurs dans l'art, associer formes et matières.",
            introduction = "Sais-tu qu'à Babylone et à Damas, il y a très longtemps, les bâtisseurs fabriquaient de magnifiques briques bleues comme le ciel d'été pour construire des portes géantes ? Sur 'La Porte d'Ishtar', de fiers lions dorés avancent pour protéger la cité magique !",
            keyPoints = listOf(
                "Le bleu éclatant des briques de Babylone est fait avec de la poudre de roche précieuse appelée lapis-lazuli.",
                "Le lion doré symbolise le courage, la force et la protection de la déesse Ishtar.",
                "Les artistes d'Orient aimaient tracer des étoiles dorées et des motifs répétés avec une régularité parfaite.",
                "L'astrolabe est un objet rond en laiton brillant que les enfants et les marins apprenaient à regarder pour s'orienter la nuit."
            ),
            questionsForObservation = listOf(
                "Combien de pattes a le lion doré sur les briques bleues ?",
                "Pourquoi les bâtisseurs ont-ils choisi la couleur bleue pour une immense porte de ville ?",
                "Si tu devais inventer un animal protecteur pour ta chambre, quel animal choisirais-tu ?"
            ),
            vocabularyWords = mapOf(
                "Lapis-lazuli" to "Une pierre précieuse d'un bleu très profond et brillant comme la nuit.",
                "Brique émaillée" to "Une brique d'argile cuite au four et recouverte d'un vernis brillant et coloré.",
                "Bas-relief" to "Une sculpture qui ressort légèrement d'un mur ou d'une plaque sans en être détachée.",
                "Astrolabe" to "Un instrument en cuivre ou en laiton qui servait à regarder la position des étoiles."
            ),
            suggestedActivity = "Prends une feuille bleue foncée et dessine à la peinture jaune ou dorée ton lion protecteur avec des motifs géométriques tout autour !",
            relatedArtworkIds = listOf("art_ishtar_gate", "art_islamic_astrolabe"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_ce1_orient_1",
                    question = "Quel animal doré marche sur les briques bleues de la Porte de Babylone ?",
                    options = listOf("Un kangourou bondissant", "Un lion fier et majestueux", "Un petit panda", "Un flamant rose"),
                    correctOptionIndex = 1,
                    explanation = "Bravo ! Le lion est le symbole sacré du courage et de la protection dans l'Orient antique !"
                ),
                QuizQuestion(
                    id = "q_ce1_orient_2",
                    question = "De quelle couleur précieuse brille la Porte d'Ishtar ?",
                    options = listOf("Bleu lapis-lazuli éclatant", "Vert fluo", "Gris souris", "Marron terreux"),
                    correctOptionIndex = 0,
                    explanation = "Magnifique ! Ce bleu cobalt émaillé étincelait sous le soleil chaud du Moyen-Orient !"
                )
            ),
            keywords = listOf("CE1", "Babylone", "lion", "or", "bleu", "étoile", "astrolabe", "Orient", "Moyen-Orient", "Islam", "Mesopotamie", "brique", "Ishtar", "بابل", "عشتار", "شرق", "أسد")
        ),
        SchoolDossier(
            id = "dossier_cm1_astrolabe",
            grade = SchoolGrade.CM1,
            title = "L'Astrolabe & les Savants Astronomes d'Orient",
            theme = "Sciences, Arts & Navigation dans l'Âge d'Or Islamique",
            curriculumProgram = "Cycle 3 (CM1) : Histoire & Sciences — Les grands foyers de civilisation médiévale, l'astronomie arabo-musulmane, l'invention des instruments de mesure du temps.",
            introduction = "Bien avant le GPS, les smartphones et les satellites, les savants arabes de Damas, Bagdad et Cordoue calculaient la position des planètes et l'heure solaire grâce à un bijou d'ingénierie et de beauté : l'astrolabe planisphérique en laiton gravé d'or.",
            keyPoints = listOf(
                "L'astrolabe se compose d'une boîte circulaire (la mère), de disques gravés pour différentes latitudes (les tympans), et d'une grille ajourée (l'araignée ou rete).",
                "Les pointes triangulaires ou flammées sur l'araignée indiquent la position exacte des étoiles les plus brillantes (comme Véga ou Sirius).",
                "Cet instrument permettait aux caravanes dans le désert et aux marins en mer Rouge et en Méditerranée de naviguer sans jamais se perdre.",
                "En plus de son utilité scientifique, chaque astrolabe est un chef-d'œuvre artistique orné de calligraphies coufiques et d'arabesques florales ciselées à la main."
            ),
            questionsForObservation = listOf(
                "Repère dans la vue 3D l'anneau de suspension au sommet : à quoi servait-il ?",
                "Combien de pointes en forme de flammes vois-tu sur la grille céleste ?",
                "Pourquoi les chiffres et lettres sont-ils gravés avec des arabesques calligraphiques ?"
            ),
            vocabularyWords = mapOf(
                "Araignée (Rete)" to "La plaque ajourée pivotante qui porte les pointes des étoiles et le cercle du zodiaque.",
                "Latitude" to "La distance d'un point de la Terre par rapport à la ligne de l'Équateur.",
                "Laiton" to "Un alliage de cuivre et de zinc d'une belle couleur dorée, résistant à la corrosion marine.",
                "Âge d'Or Islamique" to "Grande période d'épanouissement scientifique, artistique et philosophique du VIIIe au XIVe siècle."
            ),
            suggestedActivity = "Fabrique un cherche-étoiles en carton : découpe un cercle mobile pour aligner la date et l'heure et observer les constellations ce soir !",
            relatedArtworkIds = listOf("art_islamic_astrolabe", "art_alhambra_court"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm1_orient_1",
                    question = "À quoi servait principalement un astrolabe astronomique ?",
                    options = listOf("À écouter de la musique radio", "À mesurer la hauteur des étoiles et donner l'heure exacte", "À faire griller du pain", "À peser des sacs de farine"),
                    correctOptionIndex = 1,
                    explanation = "Parfait ! L'astrolabe était le véritable ordinateur de bord astronomique du Moyen Âge !"
                ),
                QuizQuestion(
                    id = "q_cm1_orient_2",
                    question = "Dans quel métal doré noble l'astrolabe de Damas a-t-il été ciselé ?",
                    options = listOf("En plastique recyclé", "En laiton finement damasquiné et doré", "En carton mâché", "En étain brut"),
                    correctOptionIndex = 1,
                    explanation = "Exactement ! Le laiton ciselé permettait une précision micrométrique pour les graduations d'angles !"
                )
            ),
            keywords = listOf("CM1", "astrolabe", "science", "astronomie", "Damas", "étoiles", "géométrie", "Islam", "Moyen-Orient", "mathématiques", "navigation", "laiton", "savants", "Cordoue", "Bagdad", "أسطرلاب", "فلك", "عرب")
        ),
        SchoolDossier(
            id = "dossier_cm2_alhambra_babylone",
            grade = SchoolGrade.CM2,
            title = "Babylone & l'Alhambra : Architecture Monumentale et Géométrie Sacrée",
            theme = "Grandes Civilisations & Patrimoine Mondial de l'Humanité",
            curriculumProgram = "Cycle 3 (CM2) : Histoire des Arts & Géométrie — L'art hispano-mauresque, l'architecture nasride, l'acoustique et la géométrie des pavages et voûtes alvéolées.",
            introduction = "De Babylone en Mésopotamie jusqu'à la forteresse rouge de l'Alhambra de Grenade, les architectes d'Orient ont su combiner la science des mathématiques, la maîtrise de l'eau et la splendeur des décors pour créer des palais et des portes légendaires qui traversent les millénaires.",
            keyPoints = listOf(
                "L'Alhambra utilise des motifs de zelliges : des mosaïques géométriques dont les formes s'emboîtent sans laisser aucun vide (pavages parfaits).",
                "Les muqarnas sont des stalactites en plâtre sculpté qui créent des transitions spectaculaires entre les angles droits et les coupoles circulaires.",
                "L'eau joue un rôle essentiel : fontaines, vasques et canaux créent un miroir liquide rafraîchissant et amplifient la sérénité du lieu.",
                "À Babylone, les architectes de Nabuchodonosor ont inventé des mortiers au bitume et des émaux vitrifiés capables de résister à plus de 2500 ans d'histoire."
            ),
            questionsForObservation = listOf(
                "Combien de lions soutiennent la grande vasque de marbre dans la Cour de l'Alhambra ?",
                "Observe les voûtes de stuc : à quoi ressemble la texture sculptée ? (nids d'abeille, stalactites ?)",
                "Quelle phrase célèbre les artistes nasrides ont-ils gravée en calligraphie arabe tout autour des murs ?"
            ),
            vocabularyWords = mapOf(
                "Zellige" to "Pavage de céramiques émaillées découpées à la main selon des formes géométriques parfaites.",
                "Muqarnas" to "Voûte alvéolée tridimensionnelle rappelant des nids d'abeille suspendus.",
                "Pavage" to "Agencement de figures géométriques recouvrant entièrement une surface plane sans chevauchement ni interstice.",
                "Épigraphie" to "Étude des inscriptions et écritures gravées sur la pierre ou les monuments."
            ),
            suggestedActivity = "Avec une règle et un compas, trace une rosace géométrique à 8 branches inspirée des mosaïques de l'Alhambra et colorie-la avec des couleurs complémentaires !",
            relatedArtworkIds = listOf("art_alhambra_court", "art_ishtar_gate", "art_islamic_astrolabe"),
            quiz = listOf(
                QuizQuestion(
                    id = "q_cm2_orient_1",
                    question = "Comment appelle-t-on les spectaculaires voûtes alvéolées en forme de nids d'abeille sculptées dans le stuc de l'Alhambra ?",
                    options = listOf("Les gargouilles", "Les muqarnas", "Les colonnes doriques", "Les vitraux"),
                    correctOptionIndex = 1,
                    explanation = "Bravo ! Les muqarnas sont l'une des inventions architecturales les plus admirées de la civilisation islamique !"
                ),
                QuizQuestion(
                    id = "q_cm2_orient_2",
                    question = "Quel élément naturel circule dans la Cour des Lions pour rafraîchir l'atmosphère et refléter les arches ?",
                    options = listOf("L'eau vive jaillissant de la fontaine et des canaux", "Des flammes de torches", "Du sable du désert", "Des feuilles mortes"),
                    correctOptionIndex = 0,
                    explanation = "Magnifique ! L'eau symbolise la pureté et la vie dans les jardins paradisiaques de l'Alhambra !"
                )
            ),
            keywords = listOf("CM2", "Babylone", "Alhambra", "muqarnas", "zellige", "Ishtar", "Mésopotamie", "Andalousie", "Islam", "architecture", "patrimoine", "archéologie", "fontaine", "lions", "géométrie", "قصر الحمراء", "بابل", "مقرنصات", "زليج", "شرق")
        )
    )
}
