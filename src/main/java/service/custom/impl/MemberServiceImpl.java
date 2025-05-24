package service.custom.impl;

import Repository.RepoFactory;
import Repository.custom.MemberRepo;
import dto.MemberDto;
import exceptions.MemberExceptions;
import module.Member;
import service.custom.MemberService;
import util.RepoType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    MemberRepo memRepo = RepoFactory.getInstance().getRepoType(RepoType.MEMBER);

    @Override
    public boolean addMember(MemberDto member) throws SQLException {
        return memRepo.save(convertToEntity(member));
    }


    @Override
    public boolean updateMember(MemberDto member) throws SQLException {
        return memRepo.update(convertToEntity(member));
    }


    @Override
    public boolean deleteMember(String uid) throws SQLException, MemberExceptions {
        return memRepo.delete(uid);
    }


    @Override
    public Optional<MemberDto> searchMember(String memId) {
        try{
            Optional<Member> member = memRepo.search(memId);

            if (member.isPresent()){
                MemberDto memDto = convertToDto(member.get());
                return Optional.of(memDto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }



    @Override
    public List<MemberDto> getAll() {
        List<Member> members = memRepo.getAll();
        return members.stream()
                .map(member -> new MemberDto(
                        member.getMemId(),
                        member.getMemName(),
                        member.getAddress(),
                        member.getContactNo(),
                        member.getMembershipDate()
                ))
                .collect(Collectors.toList());
    }




    @Override
    public MemberDto searchMemberById(String memberId) throws SQLException {
        Optional<Member> memberEntity = memRepo.search(memberId);
        if (memberEntity.isEmpty()) {
            return null;
        }
        Member member = memberEntity.get(); // unwrap Optional
        return new MemberDto(member.getMemId(),member.getMemName(),member.getAddress(),member.getContactNo(),member.getMembershipDate());
    }




    // Convert MemberDTO to MemberEntity
    private Member convertToEntity(MemberDto memberDto) {
        Member member = new Member();
        member.setMemId(memberDto.getMemId());
        member.setMemName(memberDto.getMemName());
        member.setAddress(memberDto.getAddress());
        member.setContactNo(memberDto.getContactNo());
        member.setMembershipDate(memberDto.getMembershipDate());

        return member;
    }

    private MemberDto convertToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemId(member.getMemId());
        memberDto.setMemName(member.getMemName());
        memberDto.setAddress(member.getAddress());
        memberDto.setContactNo(member.getContactNo());
        memberDto.setMembershipDate(member.getMembershipDate());

        return memberDto;
    }
}
