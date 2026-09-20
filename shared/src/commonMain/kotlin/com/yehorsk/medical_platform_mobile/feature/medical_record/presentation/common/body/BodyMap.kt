package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyPart
import com.yehorsk.theme.AppTheme

private fun indexToColor(index: Int): Color {
    // index 0 is reserved for "background / no hit"
    val id = index + 1
    return Color(
        red = (id and 0xFF) / 255f,
        green = ((id shr 8) and 0xFF) / 255f,
        blue = ((id shr 16) and 0xFF) / 255f,
        alpha = 1f
    )
}

private fun colorToIndex(color: Color): Int {
    val id = (color.red * 255).toInt() or
            ((color.green * 255).toInt() shl 8) or
            ((color.blue * 255).toInt() shl 16)
    return id - 1 // -1 means "no hit"
}

@Composable
fun BodyMap(
    regions: List<BodyHitRegion>,
    bodyVector: ImageVector,
    modifier: Modifier = Modifier,
    onPartSelected: (BodyHitRegion?) -> Unit,
    selected: List<BodyHitRegion> = emptyList(),
) {
    val viewportWidth = bodyVector.viewportWidth
    val viewportHeight = bodyVector.viewportHeight

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var hitMap: ImageBitmap? by remember { mutableStateOf(null) }

    val bodyPainter = rememberVectorPainter(bodyVector)

    LaunchedEffect(canvasSize, regions) {
        if (canvasSize.width == 0 || canvasSize.height == 0) return@LaunchedEffect
        val scale = minOf(
            canvasSize.width / viewportWidth,
            canvasSize.height / viewportHeight
        )
        val bitmap = ImageBitmap(canvasSize.width, canvasSize.height)
        val canvas = Canvas(bitmap)
        val drawScope = CanvasDrawScope()
        drawScope.draw(
            density = Density(1f),
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = Size(canvasSize.width.toFloat(), canvasSize.height.toFloat())
        ) {
            scale(scale, scale, pivot = Offset.Zero) {
                regions.forEachIndexed { index, region ->
                    drawPath(region.path, color = indexToColor(index))
                }
            }
        }
        hitMap = bitmap
    }

    Canvas(
        modifier = modifier
            .onSizeChanged { canvasSize = it }
            .pointerInput(hitMap, regions) {
                detectTapGestures { offset ->
                    val bmp = hitMap ?: return@detectTapGestures
                    val x = offset.x.toInt().coerceIn(0, bmp.width - 1)
                    val y = offset.y.toInt().coerceIn(0, bmp.height - 1)
                    val pixel = bmp.toPixelMap(x, y, 1, 1)[0, 0]
                    val idx = colorToIndex(pixel)
                    onPartSelected(regions.getOrNull(idx))
                }
            }
    ) {
        val scale = minOf(size.width / viewportWidth, size.height / viewportHeight)
        scale(scale, scale, pivot = Offset.Zero) {
            with(bodyPainter) {
                draw(size = Size(viewportWidth, viewportHeight))
            }
            selected.let { sel ->
                regions.forEach { region ->
                    if (region in selected) {
                        drawPath(path = region.path, color = Color(0x664FC3F7))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BodyMapFrontPreview(){
    AppTheme {
        var selectedRegions by remember { mutableStateOf<List<BodyHitRegion>>(emptyList()) }

        BodyMap(
            regions = frontBodyRegions,
            bodyVector = FrontAnatomy,
            modifier = Modifier.fillMaxWidth().aspectRatio(596f / 1137f),
            selected = selectedRegions,
            onPartSelected = { region ->
                val hit = region ?: return@BodyMap
                selectedRegions = if (hit in selectedRegions) {
                    selectedRegions - hit
                } else {
                    selectedRegions + hit
                }
            }
        )
    }
}

@Preview
@Composable
fun BodyMapBackPreview(){
    AppTheme {
        var selectedRegions by remember { mutableStateOf<List<BodyHitRegion>>(emptyList()) }

        BodyMap(
            regions = backBodyRegions,
            bodyVector = BackAnatomy,
            modifier = Modifier.fillMaxWidth().aspectRatio(596f / 1137f),
            selected = selectedRegions,
            onPartSelected = { region ->
                val hit = region ?: return@BodyMap
                selectedRegions = if (hit in selectedRegions) {
                    selectedRegions - hit
                } else {
                    selectedRegions + hit
                }
            }
        )
    }
}