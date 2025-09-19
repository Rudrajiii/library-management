package library.source;

import library.model.Admin;

public class AdminData {
    private Admin[] admins = {
        new Admin("hacker","1234"),
        new Admin("dev" , "12345")
    };

    public Admin[] get_admin_data(){
        return admins;
    }   
}
