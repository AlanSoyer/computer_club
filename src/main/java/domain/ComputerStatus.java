package domain;

/**
 * Класс-справочник статусов компьютеров
 */
public class ComputerStatus {
    private Long id;
    private String statusName;

    public ComputerStatus() {}

    public ComputerStatus(String statusName) {
        this.statusName = statusName;
    }

    public ComputerStatus(Long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }

    @Override
    public String toString() {
        return "ComputerStatus {" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}