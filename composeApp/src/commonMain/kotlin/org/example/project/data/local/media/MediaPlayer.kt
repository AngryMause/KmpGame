package org.example.project.data.local.media
 interface AundioPlayer {

     fun playSound(id: Int)
     fun release()
 }

expect fun audioPlayer():AundioPlayer

