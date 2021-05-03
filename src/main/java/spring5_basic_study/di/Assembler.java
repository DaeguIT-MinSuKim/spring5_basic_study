package spring5_basic_study.di;

public class Assembler {
	private MemberDao memberDao;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;

	/**
	 * MemberRegisterService 객체와 ChangePasswordService 객체에 대한 의존을 주입 -
	 * MemberRegisterService는 생성자를 통해 MemberDao 객체를 주입받음 - ChangePasswordService는
	 * setter를 통해 MemberDao 객체를 주입받음
	 */
	public Assembler() {
		memberDao = new MemberDao();
		regSvc = new MemberRegisterService(memberDao);
		pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao);
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public MemberRegisterService getMemberRegisterService() {
		return regSvc;
	}

	public ChangePasswordService getChangePasswordService() {
		return pwdSvc;
	}

}
