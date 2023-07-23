package faang.school.projectservice.service.internship;

import faang.school.projectservice.exception.DataValidationException;
import faang.school.projectservice.model.Internship;
import faang.school.projectservice.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class InternshipService {
    private final InternshipRepository internshipRepository;

    public Internship createInternship(Internship internship) {
        createInternshipValidation(internship);
        return internshipRepository.save(internship);
    }

    private void createInternshipValidation(Internship internship) {
        if (internship.getInterns() == null) {
            throw new DataValidationException("No interns!");
        }
        if (internship.getEndDate().isAfter(internship.getStartDate().plus(3, ChronoUnit.MONTHS))) {
            throw new DataValidationException("Internship's duration is too long!");
        }
        if (internship.getMentorId() == null) {
            throw new DataValidationException("Internship has no mentor!");
        }
    }
}
