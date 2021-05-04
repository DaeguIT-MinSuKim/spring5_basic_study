package spring5_basic_study.di;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForAssembler {
	private static Assembler assembler = new Assembler();

	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("명령어를 입력하세요:");
				String command = br.readLine();
				if (command.equalsIgnoreCase("exit")) {
					System.out.println("종료합니다.");
					break;
				}
				if (command.startsWith("new ")) {
					processNewCommand(command.split(" "));
					continue;
				}
				if (command.startsWith("change ")) {
					processChangeCommand(command.split(" "));
					continue;
				}
				if (command.startsWith("list")) {
					processListCommand(command.split(" "));
					continue;
				}
				printHelp();
			}
		}
	}

	private static void processListCommand(String[] arg) {
		if (arg.length != 1) {
			printHelp();
			return;
		}
		MemberDao dao = assembler.getMemberDao();
		dao.showList();
	}

	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = assembler.getMemberRegisterService();
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일지하지 않습니다.\n");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("등록했습니다.");
		} catch (DuplicateMemberException e) {
			System.err.println("이미 존재하는 이메일입니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService changePwdSvc = assembler.getChangePasswordService();
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.err.println("존재하지 않는 이메일입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.err.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}

	private static void printHelp() {
		System.out.println("--------------------------------------");
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 암호 암호확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println("list");
		System.out.println("--------------------------------------");
	}

}
