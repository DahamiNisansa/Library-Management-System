package module;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Member {
    private String memId;
    private String memName;
    private String address;
    private String contactNo;
    private String membershipDate;


}
