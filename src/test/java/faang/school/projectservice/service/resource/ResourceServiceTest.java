package faang.school.projectservice.service.resource;

import faang.school.projectservice.dto.project.ProjectDto;
import faang.school.projectservice.image.ImageResizer;
import faang.school.projectservice.jpa.ResourceRepository;
import faang.school.projectservice.mapper.ProjectMapper;
import faang.school.projectservice.model.Project;
import faang.school.projectservice.model.Resource;
import faang.school.projectservice.repository.ProjectRepository;
import faang.school.projectservice.service.s3.S3Service;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private S3Service s3Service;
    @Mock
    private ImageResizer imageResizer;
    @Spy
    private ProjectMapper projectMapper;
    @InjectMocks
    private ResourceService resourceService;

    private Resource resource;
    private Resource resource2;
    private Project project;
    private ProjectDto projectDto;
    private MultipartFile file;

    @SneakyThrows
    @Test
    void testAddCoverToTheProject() {
        project = Project.builder()
                .id(1L)
                .coverImageId("Image: " + 1L)
                .build();
        projectDto = ProjectDto.builder()
                .id(1L)
                .name("CorpX")
                .build();
        Long id = 1L;
        MultipartFile file1 = new MockMultipartFile(
                "file",
                "example.txt",
                "text/plain",
                "This is some example content".getBytes()
        );
        resource2 = Resource.builder()
                .id(2L)
                .build();
        File file = new File("/path/to/file/or/directory");
        String folder = projectDto.getId() + projectDto.getName();

        when(imageResizer.resizeAndCompressImage(file1)).thenReturn(file);
        when(projectRepository.getProjectById(1L)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDto);
        when(s3Service.uploadFile(file1, folder)).thenReturn(resource2);
        when(resourceRepository.save(any())).thenReturn(resource2);

        Resource resourse1 = resourceService.addACoverToTheProject(1L, file1);

        verify(imageResizer, times(1)).resizeAndCompressImage(file1);
        verify(projectRepository, times(1)).getProjectById(1L);
        verify(projectMapper, times(1)).toDto(project);
        verify(s3Service, times(1)).uploadFile(file1, folder);
        assertEquals(resource2.getId(), resourse1.getId(), "Returned resource should have the uploaded resource's ID");
        assertNotNull(resourse1, "Returned resource should not be null");

    }

    @Test
    void deleteResource() {
        resource2 = Resource.builder()
                .key("1")
                .build();

        resourceService.deleteResource(resource2.getKey());

        verify(s3Service, times(1)).deleteFile(anyString());
    }
}
