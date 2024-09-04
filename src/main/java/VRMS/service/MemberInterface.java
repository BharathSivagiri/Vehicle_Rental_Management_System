package VRMS.service;

import VRMS.entities.Member;
import VRMS.exceptions.DuplicateMemberException;
import VRMS.exceptions.MemberNotFoundException;

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
