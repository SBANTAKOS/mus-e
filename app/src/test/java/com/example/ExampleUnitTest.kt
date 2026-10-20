package com.example

import com.example.engine3d.SculptureGenerator
import com.example.engine3d.SculptureRenderer
import com.example.engine3d.Vec3
import com.example.model.ArtworkCategory
import com.example.model.MuseumRepository
import com.example.model.SculptureMaterial
import com.example.model.SculptureType
import com.example.viewmodel.MuseumTab
import com.example.viewmodel.MuseumViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testVector3DMath() {
        val v1 = Vec3(1f, 0f, 0f)
        val v2 = Vec3(0f, 1f, 0f)
        val cross = v1.cross(v2)
        assertEquals(0f, cross.x, 0.001f)
        assertEquals(0f, cross.y, 0.001f)
        assertEquals(1f, cross.z, 0.001f)

        val dot = v1.dot(v2)
        assertEquals(0f, dot, 0.001f)

        val rotY = v1.rotateY((Math.PI / 2).toFloat())
        assertEquals(0f, rotY.x, 0.001f)
        assertEquals(-1f, rotY.z, 0.001f)
    }

    @Test
    fun testSculptureGeometryGeneration() {
        for (type in SculptureType.values()) {
            val mesh = SculptureGenerator.getMeshFor(type)
            assertNotNull(mesh)
            assertTrue("Mesh for ${type.name} should have vertices", mesh.vertices.isNotEmpty())
            assertTrue("Mesh for ${type.name} should have faces", mesh.faces.isNotEmpty())

            val projected = SculptureRenderer.projectAndRender(
                mesh = mesh,
                pitchRad = 0f,
                yawRad = 0.5f,
                zoomScale = 1.0f,
                canvasWidth = 800f,
                canvasHeight = 1200f,
                lightAngleRad = 0.5f,
                material = SculptureMaterial.CARRARA_MARBLE,
                wireframeMode = false
            )
            assertTrue("Should project faces", projected.isNotEmpty())
        }
    }

    @Test
    fun testMuseumRepositoryDataset() {
        val artworks = MuseumRepository.artworks
        assertTrue("Artworks list must not be empty", artworks.isNotEmpty())

        val has3D = artworks.any { it.has3DModel }
        assertTrue("Must include 3D sculpture artworks", has3D)

        val rooms = MuseumRepository.virtualRooms
        assertTrue("Must have 360 virtual rooms", rooms.isNotEmpty())
        for (room in rooms) {
            assertTrue("Room ${room.id} must have hotspots", room.hotspots.isNotEmpty())
        }
    }

    @Test
    fun testViewModelStateOperations() {
        val vm = MuseumViewModel()
        assertEquals(MuseumTab.EXHIBITIONS, vm.currentTab)

        vm.selectTab(MuseumTab.SCULPTURES_3D)
        assertEquals(MuseumTab.SCULPTURES_3D, vm.currentTab)

        val artwork = vm.allArtworks.first()
        vm.openArtworkDetail(artwork)
        assertEquals(artwork, vm.selectedArtworkForDetail)

        vm.closeArtworkDetail()
        assertEquals(null, vm.selectedArtworkForDetail)

        val initialFavsCount = vm.favoriteArtworkIds.size
        vm.toggleFavorite(artwork)
        if (vm.favoriteArtworkIds.contains(artwork.id)) {
            assertEquals(initialFavsCount + 1, vm.favoriteArtworkIds.size)
        } else {
            assertEquals(initialFavsCount - 1, vm.favoriteArtworkIds.size)
        }

        vm.selectTab(MuseumTab.ENCYCLOPEDIA)
        assertEquals(MuseumTab.ENCYCLOPEDIA, vm.currentTab)
    }

    @Test
    fun testEncyclopediaDataset() {
        val movements = com.example.model.EncyclopediaRepository.artMovements
        assertTrue("Movements should not be empty", movements.isNotEmpty())

        val techniques = com.example.model.EncyclopediaRepository.artTechniques
        assertTrue("Techniques should not be empty", techniques.isNotEmpty())

        val biographies = com.example.model.EncyclopediaRepository.masterBiographies
        assertTrue("Biographies should not be empty", biographies.isNotEmpty())

        val milestones = com.example.model.EncyclopediaRepository.timelineMilestones
        assertTrue("Timeline milestones should not be empty", milestones.isNotEmpty())
    }

    @Test
    fun testSchoolResources() {
        val dossiers = com.example.model.SchoolRepository.dossiers
        assertTrue("Dossiers should not be empty", dossiers.isNotEmpty())

        val ce1Dossiers = dossiers.filter { it.grade == com.example.model.SchoolGrade.CE1 }
        val cm1Dossiers = dossiers.filter { it.grade == com.example.model.SchoolGrade.CM1 }
        val cm2Dossiers = dossiers.filter { it.grade == com.example.model.SchoolGrade.CM2 }

        assertTrue("Must have CE1 dossiers", ce1Dossiers.isNotEmpty())
        assertTrue("Must have CM1 dossiers", cm1Dossiers.isNotEmpty())
        assertTrue("Must have CM2 dossiers", cm2Dossiers.isNotEmpty())

        for (d in dossiers) {
            assertTrue("Dossier ${d.id} must have keywords", d.keywords.isNotEmpty())
            assertTrue("Dossier ${d.id} must have observation questions", d.questionsForObservation.isNotEmpty())
        }

        // Check artwork keywords
        for (art in com.example.model.MuseumRepository.artworks) {
            assertTrue("Artwork ${art.id} must have keywords", art.keywords.isNotEmpty())
            assertTrue("Artwork ${art.id} must have suitable grades", art.suitableGrades.isNotEmpty())
        }
    }

    @Test
    fun testUserLevelProgression() {
        val novice = com.example.user.UserLevel.fromXp(50)
        assertEquals(com.example.user.UserLevel.NOVICE, novice)

        val amateur = com.example.user.UserLevel.fromXp(200)
        assertEquals(com.example.user.UserLevel.AMATEUR, amateur)

        val connaisseur = com.example.user.UserLevel.fromXp(550)
        assertEquals(com.example.user.UserLevel.CONNAISSEUR, connaisseur)

        val conservateur = com.example.user.UserLevel.fromXp(1200)
        assertEquals(com.example.user.UserLevel.CONSERVATEUR, conservateur)

        val grandMecene = com.example.user.UserLevel.fromXp(2500)
        assertEquals(com.example.user.UserLevel.GRAND_MECENE, grandMecene)

        for (lvl in com.example.user.UserLevel.values()) {
            assertTrue("Level ${lvl.name} must have perks", lvl.perks.isNotEmpty())
        }
    }

    @Test
    fun testAdMobConfiguration() {
        assertEquals("ca-app-pub-5349893955586234~4645951817", com.example.admob.AdMobConfig.APP_ID)
        assertEquals("ca-app-pub-5349893955586234/3713063962", com.example.admob.AdMobConfig.PROD_AD_UNIT_ID)
        assertTrue(com.example.admob.AdMobConfig.BANNER_TEST_UNIT_ID.contains("ca-app-pub-3940256099942544"))
        assertTrue(com.example.admob.AdMobConfig.INTERSTITIAL_TEST_UNIT_ID.contains("ca-app-pub-3940256099942544"))
        assertTrue(com.example.admob.AdMobConfig.REWARDED_TEST_UNIT_ID.contains("ca-app-pub-3940256099942544"))
    }
}
