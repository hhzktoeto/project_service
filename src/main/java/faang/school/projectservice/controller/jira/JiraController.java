package faang.school.projectservice.controller.jira;

import faang.school.projectservice.dto.jira.IssueDto;
import faang.school.projectservice.service.jira.JiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/jira")
public class JiraController {
    private final JiraService jiraService;

    @PostMapping("/issue")
    public String createIssue(@Valid @RequestBody IssueDto issueDto) {
        return jiraService.createIssue(issueDto);
    }

    @GetMapping("/issue/{issueKey}") // В Jira ключ задачи прямо в URL передаётся (прим. BJS2-3770)
    public IssueDto getIssue(@PathVariable String issueKey) {
        return jiraService.getIssue(issueKey);
    }

    @GetMapping("/project/{projectKey}/issues")
    public List<IssueDto> getAllIssues(@PathVariable String projectKey) {
        return jiraService.getAllIssues(projectKey);
    }

    @GetMapping("/project/{projectKey}/issues/status")
    public List<IssueDto> getIssuesByStatusId(@PathVariable String projectKey, @RequestParam long statusId) {
        return jiraService.getIssuesByStatusId(projectKey, statusId);
    }

    @GetMapping("/project/{projectKey}/issues/assignee")
    public List<IssueDto> getIssuesByAssigneeId(@PathVariable String projectKey, @RequestParam String assigneeId) {
        return jiraService.getIssuesByAssigneeId(projectKey, assigneeId);
    }
}
