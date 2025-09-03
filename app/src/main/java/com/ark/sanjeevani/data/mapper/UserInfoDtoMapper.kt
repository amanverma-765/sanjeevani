package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.domain.model.LoginUserInfo
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

object UserInfoDtoMapper {
    fun UserInfo.toLoginUserInfo(): LoginUserInfo {
        val name = userMetadata?.get("name")?.jsonPrimitive?.contentOrNull ?: "User"
        val profileUrl = userMetadata?.get("avatar_url")?.jsonPrimitive?.contentOrNull ?: ""

        return LoginUserInfo(
            id = this.id,
            name = name,
            email = this.email!!,
            profileUrl = profileUrl
        )
    }

}