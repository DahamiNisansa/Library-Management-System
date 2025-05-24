package module;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private String id;
    private String title;
    private String isbn;
    private String Category;
    private String availabilityStatus;


}
