package domain;

/**
 * Класс данных о посетителях
 */
public class Visitor {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String identityDocument;
    private String address;
    private String phone;

    public Visitor() {}

    public Visitor(String firstName, String lastName, String patronymic,
                   String identityDocument, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.identityDocument = identityDocument;
        this.address = address;
        this.phone = phone;
    }

    public Visitor(Long id, String firstName, String lastName, String patronymic,
                   String identityDocument, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.identityDocument = identityDocument;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    public String getIdentityDocument() { return identityDocument; }
    public void setIdentityDocument(String identityDocument) { this.identityDocument = identityDocument; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFullName() {
        return lastName + " " + firstName + (patronymic != null ? " " + patronymic : "");
    }

    @Override
    public String toString() {
        return "Visitor {" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + phone + '\'' +
                ", identityDocument='" + identityDocument + '\'' +
                '}';
    }
}