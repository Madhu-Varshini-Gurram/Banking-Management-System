package bank.model;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String email;


    public Customer(String customerId,String name,String phone,String email){
        this.customerId=customerId;
        this.name=name;
        this.phone=phone;
        this.email=email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public static boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email != null && email.matches(emailPattern);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[6-9][0-9]{9}$");
    }

    public static boolean isValidCustomerId(String customerId) {
        return customerId != null &&  customerId.matches("^C[0-9]{3,6}$");
    }

    @Override
    public String toString() {
        return "[Customer Id: "+customerId +" name: "+name +" phone: "+phone +" email: "+email + "]";
    }
}
