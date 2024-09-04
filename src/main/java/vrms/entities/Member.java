package vrms.entities;

import java.io.Serializable;

public class Member implements Serializable {
    private String memberName;
    private String memberId;
    private String memberPhone;
    private String memberEmail;
    private boolean isSpecialMember;

    public Member(String memberName, String memberId, String memberEmail, String memberPhone, boolean isSpecialMember) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.memberPhone = memberPhone;
        this.memberEmail = memberEmail;
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

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public boolean isSpecialMember() {
        return isSpecialMember;
    }

    public void setSpecialMember(boolean specialMember) {
        isSpecialMember = specialMember;
    }

    @Override
    public String toString() {
        return "Name = '" + memberName + '\'' +
                ", ID = '" + memberId + '\'' +
                ", Phone Number = '" + memberPhone + '\'' +
                ", Email = '" + memberEmail + '\'' +
                ", Premium(true) or Regular(False) = " + isSpecialMember;
    }
}