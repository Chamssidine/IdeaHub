package com.solodev.ideahub.ui.screen.components

import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CircleShape: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                addRoundRect(
                    roundRect = RoundRect(
                        rect = size.toRect(),
                        radiusX = size.height / 2f,
                        radiusY = size.height / 2f
                    )
                )
            }
        )
    }
}