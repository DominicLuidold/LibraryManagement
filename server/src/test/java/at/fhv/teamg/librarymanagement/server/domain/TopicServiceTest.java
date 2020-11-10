package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TopicServiceTest {
    @Test
    void getAllTopics_shouldReturnFilledList() {
        Topic topicMock = mock(Topic.class);
        List<Topic> topics = new LinkedList<>();
        topics.add(topicMock);

        TopicService topicService = spy(TopicService.class);
        doReturn(topics).when(topicService).getAll();

        assertFalse(topicService.getAllTopics().isEmpty());
    }
}
