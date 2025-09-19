package library.model;

public class Admin {
    private int id;
    private String username;
    private String adminId;

    // admin constructor
    public Admin( String username , String adminId){
        this.username = username;
        this.adminId = adminId;
    }

    public Admin(int id, String username , String adminId){
        this.id = id;
        this.username = username;
        this.adminId = adminId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getAdminUsername(){
        return username;
    }

    public void setAdminUsername(String username){
        this.username = username;
    }

    public String getAdminId(){
        return adminId;
    }

    public void setAdminId(String adminId){
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Admin{id= " + id + ", username='" + username + "', adminId='" + adminId + "'}";
    }
}
