package kr.or.ddit.springmvc.board.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.springmvc.board.vo.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"/egovframework/spring/context-common.xml"
		,"/egovframework/spring/context-datasource.xml"
		,"/egovframework/spring/context-mapper.xml"
})

public class BoardMapperJUnit {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardMapperJUnit.class);
	
	@Resource(name="boardMapper")
	private BoardMapper boardMapper;
	
	//@Test
	public void testCreate() throws Exception{
		/***Given***/
		BoardVO paramBoardVO = new BoardVO();
		/***When***/
		
		for (int i = 0; i < 300; i++) {
			paramBoardVO.setContents((i+1)+"번째 내용 테스트");
			paramBoardVO.setOpenYn("Y");
			paramBoardVO.setTitle((i+1)+"번째 제목 테스트");
			paramBoardVO.setUserPassword("1234");
			paramBoardVO.setWriter("나");
			boardMapper.create(paramBoardVO);			
			LOGGER.info("paramBoardVO.getBoardSn : {}", paramBoardVO.getBoardSn());
		}
		
		/***Then***/
		assertNotNull(paramBoardVO.getBoardSn());
		

	}
	
	//@Test
	public void testUpdate() throws Exception{
		/***Given***/
		BoardVO paramBoardVO = new BoardVO();
		/***When***/
		paramBoardVO.setBoardSn("300");
		paramBoardVO.setContents("내용 테스트-수정");
		paramBoardVO.setOpenYn("N");
		paramBoardVO.setTitle("번째 제목 테스트-수정");
		paramBoardVO.setUserPassword("1234");
		paramBoardVO.setWriter("나-수정");
		int cnt = boardMapper.update(paramBoardVO);			
		LOGGER.info("cnt"+cnt);
		/***Then***/
		assertTrue(cnt > 0);
		
		
	}
	
	//@Test
	public void testDelete() throws Exception{
		/***Given***/
		BoardVO paramBoardVO = new BoardVO();
		/***When***/
		
		paramBoardVO.setBoardSn("2");
		int cnt = boardMapper.delete(paramBoardVO);			
		LOGGER.info("cnt"+cnt);
		
		/***Then***/
		assertTrue(cnt > 0);
		
	}
		
	//@Test
	public void testRetrieve() throws Exception {
		/***Given***/
		BoardVO paramBoardVO = new BoardVO();
		paramBoardVO.setBoardSn("5");
		/***When***/
		BoardVO resultBoardVO = boardMapper.retrieve(paramBoardVO);
		
		LOGGER.info(resultBoardVO.getBoardSn());
		
		/***Then***/
		assertEquals(paramBoardVO.getBoardSn(), resultBoardVO.getBoardSn());
		
	}
	
	@Test
	public void testRetrieveList() throws Exception {
		/***Given***/
		BoardVO paramBoardVO = new BoardVO();
		/***When***/
		List<BoardVO> list = boardMapper.retrieveList(paramBoardVO);
		
		for (BoardVO boardVO : list) {
			LOGGER.info(boardVO.getBoardSn());
		}
		
		LOGGER.info("list.size() : {}", list.size()); //299개가 되어야함.
		/***Then***/
		assertTrue(list.size() > 0);
		
	}
	
	
	
	
}
