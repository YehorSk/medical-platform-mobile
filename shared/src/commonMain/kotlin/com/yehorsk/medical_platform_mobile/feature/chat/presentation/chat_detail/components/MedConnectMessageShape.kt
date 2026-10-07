package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class MessageTail {
    START,
    END
}

class MedConnectMessageShape(
    private val tail: MessageTail,
    private val tailSize: Dp = 16.dp,
    private val cornerRadius: Dp = 8.dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val tailPx = with(density) { tailSize.toPx() }
        val radiusPx = with(density) { cornerRadius.toPx() }

        val shapePath = when (tail) {

            MessageTail.START -> {
                val bubble = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = tailPx,
                            top = 0f,
                            right = size.width,
                            bottom = size.height,
                            cornerRadius = CornerRadius(
                                radiusPx,
                                radiusPx
                            )
                        )
                    )
                }

                val tailPath = Path().apply {
                    moveTo(0f, size.height)
                    lineTo(
                        tailPx,
                        size.height - radiusPx
                    )
                    lineTo(
                        tailPx + radiusPx,
                        size.height
                    )
                    close()
                }

                Path.combine(
                    PathOperation.Union,
                    bubble,
                    tailPath
                )
            }

            MessageTail.END -> {
                val bubble = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = 0f,
                            top = 0f,
                            right = size.width - tailPx,
                            bottom = size.height,
                            cornerRadius = CornerRadius(
                                radiusPx,
                                radiusPx
                            )
                        )
                    )
                }

                val tailPath = Path().apply {
                    moveTo(size.width, size.height)
                    lineTo(
                        size.width - tailPx,
                        size.height - radiusPx
                    )
                    lineTo(
                        size.width - tailPx - radiusPx,
                        size.height
                    )
                    close()
                }

                Path.combine(
                    PathOperation.Union,
                    bubble,
                    tailPath
                )
            }
        }

        return Outline.Generic(shapePath)
    }
}