package com.yehorsk.medical_platform_mobile.feature.auth.ui.register.viewmodel

sealed interface RegisterAction {
    data class UpdateFirstName(val firstName: String) : RegisterAction
    data class UpdateLastName(val lastName: String) : RegisterAction
    data class UpdateEmail(val email: String) : RegisterAction
    data class UpdatePhone(val phone: String) : RegisterAction
    data class UpdatePwd(val pwd: String) : RegisterAction
    data class UpdatePwdRepeat(val pwdRepeat: String) : RegisterAction
    data class UpdateRole(val role: String) : RegisterAction
    data class UpdateLicenseNumber(val licenseNumber: String) : RegisterAction
    data class UpdateSpecialization(val specialization: String) : RegisterAction
    data object ChangePwdVisibility : RegisterAction
    data object OnRegisterClicked : RegisterAction
    data object OnSignInClicked : RegisterAction
}