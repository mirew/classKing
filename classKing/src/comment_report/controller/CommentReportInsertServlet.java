package comment_report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

import comment_report.model.service.CommentReportService;
import comment_report.model.vo.CommentReport;
import report.model.service.ReportService;
import report.model.vo.Report;

/**
 * Servlet implementation class CommentReportInsertServlet
 */
@WebServlet("/crinsert")
public class CommentReportInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentReportInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int maxSize = 1024 * 1024 * 10;
		
		RequestDispatcher view =null;
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("view/comment_report/commentReportErorr.jsp");
			request.setAttribute("message", "form 태그에 enctype속성이 설정되지 않았습니다.");
			view.forward(request, response);
		
		}
		
		
		String root = request.getSession().getServletContext().getRealPath("/");
		
		String savePath = root + "upload/comment_report_upload";
		
		// request �� MultipartRequest ��ü�� ��ȯ��
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		//int classNo = Integer.parseInt(mrequest.getParameter("classNo"));
		CommentReport creport = new CommentReport();
		creport.setCommentNo(Integer.parseInt(mrequest.getParameter("commentNo")));
		creport.setMemberId(mrequest.getParameter("loginUser"));
		creport.setRepoartCategoryNote(mrequest.getParameter("reportCategoryNote"));
		creport.setReportNote(mrequest.getParameter("reportNote"));
		
		
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
			creport.setReportOriginalFile(originFileName);
			creport.setReportRenameFile(renameFileName);
			
			
		}
		
		// 3. 서비스 클래스 메소드로 값 전달하고, 결과 받기 
		//int result = new BoardService().insertBoard(board);
		int result = new CommentReportService().insertReport(creport);
		
		response.setContentType("text/html; charset=utf-8");
		if(result > 0) {
			
			view = request.getRequestDispatcher("views/report/reportComplete.html");
			view.forward(request, response);
			
//			   PrintWriter out = response.getWriter(); //선언
//			   
//			   String str="";
//			   str = "<script language='javascript'>";
//			   str += "opener.window.location.reload();";  //오프너 새로고침
//			   str += "self.close();";   // 창닫기
//			    str += "</script>";
//			   out.print(str);
//	
		}else {
			view = request.getRequestDispatcher("views/comment_report/commentReportError.jsp");
			request.setAttribute("message", "댓글 신고 등록 실패!");
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
