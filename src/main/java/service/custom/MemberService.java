package service.custom;

import dto.MemberDto;
import exceptions.MemberExceptions;
import module.Member;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberService extends SuperService {
    boolean addMember (MemberDto member) throws SQLException;
    boolean updateMember (MemberDto member) throws SQLException;
    boolean deleteMember (String mId) throws SQLException, MemberExceptions;
    Optional<MemberDto> searchMember (String mName);
    List<MemberDto> getAll();
    MemberDto searchMemberById(String memberId) throws SQLException;
}
