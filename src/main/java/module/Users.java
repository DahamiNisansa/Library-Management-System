package module;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Users {
    private int uid;
    private String uName;
    private String contactNo;
    private String address;
    private String membershipDate;

    //public Users(String uid, String uName, String contactNo, String address, String membershipDate) {
    //}
}
