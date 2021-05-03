package spring5_basic_study.di;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MemberDao {
	private static long nextId = 0;
	private Map<String, Member> map = new HashMap<>();

	public Member selectByEmail(String email) {
		return map.get(email);
	}

	public void insert(Member member) {
		member.setId(++nextId);
		map.put(member.getEmail(), member);
	}

	public void update(Member member) {
		map.put(member.getEmail(), member);
	}
	
	public void showList() {
		for(Entry<String, Member> e : map.entrySet()) {
			System.out.printf("%s -> %s%n", e.getKey(), e.getValue());
		}
	}

}
