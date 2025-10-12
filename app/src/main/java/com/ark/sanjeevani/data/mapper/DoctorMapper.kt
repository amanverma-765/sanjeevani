package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.DietitianDto
import com.ark.sanjeevani.data.dto.DoctorCategoryDto
import com.ark.sanjeevani.data.dto.DoctorDto
import com.ark.sanjeevani.data.dto.PhysioTherapistDto
import com.ark.sanjeevani.domain.model.Dietitian
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory
import com.ark.sanjeevani.domain.model.Physiotherapist

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

    fun PhysioTherapistDto.toPhysiotherapist(): Physiotherapist {
        return Physiotherapist(
            id = this.id,
            name = this.name,
            email = this.email,
            phone = this.phone,
            avatar = this.avatar,
            experienceMonths = this.experienceMonths
        )
    }

    fun DietitianDto.toDietitian(): Dietitian {
        return Dietitian(
            id = this.id,
            name = this.name,
            email = this.email,
            phone = this.phone,
            avatar = this.avatar,
            experienceMonths = this.experienceMonths
        )
    }
}