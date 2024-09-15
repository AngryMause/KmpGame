package org.example.project

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking

//private val cache: MutableMap<String, Font> = mutableMapOf()
//actual fun getFont(
//    name: String,
//    res: String,
//    weight: FontWeight,
//    style: FontStyle
//): Font {
//    return cache.getOrPut(res) {
//        val byteArray = runBlocking {
//            resource("font/$res.ttf").readBytes()
//        }
//        Font(res, byteArray, weight, style)
//    }
//}