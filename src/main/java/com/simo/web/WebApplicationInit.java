package com.simo.web;


import com.simo.web.announcement.repository.AnnouncementRepository;
import com.simo.web.comment.service.CommentServiceImpl;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.repository.RegionRepository;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.repository.TaskRepository;
import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.repository.UserRepository;
import com.simo.web.user.service.RoleServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class WebApplicationInit implements CommandLineRunner {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final TaskRepository taskRepository;
    private final CommentServiceImpl commentService;
    private final RoleServiceImpl roleService;

    public WebApplicationInit(AnnouncementRepository announcementRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RegionRepository regionRepository, TaskRepository taskRepository, CommentServiceImpl commentService, RoleServiceImpl roleService) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.regionRepository = regionRepository;
        this.taskRepository = taskRepository;
        this.commentService = commentService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

            //init();

    }

    private void init() {
//        // if (announcementRepository.count() == 0){
//        AnnouncementEntity a1 = new AnnouncementEntity();
//        a1.setTitle("Office to clean");
//        a1.setDescription("Two story office in London");
//        a1.setCreatedOn(Instant.now());
//        a1.setUpdatedOn(Instant.now());
//        announcementRepository.save(a1);
//
//        AnnouncementEntity a2 = new AnnouncementEntity();
//        a2.setTitle("2Office to clean");
//        a2.setDescription("2Two story office in London");
//        a2.setCreatedOn(Instant.now());
//        a2.setUpdatedOn(Instant.now());
//        announcementRepository.save(a2);
//
//        AnnouncementEntity a3 = new AnnouncementEntity();
//        a3.setTitle("3Office to clean");
//        a3.setDescription("3Two story office in London");
//        a3.setCreatedOn(Instant.now());
//        a3.setUpdatedOn(Instant.now());
//        announcementRepository.save(a3);
//
//        AnnouncementEntity a4 = new AnnouncementEntity();
//        a4.setTitle("4Office to clean");
//        a4.setDescription("4Two story office in London");
//        a4.setCreatedOn(Instant.now());
//        a4.setUpdatedOn(Instant.now());
//        announcementRepository.save(a4);
//        //  }

        // if (userRepository.count() == 0){
        //admin

        RegionEntity regionHaskovo = new RegionEntity();
        regionHaskovo.setName("Haskovo");

        regionRepository.save(regionHaskovo);

        RegionEntity regionSofia = new RegionEntity();
        regionSofia.setName("Sofia");

        regionRepository.save(regionSofia);

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ROLE_ADMIN");

        roleService.saveRole(adminRole);

        RoleEntity cleanerRole = new RoleEntity();
        cleanerRole.setName("ROLE_CLEANER");

        roleService.saveRole(cleanerRole);

        RoleEntity clientRole = new RoleEntity();
        clientRole.setName("ROLE_CLIENT");

        roleService.saveRole(clientRole);

        //Admin
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@example.com");
        admin.setPasswordHash(passwordEncoder.encode("1234"));
        admin.setRoles(Set.of(adminRole));
        admin.setFirstName("Admin");
        admin.setLastName("Master");
        admin.setRegion(regionSofia);
        admin.setCleanerTasks(new ArrayList<>());
        admin.setClientTasks(new ArrayList<>());
        admin.setAnnouncements(new ArrayList<>());
        admin.setComments(new ArrayList<>());
        admin.setResponses(new ArrayList<>());

        userRepository.save(admin);

        //cleanerOne
        UserEntity cleaner = new UserEntity();
        cleaner.setEmail("cleaner@example.com");
        cleaner.setPasswordHash(passwordEncoder.encode("cleaner"));
        cleaner.setRoles(Set.of(cleanerRole));
        cleaner.setFirstName("Simeon");
        cleaner.setLastName("Atanasov");
        cleaner.setRegion(regionHaskovo);
        cleaner.setCleanerTasks(new ArrayList<>());
        cleaner.setClientTasks(new ArrayList<>());
        cleaner.setAnnouncements(new ArrayList<>());
        cleaner.setComments(new ArrayList<>());
        cleaner.setResponses(new ArrayList<>());

        userRepository.save(cleaner);

        //cleanerTwo
        UserEntity cleanerTwo = new UserEntity();
        cleanerTwo.setEmail("cleanerTwo@example.com");
        cleanerTwo.setPasswordHash(passwordEncoder.encode("cleaner"));
        cleanerTwo.setRoles(Set.of(cleanerRole));
        cleanerTwo.setFirstName("Rosencho");
        cleanerTwo.setLastName("Gumeniq");
        cleanerTwo.setRegion(regionSofia);
        cleanerTwo.setCleanerTasks(new ArrayList<>());
        cleanerTwo.setClientTasks(new ArrayList<>());
        cleanerTwo.setAnnouncements(new ArrayList<>());
        cleanerTwo.setComments(new ArrayList<>());
        cleanerTwo.setResponses(new ArrayList<>());

        userRepository.save(cleanerTwo);

        //ClientOne
        UserEntity clientOne = new UserEntity();
        clientOne.setEmail("clientOne@example.com");
        clientOne.setPasswordHash(passwordEncoder.encode("client"));
        clientOne.setRoles(Set.of(clientRole));
        clientOne.setFirstName("petar");
        clientOne.setLastName("Petrov");
        clientOne.setRegion(regionSofia);
        clientOne.setCleanerTasks(new ArrayList<>());
        clientOne.setClientTasks(new ArrayList<>());
        clientOne.setAnnouncements(new ArrayList<>());
        clientOne.setComments(new ArrayList<>());
        clientOne.setResponses(new ArrayList<>());

        userRepository.save(clientOne);

        //ClientTwo
        UserEntity clientTwo = new UserEntity();
        clientTwo.setEmail("clientTwo@example.com");
        clientTwo.setPasswordHash(passwordEncoder.encode("client"));
        clientTwo.setRoles(Set.of(clientRole));
        clientTwo.setFirstName("Ivanak");
        clientTwo.setLastName("Hristova");
        clientTwo.setRegion(regionHaskovo);
        clientTwo.setCleanerTasks(new ArrayList<>());
        clientTwo.setClientTasks(new ArrayList<>());
        clientTwo.setAnnouncements(new ArrayList<>());
        clientTwo.setComments(new ArrayList<>());
        clientTwo.setResponses(new ArrayList<>());

        userRepository.save(clientTwo);

        //Tasks
        TaskEntity task1 = new TaskEntity();
        task1.setName("Diunerite Orfej");
        task1.setClient(clientOne);
        task1.setRegion(regionHaskovo);

        taskRepository.save(task1);

        TaskEntity task2 = new TaskEntity();
        task2.setName("Na batko kashtata");
        task2.setClient(clientTwo);
        task2.setRegion(regionHaskovo);

        taskRepository.save(task2);

        TaskEntity task3 = new TaskEntity();
        task3.setName("Office Ros");
        task3.setClient(clientOne);
        task3.setRegion(regionHaskovo);

        taskRepository.save(task3);

        TaskEntity task4 = new TaskEntity();
        task4.setName("Kenana e za menetene brat");
        task4.setClient(clientTwo);
        task4.setRegion(regionSofia);

        taskRepository.save(task4);

        TaskEntity task5 = new TaskEntity();
        task5.setName("Val Mar");
        task5.setClient(clientOne);
        task5.setRegion(regionSofia);

        taskRepository.save(task5);

    }
}
