package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class TopicServiceTest {
    private static final UUID validTopicId = UUID.randomUUID();
    private static final UUID notValidTopicId = UUID.randomUUID();

    @Test
    void getAllTopics_shouldReturnFilledList() {
        Topic topicMock = mock(Topic.class);
        List<Topic> topics = new LinkedList<>();
        topics.add(topicMock);

        TopicService topicService = spy(TopicService.class);
        doReturn(topics).when(topicService).getAll();

        assertFalse(topicService.getAllTopics().isEmpty());
    }

    @Test
    void findTopicById_shouldReturnDto() {
        TopicDto.TopicDtoBuilder builder = new TopicDto.TopicDtoBuilder();
        builder.id(validTopicId);

        TopicService topicService = spy(TopicService.class);
        doReturn(Optional.of(mock(Topic.class))).when(topicService).findTopicById(validTopicId);

        assertTrue(topicService.findTopicById(builder.build()).isPresent());
    }

    @Test
    void findTopicById_shouldReturnEmpty() {
        TopicDto.TopicDtoBuilder builder = new TopicDto.TopicDtoBuilder();
        builder.id(notValidTopicId);

        TopicService topicService = spy(TopicService.class);
        doReturn(Optional.empty()).when(topicService).findTopicById(notValidTopicId);

        assertFalse(topicService.findTopicById(builder.build()).isPresent());
    }
}
