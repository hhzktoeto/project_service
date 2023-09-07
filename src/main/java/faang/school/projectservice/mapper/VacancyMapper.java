package faang.school.projectservice.mapper;

import faang.school.projectservice.dto.project.VacancyDto;
import faang.school.projectservice.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VacancyMapper {
    VacancyDto toDto(Vacancy vacancy);

    Vacancy toEntity(VacancyDto vacancy);
}
