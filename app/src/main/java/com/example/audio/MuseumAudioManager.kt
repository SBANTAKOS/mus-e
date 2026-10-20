package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.Artwork
import java.util.Locale
import kotlin.concurrent.thread
import kotlin.math.sin

enum class NarrationLanguage(val code: String, val label: String) {
    ENGLISH("en", "English"),
    ARABIC("ar", "العربية"),
    FRENCH("fr", "Français")
}

class MuseumAudioManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    var isPlaying by mutableStateOf(false)
        private set

    var currentArtwork by mutableStateOf<Artwork?>(null)
        private set

    var playbackProgress by mutableFloatStateOf(0f)
        private set

    var currentAppLanguage by mutableStateOf(AppLanguage.ENGLISH)

    // Backward-compatible language enum property
    val language: NarrationLanguage
        get() = when (currentAppLanguage) {
            AppLanguage.ENGLISH -> NarrationLanguage.ENGLISH
            AppLanguage.ARABIC -> NarrationLanguage.ARABIC
            AppLanguage.FRENCH -> NarrationLanguage.FRENCH
        }

    var isAmbientSoundActive by mutableStateOf(false)
        private set

    private var ambientTrack: AudioTrack? = null
    private var isAmbientRunning = false
    private val mainHandler = Handler(Looper.getMainLooper())
    private var progressRunnable: Runnable? = null
    private var estimatedDurationSec = 25f
    private var currentElapsedSec = 0f

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            updateTtsLanguage()
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    mainHandler.post {
                        isPlaying = true
                        startProgressTracking()
                    }
                }

                override fun onDone(utteranceId: String?) {
                    mainHandler.post {
                        isPlaying = false
                        playbackProgress = 1.0f
                        stopProgressTracking()
                    }
                }

                override fun onError(utteranceId: String?) {
                    mainHandler.post {
                        isPlaying = false
                        stopProgressTracking()
                    }
                }
            })
        }
    }

    private fun updateTtsLanguage() {
        if (!isTtsReady) return
        val targetLocale = when (currentAppLanguage) {
            AppLanguage.ENGLISH -> Locale.US
            AppLanguage.ARABIC -> Locale("ar")
            AppLanguage.FRENCH -> Locale.FRENCH
        }
        val res = tts?.setLanguage(targetLocale)
        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.getDefault())
        }
    }

    fun setAppLanguage(lang: AppLanguage) {
        currentAppLanguage = lang
        updateTtsLanguage()
        if (isPlaying && currentArtwork != null) {
            playArtworkNarration(currentArtwork!!)
        }
    }

    fun toggleLanguage() {
        val next = AppLanguageManager.cycleNextLanguage()
        setAppLanguage(next)
    }

    fun playArtworkNarration(artwork: Artwork) {
        currentArtwork = artwork
        val textToSpeak = when (currentAppLanguage) {
            AppLanguage.ENGLISH -> artwork.audioNarrationEn
            AppLanguage.ARABIC -> artwork.audioNarrationAr
            AppLanguage.FRENCH -> artwork.audioNarrationFr
        }

        // Estimate duration based on word count (~130 words per minute)
        val wordCount = textToSpeak.split("\\s+".toRegex()).size
        estimatedDurationSec = (wordCount / 2.2f).coerceAtLeast(10f)
        currentElapsedSec = 0f
        playbackProgress = 0f

        if (isTtsReady) {
            updateTtsLanguage()
            tts?.stop()
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "curator_audio_${artwork.id}")
            isPlaying = true
            startProgressTracking()
        } else {
            // Fallback progress animation if TTS engine initializes in background
            isPlaying = true
            startProgressTracking()
        }
    }

    fun pauseOrResume() {
        if (isPlaying) {
            tts?.stop()
            isPlaying = false
            stopProgressTracking()
        } else {
            currentArtwork?.let { playArtworkNarration(it) }
        }
    }

    fun stopAudio() {
        tts?.stop()
        isPlaying = false
        stopProgressTracking()
        playbackProgress = 0f
    }

    private fun startProgressTracking() {
        stopProgressTracking()
        progressRunnable = object : Runnable {
            override fun run() {
                if (isPlaying) {
                    currentElapsedSec += 0.2f
                    playbackProgress = (currentElapsedSec / estimatedDurationSec).coerceIn(0f, 1f)
                    if (playbackProgress >= 1f) {
                        isPlaying = false
                        stopProgressTracking()
                    } else {
                        mainHandler.postDelayed(this, 200)
                    }
                }
            }
        }
        mainHandler.post(progressRunnable!!)
    }

    private fun stopProgressTracking() {
        progressRunnable?.let { mainHandler.removeCallbacks(it) }
        progressRunnable = null
    }

    // Museum ambient reverb & harp soundscape synthesis
    fun toggleAmbientSound() {
        if (isAmbientSoundActive) {
            stopAmbientSound()
        } else {
            startAmbientSound()
        }
    }

    private fun startAmbientSound() {
        isAmbientSoundActive = true
        isAmbientRunning = true

        thread {
            try {
                val sampleRate = 22050
                val minBufSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                ambientTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                ambientTrack?.play()

                // Classical museum ethereal chord progression: A minor 9 / D minor 7 / E minor ethereal
                val chordFrequencies = listOf(
                    floatArrayOf(220.0f, 261.63f, 329.63f, 493.88f), // Am9
                    floatArrayOf(174.61f, 220.0f, 261.63f, 349.23f), // Fmaj7
                    floatArrayOf(146.83f, 220.0f, 293.66f, 369.99f), // Dm9
                    floatArrayOf(164.81f, 246.94f, 329.63f, 392.00f)  // Em7
                )

                val buffer = ShortArray(1024)
                var phase = 0.0
                var currentChordIndex = 0
                var samplesInCurrentChord = 0
                val chordLengthSamples = sampleRate * 4 // 4 seconds per gentle chord

                while (isAmbientRunning) {
                    val chord = chordFrequencies[currentChordIndex]
                    for (i in buffer.indices) {
                        var sample = 0.0
                        // Smooth envelope attack and release
                        val envPos = samplesInCurrentChord.toFloat() / chordLengthSamples
                        val envelope = sin(envPos * Math.PI.toFloat()) * 0.15f // very subtle ambient volume

                        for (f in chord) {
                            sample += sin(2.0 * Math.PI * f * (samplesInCurrentChord.toDouble() / sampleRate))
                        }
                        sample = (sample / chord.size) * envelope * Short.MAX_VALUE
                        buffer[i] = sample.toInt().toShort()

                        samplesInCurrentChord++
                        if (samplesInCurrentChord >= chordLengthSamples) {
                            samplesInCurrentChord = 0
                            currentChordIndex = (currentChordIndex + 1) % chordFrequencies.size
                        }
                    }

                    ambientTrack?.write(buffer, 0, buffer.size)
                }

                ambientTrack?.stop()
                ambientTrack?.release()
                ambientTrack = null
            } catch (e: Exception) {
                isAmbientSoundActive = false
                isAmbientRunning = false
            }
        }
    }

    private fun stopAmbientSound() {
        isAmbientRunning = false
        isAmbientSoundActive = false
    }

    fun release() {
        stopAudio()
        stopAmbientSound()
        tts?.shutdown()
        tts = null
    }
}
