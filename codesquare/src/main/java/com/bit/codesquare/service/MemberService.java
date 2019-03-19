package com.bit.codesquare.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.dto.member.MessageInfo;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.mapper.member.MessageInfoMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Service
public class MemberService {

	@Autowired
	MemberMapper mm;

	@Autowired
	JavaMailSender mailsender;

	@Autowired
	MessageInfoMapper mim;

	@Autowired
	CodesquareUtil csu;
	
	public List<Board> getWantedList(String userId, @Param("cri") Criteria cri) {

		List<Board> list = mm.getWantedList(userId, cri);
		for (Board item : list) {
			item.setWriteDateBoard(csu.compareDateTime(item.getWriteDate()));
			BasicJsonParser jsonParser = new BasicJsonParser();
			List<JoiningAndRecruitmentLog> wantedList = mm.getWantedPList(item.getId());
			for (JoiningAndRecruitmentLog item2 : wantedList) {
				item2.setApplyMap(jsonParser.parseMap(item2.getApplyContent()));
				item2.setApplyDateString(csu.compareDateTime(item2.getApplyDate()));
			}
			item.setWantedPlist(wantedList);
		
		
		}
		

		return list;
	}

	public void mailSending(String userId) {

		int certCharLength = 8;
		final char[] characterTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', '0' };

		Random random = new Random(System.currentTimeMillis());
		int tablelength = characterTable.length;
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < certCharLength; i++) {
			buf.append(characterTable[random.nextInt(tablelength)]);
		}

		Member member = mm.getUser(userId);

		member.setPassword(new BCryptPasswordEncoder().encode(buf));
		mm.changePw(member);

		String setfrom = "info@codesquare.com";
		String tomail = member.getEmail();
		String title = "[NO-REPLY]CodeSquare" + userId + "님의 초기화 된 비밀번호";
		String content = userId + "님의 초기화된 비밀 번호는 " + buf + " 입니다. ";

		try {
			MimeMessage message = mailsender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);

			mailsender.send(message);

		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
