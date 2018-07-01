package notice.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeFileDownServlet
 */
@WebServlet("/nfdown")
public class NoticeFileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공지글 첨부파일 다운로드 처리용 컨트롤러
		request.setCharacterEncoding("utf-8");
		//웹프로젝트 내에 파일이 저장된 폴더의 경로 정보 얻어오기
		String readFolder=request.getSession().getServletContext().getRealPath("/upload/notice_upload");
		
		String originalFileName=request.getParameter("ofile");
		String renameFileName=request.getParameter("rfile");
		
		//클라이언트로 내보낼 출력 스트림 생성
		ServletOutputStream downOut=response.getOutputStream();
		
		File downFile=new File(readFolder +"/"+renameFileName);
		
		response.setContentType("text/plain;charset=utf-8");
		//한글 파일명 인코딩 처리함
		response.addHeader("Content-Disposition", "attachment;filename=\""+new String(originalFileName.getBytes("utf-8"),"ISO-8859-1")+"\"");
		//////////////////////파일 다운 처리
		response.setContentLength((int)downFile.length());
		
		//폴더에서 파일 데이터 읽어서, 클라이언트로 내보냄(보조스트림이용)
		BufferedInputStream bin=new BufferedInputStream(new FileInputStream(downFile));
		//보조스트림의 목적은 성능의 개선
		//Buffered 버퍼공간 이용하는 스트림 - 읽어들인 정보를 버퍼에 보관. 버퍼는 메모리에 있는 임시기억공간. 
		//읽어서 변수에 옮기기 전에 버퍼에 보관해두기 위해서. 이 시간에 CPU는 다른 작업 할수 있어서 속도가 빨라짐.
		int read = -1;
		while((read = bin.read()) != -1) {
			downOut.write(read);
			downOut.flush();
		}
		
		downOut.close(); 
		bin.close();// 반드시 반납. 안하면 다른애가 사용못함.
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
