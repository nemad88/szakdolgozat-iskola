package hu.adam.nemeth.services.implementation.details;


import hu.adam.nemeth.model.common.Person;
import hu.adam.nemeth.services.CustomUserDetailsService;
import hu.adam.nemeth.services.StudentService;
import hu.adam.nemeth.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private StudentService studentService;
    private TeacherService teacherService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("User keresés erre: " + userName);

        Person user = studentService.findByUserName(userName);

        if (user == null) {
            user = teacherService.findByUserName(userName);
            if (user == null) {
                throw new UsernameNotFoundException(userName);
            }
        }

        return new UserDetailsImpl(user);
    }

}
