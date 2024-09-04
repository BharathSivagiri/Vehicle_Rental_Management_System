package VRMS.entities;

import java.io.Serializable;

public class Member implements Serializable {
    private String memberName;
    private String memberId;
    private String email;
    private String phoneNumber;
    private boolean isSpecialMember;

    public Member(String memberName, String memberId, String email, String phoneNumber, boolean isSpecialMember) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isSpecialMember = isSpecialMember;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSpecialMember() {
        return isSpecialMember;
    }

    public void setSpecialMember(boolean specialMember) {
        isSpecialMember = specialMember;
    }

    @Override
    public String toString() {
        return "Member List [" +
                "memberName='" + memberName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isSpecialMember=" + isSpecialMember +
                ']';
    }
}