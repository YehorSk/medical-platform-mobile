package com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body

import androidx.compose.ui.graphics.Path
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyPart
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion

private fun createRightHandPath(): Path{
    return Path().apply {
        moveTo(69f, 612f)
        cubicTo(73.3f, 601.1f, 78.5f, 584.5f, 84f, 568f)
        cubicTo(85.3f, 558.8f, 87.2f, 552f, 87.5f, 546f)
        cubicTo(86.5f, 537f, 86.5f, 538.5f, 86.5f, 533.5f)
        cubicTo(79f, 530.3f, 58.5f, 521.5f, 48f, 516.5f)
        cubicTo(39f, 520f, 36.5f, 519.5f, 25.5f, 526.5f)
        cubicTo(20.5f, 531.5f, 8.6f, 537f, 7.5f, 541.5f)
        cubicTo(6.1f, 543.5f, 4.6f, 544.7f, 3.5f, 545.5f)
        cubicTo(-1.2f, 551.3f, 1f, 552.5f, 3f, 554.5f)
        cubicTo(5f, 555.7f, 8.1f, 555.9f, 13.5f, 554f)
        cubicTo(17.2f, 552.7f, 17f, 549.5f, 21f, 547.5f)
        cubicTo(25f, 545.5f, 27.7f, 544.2f, 28.5f, 545f)
        cubicTo(27.5f, 547f, 23.7f, 553.9f, 20f, 568f)
        cubicTo(16.3f, 582.1f, 13f, 595f, 11f, 606f)
        cubicTo(9.4f, 616.9f, 10.4f, 621.3f, 12f, 622.5f)
        cubicTo(13.6f, 623.7f, 15.8f, 624.3f, 18f, 622.5f)
        cubicTo(20.2f, 620.7f, 20.7f, 621f, 23f, 613.5f)
        cubicTo(24f, 605.5f, 28f, 592.6f, 30.5f, 585f)
        cubicTo(33f, 577.5f, 34f, 574.5f, 35f, 575.5f)
        cubicTo(35.5f, 575.5f, 30.6f, 588.3f, 28.5f, 600f)
        cubicTo(26.5f, 612f, 22.9f, 624.7f, 24f, 631.5f)
        cubicTo(24.4f, 634f, 24.5f, 636.6f, 28f, 637f)
        cubicTo(32.5f, 637.5f, 34.8f, 633.5f, 36f, 628f)
        cubicTo(38.5f, 616.5f, 46.5f, 581.5f, 49f, 582f)
        cubicTo(49.5f, 582f, 44.5f, 599f, 42.5f, 613f)
        cubicTo(41.4f, 618.1f, 39.8f, 622.6f, 39.5f, 625.5f)
        cubicTo(38.6f, 635f, 41.5f, 635f, 42.5f, 635.5f)
        cubicTo(44.5f, 636f, 47.9f, 636.5f, 51f, 628.5f)
        cubicTo(54.5f, 615.5f, 57.5f, 606.5f, 59.5f, 596f)
        cubicTo(62.5f, 585.5f, 64.5f, 581.5f, 65f, 583.5f)
        cubicTo(65f, 586.4f, 60.3f, 602.9f, 58.9f, 610.4f)
        cubicTo(57.5f, 617.9f, 58f, 615f, 58f, 618.5f)
        cubicTo(58.7f, 620.9f, 58.5f, 621.5f, 61.5f, 622f)
        cubicTo(63.5f, 622.3f, 66f, 622.5f, 69f, 612f)
        close()
    }
}

private fun createLeftHandPath(): Path{
    return Path().apply {
        moveTo(526.5f, 612.5f)
        cubicTo(522.2f, 601.6f, 517f, 585f, 511.5f, 568.5f)
        cubicTo(510.2f, 559.3f, 508.3f, 552.5f, 508f, 546.5f)
        cubicTo(509f, 537.5f, 509f, 536.5f, 509f, 531.5f)
        cubicTo(516.5f, 528.3f, 535f, 521f, 545.5f, 516f)
        cubicTo(554.5f, 519.5f, 559f, 520f, 570f, 527f)
        cubicTo(575f, 532f, 586.9f, 537.5f, 588f, 542f)
        cubicTo(589.4f, 544f, 590.9f, 545.2f, 592f, 546f)
        cubicTo(596.7f, 551.8f, 594.5f, 553f, 592.5f, 555f)
        cubicTo(590.5f, 556.2f, 587.4f, 556.4f, 582f, 554.5f)
        cubicTo(578.3f, 553.2f, 578.5f, 550f, 574.5f, 548f)
        cubicTo(570.5f, 546f, 567.8f, 544.7f, 567f, 545.5f)
        cubicTo(568f, 547.5f, 571.8f, 554.4f, 575.5f, 568.5f)
        cubicTo(579.2f, 582.6f, 582.5f, 595.5f, 584.5f, 606.5f)
        cubicTo(586.1f, 617.4f, 585.1f, 621.8f, 583.5f, 623f)
        cubicTo(581.9f, 624.2f, 579.7f, 624.8f, 577.5f, 623f)
        cubicTo(575.3f, 621.2f, 574.8f, 621.5f, 572.5f, 614f)
        cubicTo(571.5f, 606f, 567.5f, 593.1f, 565f, 585.5f)
        cubicTo(562.5f, 578f, 561.5f, 575f, 560.5f, 576f)
        cubicTo(560f, 576f, 564.9f, 588.8f, 567f, 600.5f)
        cubicTo(569f, 612.5f, 572.6f, 625.2f, 571.5f, 632f)
        cubicTo(571.1f, 634.5f, 571f, 637.1f, 567.5f, 637.5f)
        cubicTo(563f, 638f, 560.7f, 634f, 559.5f, 628.5f)
        cubicTo(557f, 617f, 549f, 582f, 546.5f, 582.5f)
        cubicTo(546f, 582.5f, 551f, 599.5f, 553f, 613.5f)
        cubicTo(554.1f, 618.6f, 555.7f, 623.1f, 556f, 626f)
        cubicTo(556.9f, 635.5f, 554f, 635.5f, 553f, 636f)
        cubicTo(551f, 636.5f, 547.6f, 637f, 544.5f, 629f)
        cubicTo(541f, 616f, 538f, 607f, 536f, 596.5f)
        cubicTo(533f, 586f, 531f, 582f, 530.5f, 584f)
        cubicTo(530.5f, 586.9f, 535.2f, 603.4f, 536.6f, 610.9f)
        cubicTo(538f, 618.4f, 537.5f, 615.5f, 537.5f, 619f)
        cubicTo(536.8f, 621.4f, 537f, 622f, 534f, 622.5f)
        cubicTo(532f, 622.8f, 529.5f, 623f, 526.5f, 612.5f)
        close()
    }
}

private fun createLeftWristPath(): Path{
    return Path().apply {
        moveTo(504f, 518.5f)
        lineTo(540f, 502f)
        cubicTo(542.7f, 507.1f, 544f, 515f, 547f, 515f)
        cubicTo(550f, 515f, 509f, 532f, 509f, 532f)
        lineTo(504f, 518.5f)
        close()
    }
}

private fun createRightWristPath(): Path{
    return Path().apply {
        moveTo(91f, 519.5f)
        lineTo(55f, 503f)
        cubicTo(52.3f, 508.1f, 51f, 516f, 48f, 516f)
        cubicTo(45f, 516f, 86f, 533f, 86f, 533f)
        lineTo(91f, 519.5f)
        close()
    }
}

private fun createLeftForearmPath(): Path{
    return Path().apply {
        moveTo(504f, 518f)
        cubicTo(514f, 515f, 533.2f, 505.2f, 540.5f, 502f)
        cubicTo(537.5f, 493f, 534.5f, 488.3f, 528.5f, 466.5f)
        cubicTo(522.5f, 444.7f, 519.7f, 423f, 514.5f, 404.5f)
        cubicTo(512f, 395.5f, 512.5f, 397f, 508f, 385f)
        cubicTo(495.5f, 388f, 458.5f, 403.5f, 442f, 407.5f)
        cubicTo(446f, 419f, 443.9f, 412.8f, 448f, 424f)
        cubicTo(452.1f, 435.2f, 451f, 431.5f, 459f, 447.5f)
        cubicTo(468.5f, 462.9f, 485.7f, 484.6f, 495.5f, 501f)
        cubicTo(497.9f, 505f, 499f, 507.5f, 504f, 518f)
        close()
    }
}

private fun createRightForearmPath(): Path{
    return Path().apply {
        moveTo(91.5f, 519f)
        cubicTo(81.5f, 516f, 62.3f, 506.2f, 55f, 503f)
        cubicTo(58f, 494f, 61f, 489.3f, 67f, 467.5f)
        cubicTo(73f, 445.7f, 75.8f, 424f, 81f, 405.5f)
        cubicTo(83.5f, 396.5f, 83f, 398f, 87.5f, 386f)
        cubicTo(100f, 389f, 137f, 404.5f, 153.5f, 408.5f)
        cubicTo(149.5f, 420f, 151.6f, 413.8f, 147.5f, 425f)
        cubicTo(143.4f, 436.2f, 144.5f, 432.5f, 136.5f, 448.5f)
        cubicTo(127f, 463.9f, 109.8f, 485.6f, 100f, 502f)
        cubicTo(97.6f, 506f, 96.5f, 508.5f, 91.5f, 519f)
        close()
    }
}

private fun createLeftElbowPath(): Path{
    return Path().apply {
        moveTo(507.5f, 385.5f)
        cubicTo(503.3f, 374.8f, 500.4f, 369.3f, 494.5f, 360f)
        cubicTo(473.4f, 371.7f, 461.5f, 378.2f, 438f, 384f)
        cubicTo(439f, 394.2f, 440.1f, 399.6f, 443f, 408.5f)
        lineTo(507.5f, 385.5f)
        close()
    }
}

private fun createRightElbowPath(): Path{
    return Path().apply {
        moveTo(88f, 385.5f)
        cubicTo(92.2f, 374.8f, 95.1f, 369.3f, 101f, 360f)
        cubicTo(122.1f, 371.7f, 134f, 378.2f, 157.5f, 384f)
        cubicTo(156.5f, 394.2f, 155.4f, 399.6f, 152.5f, 408.5f)
        lineTo(88f, 385.5f)
        close()
    }
}

private fun createLeftArmPath(): Path{
    return Path().apply {
        moveTo(438.5f, 384f)
        cubicTo(451.5f, 382.5f, 484f, 368.5f, 495.5f, 359.5f)
        cubicTo(492.1f, 352.8f, 489.5f, 350f, 487.5f, 339.5f)
        cubicTo(485.8f, 330.5f, 486f, 333.5f, 482.5f, 322f)
        cubicTo(478.6f, 308f, 465.9f, 284f, 461f, 275.5f)
        cubicTo(452f, 260f, 447f, 254f, 438.5f, 247f)
        cubicTo(436.1f, 261.5f, 423.5f, 302.6f, 411.5f, 318.9f)
        cubicTo(412.9f, 324.2f, 414.6f, 329.4f, 416.5f, 334f)
        cubicTo(423.5f, 352.5f, 430f, 360.5f, 436f, 372f)
        cubicTo(439f, 384f, 437f, 377.5f, 438.5f, 384f)
        close()
    }
}

private fun createRightArmPath(): Path{
    return Path().apply {
        moveTo(158f, 384f)
        cubicTo(145f, 382.5f, 112.5f, 368.5f, 101f, 359.5f)
        cubicTo(104.4f, 352.8f, 107f, 350f, 109f, 339.5f)
        cubicTo(110.7f, 330.5f, 110.5f, 333.5f, 114f, 322f)
        cubicTo(117.9f, 308f, 130.6f, 284f, 135.5f, 275.5f)
        cubicTo(144.5f, 260f, 149.5f, 254f, 158f, 247f)
        cubicTo(160.4f, 261.5f, 173f, 302.6f, 185f, 318.9f)
        cubicTo(183.6f, 324.2f, 181.9f, 329.4f, 180f, 334f)
        cubicTo(173f, 352.5f, 166.5f, 360.5f, 160.5f, 372f)
        cubicTo(157.5f, 384f, 159.5f, 377.5f, 158f, 384f)
        close()
    }
}

private fun createLeftShoulderPath(): Path{
    return Path().apply {
        moveTo(439f, 247.5f)
        cubicTo(448.5f, 254.5f, 455f, 266f, 462.5f, 277.5f)
        cubicTo(462.5f, 265f, 462f, 240.6f, 458f, 226.1f)
        cubicTo(452.3f, 212f, 446.9f, 206.1f, 437f, 195.6f)
        cubicTo(430.8f, 189f, 386.5f, 177.5f, 375f, 175f)
        cubicTo(391.5f, 210f, 415.5f, 215f, 439f, 247.5f)
        close()
    }
}

private fun createRightShoulderPath(): Path{
    return Path().apply {
        moveTo(157.5f, 247.5f)
        cubicTo(148f, 254.5f, 141.5f, 266f, 134f, 277.5f)
        cubicTo(134f, 265f, 134.5f, 240.6f, 138.5f, 226.1f)
        cubicTo(144.2f, 212f, 149.6f, 206.1f, 159.5f, 195.6f)
        cubicTo(165.7f, 189f, 210f, 177.5f, 221.5f, 175f)
        cubicTo(205f, 210f, 181f, 215f, 157.5f, 247.5f)
        close()
    }
}

private fun createNeckPath(): Path{
    return Path().apply {
        moveTo(258.5f, 122.5f)
        cubicTo(257f, 125.5f, 259.5f, 142f, 258f, 153.5f)
        cubicTo(249.2f, 162.6f, 235.8f, 169.1f, 222f, 174.5f)
        cubicTo(279.6f, 171.2f, 313.2f, 171.3f, 375f, 174.5f)
        cubicTo(360.6f, 171.5f, 336.1f, 154.4f, 338.5f, 153.5f)
        cubicTo(338.5f, 153.5f, 336.5f, 128f, 337.5f, 123f)
        cubicTo(306.8f, 131.3f, 286f, 132.5f, 258.5f, 123f)
    }
}

private fun createHeadPath(): Path{
    return Path().apply {
        moveTo(248f, 105f)
        cubicTo(250.6f, 107.3f, 252.8f, 103f, 254f, 102.5f)
        cubicTo(254.4f, 110.6f, 255.3f, 113f, 258f, 122f)
        cubicTo(288.7f, 132.9f, 306.3f, 131.9f, 338.5f, 122f)
        cubicTo(338.5f, 122f, 341.5f, 107.5f, 341.5f, 102.5f)
        cubicTo(341.5f, 100.5f, 342f, 106f, 346f, 105f)
        cubicTo(349f, 104f, 350.5f, 99f, 354f, 93f)
        cubicTo(356.2f, 87.3f, 356.9f, 81.8f, 357f, 77f)
        cubicTo(357.1f, 74.3f, 356.6f, 71.4f, 355f, 70f)
        cubicTo(353.7f, 68.9f, 352f, 68f, 350.5f, 68.5f)
        cubicTo(349f, 68.5f, 348f, 70.5f, 347f, 71.5f)
        cubicTo(346.6f, 71.1f, 347.8f, 67.5f, 347.5f, 66.5f)
        cubicTo(348f, 61.5f, 348.2f, 54.2f, 348f, 46.5f)
        cubicTo(347.8f, 38.8f, 347.5f, 39.5f, 345.5f, 32.5f)
        cubicTo(342f, 25.8f, 337.9f, 18.5f, 331f, 12.5f)
        cubicTo(324.1f, 6.5f, 318.1f, 4.9f, 311.5f, 2.5f)
        cubicTo(304.9f, 0.1f, 303.9f, 0.5f, 298f, 0.5f)
        cubicTo(292.1f, 0.5f, 289.2f, -0.6f, 282f, 2.5f)
        cubicTo(274.8f, 5.6f, 268.8f, 9f, 262f, 16f)
        cubicTo(255.2f, 23f, 250.8f, 26.5f, 248f, 37.5f)
        cubicTo(245.2f, 48.5f, 248f, 60.5f, 248f, 66.5f)
        cubicTo(248f, 69f, 248.8f, 71.1f, 248.5f, 71.5f)
        cubicTo(247.6f, 72.7f, 245.9f, 68.3f, 244.5f, 68.5f)
        cubicTo(243.3f, 68.7f, 242.4f, 69f, 241f, 70f)
        cubicTo(240.1f, 70.6f, 239f, 71.8f, 239f, 73.5f)
        cubicTo(239f, 77.9f, 239.1f, 84.5f, 241f, 91f)
        cubicTo(242.9f, 97.5f, 245.4f, 102.7f, 248f, 105f)
        close()
    }
}

private fun createBackPath(): Path{
    return Path().apply {
        moveTo(374.5f, 174.5f)
        cubicTo(315.9f, 171.5f, 282.6f, 171.3f, 222f, 174.5f)
        cubicTo(209.9f, 195.9f, 200.4f, 206.8f, 178.2f, 224.5f)
        cubicTo(169.7f, 233.1f, 165.2f, 237.9f, 158.5f, 247f)
        cubicTo(164.3f, 275.1f, 170.5f, 291f, 185.9f, 319.5f)
        cubicTo(193.4f, 342.2f, 197f, 355.2f, 207.5f, 376.5f)
        cubicTo(278.4f, 379.9f, 318.5f, 380.1f, 389.5f, 376.5f)
        cubicTo(399.1f, 355.2f, 402f, 343f, 412.2f, 319.5f)
        cubicTo(425f, 291.7f, 430.7f, 275.9f, 438.5f, 247f)
        cubicTo(431.5f, 237.4f, 427.1f, 232.6f, 418.6f, 224.5f)
        cubicTo(395.7f, 206.2f, 386.6f, 195.7f, 374.5f, 174.5f)
        close()
    }
}

private fun createLoinPath(): Path{
    return Path().apply {
        moveTo(206.5f, 376.5f)
        cubicTo(278.3f, 380f, 318.5f, 380f, 390f, 376.5f)
        lineTo(386f, 414f)
        cubicTo(391.3f, 434.9f, 392.3f, 446.5f, 390f, 467f)
        cubicTo(373.6f, 463.5f, 363.9f, 462.9f, 345.5f, 464f)
        cubicTo(326.7f, 464.8f, 316.4f, 466f, 298.5f, 469.5f)
        cubicTo(282f, 466.4f, 272.2f, 465.2f, 254f, 464f)
        cubicTo(235.8f, 463.2f, 225.4f, 463.7f, 206.5f, 467f)
        cubicTo(205.1f, 447.8f, 206f, 435f, 211f, 414f)
        lineTo(207.5f, 377.5f)
    }
}

private fun createButtocksPath(): Path{
    return Path().apply {
        moveTo(304f, 603.5f)
        cubicTo(300.5f, 595.4f, 299.3f, 591f, 298.5f, 583f)
        lineTo(293f, 603.5f)
        cubicTo(283.6f, 616.2f, 274f, 620f, 250.5f, 622f)
        cubicTo(227.6f, 624.6f, 216.2f, 619.9f, 204f, 600.5f)
        lineTo(188.5f, 576f)
        lineTo(194f, 533.5f)
        cubicTo(197.6f, 506.4f, 200.1f, 491.6f, 207.5f, 467f)
        cubicTo(223.4f, 464.5f, 232.8f, 463.9f, 250.5f, 464.5f)
        cubicTo(268.8f, 464.7f, 279.3f, 465.8f, 298.5f, 470f)
        cubicTo(314.5f, 466.6f, 324.3f, 465.1f, 344.5f, 464.5f)
        cubicTo(362.7f, 462.5f, 372.5f, 463.3f, 389.5f, 467f)
        cubicTo(396.1f, 492.2f, 398.9f, 506.7f, 402.5f, 533.5f)
        lineTo(408.5f, 576f)
        lineTo(391.5f, 601f)
        cubicTo(382.7f, 618.3f, 372.4f, 622.9f, 344.5f, 622f)
        cubicTo(324.4f, 620.3f, 315.4f, 616.3f, 304f, 603.5f)
        close()
    }
}

private fun createRightHamstringPath(): Path{
    return Path().apply {
        moveTo(204f, 600.5f)
        lineTo(188.5f, 576f)
        cubicTo(184.1f, 626.8f, 184.8f, 658.5f, 191f, 710.5f)
        cubicTo(197.9f, 731.5f, 204.7f, 760.1f, 203f, 760.5f)
        cubicTo(203f, 760.5f, 244.4f, 770.3f, 270.5f, 767f)
        lineTo(280f, 707.5f)
        cubicTo(292.4f, 660.3f, 296.1f, 633f, 298.5f, 583f)
        lineTo(293f, 603.5f)
        cubicTo(283.6f, 616.2f, 274f, 620f, 250.5f, 622f)
        cubicTo(227.6f, 624.6f, 216.2f, 619.9f, 204f, 600.5f)
        close()
    }
}

private fun createLeftHamstringPath(): Path{
    return Path().apply {
        moveTo(393f, 600.5f)
        lineTo(408.5f, 576f)
        cubicTo(412.9f, 626.8f, 412.2f, 658.5f, 406f, 710.5f)
        cubicTo(399.1f, 731.5f, 392.3f, 760.1f, 394f, 760.5f)
        cubicTo(394f, 760.5f, 352.6f, 770.3f, 326.5f, 767f)
        lineTo(317f, 707.5f)
        cubicTo(304.6f, 660.3f, 300.9f, 633f, 298.5f, 583f)
        lineTo(304f, 603.5f)
        cubicTo(313.4f, 616.2f, 323f, 620f, 346.5f, 622f)
        cubicTo(369.4f, 624.6f, 380.8f, 619.9f, 393f, 600.5f)
        close()
    }
}

private fun createRightKneePath(): Path{
    return Path().apply {
        moveTo(203f, 760.5f)
        cubicTo(203f, 760.5f, 244.4f, 770.3f, 270.5f, 767f)
        cubicTo(270.2f, 786.8f, 270.5f, 797.5f, 268.5f, 805.5f)
        cubicTo(263.4f, 819.1f, 259.8f, 826.4f, 258f, 842.5f)
        cubicTo(233.1f, 838.8f, 219.8f, 839f, 196.5f, 842.5f)
        cubicTo(198.4f, 832.7f, 199.2f, 827.5f, 200.5f, 818.5f)
        cubicTo(198.3f, 790.4f, 198.5f, 777.2f, 203f, 760.5f)
        close()
    }
}

private fun createLeftKneePath(): Path{
    return Path().apply {
        moveTo(393.5f, 760f)
        cubicTo(393.5f, 760f, 352.1f, 769.8f, 326f, 766.5f)
        cubicTo(326.3f, 786.3f, 326f, 797f, 328f, 805f)
        cubicTo(333.1f, 818.6f, 336.7f, 825.9f, 338.5f, 842f)
        cubicTo(363.4f, 838.3f, 376.7f, 838.5f, 400f, 842f)
        cubicTo(398.1f, 832.2f, 397.3f, 827f, 396f, 818f)
        cubicTo(398.2f, 789.9f, 398f, 776.7f, 393.5f, 760f)
        close()
    }
}

private fun createRightCalfPath(): Path{
    return Path().apply {
        moveTo(258f, 842.5f)
        cubicTo(234.2f, 839.1f, 220.8f, 839f, 197f, 842.5f)
        cubicTo(193.6f, 858.9f, 192f, 867.5f, 192f, 887f)
        cubicTo(192f, 906.5f, 196.8f, 929.3f, 201f, 957f)
        cubicTo(207.2f, 991.4f, 211.3f, 1010.9f, 214.5f, 1048.5f)
        cubicTo(226.9f, 1051.5f, 233.5f, 1051.4f, 247.5f, 1048.5f)
        cubicTo(247f, 1011.8f, 249.5f, 991.8f, 258f, 957f)
        cubicTo(267.6f, 926f, 268.5f, 908.9f, 264f, 878.5f)
        cubicTo(260.7f, 864.2f, 259.4f, 856.4f, 258f, 842.5f)
        close()
    }
}

private fun createLeftCalfPath(): Path{
    return Path().apply {
        moveTo(338.5f, 842f)
        cubicTo(362.3f, 838.6f, 375.7f, 838.5f, 399.5f, 842f)
        cubicTo(402.9f, 858.4f, 404.5f, 867f, 404.5f, 886.5f)
        cubicTo(404.5f, 906f, 399.7f, 928.8f, 395.5f, 956.5f)
        cubicTo(389.3f, 990.9f, 385.2f, 1010.4f, 382f, 1048f)
        cubicTo(369.6f, 1051f, 363f, 1050.9f, 349f, 1048f)
        cubicTo(349.5f, 1011.3f, 347f, 991.3f, 338.5f, 956.5f)
        cubicTo(328.9f, 925.5f, 328f, 908.4f, 332.5f, 878f)
        cubicTo(335.8f, 863.7f, 337.1f, 855.9f, 338.5f, 842f)
        close()
    }
}

private fun createRightAnklePath(): Path{
    return Path().apply {
        moveTo(247.5f, 1049f)
        cubicTo(234.9f, 1051.8f, 226.5f, 1050.8f, 215f, 1049f)
        cubicTo(215.2f, 1060.2f, 215f, 1066.4f, 213f, 1077f)
        cubicTo(211.1f, 1080.2f, 210.6f, 1082f, 210.5f, 1085f)
        cubicTo(211.4f, 1088.5f, 211.5f, 1090.5f, 210.5f, 1094f)
        cubicTo(213.3f, 1106.5f, 213.3f, 1112.6f, 210.5f, 1125.5f)
        cubicTo(226.4f, 1110.6f, 235.9f, 1110.3f, 253.5f, 1119f)
        cubicTo(255.5f, 1113.9f, 253.9f, 1110.3f, 249.5f, 1103.5f)
        cubicTo(250.6f, 1091.4f, 250.2f, 1084.7f, 247.5f, 1073f)
        cubicTo(247.7f, 1064.2f, 247.6f, 1058.8f, 247.5f, 1049.5f)
        lineTo(247.5f, 1049f)
        close()
    }
}

private fun createLeftAnklePath(): Path{
    return Path().apply {
        moveTo(349f, 1048.5f)
        cubicTo(361.6f, 1051.3f, 370f, 1050.3f, 381.5f, 1048.5f)
        cubicTo(381.3f, 1059.7f, 381.5f, 1065.9f, 383.5f, 1076.5f)
        cubicTo(385.4f, 1079.7f, 385.9f, 1081.5f, 386f, 1084.5f)
        cubicTo(385.1f, 1088f, 385f, 1090f, 386f, 1093.5f)
        cubicTo(383.2f, 1106f, 383.2f, 1112.1f, 386f, 1125f)
        cubicTo(370.1f, 1110.1f, 360.6f, 1109.8f, 343f, 1118.5f)
        cubicTo(341f, 1113.4f, 342.6f, 1109.8f, 347f, 1103f)
        cubicTo(345.9f, 1090.9f, 346.3f, 1084.2f, 349f, 1072.5f)
        cubicTo(348.8f, 1063.7f, 348.9f, 1058.3f, 349f, 1049f)
        lineTo(349f, 1048.5f)
        close()
    }
}

private fun createRightSolePath(): Path{
    return Path().apply {
        moveTo(253f, 1119f)
        cubicTo(235.1f, 1110.1f, 225.8f, 1111.4f, 210.5f, 1126f)
        cubicTo(210.6f, 1129.4f, 211.3f, 1131.1f, 216f, 1132.5f)
        cubicTo(227.9f, 1134.1f, 234.1f, 1134.2f, 244.5f, 1132f)
        cubicTo(251.6f, 1128.3f, 253.1f, 1125.3f, 253f, 1119f)
        close()
    }
}

private fun createLeftSolePath(): Path{
    return Path().apply {
        moveTo(343.5f, 1118.5f)
        cubicTo(361.4f, 1109.6f, 370.7f, 1110.9f, 386f, 1125.5f)
        cubicTo(385.9f, 1128.9f, 385.2f, 1130.6f, 380.5f, 1132f)
        cubicTo(368.6f, 1133.6f, 362.4f, 1133.7f, 352f, 1131.5f)
        cubicTo(344.9f, 1127.8f, 343.4f, 1124.8f, 343.5f, 1118.5f)
        close()
    }
}

private fun createRightFootPath(): Path{
    return Path().apply {
        moveTo(210.5f, 1095f)
        moveTo(210.5f, 1095.5f)
        cubicTo(212.9f, 1107.9f, 212.7f, 1114.5f, 210.5f, 1126.5f)
        cubicTo(202.3f, 1123.5f, 197.5f, 1121.6f, 188.5f, 1118f)
        cubicTo(185.8f, 1115.2f, 185.9f, 1113.6f, 188.5f, 1111f)
        cubicTo(190.7f, 1108.9f, 192.1f, 1108.2f, 194.5f, 1107.5f)
        cubicTo(201.3f, 1103.5f, 204.7f, 1100.8f, 210.5f, 1095.5f)
        close()
    }
}

private fun createLeftFootPath(): Path{
    return Path().apply {
        moveTo(386f, 1094.5f)
        moveTo(386f, 1095f)
        cubicTo(383.6f, 1107.4f, 383.8f, 1114f, 386f, 1126f)
        cubicTo(394.2f, 1123f, 399f, 1121.1f, 408f, 1117.5f)
        cubicTo(410.7f, 1114.7f, 410.6f, 1113.1f, 408f, 1110.5f)
        cubicTo(405.8f, 1108.4f, 404.4f, 1107.7f, 402f, 1107f)
        cubicTo(395.2f, 1103f, 391.8f, 1100.3f, 386f, 1095f)
        close()
    }
}

val backBodyRegions = listOf(
    BodyHitRegion(
        name = "left_hand",
        part = BodyPart.LEFT_HAND,
        path = createLeftHandPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_hand",
        part = BodyPart.RIGHT_HAND,
        path = createRightHandPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_forearm",
        part = BodyPart.LEFT_FOREARM,
        path = createLeftForearmPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_forearm",
        part = BodyPart.RIGHT_FOREARM,
        path = createRightForearmPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_arm",
        part = BodyPart.LEFT_ARM,
        path = createLeftArmPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_arm",
        part = BodyPart.RIGHT_ARM,
        path = createRightArmPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_shoulder",
        part = BodyPart.RIGHT_SHOULDER,
        path = createRightShoulderPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_shoulder",
        part = BodyPart.LEFT_SHOULDER,
        path = createLeftShoulderPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "neck",
        part = BodyPart.NECK,
        path = createNeckPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "back",
        part = BodyPart.BACK,
        path = createBackPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "loin",
        part = BodyPart.LOIN,
        path = createLoinPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "buttocks",
        part = BodyPart.BUTTOCKS,
        path = createButtocksPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "head",
        part = BodyPart.HEAD,
        path = createHeadPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_wrist",
        part = BodyPart.LEFT_WRIST,
        path = createLeftWristPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_wrist",
        part = BodyPart.RIGHT_WRIST,
        path = createRightWristPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_calf",
        part = BodyPart.LEFT_CALF,
        path = createLeftCalfPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_calf",
        part = BodyPart.RIGHT_CALF,
        path = createRightCalfPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_ankle",
        part = BodyPart.LEFT_ANKLE,
        path = createLeftAnklePath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_ankle",
        part = BodyPart.RIGHT_ANKLE,
        path = createRightAnklePath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_foot",
        part = BodyPart.LEFT_FOOT,
        path = createLeftFootPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_foot",
        part = BodyPart.RIGHT_FOOT,
        path = createRightFootPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_hamstring",
        part = BodyPart.LEFT_HAMSTRING,
        path = createLeftHamstringPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_hamstring",
        part = BodyPart.RIGHT_HAMSTRING,
        path = createRightHamstringPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_knee",
        part = BodyPart.LEFT_KNEE,
        path = createLeftKneePath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_knee",
        part = BodyPart.RIGHT_KNEE,
        path = createRightKneePath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_elbow",
        part = BodyPart.LEFT_ELBOW,
        path = createLeftElbowPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_elbow",
        part = BodyPart.RIGHT_ELBOW,
        path = createRightElbowPath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "left_sole",
        part = BodyPart.LEFT_SOLE,
        path = createLeftSolePath(),
        region = BodyRegion.BACK
    ),
    BodyHitRegion(
        name = "right_sole",
        part = BodyPart.RIGHT_SOLE,
        path = createRightSolePath(),
        region = BodyRegion.BACK
    ),
)