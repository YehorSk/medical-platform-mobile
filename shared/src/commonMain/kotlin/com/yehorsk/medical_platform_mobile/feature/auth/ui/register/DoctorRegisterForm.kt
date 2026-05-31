package com.yehorsk.medical_platform_mobile.feature.auth.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.auth.ui.component.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.auth.ui.component.PwdTextField

@Composable
fun DoctorRegisterForm(
    firstName: String,
    lastName: String,
    email: String,
    licenseNumber: String,
    specialization: String,
    pwd: String,
    pwdRepeat: String,
    passwordVisible: Boolean,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onLicenseNumberChange: (String) -> Unit,
    onSpecializationChange: (String) -> Unit,
    onPwdChange: (String) -> Unit,
    onPwdRepeatChange: (String) -> Unit,
    onPasswordVisibleChange: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box(modifier = Modifier.weight(1f)) {
                DefaultTextField(value = firstName, header = "First Name", placeholder = "First Name", onValueChange = onFirstNameChange)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                DefaultTextField(value = lastName, header = "Last Name", placeholder = "Last Name", onValueChange = onLastNameChange)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(value = email, header = "Email", placeholder = "Email", onValueChange = onEmailChange, keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(value = licenseNumber, header = "License Number", placeholder = "License Number", onValueChange = onLicenseNumberChange)
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(value = specialization, header = "Specialization", placeholder = "Specialization", onValueChange = onSpecializationChange)
        Spacer(modifier = Modifier.height(8.dp))
        PwdTextField(value = pwd, header = "Password", onValueChange = onPwdChange, passwordVisible = passwordVisible, onPasswordVisibleChange = onPasswordVisibleChange)
        Spacer(modifier = Modifier.height(8.dp))
        PwdTextField(value = pwdRepeat, header = "Confirm Password", onValueChange = onPwdRepeatChange, passwordVisible = passwordVisible, onPasswordVisibleChange = onPasswordVisibleChange)
        Spacer(modifier = Modifier.height(16.dp))
    }
}