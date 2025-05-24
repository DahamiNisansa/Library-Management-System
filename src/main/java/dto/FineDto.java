package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString


public class FineDto {

    private int fine_id;
    private String borrow_record_id;
    private BigDecimal fineAmount;
    private LocalDate paymentDate;

}
