package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.*;
import com.HelloWay.HelloWay.payload.request.SignupRequest;
import com.HelloWay.HelloWay.payload.response.MessageResponse;
import com.HelloWay.HelloWay.repos.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.HelloWay.HelloWay.services.ImageService;
import com.HelloWay.HelloWay.services.SpaceService;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.HelloWay.HelloWay.entities.ERole.ROLE_WAITER;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    SpaceService spaceService;

    @Autowired
    ImageService imageService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository ;
    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Space addNewSpace(@RequestBody Space space) throws IOException {
        return spaceService.addNewSpace(space);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Space> allSpaces(){
        return spaceService.findAllSpaces();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Space findSpaceById(@PathVariable("id") long id){
        return spaceService.findSpaceById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateSpace(@RequestBody Space space){
        spaceService.updateSpace(space); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteSpace(@PathVariable("id") long id){
        spaceService.deleteSpace(id); }
    //Pas encore tester : Que apres les crud de categorie Controller
    @PostMapping("/add/idModerator/{idModerator}/idCategory/{idCategory}")
    @ResponseBody
    public Space addNewSpaceByIdModeratorAndIdCategory(@RequestBody Space space, @PathVariable Long idModerator, @PathVariable Long idCategory) {
        return spaceService.addSpaceByIdModeratorAndIdSpaceCategory(space, idModerator, idCategory);
    }

    @PostMapping("/add/idModerator/{idModerator}/idSpaceCategory/{idSpaceCategory}")
    @ResponseBody
    public Space addNewSpaceByIdModeratorAndIdSpaceCategory(@RequestBody Space space, @PathVariable Long idModerator, @PathVariable Long idSpaceCategory) {
        return spaceService.addSpaceByIdModeratorAndSpaceCategory(space, idModerator, idSpaceCategory);
    }

    @GetMapping("/idModerator/{idModerator}")
    @ResponseBody
    public Space getSpaceByIdModerator( @PathVariable Long idModerator) {
        return spaceService.getSpaceByIdModerator(idModerator);
    }

    @GetMapping("/idCategory/{idCategory}")
    @ResponseBody
    public Space getSpaceByIdCategory( @PathVariable Long idCategory) {
        // get spaceByIdSpaceCategorie
        return spaceService.getSpaceByIdCategory(idCategory);
    }


    // Done without testing
    @GetMapping("/idSpaceCategory/{idSpaceCategory}")
    @ResponseBody
    public List<Space> getSpacesByIdSpaceCategory( @PathVariable Long idSpaceCategory) {

        return spaceService.getSpacesByIdSpaceCategory(idSpaceCategory);
    }

    @PostMapping("/images")
    public ResponseEntity<?> addSpaceWithImages(@RequestParam("titleSpace") String titleSpace,
                                                @RequestParam("latitude") String latitude,
                                                @RequestParam("longitude") String longitude,
                                                @RequestParam("rating") Float rating,
                                                @RequestParam("numberOfRating") int numberOfRating,
                                                @RequestParam("description") String description,
                                                @RequestParam("images") List<MultipartFile> images) throws IOException {
        Space space = new Space();
        space.setTitleSpace(titleSpace);
        space.setLatitude(latitude);
        space.setLongitude(longitude);
        space.setRating(rating);
        space.setNumberOfRating(numberOfRating);
        space.setDescription(description);

        List<Image> spaceImages = new ArrayList<>();
        for (MultipartFile image : images) {
            Image spaceImage = new Image();
            spaceImage.setFileName(image.getOriginalFilename());
            spaceImage.setFileType(image.getContentType());
            spaceImage.setData(image.getBytes());
            spaceImage.setSpace(space);
            imageService.addImageLa(spaceImage);
            spaceImages.add(spaceImage);
        }
        space.setImages(spaceImages);


        spaceService.addNewSpace(space);

        return ResponseEntity.ok("Space created successfully with images.");
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImage(@PathVariable("id") Long id,
                                           @RequestParam("file") MultipartFile file) {
        try {
            Space space = spaceRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException()) ;

            // Create the Image entity and set the reference to the Space entity
            Image image = new Image();
            image.setSpace(space);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setData(file.getBytes());

            // Persist the Image entity to the database
            imageRepository.save(image);

            return ResponseEntity.ok().body("Image uploaded successfully");
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading file", ex);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/{idSpace}/images/{idProduct}")
    public ResponseEntity<String> addImageBySpaceIdAndProductId(@PathVariable("idSpace") Long id,
                                                                @RequestParam("file") MultipartFile file, @PathVariable Long idProduct) {
        try {
            Space space = spaceRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException()) ;
            Product product = productRepository.findById(idProduct).orElse(null);
            // Create the Image entity and set the reference to the Space entity
            Image image = new Image();
            image.setSpace(space);
            image.setProduct(product);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setData(file.getBytes());

            // Persist the Image entity to the database
            imageRepository.save(image);

            return ResponseEntity.ok().body("Image uploaded successfully");
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading file", ex);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/moderatorUserId/{moderatorUserId}/{spaceId}/servers/{serverId}/zones/{zoneId}")
    public ResponseEntity<String> setServerInZone(
            @PathVariable Long spaceId,
            @PathVariable Long moderatorUserId,
            @PathVariable Long serverId,
            @PathVariable Long zoneId) {
        try {
            spaceService.setServerInZone(spaceId, moderatorUserId, serverId, zoneId);
            return ResponseEntity.ok("Server successfully assigned to the zone.");
        } catch (NotFoundException e) {
            // Handle the exception
            // For example, return an appropriate error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.");
        }

    }

    @PostMapping("/moderatorUserId/{moderatorUserId}/{spaceId}/servers/{serverId}")
    public ResponseEntity<String> addServerInSpace(
            @PathVariable Long spaceId,
            @PathVariable Long moderatorUserId,
            @PathVariable Long serverId) {
        try {
            spaceService.addServerInSpace(spaceId, moderatorUserId, serverId);
            return ResponseEntity.ok("Server successfully assigned to the Space.");
        } catch (NotFoundException e) {
            // Handle the exception
            // For example, return an appropriate error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.");
        }

    }

    @PostMapping("/moderatorUserId/{moderatorUserId}/{spaceId}/servers")
    @ResponseBody
    public ResponseEntity<?> createServerForSpace(
            @PathVariable Long spaceId,
            @PathVariable Long moderatorUserId,
            @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account ya 3loulou
        User user = new User(signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getLastname(),
                signupRequest.getBirthday(),
                signupRequest.getPhone(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role assistantRole = roleRepository.findByName(ROLE_WAITER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(assistantRole);
        user.setRoles(roles);
        User userSaved = userRepository.save(user);
        try {
            spaceService.addServerInSpace(spaceId, moderatorUserId, userSaved.getId());
            return ResponseEntity.ok("Server successfully assigned to the Space.");
        } catch (NotFoundException e) {
            // Handle the exception
            // For example, return an appropriate error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.");
        }

    }

    @GetMapping("/all/paging")
    public Page<Space> getSpaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return spaceService.getSpaces(page, size);
    }

}
