package kr.or.ddit.springmvc.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.springmvc.board.dao.BoardMapper;
import kr.or.ddit.springmvc.board.vo.BoardVO;

@Service("boardService")
public class BoardService {
   
   @Resource(name = "boardMapper")
   private BoardMapper boardMapper;

   public List<BoardVO> boardList(BoardVO boardVO) throws Exception {
      return boardMapper.retrieveList(boardVO);
   }
   
   public String insertBoard(BoardVO boardVO) throws Exception {
	   boardMapper.create(boardVO);
	   String boardSn = boardVO.getBoardSn();
	   return boardSn;
   }
   
   public int updateBoard(BoardVO boardVO) throws Exception {
      return boardMapper.update(boardVO);
   }
   
   public int deleteBoard(BoardVO boardVO) throws Exception {
      return boardMapper.delete(boardVO);
   }

   public BoardVO selectBoardOne(BoardVO boardVO) throws Exception {
      return boardMapper.retrieve(boardVO);
   }

   public List<BoardVO> selectBoardList(BoardVO boardVO) throws Exception  {
      return boardMapper.retrievePagingList(boardVO);
   }

   public int selectBoardListTotCnt(BoardVO boardVO) throws Exception {
      return boardMapper.selectBoardListTotCnt(boardVO);
   }
   
   
}