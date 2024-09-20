package org.example.project.data.local.media

import android.content.Context
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
actual fun audioPlayer(): AundioPlayer = AudioPlayerImpl()
class AudioPlayerImpl() : AundioPlayer {

    private val mediaPlayer = ExoPlayer.Builder(applicationContext).build()

    @OptIn(ExperimentalResourceApi::class)
    val mediaPlauer =
        MediaPlayer.create(applicationContext, R.raw.some_song)
//    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//    private val mediaPlayer=  MediaPlayer(applicationContext)
//    private val mediaItems = soundResList.map {
//    }

    init {

    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @OptIn(ExperimentalResourceApi::class)
    override fun playSound(id: Int) {
        val mediaItem = MediaItem.fromUri(Res.getUri("files/swichback.mp3"))
        Log.e("AudioPlayerImpl", "playSound: $mediaItem")
        Log.e("AudioPlayerImpl", "playSound: ${Res.getUri("files/some_song.mp3")}")
//        mediaPlayer.setDataSource(applicationContext, Uri.parse(Res.getUri("files/some_song.mp3")))
//        mediaPlauer.start()
        mediaPlayer.setMediaItem(
            mediaItem
        )
//        mediaPlayer.isLooping=true
//        mediaPlayer.start()
//        mediaPlayer.prepare()
        mediaPlayer.volume = 1f
        mediaPlayer.play()
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun release() {
        mediaPlayer.release()
    }
}