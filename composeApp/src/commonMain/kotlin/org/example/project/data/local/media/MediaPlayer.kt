package org.example.project.data.local.media


interface AudioPlayer {
    fun playMainSound(id: Int)
    fun playEndSound(id: Int)
    fun pauseMainSound()
}


expect fun audioPlayer(): AudioPlayer




object AudioPlayerComponent {
    private var _audioPlayer: AudioPlayer? = null
    val audioPlayer
        get() = _audioPlayer
            ?: throw IllegalStateException("Make sure to call AudioPlayerComponent.init()")

    fun init() {
        _audioPlayer = audioPlayer()
    }
}

val soundList = listOf(
    "files/end_game_music.mp3", "files/game_music.mp3"
)





