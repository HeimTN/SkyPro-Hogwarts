package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.awt.print.Pageable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private StudentRepository studentRepository;
    private AvatarRepository avatarRepository;
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository){
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException{
        logger.debug("Method uploadAvatar: Find student by id: {}", studentId);
        Student student = studentRepository.findById(studentId).get();
        logger.debug("Method uploadAvatar: Create filePath");
        Path filePath = Path.of(avatarsDir, student + "."+ getExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        logger.debug("Method uploadAvatar: Start upload avatar");
        try(
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ){
            bis.transferTo(bos);
        }
        catch (IOException e){
            logger.error("Method uploadAvatar: Avatar not upload");
            logger.error(e.toString());
        }
        finally {
            logger.debug("Method uploadAvatar: Finished upload avatar");
        }
        logger.debug("Method uploadAvatar: Start create model Avatar and save to DB");
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        logger.info("Avatar is upload and save in DB");
    }
    public Avatar findAvatar(Long studentId){
        logger.info("Find Avatar by student id: {}", studentId);
        return avatarRepository.findByStudentId(studentId);
    }
    private String getExtension(String fileName){
        logger.debug("Method getExtension: Find extension by {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> findAllAvatarPag(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        logger.info("Find all Avatar by page request: {}", pageRequest);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
