package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.DoctorCategoryDto
import com.ark.sanjeevani.data.dto.DoctorDto
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory

object DoctorMapper {

    fun DoctorCategoryDto.toDoctorCategory(): DoctorCategory {
        return DoctorCategory(
            id = this.id,
            name = this.name
        )
    }

    fun DoctorDto.toDoctor(): Doctor {
        return Doctor(
            id = this.id,
            name = this.name,
            email = this.email,
            phone = this.phone,
            avatar = this.avatar,
            experienceMonths = this.experienceMonths
        )
    }
}