package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.util.extractDate
import com.yehorsk.medical_platform_mobile.util.extractTime
import com.yehorsk.theme.AppTheme

@Composable
fun DashAppointmentItem(
    modifier: Modifier = Modifier,
    appointment: Appointment,
    onClick: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${appointment.doctor.title} ${appointment.doctor.firstName} ${appointment.doctor.lastName}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = appointment.specialization.name,
                fontSize = 14.sp,
                color = Color(0xFF717182),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${extractDate(appointment.datetime.toString())} ${extractTime(appointment.datetime.toString())}",
                fontSize = 14.sp,
                color = Color(0xFF717182),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        StatusBox(
            status = appointment.status
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun DashAppointmentItemPreview() {
//    val appointment = Appointment(
//        id = 1,
//        datetime = "2026-06-03T10:30:00Z",
//        status = AppointmentStatus.CONFIRMED,
//        note = "Regular checkup",
//        createdAt = "2026-06-03T10:30:00Z",
//        updatedAt = "2026-06-03T10:30:00Z",
//        specialization = Specialization(
//            id = 1,
//            name = "Cardiology"
//        ),
//        doctor = User(
//            id = 1,
//            email = "doctor@example.com",
//            firstName = "John",
//            lastName = "Doe",
//            role = UserRole.DOCTOR,
//            title = "MUDr."
//        )
//    )
//
//    AppTheme {
//        DashAppointmentItem(
//            appointment = appointment,
//            onClick = {}
//        )
//    }
//}