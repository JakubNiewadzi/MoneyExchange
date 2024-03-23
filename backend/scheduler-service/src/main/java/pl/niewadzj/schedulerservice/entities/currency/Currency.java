package pl.niewadzj.schedulerservice.entities.currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private LocalDateTime rateDate;
    private Float exchangeRate;

}
