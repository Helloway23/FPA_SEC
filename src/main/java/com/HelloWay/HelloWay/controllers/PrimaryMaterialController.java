package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.PrimaryMaterial;
import com.HelloWay.HelloWay.services.PrimaryMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/primary-materials")
public class PrimaryMaterialController {

    private final PrimaryMaterialService primaryMaterialService;

    @Autowired
    public PrimaryMaterialController(PrimaryMaterialService primaryMaterialService) {
        this.primaryMaterialService = primaryMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<PrimaryMaterial>> getAllPrimaryMaterials() {
        List<PrimaryMaterial> primaryMaterials = primaryMaterialService.getAllPrimaryMaterials();
        return ResponseEntity.ok(primaryMaterials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrimaryMaterial> getPrimaryMaterialById(@PathVariable("id") Long id) {
        PrimaryMaterial primaryMaterial = primaryMaterialService.getPrimaryMaterialById(id);
        if (primaryMaterial != null) {
            return ResponseEntity.ok(primaryMaterial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PrimaryMaterial> createPrimaryMaterial(@RequestBody PrimaryMaterial primaryMaterial) {
        PrimaryMaterial createdPrimaryMaterial = primaryMaterialService.createPrimaryMaterial(primaryMaterial);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrimaryMaterial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrimaryMaterial> updatePrimaryMaterial(
            @PathVariable("id") Long id,
            @RequestBody PrimaryMaterial updatedPrimaryMaterial
    ) {
        PrimaryMaterial updatedMaterial = primaryMaterialService.updatePrimaryMaterial(id, updatedPrimaryMaterial);
        if (updatedMaterial != null) {
            return ResponseEntity.ok(updatedMaterial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrimaryMaterial(@PathVariable("id") Long id) {
        primaryMaterialService.deletePrimaryMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<List<PrimaryMaterial>> getPrimaryMaterialsBySpaceId(@PathVariable("spaceId") Long spaceId) {
        List<PrimaryMaterial> primaryMaterials = primaryMaterialService.getPrimaryMaterialsBySpaceId(spaceId);
        return ResponseEntity.ok(primaryMaterials);
    }

    @PostMapping("/space/{spaceId}")
    public ResponseEntity<PrimaryMaterial> addPrimaryMaterialToSpace(
            @PathVariable("spaceId") Long spaceId,
            @RequestBody PrimaryMaterial primaryMaterial
    ) {
        PrimaryMaterial createdPrimaryMaterial = primaryMaterialService.addPrimaryMaterialToSpace(spaceId, primaryMaterial);
        if (createdPrimaryMaterial != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPrimaryMaterial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/space/{spaceId}/{primaryMaterialId}")
    public ResponseEntity<PrimaryMaterial> updatePrimaryMaterialInSpace(
            @PathVariable("spaceId") Long spaceId,
            @PathVariable("primaryMaterialId") Long primaryMaterialId,
            @RequestBody PrimaryMaterial updatedPrimaryMaterial
    ) {
        PrimaryMaterial updatedPrimaryMaterialLatest = primaryMaterialService.updatePrimaryMaterialInSpace(spaceId, primaryMaterialId, updatedPrimaryMaterial);
        if (updatedPrimaryMaterial != null) {
            return ResponseEntity.ok(updatedPrimaryMaterialLatest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/space/{spaceId}/{primaryMaterialId}")
    public ResponseEntity<Void> removePrimaryMaterialFromSpace(
            @PathVariable("spaceId") Long spaceId,
            @PathVariable("primaryMaterialId") Long primaryMaterialId
    ) {
        primaryMaterialService.removePrimaryMaterialFromSpace(spaceId, primaryMaterialId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/space/{spaceId}/expiration")
    public ResponseEntity<List<PrimaryMaterial>> getExpiredPrimaryMaterialsBySpaceId(@PathVariable("spaceId") Long spaceId) {
        List<PrimaryMaterial> expiredPrimaryMaterials = primaryMaterialService.getExpiredPrimaryMaterialsBySpaceId(spaceId);
        return ResponseEntity.ok(expiredPrimaryMaterials);
    }

    @PatchMapping("/{primaryMaterialId}/quantity")
    public ResponseEntity<PrimaryMaterial> updatePrimaryMaterialQuantity(
            @PathVariable("primaryMaterialId") Long primaryMaterialId,
            @RequestParam("quantity") double quantity
    ) {
        PrimaryMaterial updatedPrimaryMaterial = primaryMaterialService.updatePrimaryMaterialQuantity(primaryMaterialId, quantity);
        if (updatedPrimaryMaterial != null) {
            return ResponseEntity.ok(updatedPrimaryMaterial);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/space/{spaceId}/name/{name}")
    public ResponseEntity<List<PrimaryMaterial>> getPrimaryMaterialsBySpaceIdAndName(
            @PathVariable("spaceId") Long spaceId,
            @PathVariable("name") String name
    ) {
        List<PrimaryMaterial> primaryMaterials = primaryMaterialService.getPrimaryMaterialsBySpaceIdAndName(spaceId, name);
        return ResponseEntity.ok(primaryMaterials);
    }
}
