package org.example.project

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect fun getFont(
    name: String,
    res: String,
    weight: FontWeight,
    style: FontStyle
): Any