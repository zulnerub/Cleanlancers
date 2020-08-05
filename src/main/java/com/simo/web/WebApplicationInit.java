package com.simo.web;


import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.announcement.repository.AnnouncementRepository;
import com.simo.web.comment.service.CommentServiceImpl;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.repository.RegionRepository;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.repository.TaskRepository;
import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class WebApplicationInit implements CommandLineRunner {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final TaskRepository taskRepository;
    private final CommentServiceImpl commentService;

    public WebApplicationInit(AnnouncementRepository announcementRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RegionRepository regionRepository, TaskRepository taskRepository, CommentServiceImpl commentService) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.regionRepository = regionRepository;
        this.taskRepository = taskRepository;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {

            init();




    }

    private void init() {
        // if (announcementRepository.count() == 0){
        AnnouncementEntity a1 = new AnnouncementEntity();
        a1.setTitle("Office to clean");
        a1.setDescription("Two story office in London");
        a1.setCreatedOn(Instant.now());
        a1.setUpdatedOn(Instant.now());
        announcementRepository.save(a1);

        AnnouncementEntity a2 = new AnnouncementEntity();
        a2.setTitle("2Office to clean");
        a2.setDescription("2Two story office in London");
        a2.setCreatedOn(Instant.now());
        a2.setUpdatedOn(Instant.now());
        announcementRepository.save(a2);

        AnnouncementEntity a3 = new AnnouncementEntity();
        a3.setTitle("3Office to clean");
        a3.setDescription("3Two story office in London");
        a3.setCreatedOn(Instant.now());
        a3.setUpdatedOn(Instant.now());
        announcementRepository.save(a3);

        AnnouncementEntity a4 = new AnnouncementEntity();
        a4.setTitle("4Office to clean");
        a4.setDescription("4Two story office in London");
        a4.setCreatedOn(Instant.now());
        a4.setUpdatedOn(Instant.now());
        announcementRepository.save(a4);
        //  }

        // if (userRepository.count() == 0){
        //admin
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@example.com");
        admin.setPasswordHash(passwordEncoder.encode("1234"));

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ROLE_ADMIN");

        admin.setRoles(List.of(adminRole));
        admin.setFirstName("Admin");
        admin.setLastName("Master");

        userRepository.save(admin);

        //user
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");
        user.setPasswordHash(passwordEncoder.encode("5678"));

        RoleEntity userRole = new RoleEntity();
        userRole.setName("ROLE_CLIENT");

        user.setRoles(List.of(userRole));
        user.setFirstName("Simeon");
        user.setLastName("Atanasov");

        userRepository.save(user);
        //  }

        //  if (this.regionRepository.count() == 0){

        RegionEntity regionHaskovo = new RegionEntity();
        regionHaskovo.setName("Haskovo");

        regionRepository.save(regionHaskovo);

        RegionEntity regionSofia = new RegionEntity();
        regionSofia.setName("Sofia");

        regionRepository.save(regionSofia);

        TaskEntity task1 = new TaskEntity();
        task1.setName("Diunerite Orfej");
        task1.setClient(user);
        task1.setRegion(regionHaskovo);

        taskRepository.save(task1);

        TaskEntity task2 = new TaskEntity();
        task2.setName("Na batko kashtata");
        task2.setClient(admin);
        task2.setRegion(regionHaskovo);

        taskRepository.save(task2);

        TaskEntity task3 = new TaskEntity();
        task3.setName("Office Ros");
        task3.setClient(user);
        task3.setRegion(regionHaskovo);

        taskRepository.save(task3);

        TaskEntity task4 = new TaskEntity();
        task4.setName("Kenana e za menetene brat");
        task4.setClient(admin);
        task4.setRegion(regionSofia);

        taskRepository.save(task4);

        TaskEntity task5 = new TaskEntity();
        task5.setName("Val Mar");
        task5.setClient(user);
        task5.setRegion(regionSofia);

        taskRepository.save(task5);

//        CommentEntity commentEntity1 = new CommentEntity();
//        commentEntity1.setAuthor(user);
//        commentEntity1.setCreatedOn(Instant.now());
//        commentEntity1.setDescription("Random komment bl bla bla sdadasdasdasdasda");
////        commentEntity1.setResponses(new ArrayList<>());
//        commentEntity1.setTask(task1);
//
//        commentService.save(commentEntity1);

//        CommentEntity commentEntity2 = new CommentEntity();
//        commentEntity2.setAuthor(admin);
//        commentEntity2.setCreatedOn(Instant.now());
//        commentEntity2.setDescription("Ranasdasddom komasdasdasdment bl bla bla sdadasdasdasdasda");
////        commentEntity2.setResponses(new ArrayList<>());
//        commentEntity2.setTask(task2);
//
//
////        commentEntity2.getResponses().add(commentEntity1);
//
//        commentService.save(commentEntity2);
    }
}
