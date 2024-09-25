package org.example.project.data.local.media

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import firstkmpproject.composeapp.generated.resources.Res
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun playMainSound(id: Int) {
        if (mediaPlayer1 != null) {
            mediaPlayer1.isLooping = true
            mediaPlayer1.start()
        } else {
            throw IllegalStateException("MediaPlayer is null")
        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun release() {
    }
}