package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.LinkedList;
import java.util.List;

public class TopicService {
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

    protected List<Topic> getAll() {
        TopicDao dao = new TopicDao();
        return dao.getAll();
    }
}