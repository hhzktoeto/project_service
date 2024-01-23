package faang.school.projectservice.controller;

import faang.school.projectservice.dto.ProjectDto;
import faang.school.projectservice.dto.ProjectFilterDto;
import faang.school.projectservice.dto.ProjectUpDateDto;
import faang.school.projectservice.serviсe.ProjectService;
import faang.school.projectservice.validator.ProjectValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectValidator projectValidator;


    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "Create new project")
    //^ для возможности вручную добавлять id пользователя в сваггере и добавления названия эндпоинтов там же
    @PostMapping("/projects") // не идемпотентный, всегда создает новую сущность
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        projectValidator.validateCreateProject(projectDto);
        return projectService.createProject(projectDto);
    }

    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "update project")
    @PutMapping("/projects/{id}")
    // идемпотентный andPoint - сколько бы раз не вызывали с одинаковыми параметрами-результат один и тот же
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectUpDateDto projectDto) {
        projectValidator.validateUpdateProject(id, projectDto);
        return projectService.updateProject(id, projectDto);

    }

    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "Get all projects with filters")
    @PutMapping("/projects/filter")
    public List<ProjectDto> getAllProjectsWithFilter(@RequestBody ProjectFilterDto projectFilterDto) {
        projectValidator.validateFilter(projectFilterDto);
        return projectService.getAllProjectsWithFilter(projectFilterDto);
    }

    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "Get all projects")
    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "Get project by id")
    @GetMapping("/projects/{id}") // всегда идемпотентный andPoint
    public ProjectDto getProjectById(@PathVariable Long id) {
        projectValidator.validateProjectId(id);
        return projectService.getProjectById(id);
    }

    @Operation(parameters = {@Parameter(in = ParameterIn.HEADER, name = "x-user-id", required = true)}, summary = "Delete project by id")
    @DeleteMapping("/projects/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectValidator.validateProjectId(id);
        projectService.deleteProjectById(id);
    }
    // ^^ по заданию этого не было, но, кажется, должна быть такая возможность для владельца проекта
}
