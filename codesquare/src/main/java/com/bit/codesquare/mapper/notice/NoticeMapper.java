package com.bit.codesquare.mapper.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bit.codesquare.dto.notice.Notice;


@Mapper
public interface NoticeMapper {
	
//@Select( "SELECT * FROM codesquare.Notice where userId='dudgus'") //UserId=#{???}로 나중에 변경
//	public List<Notice> nameView() throws Exception;
	

@Insert("INSERT INTO codesquare.Notice (noticeContent,noticeLink) VALUES (#{qq},#{aline})")	
	void insert(String qq, String aline) throws Exception;
	
	//삭제 void delete(int Noticeid) throws Exception;

//디비리스트
//@Select( "SELECT * FROM codesquare.Notice where userId='dudgus'")
//	public String getLectureHandWriting2(Map<String,Object> map)throws Exception;

@Select( "SELECT * FROM codesquare.Notice order by sendDate desc" ) //UserId=#{???}로 나중에 변경
public List<Notice> getLectureHandWriting2() throws Exception;

}
