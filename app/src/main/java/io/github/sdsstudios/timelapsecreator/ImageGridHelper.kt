package io.github.sdsstudios.timelapsecreator

import io.github.sdsstudios.timelapsecreator.DisplayUtils.pxToDp

/**
 * Created by Seth on 21/02/18.
 */

object ImageGridHelper {

    //TODO ADD REMOVE BUTTON
    private const val MIN_IMAGE_WIDTH_DP = 75

    fun calculateColumnSpan(parentWidthPx: Int): Int {
        val span = pxToDp(parentWidthPx) / MIN_IMAGE_WIDTH_DP

        if (span == 0) return 1

        return span
    }

    fun calculateImageWidthPx(columnSpan: Int, parentWidthPx: Int): Int {
        val widthDp = pxToDp(parentWidthPx) / columnSpan

        if (widthDp < MIN_IMAGE_WIDTH_DP) return parentWidthPx

        return DisplayUtils.dpToPx(widthDp)
    }
}