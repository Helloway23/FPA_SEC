package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.PrimaryMaterial;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.repos.PrimaryMaterialRepository;
import com.HelloWay.HelloWay.repos.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimaryMaterialService {

    private final PrimaryMaterialRepository primaryMaterialRepository;
    private final SpaceRepository  spaceRepository;

    @Autowired
    public PrimaryMaterialService(PrimaryMaterialRepository primaryMaterialRepository, SpaceRepository  spaceRepository) {
        this.primaryMaterialRepository = primaryMaterialRepository;
        this.spaceRepository = spaceRepository;
    }

    public List<PrimaryMaterial> getAllPrimaryMaterials() {
        return primaryMaterialRepository.findAll();
    }

    public PrimaryMaterial getPrimaryMaterialById(Long id) {
        return primaryMaterialRepository.findById(id).orElse(null);
    }

    public PrimaryMaterial createPrimaryMaterial(PrimaryMaterial primaryMaterial) {
        return primaryMaterialRepository.save(primaryMaterial);
    }

    public PrimaryMaterial updatePrimaryMaterial(Long id, PrimaryMaterial updatedPrimaryMaterial) {
        PrimaryMaterial existingPrimaryMaterial = primaryMaterialRepository.findById(id).orElse(null);
        if (existingPrimaryMaterial != null) {
            existingPrimaryMaterial.setName(updatedPrimaryMaterial.getName());
            existingPrimaryMaterial.setDescription(updatedPrimaryMaterial.getDescription());
            existingPrimaryMaterial.setUnitOfMeasure(updatedPrimaryMaterial.getUnitOfMeasure());
            existingPrimaryMaterial.setStockQuantity(updatedPrimaryMaterial.getStockQuantity());
            existingPrimaryMaterial.setPrice(updatedPrimaryMaterial.getPrice());
            existingPrimaryMaterial.setExpirationDate(updatedPrimaryMaterial.getExpirationDate());
            existingPrimaryMaterial.setSupplier(updatedPrimaryMaterial.getSupplier());
            existingPrimaryMaterial.setSupplierNumber(updatedPrimaryMaterial.getSupplierNumber());
            existingPrimaryMaterial.setSpace(updatedPrimaryMaterial.getSpace());
            return primaryMaterialRepository.save(existingPrimaryMaterial);
        }
        return null;
    }

    public void deletePrimaryMaterial(Long id) {
        primaryMaterialRepository.deleteById(id);
    }
    public List<PrimaryMaterial> getPrimaryMaterialsBySpaceId(Long spaceId) {
        return primaryMaterialRepository.findBySpaceId(spaceId);
    }

    public PrimaryMaterial addPrimaryMaterialToSpace(Long spaceId, PrimaryMaterial primaryMaterial) {
        // Check if the space exists
        Space space = spaceRepository.findById(spaceId).orElse(null);
        if (space != null) {
            // Set the space for the primary material
            primaryMaterial.setSpace(space);
            return primaryMaterialRepository.save(primaryMaterial);
        }
        return null;
    }

    public PrimaryMaterial updatePrimaryMaterialInSpace(Long spaceId, Long primaryMaterialId, PrimaryMaterial updatedPrimaryMaterial) {
        // Check if the space exists
        Space space = spaceRepository.findById(spaceId).orElse(null);
        if (space != null) {
            // Check if the primary material exists in the given space
            PrimaryMaterial existingPrimaryMaterial = primaryMaterialRepository.findByIdAndSpaceId(primaryMaterialId, spaceId).orElse(null);
            if (existingPrimaryMaterial != null) {
                // Update the primary material attributes
                existingPrimaryMaterial.setName(updatedPrimaryMaterial.getName());
                existingPrimaryMaterial.setDescription(updatedPrimaryMaterial.getDescription());
                existingPrimaryMaterial.setUnitOfMeasure(updatedPrimaryMaterial.getUnitOfMeasure());
                existingPrimaryMaterial.setStockQuantity(updatedPrimaryMaterial.getStockQuantity());
                existingPrimaryMaterial.setPrice(updatedPrimaryMaterial.getPrice());
                existingPrimaryMaterial.setExpirationDate(updatedPrimaryMaterial.getExpirationDate());
                existingPrimaryMaterial.setSupplier(updatedPrimaryMaterial.getSupplier());
                existingPrimaryMaterial.setSupplierNumber(updatedPrimaryMaterial.getSupplierNumber());
                return primaryMaterialRepository.save(existingPrimaryMaterial);
            }
        }
        return null;
    }

         public void removePrimaryMaterialFromSpace(Long spaceId, Long primaryMaterialId) {
             // Check if the space exists
             Space space = spaceRepository.findById(spaceId).orElse(null);
             if (space != null) {
                 // Check if the primary material exists in the given space
                 PrimaryMaterial primaryMaterial = primaryMaterialRepository.findByIdAndSpaceId(primaryMaterialId, spaceId).orElse(null);
                 if (primaryMaterial != null) {
                     primaryMaterialRepository.delete(primaryMaterial);
                 }
             }
         }

        public List<PrimaryMaterial> getPrimaryMaterialsBySpaceIdAndName(Long spaceId, String name) {
            return primaryMaterialRepository.findBySpaceIdAndName(spaceId, name);
        }

        public List<PrimaryMaterial> getExpiredPrimaryMaterialsBySpaceId(Long spaceId) {
            return primaryMaterialRepository.findExpiredBySpaceId(spaceId);
        }

    public PrimaryMaterial updatePrimaryMaterialQuantity(Long primaryMaterialId, double quantity) {
        PrimaryMaterial primaryMaterial = getPrimaryMaterialById(primaryMaterialId);
        primaryMaterial.setStockQuantity(quantity);
        return primaryMaterialRepository.save(primaryMaterial);
    }
}
