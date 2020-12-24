package kr.or.ddit.springmvc.board.dao;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.or.ddit.springmvc.board.vo.BoardVO;

@Mapper("boardMapper")
public interface BoardMapper {
	
	//리스트 출력 메서드부터 생성 -> create -> update, delete -> retrieve(1건)
	List<BoardVO> retrieveList(BoardVO boardVO) throws Exception;
	
	BoardVO retrieve(BoardVO boardVO) throws Exception;
	
	void create(BoardVO boardVO)throws Exception;
	
	int update(BoardVO boardVO) throws Exception;
	
	int delete(BoardVO boardVO) throws Exception;
}
