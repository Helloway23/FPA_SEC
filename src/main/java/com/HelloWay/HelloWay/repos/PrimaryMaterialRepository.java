package com.HelloWay.HelloWay.repos;

import com.HelloWay.HelloWay.entities.PrimaryMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrimaryMaterialRepository extends JpaRepository<PrimaryMaterial, Long> {
    List<PrimaryMaterial> findBySpaceId(Long spaceId);
    Optional<PrimaryMaterial> findByIdAndSpaceId(Long primaryMaterialId, Long spaceId);

    List<PrimaryMaterial> findBySpaceIdAndName(Long spaceId, String name);

    List<PrimaryMaterial> findExpiredBySpaceId(Long spaceId);
}
