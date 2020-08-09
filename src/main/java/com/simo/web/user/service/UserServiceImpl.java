package com.simo.web.user.service;

import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.service.RegionServiceImpl;
import com.simo.web.user.model.*;
import com.simo.web.user.model.mapper.UserMapper;
import com.simo.web.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final RegionServiceImpl regionService;
    private final RoleServiceImpl roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, RegionServiceImpl regionService, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.regionService = regionService;
        this.roleService = roleService;
    }

    @Override
    public List<UserEntity> findAllCleaners() {

        return this.userRepository.findAll()
                .stream()
                .filter(ue -> ue.getRoles()
                        .stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList())
                        .contains("ROLE_CLEANER"))
                .collect(Collectors.toList());

    }

    @Override
    public List<UserServiceDTO> searchCleaners(String email, String firstName, String lastName, String regionName) {
        return this.userRepository.getCleanersBySearchParams(email, firstName, lastName,  regionName).orElse(new ArrayList<>())
                .stream()
                .filter(ue -> ue.getRoles()
                        .stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList())
                        .contains("ROLE_CLEANER"))
                .map(UserMapper.INSTANCE::mapUserEntityToUserServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existUser(String email) {
        Objects.requireNonNull(email);
        return userRepository.findOneByEmail(email).isPresent();
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return this.userRepository.findOneByEmail(username).orElse(null);
    }

    @Override
    public UserEntity registerUser(UserRegisterDTO userRegisterDTO) {

        UserServiceDTO userServiceDTO = new UserServiceDTO();

        if (userRegisterDTO.getPassword() != null) {
            userServiceDTO.setPasswordHash(
                    this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        }

        if (this.userRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity("ROLE_ADMIN");
            this.roleService.saveRole(adminRole);
            userServiceDTO.setRoles(List.of(adminRole));
        } else {
            RoleEntity userRole = this.roleService.findRoleByName(userRegisterDTO.getRole());
            userServiceDTO.setRoles(List.of(userRole));
        }

        userServiceDTO.setEmail(userRegisterDTO.getEmail());
        userServiceDTO.setFirstName(userRegisterDTO.getFirstName());
        userServiceDTO.setLastName(userRegisterDTO.getLastName());
        userServiceDTO.setCleanerTasks(new ArrayList<>());
        userServiceDTO.setClientTasks(new ArrayList<>());
        userServiceDTO.setComments(new ArrayList<>());
        userServiceDTO.setResponses(new ArrayList<>());
        userServiceDTO.setAnnouncements(new ArrayList<>());

        RegionEntity region = this.regionService.findRegionByName(userRegisterDTO.getRegion());
        userServiceDTO.setRegion(region);

        UserEntity userEntity =
                UserMapper.INSTANCE.mapUserServiceModelToUserEntity(userServiceDTO);

        return this.userRepository.saveAndFlush(userEntity);

    }

    @Override
    public void createAndLoginUser(UserRegisterDTO userRegisterDTO) {
        UserEntity newUser = registerUser(userRegisterDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userRegisterDTO.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }



    @Override
    public List<String> filterByUserEmail(String userEmail) {
        return this.userRepository.filterUsersByEmail(userEmail).orElse(null)
                .stream()
                .map(UserEntity::getEmail)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> filterByUserFirstName(String firstName) {
        return this.userRepository.filterUsersByFirstName(firstName).orElse(null);
    }

    @Override
    public List<String> filterByUserLastName(String lastname) {
        return this.userRepository.filterUsersByLastname(lastname).orElse(null);
    }

    @Override
    public List<String> filterByUserRegionName(String regionName) {
        return this.userRepository.filterUsersByRegionName(regionName).orElse(null);
    }

    @Override
    public void updateUser(UserServiceDTO user) {
        this.userRepository.save(UserMapper.INSTANCE.mapUserServiceModelToUserEntity(user));
    }

    @Override
    public List<String> filterCleanersByEmailLikeAndTaskRegion(String emailLike, String taskRegion) {

        List<String> collect = this.userRepository.filterUsersByEmail(emailLike).orElse(null)
                .stream()
                .filter(u -> u.getRegion().getName().equals(taskRegion))
                .collect(Collectors.toList())
                .stream().filter(e -> e.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()).contains("ROLE_CLEANER"))
                .map(UserEntity::getEmail)
                .collect(Collectors.toList());

    return collect;
    }

    @Override
    public List<UserServiceDTO> findAllUsersBySearchParams(String username, String firstName, String lastName) {
        return this.userRepository.getUsersBySearchParams(username, firstName, lastName).orElse(new ArrayList<>())
                .stream()
                .map(UserMapper.INSTANCE::mapUserEntityToUserServiceModel)
                .collect(Collectors.toList());
    }


    @Override
    public List<UserServiceDTO> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::mapUserEntityToUserServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceDTO findById(Long id) {
        return UserMapper.INSTANCE
                .mapUserEntityToUserServiceModel(
                        this.userRepository.findById(id).orElse(null));
    }


}
