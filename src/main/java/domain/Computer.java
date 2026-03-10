package domain;

/**
 * Класс данных о компьютерах
 */
public class Computer {
    private Long id;
    private String computerName;
    private String description;
    private Long statusId;
    private ComputerStatus status;

    public Computer() {}

    public Computer(String computerName, String description, ComputerStatus status) {
        this.computerName = computerName;
        this.description = description;
        this.status = status;
    }

    public Computer(String computerName, String description, Long statusId, ComputerStatus status) {
        this.computerName = computerName;
        this.description = description;
        this.statusId = statusId;
        this.status = status;
    }

    public Computer(Long id, String computerName, String description, Long statusId, ComputerStatus status) {
        this.id = id;
        this.computerName = computerName;
        this.description = description;
        this.statusId = statusId;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComputerName() { return computerName; }
    public void setComputerName(String computerName) { this.computerName = computerName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getStatusId() { return statusId; }
    public void setStatusId(Long statusId) { this.statusId = statusId; }

    public ComputerStatus getStatus() { return status; }
    public void setStatus(ComputerStatus status) { this.status = status; }

    public String getStatusName() {
        return status != null ? status.getStatusName() : null;
    }

    @Override
    public String toString() {
        return "Computer {" +
                "id=" + id +
                ", computerName='" + computerName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + (status != null ? status.getStatusName() : "null") +
                '}';
    }
}