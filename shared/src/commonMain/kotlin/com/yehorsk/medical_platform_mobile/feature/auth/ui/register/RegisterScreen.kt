package com.yehorsk.medical_platform_mobile.feature.auth.ui.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.feature.auth.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.auth.ui.component.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.auth.ui.component.PwdTextField
import com.yehorsk.medical_platform_mobile.feature.auth.ui.component.RoleToggle
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
){
    RegisterScreenRoot(
        modifier = modifier
    )
}

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier
){
    var selectedRole by remember { mutableStateOf(UserRole.PATIENT) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var pwdRepeat by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2B5CE6))
                .padding(vertical = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(Color(0xFF4A72F0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(UiRes.drawable.stethoscope_24px),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Create Account",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Join MedConnect Today",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            RoleToggle(
                selectedRole = selectedRole,
                onRoleSelected = { selectedRole = it }
            )

            AnimatedVisibility(
                visible = selectedRole == UserRole.PATIENT,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ){
                PatientRegisterForm(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phone = phone,
                    pwd = pwd,
                    pwdRepeat = pwdRepeat,
                    passwordVisible = passwordVisible,
                    onFirstNameChange = { firstName = it },
                    onLastNameChange = { lastName = it },
                    onEmailChange = { email = it },
                    onPhoneChange = { phone = it },
                    onPwdChange = { pwd = it },
                    onPwdRepeatChange = { pwdRepeat = it },
                    onPasswordVisibleChange = { passwordVisible = !passwordVisible },
                )
            }
            AnimatedVisibility(
                visible = selectedRole == UserRole.DOCTOR,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                DoctorRegisterForm(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    licenseNumber = licenseNumber,
                    specialization = specialization,
                    pwd = pwd,
                    pwdRepeat = pwdRepeat,
                    passwordVisible = passwordVisible,
                    onFirstNameChange = { firstName = it },
                    onLastNameChange = { lastName = it },
                    onEmailChange = { email = it },
                    onLicenseNumberChange = { licenseNumber = it },
                    onSpecializationChange = { specialization = it },
                    onPwdChange = { pwd = it },
                    onPwdRepeatChange = { pwdRepeat = it },
                    onPasswordVisibleChange = { passwordVisible = !passwordVisible },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B5CE6))
            ) {
                Text(
                    text = "Create Patient Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Sign in", color = Color(0xFF2B5CE6), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    MaterialTheme {
        RegisterScreenRoot()
    }
}