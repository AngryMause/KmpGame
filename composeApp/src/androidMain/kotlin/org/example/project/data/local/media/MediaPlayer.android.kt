package org.example.project.data.local.media

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import org.example.project.applicationContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
actual fun audioPlayer(): AundioPlayer = AudioPlayerImpl()
class AudioPlayerImpl() : AundioPlayer {

    private val mediaPlayer = ExoPlayer.Builder(applicationContext).build()
//    private val mediaItems = soundResList.map {
//    }

    init {
        mediaPlayer.prepare()
    }

    @OptIn(ExperimentalResourceApi::class)
    override fun playSound(id: Int) {
        mediaPlayer.setMediaItem(
            MediaItem.fromUri(Res.getUri("files/b4.mp3"))
        )
        mediaPlayer.play()
    }


    override fun release() {
        mediaPlayer.release()
    }
}