package hu.adam.nemeth.bootstrap;

import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.services.SubjectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final SubjectService subjectService;

    public DataLoader(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = subjectService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {

        Subject subject01 = new Subject("Matematika");
        Subject savedSubject01 = subjectService.save(subject01);
        Subject subject02 = new Subject("Testnevelés");
        Subject savedSubject02 = subjectService.save(subject02);
        Subject subject03 = new Subject("Irodalom");
        Subject savedSubject03 = subjectService.save(subject03);
        Subject subject04 = new Subject("Földrajz");
        Subject savedSubject04 = subjectService.save(subject04);
        Subject subject05 = new Subject("Informatika");
        Subject savedSubject05 = subjectService.save(subject05);
        Subject subject06 = new Subject("Ének");
        Subject savedSubject06 = subjectService.save(subject06);
        Subject subject07 = new Subject("Rajz");
        Subject savedSubject07 = subjectService.save(subject07);



        //TEST
        Subject findedById = subjectService.findById(2L);
        System.out.println("Az id altal talat tantargy" + findedById.getDescription());

        subjectService.deleteById(5L);

        Set<Subject> subjects = subjectService.findAll();

        System.out.println("A maradek:");
        for (Subject subject : subjects) {
            System.out.println(subject.getDescription());
        }


    }
}
