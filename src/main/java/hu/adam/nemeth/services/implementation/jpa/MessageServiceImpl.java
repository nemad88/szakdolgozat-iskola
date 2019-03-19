package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.MessageRepository;
import hu.adam.nemeth.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Set<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        messageRepository.findAll().forEach(messages::add);
        return messages;
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

    public Set<Message> findAllByTeacher(Teacher teacher){
        return messageRepository.findAllByTeacher(teacher);
    }

    @Override
    public Set<Message> findAllByStudent(Student student) {
        return messageRepository.findAllByStudent(student);
    }
}
