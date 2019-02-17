package com.bit.codesquare.service.study;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.study.StudyMapper;

@Service
public class StudyService {

	@Autowired
	StudyMapper studyMapper;
	
	Logger logger = LoggerFactory.getLogger(StudyService.class);
	
}