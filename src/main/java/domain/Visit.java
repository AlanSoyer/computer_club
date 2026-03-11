package domain;

import java.time.LocalDate;

/**
 * Класс данных о посещениях компьютерного клуба
 */
public class Visit {
    private Long id;
    private Long visitorId;
    private Long computerId;
    private Visitor visitor;
    private Computer computer;
    private LocalDate visitDate;
    private Integer duration;
    private Double payment;

    public Visit() {}

    public Visit(Visitor visitor, Computer computer, LocalDate visitDate,
                 Integer duration, Double payment) {
        this.visitor = visitor;
        this.computer = computer;
        this.visitDate = visitDate;
        this.duration = duration;
        this.payment = payment;
    }

    public Visit(Long id, Visitor visitor, Computer computer, LocalDate visitDate,
                 Integer duration, Double payment) {
        this.id = id;
        this.visitor = visitor;
        this.computer = computer;
        this.visitDate = visitDate;
        this.duration = duration;
        this.payment = payment;
    }

    public Visit(Long id, Long visitorId, Long computerId, LocalDate visitDate,
                 Integer duration, Double payment) {
        this.id = id;
        this.visitorId = visitorId;
        this.computerId = computerId;
        this.visitDate = visitDate;
        this.duration = duration;
        this.payment = payment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVisitorId() { return visitorId; }
    public void setVisitorId(Long visitorId) { this.visitorId = visitorId; }

    public Long getComputerId() { return computerId; }
    public void setComputerId(Long computerId) { this.computerId = computerId; }

    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }

    public Computer getComputer() { return computer; }
    public void setComputer(Computer computer) { this.computer = computer; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Double getPayment() { return payment; }
    public void setPayment(Double payment) { this.payment = payment; }

    public String getFormattedDuration() {
        if (duration == null) return "—";
        int hours = duration / 60;
        int minutes = duration % 60;
        return hours + " ч " + minutes + " мин";
    }

    @Override
    public String toString() {
        return "Visit {" +
                "id=" + id +
                ", visitor=" + (visitor != null ? visitor.getFullName() : "null") +
                ", computer=" + (computer != null ? computer.getComputerName() : "null") +
                ", date=" + visitDate +
                ", duration=" + getFormattedDuration() +
                ", payment=" + payment +
                '}';
    }
}