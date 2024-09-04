package VRMS.service;

import VRMS.entities.Member;
import VRMS.exceptions.DuplicateMemberException;
import VRMS.exceptions.MemberNotFoundException;

import java.util.List;

public interface MemberInterface
{
    // CRUD operations for members
    void addMember(Member member) throws DuplicateMemberException;

    void removeMember(Member member) throws MemberNotFoundException;

    List<Member> getMembers();

    List<Member> filterMembers(String memberType);
}
