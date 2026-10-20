package com.example.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.model.Artwork
import com.example.model.MuseumRepository
import com.example.model.SculptureType

enum class MuseumTab {
    EXHIBITIONS,
    SCULPTURES_3D,
    VIRTUAL_TOURS,
    ENCYCLOPEDIA,
    FAVORITES
}

class MuseumViewModel : ViewModel() {

    var currentTab by mutableStateOf(MuseumTab.EXHIBITIONS)
        private set

    var selectedArtworkForDetail by mutableStateOf<Artwork?>(null)
        private set

    var active3DSculptureType by mutableStateOf(SculptureType.VENUS_APHRODITE)
        private set

    var favoriteArtworkIds by mutableStateOf(setOf("art_venetian_night", "art_venus", "art_muse"))
        private set

    val allArtworks: List<Artwork> = MuseumRepository.artworks

    val favoriteArtworks: List<Artwork>
        get() = allArtworks.filter { favoriteArtworkIds.contains(it.id) }

    fun selectTab(tab: MuseumTab) {
        currentTab = tab
    }

    fun openArtworkDetail(artwork: Artwork) {
        selectedArtworkForDetail = artwork
    }

    fun closeArtworkDetail() {
        selectedArtworkForDetail = null
    }

    fun open3DViewerForArtwork(artwork: Artwork) {
        val sculpture = artwork.sculptureType ?: SculptureType.VENUS_APHRODITE
        active3DSculptureType = sculpture
        selectedArtworkForDetail = null
        currentTab = MuseumTab.SCULPTURES_3D
    }

    fun toggleFavorite(artwork: Artwork) {
        favoriteArtworkIds = if (favoriteArtworkIds.contains(artwork.id)) {
            favoriteArtworkIds - artwork.id
        } else {
            favoriteArtworkIds + artwork.id
        }
    }
}
