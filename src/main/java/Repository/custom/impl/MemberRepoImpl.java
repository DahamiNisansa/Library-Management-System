package Repository.custom.impl;

import Repository.custom.MemberRepo;
import db.DBConnection;
import dto.MemberDto;
import module.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepoImpl implements MemberRepo {
    @Override
    public boolean save(Member entity) {
        String sql = "INSERT INTO member (memId, memName, address, contactNo, membership_Date) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Setting parameter values from entity object
            pstmt.setString(1, entity.getMemId());
            pstmt.setString(2, entity.getMemName());
            pstmt.setString(3, entity.getAddress());
            pstmt.setString(4, entity.getContactNo());
            pstmt.setString(5, entity.getMembershipDate());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

            // Execute update query
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }



    @Override
    public boolean update(Member memEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE member SET memName = ? , address = ? , contactNo = ? , membership_Date = ? WHERE memId = ?");

        ps.setString(1, memEntity.getMemName());
        ps.setString(2, memEntity.getAddress());
        ps.setString(3, memEntity.getContactNo());
        ps.setString(4, memEntity.getMembershipDate());
        ps.setString(5, memEntity.getMemId());

        return ps.executeUpdate()>0;
    }



    @Override
    public Optional<Member> search(String memberId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM member WHERE memId = ?");
        ps.setString(1,memberId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String contactNo = rs.getString(4);
            String membershipDate = rs.getString(5);
            Member mem = new Member(id,name,address,contactNo,membershipDate);
            return Optional.of(mem);
        }
        return Optional.empty();
    }



    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM member WHERE memId = ?");

        ps.setString(1, id);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    }



    @Override
    public List<Member> getAll() {
        ArrayList<Member> memArrayList = new ArrayList<>();

        try {
            //create Db connection and load data from DB
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM member");

            //set loaded data to user object
            while (resultSet.next()){
                Member member = new Member(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                //add user object into arraylist
                memArrayList.add(member);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return memArrayList;
    }



}
