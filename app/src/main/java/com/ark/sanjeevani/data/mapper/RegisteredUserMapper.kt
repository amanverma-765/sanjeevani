package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.RegisteredUserDto
import com.ark.sanjeevani.domain.model.RegisteredUser

object RegisteredUserMapper {
    fun RegisteredUser.toRegisteredUserDto(): RegisteredUserDto {
        return RegisteredUserDto(
            name = this.name,
            email = this.email,
            phone = this.phone,
            createdAt = this.createdAt,
            role = this.role,
            avatar = this.avatar,
            countryCode = this.countryCode,
            dob = this.dob,
            gender = this.gender,
            state = this.state,
            city = this.city
        )
    }

    fun RegisteredUserDto.toRegisteredUser(): RegisteredUser {
        return RegisteredUser(
            name = this.name,
            email = this.email,
            phone = this.phone,
            role = this.role,
            createdAt = this.createdAt,
            avatar = this.avatar,
            countryCode = this.countryCode,
            dob = this.dob,
            gender = this.gender,
            state = this.state,
            city = this.city
        )
    }
}