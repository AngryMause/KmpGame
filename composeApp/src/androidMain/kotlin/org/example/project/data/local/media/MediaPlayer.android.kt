package org.example.project.data.local.media

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.example.project.R
import org.example.project.applicationContext
import org.jetbrains.compose.resources.ExperimentalResourceApi


@ExperimentalResourceApi
actual fun audioPlayer(): AudioPlayer = AudioPlayerImpl()

class AudioPlayerImpl() : AudioPlayer {

    private val mediaPlayer1: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.game_music)
    private val mediaPlayer2: MediaPlayer =
        MediaPlayer.create(applicationContext, R.raw.end_game_music)

    override fun playEndSound(id: Int) {
        mediaPlayer2.start()
    }

    override fun pauseMainSound() {
        mediaPlayer1.pause()
    }

    init {
        mediaPlayer1.isLooping = true
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun playMainSound(id: Int) {
        if (!mediaPlayer1.isPlaying) {
            mediaPlayer1.start()
        }
    }

}
