package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TopicService extends BaseMediaService {
    /**
     * Get all Topics.
     *
     * @return List of all Topics
     */
    public List<TopicDto> getAllTopics() {
        List<TopicDto> topicDtos = new LinkedList<>();

        getAll().forEach(topic -> {
            TopicDto.TopicDtoBuilder builder = new TopicDto.TopicDtoBuilder();
            builder.id(topic.getId())
                .name(topic.getName());

            topicDtos.add(builder.build());
        });

        return topicDtos;
    }

    /**
     * Find a Topic by its ID.
     *
     * @param topicDto wiht ID to find
     * @return TopicDto with all Parameters
     */
    public Optional<TopicDto> findTopicById(TopicDto topicDto) {
        Optional<Topic> foundTopic = findTopicById(topicDto.getId());
        if (foundTopic.isPresent()) {
            Topic topic = foundTopic.get();
            TopicDto foundTopicDto = new TopicDto.TopicDtoBuilder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
            return Optional.of(foundTopicDto);
        }
        return Optional.empty();
    }

    protected List<Topic> getAll() {
        TopicDao dao = new TopicDao();
        return dao.getAll();
    }
}