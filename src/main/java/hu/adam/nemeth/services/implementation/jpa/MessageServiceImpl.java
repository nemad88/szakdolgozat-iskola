package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.MessageRepository;
import hu.adam.nemeth.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Long aLong) {
        return messageRepository.findById(aLong).orElse(null);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public void deleteById(Long aLong) {
        messageRepository.deleteById(aLong);
    }

    public List<Message> findAllByTeacher(Teacher teacher) {
        return messageRepository.findAllByTeacher(teacher);
    }

    @Override
    public List<Message> findAllByStudent(Student student) {
        return messageRepository.findAllByStudent(student).stream()
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public List<Message> filterByStartDate(List<Message> messages, LocalDateTime startDate) {
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getDate().isAfter(startDate) || message.getDate().equals(startDate))
                .collect(Collectors.toList());
        return filteredMessages;
    }


    @Override
    public List<Message> filterByEndDate(List<Message> messages, LocalDateTime endDate) {
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getDate().isBefore(endDate) || message.getDate().equals(endDate))
                .collect(Collectors.toList());
        return filteredMessages;
    }

    @Override
    public List<Message> filterByTeacherId(List<Message> messages, Long teacherId) {
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getTeacher().getId().equals(teacherId))
                .collect(Collectors.toList());
        return filteredMessages;
    }

}
