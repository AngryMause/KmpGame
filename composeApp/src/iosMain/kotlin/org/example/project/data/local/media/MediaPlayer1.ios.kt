package org.example.project.data.local.media

import firstkmpproject.composeapp.generated.resources.Res


import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual fun audioPlayer(): AudioPlayer = AudioPlayerImpl()

@OptIn(ExperimentalResourceApi::class)
class AudioPlayerImpl : AudioPlayer {

    private val mediaItems = soundList.map { path ->
        val uri = Res.getUri(path)
        NSURL.URLWithString(URLString = uri)
    }


    @OptIn(ExperimentalForeignApi::class)
    override fun playEndSound(id: Int) {
        val avAudioPlayer = AVAudioPlayer(mediaItems[id]!!, error = null)
        avAudioPlayer.prepareToPlay()
        if (!avAudioPlayer.isPlaying()) {
            avAudioPlayer.play()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun playMainSound(id: Int) {
        val avAudioPlayer = AVAudioPlayer(mediaItems[id]!!, error = null)
        avAudioPlayer.prepareToPlay()
        if (!avAudioPlayer.isPlaying()) {
            avAudioPlayer.play()
        }
    }

    override fun release() {

    }
}