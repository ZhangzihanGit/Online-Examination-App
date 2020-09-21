package domain;

import java.util.List;

public class Admin extends User {
    public Admin() {

    }
    public Admin(int id, String name, UserType userType, List<Subject> subjects) {
        super(id,name,userType,subjects);
    }
}
