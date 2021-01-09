package kr.or.ddit.springmvc.board.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.or.ddit.springmvc.board.vo.BoardVO;
import kr.or.ddit.springmvc.board.service.BoardService;

@Controller
public class BoardController {
   
   @Resource(name = "boardService")
   private BoardService boardService;

   /** EgovPropertyService */
   @Resource(name = "propertiesService")
   protected EgovPropertyService propertiesService;

   /** Validator */
   @Resource(name = "beanValidator")
   protected DefaultBeanValidator beanValidator;
   
   /**
    * 글 목록을 조회한다. (pageing)
    * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
    * @param model
    * @return "egovSampleList"
    * @exception Exception
    */
   @RequestMapping(value = "/board/retrievePagingList.do")
   public String selectSampleList(BoardVO boardVO, ModelMap model) throws Exception {

      /** EgovPropertyService.sample */
      boardVO.setPageUnit(propertiesService.getInt("pageUnit"));
      boardVO.setPageSize(propertiesService.getInt("pageSize"));

      /** pageing setting */
      PaginationInfo paginationInfo = new PaginationInfo();
      boardVO.preparePaginationInfo(paginationInfo);

      List<BoardVO> sampleList = boardService.selectBoardList(boardVO);
      model.addAttribute("resultList", sampleList);
      model.addAttribute("boardVO", boardVO);
      int totCnt = boardService.selectBoardListTotCnt(boardVO);
      paginationInfo.setTotalRecordCount(totCnt);
      model.addAttribute("paginationInfo", paginationInfo);

      return "board/list";
   }



   /**
    * 글 등록 화면을 조회한다.
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param model
    * @return "egovSampleRegister"
    * @exception Exception
    */
   @RequestMapping(value = "/addSample.do", method = RequestMethod.GET)
   public String addSampleView(@ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
      model.addAttribute("sampleVO", new SampleVO());
      return "sample/egovSampleRegister";
   }

   /**
    * 글을 등록한다.
    * @param sampleVO - 등록할 정보가 담긴 VO
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param status
    * @return "forward:/egovSampleList.do"
    * @exception Exception
    */
   @RequestMapping(value = "/addSample.do", method = RequestMethod.POST)
   public String addSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status)
         throws Exception {

      // Server-Side Validation
      beanValidator.validate(sampleVO, bindingResult);

      if (bindingResult.hasErrors()) {
         model.addAttribute("sampleVO", sampleVO);
         return "sample/egovSampleRegister";
      }

      /*boardService.insertSample(sampleVO);*/
      status.setComplete();
      return "forward:/egovSampleList.do";
   }

   /**
    * 글 수정화면을 조회한다.
    * @param id - 수정할 글 id
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param model
    * @return "egovSampleRegister"
    * @exception Exception
    */
   @RequestMapping("/board/updateView.do")
   public String updateSampleView(BoardVO boardVO, Model model) throws Exception {
	  BoardVO retrieveBoardVO = boardService.selectBoardOne(boardVO);
//	  retrieveBoardVO.setSearchCondition(boardVO.getSearchCondition());
//	  retrieveBoardVO.setSearchKeyword(boardVO.getSearchKeyword());
//	  retrieveBoardVO.setPageIndex(boardVO.getPageIndex());
	  
	  retrieveBoardVO.copySearchCondition(boardVO);
	  
	  BeanUtils.copyProperties(boardVO, retrieveBoardVO);
	  model.addAttribute("boardVO", boardVO);
      // 변수명은 CoC 에 따라 sampleVO
      return "board/edit";
   }

   /**
    * 글을 수정한다.
    * @param sampleVO - 수정할 정보가 담긴 VO
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param status
    * @return "forward:/egovSampleList.do"
    * @exception Exception
    */
   @RequestMapping("/board/update.do")
   public String updateSample(BoardVO boardVO, ModelMap model)
		   throws Exception {
	   int cnt = boardService.updateBoard(boardVO);
	   
	   return "forward:/board/retrievePagingList.do";
   }
   
   @RequestMapping("/board/createView.do")
   public String createView(BoardVO boardVO, Model model) throws Exception {
      
	   model.addAttribute("boardVO", new BoardVO());
	   
	   // 변수명은 CoC 에 따라 sampleVO
      return "board/edit";
   }

   /**
    * 글을 등록한다.
    * @param sampleVO - 수정할 정보가 담긴 VO
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param status
    * @return "forward:/egovSampleList.do"
    * @exception Exception
    */
   @RequestMapping("/board/create.do")
   public String create(BoardVO boardVO, BindingResult bindingResult, HttpSession session, ModelMap model)
		   throws Exception {
	   
		// Server-Side Validation
		beanValidator.validate(boardVO, bindingResult);
	
		if (bindingResult.hasErrors()) {
			return "board/edit";
		}
		
		String sessionSaveToken = (String)session.getAttribute("SAVE_TOKEN");
		String saveToken = boardVO.getSaveToken();
		
		if(GenericValidator.isBlankOrNull(sessionSaveToken)
				||GenericValidator.isBlankOrNull(saveToken)
				||!sessionSaveToken.equals(saveToken)) {
			
		}
		else {
			boardService.insertBoard(boardVO);
			session.removeAttribute("SAVE_TOKEN");
		}
		
	   
	   return "forward:/board/retrievePagingList.do";
   }
   
   
   
   
   
   
   



   /**
    * 글을 조회한다.
    * @param sampleVO - 조회할 정보가 담긴 VO
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param status
    * @return @ModelAttribute("sampleVO") - 조회한 정보
    * @exception Exception
    */
   public BoardVO selectSample(BoardVO boardVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
      return boardService.selectBoardOne(boardVO);
   }


   /**
    * 글을 삭제한다.
    * @param sampleVO - 삭제할 정보가 담긴 VO
    * @param searchVO - 목록 조회조건 정보가 담긴 VO
    * @param status
    * @return "forward:/egovSampleList.do"
    * @exception Exception
    */
   @RequestMapping("/deleteSample.do")
   public String deleteSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
      //boardService.deleteSample(sampleVO);
      status.setComplete();
      return "forward:/egovSampleList.do";
   }
}