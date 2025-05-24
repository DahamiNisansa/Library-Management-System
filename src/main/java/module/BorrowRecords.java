package module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor

public class BorrowRecords {
    private String borrowId;
    private String book_id;
    private String member_id;
    private LocalDate borrowed_date;
    private LocalDate due_date;
    private LocalDate return_date;
    private boolean isReturned;


}
