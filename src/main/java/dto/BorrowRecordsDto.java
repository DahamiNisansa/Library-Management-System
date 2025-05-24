package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class BorrowRecordsDto {
    private String borrowId;
    private String memberId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dateGiven;
    private boolean isReturned;

}
