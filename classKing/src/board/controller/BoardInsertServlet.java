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

import board.model.service.BoardService;
import board.model.vo.Board;


/**
 * Servlet implementation class BoardEditServlet
 */
@WebServlet("/binsert")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int result3 = 0;
public static int classNo;
public static String writer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//request.setCharacterEncoding("utf-8");
		int maxSize = 1024 * 1024 * 10;
		
		int currentPage = 1;
		
		RequestDispatcher view =null;
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("view/board/boardError.jsp");
			request.setAttribute("message", "form 태그에 enctype속성이 설정되지 않았습니다.");
			view.forward(request, response);
		
		}
		
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload/board_upload";
		
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		Board board = new Board();
		board.setClassNo(Integer.parseInt(mrequest.getParameter("classNo")));
		board.setMemberId(mrequest.getParameter("memberId"));
		
		board.setBoardTitle(mrequest.getParameter("boardTitle"));
		board.setBoardNote(mrequest.getParameter("boardNote"));
		board.setBoardTagNo(mrequest.getParameter("boardTag"));
		
	
		
		
		String originFileName = mrequest.getFilesystemName("upfile");
		
		if(originFileName != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의 이름바꾸기 처리함
			//새로운 파일명 만들기 : '년월일시분초'
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
					+originFileName.substring(originFileName.lastIndexOf(".")+1);
			
			//파일명 바꾸려면 File객체의 renameTo() 사용함
			File originFile = new File(savePath + "//" + originFileName);
			File renameFile = new File(savePath + "//" + renameFileName);
			
			//파일 이름바꾸기 실행함
			//이름바꾸기 실패할 경우에는 직접 바꾸기함
			//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음 원본 삭제함
			if(!originFile.renameTo(renameFile)) {
				int read = -1;
				byte[] buf = new byte[1024];
				//원본을 읽기 위한 파일스트림 생성
				FileInputStream fin = new FileInputStream(originFile);
				//읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
				FileOutputStream fout = new FileOutputStream(renameFile);
				
				//원본 읽어서 복사본에 기록 처리 
				while((read = fin.read(buf, 0, buf.length)) != -1) {
					fout.write(buf, 0, read);
				}
				fin.close();
				fout.close();
				originFile.delete(); // 원본파일 삭제 
				
			}
			board.setBoardOriginalFile(originFileName);
			board.setBoardRenameFile(renameFileName);
			
			
		}
		
		// 3. 서비스 클래스 메소드로 값 전달하고, 결과 받기 
		result3 = new BoardService().insertBoard(board);
		classNo=board.getClassNo();
		writer=board.getMemberId();
		response.setContentType("text/html; charset=utf-8");
		if(result3 > 0) {
			//view = request.getRequestDispatcher("views/board/classBoard.jsp");
			response.sendRedirect("/classKing/blist?classNo="+board.getClassNo());
			//response.sendRedirect("/classKing/blist?page="+currentPage);
			//view.forward(request, response);
			
		}else {
			view = request.getRequestDispatcher("views/board/boardError.jsp");
			request.setAttribute("message", "게시글 등록 실패!");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
