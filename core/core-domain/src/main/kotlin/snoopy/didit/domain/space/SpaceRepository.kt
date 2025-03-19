package snoopy.didit.domain.space

import snoopy.didit.domain.space.dto.SpaceCreateDto

interface SpaceRepository {
    fun createSpace(spaceCreateDto: SpaceCreateDto): Space

    fun findByMemberId(memberId: Long): List<Space>

    fun findById(spaceId: Long): Space

    fun save(space: Space): Space
}
