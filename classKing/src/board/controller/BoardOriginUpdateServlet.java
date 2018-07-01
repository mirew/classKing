package board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.glass.ui.View;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardOriginUpdateServlet
 */
@WebServlet("/boriginup")
public class BoardOriginUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 12L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardOriginUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		int maxSize= 1024*1024*10;

		
		RequestDispatcher view = null;
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			
			view=request.getRequestDispatcher("views/board/boardError.jsp");
			request.setAttribute("message", "form태그에 enctype속성이 설정되지 않았습니다.");
			view.forward(request, response);			
		}else {
		
			
		//해당 웹 컨테이너(was톰캣)에서 구동중인 웹 애플리케이션의 루트 경로(content directory) 알아냄
		String root = request.getSession().getServletContext().getRealPath("/");
		
		
		//업로드 되는 파일이 저장될 폴더명과 루트 경로 연결
		String savePath = root + "upload/board_upload";
		
		//cos.jar 라이브러리를 사용할 경우, MultipartRequest객체 생성
		//객체 생성과 동시에 자동 파일 업로드됨
		//request 객체는 MultipartRequest 객체로 변환됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath,maxSize,"utf-8",new DefaultFileRenamePolicy());
		
		Board board = new Board();
		board.setBoardTagNo(mrequest.getParameter("boardTag"));
		board.setBoardTitle(mrequest.getParameter("boardTitle"));
		board.setBoardNote(mrequest.getParameter("boardNote"));
		board.setBoardNo(Integer.parseInt(mrequest.getParameter("boardNo")));
		board.setBoardOriginalFile(mrequest.getParameter("upfile"));
		int currentPage = Integer.parseInt(mrequest.getParameter("page"));
		int classNo = Integer.parseInt(mrequest.getParameter("classNo"));
		
//		String boriginFileName = mrequest.getParameter("ofile");
//		String brenameFileName = mrequest.getParameter("rfile");
		
		//String upfile =mrequest.getFilesystemName("upfile");
		
		
		String originFileName = mrequest.getFilesystemName("upfile");
		if (originFileName != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()))
					+"."+originFileName.substring(originFileName.lastIndexOf(".")+1);
			
			File originFile = new File(savePath+"/"+originFileName);
			File renameFile = new File(savePath+"/"+renameFileName);
			
			if(!originFile.renameTo(renameFile)) {
				//파일이름바꾸기 실패했다면
				int read = -1;
				byte[] buf=new byte[1024];
				FileInputStream fin = new FileInputStream(originFile);				
				FileOutputStream fout = new FileOutputStream(renameFile);
				
				while((read=fin.read(buf,0,buf.length))!= -1) {
					fout.write(buf,0,read);				
				}
				//스트림 반납
				fin.close();
				fout.close();
				originFile.delete();	
			}
			board.setBoardOriginalFile(originFileName);
			board.setBoardRenameFile(renameFileName);
		}
			//저장된 이전 파일 삭제함 
			int result= new BoardService().updateBoard(board);
			
			//4.받은 결과를 가지고 성공/실패에 대한 뷰를 선택해서 내보냄
			response.setContentType("text/html; charset=utf-8");
			
			if(result>0) {
				response.sendRedirect("/classKing/blist?page="+currentPage+"&classNo="+classNo);			
			}else {
				view=request.getRequestDispatcher("views/board/boardError.jsp"); //상대경로밖에 못씀. 서블릿이 root에서 실행됨
				request.setAttribute("message", "게시글 등록 실패!");
				view.forward(request, response);
			}
		}


		
	// Board board= new Board();
	//
	// board.setBoardTitle(mrequest.getParameter("boardTitle"));
	// board.setBoardNote(mrequest.getParameter("boardNote"));
	// board.setBoardNo(Integer.parseInt(mrequest.getParameter("boardNo")));
	// board.setBoardOriginalFile(mrequest.getFilesystemName("upfile"));
	// int currentPage = Integer.parseInt(mrequest.getParameter("page"));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
