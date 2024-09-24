package org.example.project.data.local.media

import firstkmpproject.composeapp.generated.resources.Res


import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual fun audioPlayer(): AundioPlayer = AudioPlayerImpl()

@OptIn(ExperimentalResourceApi::class)
class AudioPlayerImpl : AundioPlayer {

    @OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
    override fun playSound(id: Int) {
        val uri = Res.getUri("files/some_song.mp3")
        val mediaItems = NSURL.URLWithString(URLString = uri)
        val avAudioPlayer = AVAudioPlayer(mediaItems!!, error = null)
        avAudioPlayer.prepareToPlay()
        avAudioPlayer.play()
    }

    override fun release() {

    }
}