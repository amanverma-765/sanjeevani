package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.HospitalDto
import com.ark.sanjeevani.data.dto.HospitalRoomDto
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.domain.model.HospitalRoom

object HospitalMapper {
    fun HospitalDto.toHospital(): Hospital {
        return Hospital(
            id = this.id,
            name = this.name,
            lat = this.lat,
            lon = this.lon,
            type = this.type,
            createdAt = this.createdAt,
            img = this.img,
            address = this.address,
            rating = this.rating,
            website = this.website,
        )
    }

    fun HospitalRoomDto.toHospitalRoom(): HospitalRoom {
        return HospitalRoom(
            id = this.id,
            createdAt = this.createdAt,
            roomNumber = this.roomNumber,
            name = this.name,
            subtitle = this.subtitle
        )
    }
}