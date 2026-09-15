package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body

import androidx.compose.ui.graphics.Path
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyPart
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion


private fun createRightShoulderPath(): Path{
    return Path().apply {
        moveTo(221.5f, 190f)
        cubicTo(219.5f, 203.5f, 189.5f, 236.5f, 190.5f, 243f)
        cubicTo(169.5f, 262.3f, 157f, 269.8f, 134f, 279.5f)
        cubicTo(134f, 267f, 134.5f, 240f, 138.5f, 225.5f)
        cubicTo(144.2f, 211.4f, 148.7f, 204.7f, 159.5f, 195f)
        cubicTo(166.6f, 190.3f, 173.3f, 187.4f, 182.5f, 185.5f)
        cubicTo(191.7f, 183.6f, 197.6f, 184.7f, 205.5f, 185.5f)
        cubicTo(213.4f, 186.3f, 216f, 188.5f, 221.5f, 190f)
        close()
    }
}

private fun createLeftShoulderPath(): Path{
    return Path().apply {
        moveTo(374f, 189.6f)
        cubicTo(376f, 203.1f, 406f, 236.1f, 405f, 242.6f)
        cubicTo(426f, 261.9f, 438.5f, 269.4f, 461.5f, 279.1f)
        cubicTo(461.5f, 266.6f, 461f, 239.6f, 457f, 225.1f)
        cubicTo(451.3f, 211f, 446.8f, 204.2f, 436f, 194.6f)
        cubicTo(428.9f, 189.9f, 422.2f, 187f, 413f, 185.1f)
        cubicTo(403.8f, 183.2f, 397.9f, 184.3f, 390f, 185.1f)
        cubicTo(382.1f, 185.9f, 379.5f, 188.1f, 374f, 189.6f)
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

private fun createLeftUpperArmPath(): Path{
    return Path().apply {
        moveTo(437.5f, 383.6f)
        cubicTo(450.5f, 382.1f, 483f, 368.1f, 494.5f, 359.1f)
        cubicTo(491.1f, 352.4f, 488.5f, 349.6f, 486.5f, 339.1f)
        cubicTo(484.8f, 330.1f, 485f, 333.1f, 481.5f, 321.6f)
        cubicTo(477f, 308.6f, 471f, 289.6f, 462.5f, 279.1f)
        cubicTo(440.5f, 272.6f, 418.5f, 255.1f, 405.5f, 243.1f)
        lineTo(405.5f, 243.3f)
        cubicTo(405.2f, 265.7f, 404.8f, 299.6f, 409.5f, 317.6f)
        cubicTo(414.2f, 335.6f, 429f, 360.1f, 435f, 371.6f)
        cubicTo(438f, 383.6f, 436f, 377.1f, 437.5f, 383.6f)
        close()
    }
}

private fun createRightUpperArmPath(): Path{
    return Path().apply {
        moveTo(158f, 384f)
        cubicTo(145f, 382.5f, 112.5f, 368.5f, 101f, 359.5f)
        cubicTo(104.4f, 352.8f, 107f, 350f, 109f, 339.5f)
        cubicTo(110.7f, 330.5f, 110.5f, 333.5f, 114f, 322f)
        cubicTo(118.5f, 309f, 124.5f, 290f, 133f, 279.5f)
        cubicTo(155f, 273f, 177f, 255.5f, 190f, 243.5f)
        lineTo(190f, 243.7f)
        cubicTo(190.3f, 266.2f, 190.7f, 300.1f, 186f, 318f)
        cubicTo(181.3f, 336f, 166.5f, 360.5f, 160.5f, 372f)
        cubicTo(157.5f, 384f, 159.5f, 377.5f, 158f, 384f)
        close()
    }
}

private fun createNeckPath(): Path{
    return Path().apply {
        moveTo(197f, 185f)
        cubicTo(189f, 184f, 187.3f, 184.5f, 182.5f, 184.5f)
        cubicTo(192.3f, 181.7f, 215.5f, 177f, 230.5f, 170.5f)
        lineTo(230.5f, 170.5f)
        cubicTo(244.5f, 163.5f, 249.5f, 161f, 258f, 153.5f)
        cubicTo(259.5f, 142f, 257f, 125.5f, 258.5f, 122.5f)
        cubicTo(260f, 123.5f, 259.9f, 125.3f, 263f, 129.5f)
        cubicTo(266f, 133.5f, 272.3f, 137.3f, 275f, 139.5f)
        cubicTo(280f, 142.5f, 280.2f, 143f, 286.5f, 144f)
        cubicTo(292.8f, 145f, 299.8f, 145.5f, 306.5f, 144.5f)
        cubicTo(315.5f, 141f, 313.1f, 142.9f, 319.5f, 138.5f)
        cubicTo(322f, 136.8f, 327.8f, 133.3f, 330.5f, 130f)
        cubicTo(337f, 125f, 335f, 124.5f, 337.5f, 123f)
        cubicTo(336.5f, 128f, 336.9f, 145.8f, 338.5f, 153.5f)
        cubicTo(340f, 156.5f, 339.4f, 157f, 346.5f, 161f)
        cubicTo(353.6f, 165f, 360.6f, 169.2f, 374f, 174f)
        cubicTo(387.4f, 178.8f, 409.4f, 182.8f, 413.5f, 185f)
        cubicTo(417.6f, 187.2f, 409f, 185f, 394f, 185f)
        cubicTo(381.1f, 186.9f, 363.6f, 192.3f, 349f, 194.5f)
        cubicTo(334.5f, 194.5f, 325.5f, 195.5f, 314f, 197.5f)
        cubicTo(304.5f, 201f, 305f, 201.5f, 297.5f, 204.5f)
        cubicTo(290.5f, 203f, 291.5f, 202.5f, 281f, 197.5f)
        cubicTo(265.8f, 194.6f, 236.9f, 192.6f, 221.5f, 190f)
        cubicTo(207f, 185f, 205f, 186f, 197f, 185f)
        close()
    }
}

private fun createHeadPath(): Path{
    return Path().apply {
        moveTo(248f, 105f)
        cubicTo(250.6f, 107.3f, 252.8f, 103f, 254f, 102.5f)
        cubicTo(255f, 106.6f, 255.2f, 116.2f, 259f, 123f)
        cubicTo(262.8f, 129.8f, 265.5f, 133.1f, 272f, 137.5f)
        cubicTo(278.5f, 141.9f, 278.5f, 141.5f, 285.5f, 144f)
        cubicTo(293.9f, 145f, 300.5f, 145f, 307f, 144.5f)
        cubicTo(310f, 143.5f, 315.8f, 141.1f, 319.9f, 138.5f)
        cubicTo(326f, 133f, 335.9f, 128f, 338.4f, 122f)
        cubicTo(340.9f, 116f, 340.5f, 107f, 341.5f, 102.5f)
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

private fun createChestPath(): Path{
    return Path().apply {
        moveTo(282f, 198f)
        cubicTo(267f, 195.1f, 234f, 191.6f, 222f, 190f)
        cubicTo(217f, 202.5f, 202.5f, 223f, 190f, 242.5f)
        cubicTo(189f, 263.5f, 191.5f, 271.5f, 190.5f, 289.5f)
        cubicTo(188.5f, 303f, 189f, 301f, 185f, 319f)
        cubicTo(191.5f, 334.5f, 197.5f, 355f, 207f, 378f)
        cubicTo(207f, 391f, 213.5f, 397f, 216f, 402f)
        cubicTo(221f, 408.5f, 221.5f, 410f, 227f, 415f)
        cubicTo(234.8f, 417f, 241.4f, 417.4f, 250.4f, 418f)
        lineTo(251f, 418f)
        cubicTo(260.3f, 418.6f, 266.3f, 418.9f, 274f, 418f)
        cubicTo(281.5f, 416f, 280f, 415f, 289.5f, 413.5f)
        cubicTo(299.2f, 413.5f, 309f, 413.5f, 322.5f, 418f)
        cubicTo(338.3f, 418.3f, 354f, 416.5f, 368.5f, 415f)
        cubicTo(374f, 407f, 378.5f, 408f, 387f, 388.5f)
        cubicTo(389.5f, 371f, 402f, 343.5f, 410.5f, 319.5f)
        cubicTo(406.5f, 304f, 405f, 292.5f, 405f, 279.5f)
        cubicTo(406.5f, 268.1f, 406.5f, 259.5f, 406.5f, 246.5f)
        cubicTo(406.5f, 233.5f, 374.5f, 199f, 374.5f, 189f)
        cubicTo(346f, 197f, 337f, 193.1f, 321.5f, 196f)
        cubicTo(306f, 198.9f, 304f, 203f, 297f, 204.5f)
        cubicTo(290f, 203f, 292.5f, 202.5f, 282f, 198f)
        close()
    }
}

private fun createAbdomenPath(): Path{
    return Path().apply {
        moveTo(210f, 409.5f)
        cubicTo(210f, 398.5f, 208.4f, 391.5f, 208f, 387f)
        cubicTo(209.9f, 390.3f, 212f, 396.5f, 216f, 402.5f)
        cubicTo(220f, 406.5f, 222.9f, 413.4f, 227.1f, 414.7f)
        cubicTo(236f, 417.5f, 239.7f, 417.1f, 249.1f, 417.7f)
        cubicTo(258.5f, 418.3f, 265.9f, 418.9f, 274f, 418f)
        cubicTo(282f, 416.5f, 281f, 414.5f, 289.5f, 413.5f)
        cubicTo(296.2f, 413f, 301f, 413.5f, 306f, 414f)
        cubicTo(313.5f, 415.5f, 316.5f, 416f, 322.5f, 418f)
        cubicTo(337.5f, 417.5f, 355f, 417f, 368f, 415f)
        cubicTo(379f, 406.5f, 385.5f, 390f, 387f, 389.5f)
        cubicTo(386.1f, 393.4f, 385.2f, 403f, 386f, 414f)
        cubicTo(386.5f, 421f, 388f, 430f, 389.5f, 437f)
        cubicTo(390.6f, 448.4f, 389.5f, 459f, 389f, 467f)
        cubicTo(381.5f, 469.5f, 362.5f, 473.5f, 340f, 477f)
        cubicTo(318f, 478.5f, 298.3f, 479.1f, 276.5f, 478.5f)
        cubicTo(254.7f, 477.9f, 240.9f, 475.3f, 227f, 473f)
        cubicTo(214.5f, 470f, 213f, 469f, 206.5f, 467f)
        cubicTo(206f, 460f, 205.4f, 449.5f, 206f, 438f)
        cubicTo(207.5f, 426.5f, 210f, 420.5f, 210f, 409.5f)
        close()
    }
}

private fun createPelvisPath(): Path{
    return Path().apply {
        moveTo(294f, 603.5f)
        cubicTo(273.2f, 585.7f, 218f, 534.5f, 196.5f, 514f)
        lineTo(200.5f, 488.5f)
        lineTo(206f, 467f)
        cubicTo(217.5f, 471f, 233.4f, 474.3f, 257f, 476.5f)
        cubicTo(280.6f, 478.7f, 297.7f, 479.9f, 324f, 478f)
        cubicTo(360.5f, 475.4f, 374.5f, 471.5f, 388.5f, 467f)
        cubicTo(392f, 476f, 395.5f, 486.5f, 399f, 514.5f)
        cubicTo(374.5f, 537f, 322.8f, 586.1f, 301.5f, 603.5f)
        cubicTo(298.5f, 604f, 298.5f, 604f, 294f, 603.5f)
        close()
    }
}

private fun createLeftKneePath(): Path{
    return Path().apply {
        moveTo(365.4f, 766.8f)
        cubicTo(373.9f, 762f, 381.9f, 757.7f, 385.4f, 752.3f)
        cubicTo(389.9f, 745.3f, 391.7f, 743.9f, 393.9f, 749.3f)
        cubicTo(392.4f, 761.8f, 396.5f, 765.7f, 396.9f, 779.3f)
        lineTo(396.9f, 779.3f)
        cubicTo(396.9f, 795.3f, 396.9f, 804.8f, 395.4f, 816.3f)
        cubicTo(388.4f, 822.8f, 382.4f, 825.8f, 377.4f, 828.8f)
        cubicTo(370.9f, 829.8f, 370.4f, 830.3f, 366.9f, 828.8f)
        cubicTo(356.9f, 826.8f, 339.5f, 820.5f, 331.4f, 813.8f)
        cubicTo(327.9f, 805.3f, 327.9f, 805.3f, 326.4f, 795.3f)
        cubicTo(325.4f, 785.3f, 326.4f, 769.3f, 326.4f, 763.8f)
        cubicTo(328.9f, 765.8f, 330.5f, 770.3f, 333.9f, 772.8f)
        cubicTo(337.3f, 775.3f, 337.9f, 775.3f, 343.4f, 776.3f)
        cubicTo(349.7f, 775.1f, 356.9f, 771.6f, 365.4f, 766.8f)
        close()
    }
}

private fun createRightKneePath(): Path{
    return Path().apply {
        moveTo(230.5f, 767f)
        cubicTo(222f, 762.2f, 214f, 757.9f, 210.5f, 752.5f)
        cubicTo(206f, 745.5f, 204.2f, 744.1f, 202f, 749.5f)
        cubicTo(203.5f, 762f, 199.4f, 765.9f, 199f, 779.5f)
        lineTo(199f, 779.5f)
        cubicTo(199f, 795.5f, 199f, 805f, 200.5f, 816.5f)
        cubicTo(207.5f, 823f, 213.5f, 826f, 218.5f, 829f)
        cubicTo(225f, 830f, 225.5f, 830.5f, 229f, 829f)
        cubicTo(239f, 827f, 256.4f, 820.7f, 264.5f, 814f)
        cubicTo(268f, 805.5f, 268f, 805.5f, 269.5f, 795.5f)
        cubicTo(270.5f, 785.5f, 269.5f, 769.5f, 269.5f, 764f)
        cubicTo(267f, 766f, 265.4f, 770.5f, 262f, 773f)
        cubicTo(258.6f, 775.5f, 258f, 775.5f, 252.5f, 776.5f)
        cubicTo(246.2f, 775.3f, 239f, 771.8f, 230.5f, 767f)
        close()
    }
}

private fun createLeftThighPath(): Path{
    return Path().apply {
        moveTo(351f, 774f)
        cubicTo(358f, 770f, 373f, 764f, 379.5f, 757.5f)
        cubicTo(377.9f, 758.4f, 404f, 738.5f, 407.5f, 691.5f)
        cubicTo(414.5f, 643.5f, 405f, 527.5f, 399f, 514.5f)
        cubicTo(393.5f, 519f, 331.5f, 577f, 301.5f, 603.5f)
        cubicTo(301.5f, 627.5f, 298.1f, 627f, 304f, 655f)
        cubicTo(310.5f, 686f, 320f, 738f, 324f, 762f)
        cubicTo(327.5f, 767f, 328.9f, 769.4f, 332f, 772f)
        cubicTo(335.1f, 774.6f, 336.3f, 775.2f, 339.5f, 775.5f)
        cubicTo(342.5f, 776.5f, 344f, 776.5f, 351f, 774f)
        close()
    }
}

private fun createRightThighPath(): Path{
    return Path().apply {
        moveTo(246.5f, 774f)
        cubicTo(239f, 771.5f, 223.5f, 764.5f, 215f, 758f)
        cubicTo(216.6f, 758.9f, 193.7f, 741.1f, 188.5f, 691.5f)
        cubicTo(181f, 641f, 190f, 529f, 196.5f, 514.5f)
        cubicTo(202f, 519f, 264f, 577f, 294f, 603.5f)
        cubicTo(294f, 627.5f, 296.4f, 627.5f, 290.5f, 655.5f)
        cubicTo(284f, 686.5f, 274.5f, 738.5f, 270.5f, 762.5f)
        cubicTo(267f, 767.5f, 265.6f, 769.9f, 262.5f, 772.5f)
        cubicTo(259.4f, 775.1f, 258.2f, 775.2f, 255.1f, 775.5f)
        lineTo(255f, 775.5f)
        cubicTo(251.8f, 775.8f, 253f, 776.6f, 246.5f, 774f)
        close()
    }
}

private fun createLeftFootPath(): Path{
    return Path().apply {
        moveTo(389.9f, 1090f)
        cubicTo(388.4f, 1083.5f, 385.9f, 1080.4f, 384.9f, 1078f)
        cubicTo(378.4f, 1078.4f, 366.4f, 1081.5f, 352.4f, 1080f)
        cubicTo(344.4f, 1078.5f, 343.9f, 1078f, 342.4f, 1077f)
        cubicTo(341.4f, 1078f, 340.5f, 1081.2f, 341.4f, 1085.5f)
        cubicTo(341.4f, 1090f, 343.9f, 1093f, 346.9f, 1098.5f)
        cubicTo(346.9f, 1104.3f, 340.9f, 1107.5f, 341.4f, 1113.5f)
        cubicTo(341.4f, 1119.5f, 344.7f, 1124.7f, 346.9f, 1128.5f)
        cubicTo(349.1f, 1132.3f, 349.2f, 1132.1f, 352.4f, 1133.5f)
        cubicTo(355.6f, 1134.9f, 362.9f, 1136f, 365.9f, 1134f)
        cubicTo(369.3f, 1132.8f, 368.3f, 1127f, 369.9f, 1127f)
        cubicTo(371.4f, 1127f, 370.4f, 1131.3f, 372.9f, 1133f)
        cubicTo(374.7f, 1134.2f, 378.2f, 1134f, 379.9f, 1133f)
        cubicTo(381.9f, 1131.5f, 382.4f, 1129.5f, 382.9f, 1128.5f)
        cubicTo(384.4f, 1129f, 385.7f, 1130.8f, 387.4f, 1130.5f)
        cubicTo(389.9f, 1130.1f, 389.9f, 1128.5f, 391.4f, 1127f)
        cubicTo(392.4f, 1128.5f, 393.4f, 1129.5f, 396.9f, 1128.5f)
        cubicTo(397.9f, 1128f, 397.9f, 1126.5f, 398.4f, 1125.5f)
        cubicTo(399.4f, 1126f, 402.1f, 1127.2f, 403.4f, 1125.5f)
        cubicTo(404.9f, 1122.5f, 407.4f, 1119f, 406.9f, 1114.5f)
        cubicTo(406.9f, 1111.5f, 406.8f, 1108.9f, 403.4f, 1104f)
        cubicTo(400.9f, 1098.5f, 395.9f, 1095f, 389.9f, 1090f)
        close()
    }
}

private fun createRightFootPath(): Path{
    return Path().apply {
        moveTo(205.5f, 1090.5f)
        cubicTo(207f, 1084f, 209.5f, 1080.9f, 210.5f, 1078.5f)
        cubicTo(217f, 1078.9f, 229f, 1082f, 243f, 1080.5f)
        cubicTo(251f, 1079f, 251.5f, 1078.5f, 253f, 1077.5f)
        cubicTo(254f, 1078.5f, 254.9f, 1081.7f, 254f, 1086f)
        cubicTo(254f, 1090.5f, 251.5f, 1093.5f, 248.5f, 1099f)
        cubicTo(248.5f, 1104.8f, 254.5f, 1108f, 254f, 1114f)
        cubicTo(254f, 1120f, 250.7f, 1125.2f, 248.5f, 1129f)
        cubicTo(246.3f, 1132.8f, 246.2f, 1132.6f, 243f, 1134f)
        cubicTo(239.8f, 1135.4f, 232.5f, 1136.5f, 229.5f, 1134.5f)
        cubicTo(226.1f, 1133.3f, 227.1f, 1127.5f, 225.5f, 1127.5f)
        cubicTo(224f, 1127.5f, 225f, 1131.8f, 222.5f, 1133.5f)
        cubicTo(220.7f, 1134.7f, 217.2f, 1134.5f, 215.5f, 1133.5f)
        cubicTo(213.5f, 1132f, 213f, 1130f, 212.5f, 1129f)
        cubicTo(211f, 1129.5f, 209.7f, 1131.3f, 208f, 1131f)
        cubicTo(205.5f, 1130.6f, 205.5f, 1129f, 204f, 1127.5f)
        cubicTo(203f, 1129f, 202f, 1130f, 198.5f, 1129f)
        cubicTo(197.5f, 1128.5f, 197.5f, 1127f, 197f, 1126f)
        cubicTo(196f, 1126.5f, 193.2f, 1127.7f, 192f, 1126f)
        cubicTo(190.5f, 1123f, 188f, 1119.5f, 188.5f, 1115f)
        cubicTo(188.5f, 1112f, 188.6f, 1109.4f, 192f, 1104.5f)
        cubicTo(194.5f, 1099f, 199.5f, 1095.5f, 205.5f, 1090.5f)
        close()
    }
}

private fun createLeftAnklePath(): Path{
    return Path().apply {
        moveTo(363.5f, 1081.5f)
        cubicTo(372f, 1081.5f, 379f, 1079.5f, 384.5f, 1078.5f)
        cubicTo(384.5f, 1073.5f, 385f, 1064f, 386.5f, 1057.5f)
        cubicTo(386.7f, 1050.5f, 385.5f, 1046.6f, 384.5f, 1043.5f)
        cubicTo(376.3f, 1043.5f, 352.8f, 1040.2f, 344f, 1043.5f)
        lineTo(344f, 1043.5f)
        cubicTo(342.5f, 1048f, 341f, 1052.5f, 341f, 1060f)
        cubicTo(342f, 1067f, 342f, 1072.5f, 342.5f, 1078.5f)
        cubicTo(347.5f, 1080f, 355f, 1081.5f, 363.5f, 1081.5f)
        close()
    }
}

private fun createRightAnklePath(): Path{
    return Path().apply {
        moveTo(232f, 1081f)
        cubicTo(223.5f, 1081f, 216.5f, 1079f, 211f, 1078f)
        cubicTo(211f, 1073f, 210.5f, 1063.5f, 209f, 1057f)
        cubicTo(208.8f, 1050f, 210f, 1046.1f, 211f, 1043f)
        cubicTo(219.2f, 1043f, 242.7f, 1039.7f, 251.5f, 1043f)
        lineTo(251.5f, 1043f)
        cubicTo(253f, 1047.5f, 254.5f, 1052f, 254.5f, 1059.5f)
        cubicTo(253.5f, 1066.5f, 253.5f, 1072f, 253f, 1078f)
        cubicTo(248f, 1079.5f, 240.5f, 1081f, 232f, 1081f)
        close()
    }
}

private fun createRightLegPath(): Path{
    return Path().apply {
        moveTo(191.5f, 883f)
        cubicTo(194f, 847f, 198.7f, 830.2f, 200.5f, 817f)
        cubicTo(202.7f, 818.6f, 206.4f, 822.4f, 211.5f, 825f)
        cubicTo(216.6f, 827.6f, 218.8f, 830.3f, 226f, 830f)
        cubicTo(233.2f, 829.7f, 239.9f, 826.7f, 247.5f, 823.5f)
        cubicTo(255.1f, 820.3f, 262.5f, 814f, 264f, 814f)
        cubicTo(262.5f, 821f, 256f, 836.5f, 258.5f, 855.5f)
        cubicTo(262f, 874.5f, 267f, 883f, 266f, 913f)
        cubicTo(264f, 943f, 249.5f, 981.5f, 248f, 1007f)
        cubicTo(245.1f, 1032.8f, 251.5f, 1038f, 251.5f, 1042f)
        cubicTo(243f, 1041.5f, 223f, 1040.5f, 210.5f, 1043f)
        cubicTo(210.5f, 1032f, 212.4f, 1033.8f, 208.5f, 1002f)
        cubicTo(204.6f, 970.2f, 193.1f, 920f, 191.5f, 883f)
        close()
    }
}

private fun createLeftLegPath(): Path{
    return Path().apply {
        moveTo(403.6f, 883f)
        cubicTo(401.1f, 847f, 396.4f, 830.2f, 394.6f, 817f)
        cubicTo(392.4f, 818.6f, 388.7f, 822.4f, 383.6f, 825f)
        cubicTo(378.5f, 827.6f, 376.3f, 830.3f, 369.1f, 830f)
        cubicTo(361.9f, 829.7f, 355.2f, 826.7f, 347.6f, 823.5f)
        cubicTo(340f, 820.3f, 332.6f, 814f, 331.1f, 814f)
        cubicTo(332.6f, 821f, 339.1f, 836.5f, 336.6f, 855.5f)
        cubicTo(333.1f, 874.5f, 328.1f, 883f, 329.1f, 913f)
        cubicTo(331.1f, 943f, 345.6f, 981.5f, 347.1f, 1007f)
        cubicTo(350f, 1032.8f, 343.6f, 1038f, 343.6f, 1042f)
        cubicTo(352.1f, 1041.5f, 372.1f, 1040.5f, 384.6f, 1043f)
        cubicTo(384.6f, 1032f, 382.7f, 1033.8f, 386.6f, 1002f)
        cubicTo(390.5f, 970.2f, 402f, 920f, 403.6f, 883f)
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



val frontBodyRegions = listOf(
    BodyHitRegion(
        name = "left_hand",
        part = BodyPart.LEFT_HAND,
        path = createLeftHandPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_hand",
        part = BodyPart.RIGHT_HAND,
        path = createRightHandPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_forearm",
        part = BodyPart.LEFT_FOREARM,
        path = createLeftForearmPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_forearm",
        part = BodyPart.RIGHT_FOREARM,
        path = createRightForearmPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_upper_arm",
        part = BodyPart.LEFT_UPPER_ARM,
        path = createLeftUpperArmPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_upper_arm",
        part = BodyPart.RIGHT_UPPER_ARM,
        path = createRightUpperArmPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_shoulder",
        part = BodyPart.RIGHT_SHOULDER,
        path = createRightShoulderPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_shoulder",
        part = BodyPart.LEFT_SHOULDER,
        path = createLeftShoulderPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "neck",
        part = BodyPart.NECK,
        path = createNeckPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "chest",
        part = BodyPart.CHEST,
        path = createChestPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "abdomen",
        part = BodyPart.ABDOMEN,
        path = createAbdomenPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "pelvis",
        part = BodyPart.PELVIS,
        path = createPelvisPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "head",
        part = BodyPart.HEAD,
        path = createHeadPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_wrist",
        part = BodyPart.LEFT_WRIST,
        path = createLeftWristPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_wrist",
        part = BodyPart.RIGHT_WRIST,
        path = createRightWristPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_leg",
        part = BodyPart.LEFT_LEG,
        path = createLeftLegPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_leg",
        part = BodyPart.RIGHT_LEG,
        path = createRightLegPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_ankle",
        part = BodyPart.LEFT_ANKLE,
        path = createLeftAnklePath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_ankle",
        part = BodyPart.RIGHT_ANKLE,
        path = createRightAnklePath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_foot",
        part = BodyPart.LEFT_FOOT,
        path = createLeftFootPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_foot",
        part = BodyPart.RIGHT_FOOT,
        path = createRightFootPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_thigh",
        part = BodyPart.LEFT_THIGH,
        path = createLeftThighPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_thigh",
        part = BodyPart.RIGHT_THIGH,
        path = createRightThighPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_knee",
        part = BodyPart.LEFT_KNEE,
        path = createLeftKneePath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_knee",
        part = BodyPart.RIGHT_KNEE,
        path = createRightKneePath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "left_elbow",
        part = BodyPart.LEFT_ELBOW,
        path = createLeftElbowPath(),
        region = BodyRegion.FRONT
    ),
    BodyHitRegion(
        name = "right_elbow",
        part = BodyPart.RIGHT_ELBOW,
        path = createRightElbowPath(),
        region = BodyRegion.FRONT
    ),
)