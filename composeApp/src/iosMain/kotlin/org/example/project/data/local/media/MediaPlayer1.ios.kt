package org.example.project.data.local.media

actual fun audioPlayer(): AundioPlayer = AudioPlayerImpl()

//package org.example.project.data.local.media
//
//import <...>.composeapp.generated.resources.Res
//import io.github.aakira.napier.log
//import kotlinx.cinterop.ExperimentalForeignApi
//import org.jetbrains.compose.resources.ExperimentalResourceApi
//import platform.AVFAudio.AVAudioPlayer
//import platform.Foundation.NSURL
//
//@OptIn(ExperimentalResourceApi::class)
class AudioPlayerImpl : AundioPlayer {
    //    private val mediaItems = soundResList.map { path ->
//        val uri = Res.getUri(path)
//        NSURL.URLWithString(URLString = uri)
//    }
//
//    @OptIn(ExperimentalForeignApi::class)
    override fun playSound(id: Int) {
//        val avAudioPlayer = AVAudioPlayer(mediaItems[id]!!, error = null)
//        avAudioPlayer.prepareToPlay()
//        avAudioPlayer.play()
    }

    //
    override fun release() {}
}