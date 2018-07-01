package notice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet("/nupdate")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공지사항 수정 처리 컨트롤러

		request.setCharacterEncoding("utf-8");
		
		int maxSize= 1024*1024*10;
		
		
		RequestDispatcher view = null;
		if(!ServletFileUpload.isMultipartContent(request)) {
			
			view=request.getRequestDispatcher("views/notice/noticeError.jsp");
			request.setAttribute("message", "form태그에 enctype속성이 설정되지 않았습니다.");
			view.forward(request, response);			
		}
		
		//해당 웹 컨테이너(was톰캣)에서 구동중인 웹 애플리케이션의 루트 경로(content directory) 알아냄
		String root = request.getSession().getServletContext().getRealPath("/");
	
		//업로드 되는 파일이 저장될 폴더명과 루트 경로 연결
		String savePath = root + "upload/notice_upload";
		
		//cos.jar 라이브러리를 사용할 경우, MultipartRequest객체 생성
		//객체 생성과 동시에 자동 파일 업로드됨
		//request 객체는 MultipartRequest 객체로 변환됨
		MultipartRequest mrequest=new MultipartRequest(request, savePath,maxSize,"utf-8",new DefaultFileRenamePolicy());
			
		Notice notice= new Notice();
		
		notice.setNoticeTitle(mrequest.getParameter("n_title"));
		notice.setNoticeNote(mrequest.getParameter("n_content"));
		notice.setNoticeNo(Integer.parseInt(mrequest.getParameter("no")));
		notice.setNoticeOriginalFile(mrequest.getFilesystemName("upfile"));
		int currentPage = Integer.parseInt(mrequest.getParameter("page"));
		
		String originFileName = notice.getNoticeOriginalFile();
		if(originFileName != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의 이름바꾸기 처리함
			//새로운 파일명 만들기 : '년월일시분초'
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()))
					+"."+originFileName.substring(originFileName.lastIndexOf(".")+1);			

			
			File originFile = new File(savePath+"\\"+originFileName);
			File renameFile = new File(savePath+"\\"+renameFileName);
			
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
			notice.setNoticeRenameFile(renameFileName);
			
		}		
		
		//3. 서비스 클래스 메소드로 값 전달하고, 결과 받기
		int result=new NoticeService().updateNotice(notice);
		
		//4.받은 결과를 가지고 성공/실패에 대한 뷰를 선택해서 내보냄
		response.setContentType("text/html; charset=utf-8");
		
		if(result>0) {
			response.sendRedirect("/classKing/ndetail?no="+notice.getNoticeNo()+"&page="+currentPage);	 		
		}else {
			view=request.getRequestDispatcher("views/notice/noticeError.jsp"); //상대경로밖에 못씀. 서블릿이 root에서 실행됨
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
