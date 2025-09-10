package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.HospitalDto
import com.ark.sanjeevani.domain.model.Hospital

object HospitalMapper {
    fun HospitalDto.toHospital(): Hospital {
        return Hospital(
            id = this.id,
            name = this.name,
            lat = this.lat,
            lon = this.lon,
            type = this.type,
            createdAt = this.createdAt,
            img = this.img
        )
    }
}