package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.LinkedList;
import java.util.List;

public class TopicService extends BaseMediaService {
    /**
     * Get all Topics.
     *
     * @return List of all Topics
     */
    public List<TopicDto> getAllTopics() {
        List<TopicDto> topicDtoList = new LinkedList<>();

        getAll().forEach(topic -> {
            TopicDto.TopicDtoBuilder builder = new TopicDto.TopicDtoBuilder();
            builder.id(topic.getId())
                .name(topic.getName());

            topicDtoList.add(builder.build());
        });

        return topicDtoList;
    }

    protected List<Topic> getAll() {
        TopicDao dao = new TopicDao();
        return dao.getAll();
    }
}