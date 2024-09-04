package vrms.service;

import vrms.entities.Member;
import vrms.exceptions.DuplicateMemberException;
import vrms.exceptions.MemberNotFoundException;

import java.util.List;

public interface MemberInterface
{
    // CRUD operations for members
    void addMember(Member member) throws DuplicateMemberException;

    void removeMember(String MemberId) throws MemberNotFoundException;

    void updateMember(String memberId, Member updatedMember) throws MemberNotFoundException;

    List<Member> getMembers();

    List<Member> filterMembers(String memberType) throws MemberNotFoundException;
}
