package member.controller;

import java.io.*;
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

import common.CryptoUtils;
import member.model.service.MemberService;
import member.model.vo.Member;
/**
 * Servlet implementation class MemberJoinServlet
 */
@WebServlet("/mjoin")
public class MemberJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberJoinServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입
		request.setCharacterEncoding("UTF-8");

		int maxSize = 1024 * 1024 * 10;
		RequestDispatcher view = null;
		// enctype="multipart/form-data" 로 전송되었는지 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
			// enctype 설정이 되지 않았다면
			view = request.getRequestDispatcher("views/notice/noticeError.jsp");
			request.setAttribute("message", "form 태그 enctype 속성이 설정되지 않았습니다.");
			view.forward(request, response);
		}
		// 해당 웹 컨테이너(was:톰캣)에서 구동중인 웹 어플리케이션의
		// root경로(content directory)를 알아내는 작업
		String root = request.getSession().getServletContext().getRealPath("/");
		// 업로드되는 파일이 저장될 폴더명과 루트 경로 연결
		String savePath = root + "/upload/member_upload";
		// cos.jar 라이브러리를 사용할 경우, MultipartRequest 객체 생성
		// 객체 생성과 동시에 자동 파일 업로드됨
		// request 객체는 MultiRequest 객체로 변환됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "utf-8",
				new DefaultFileRenamePolicy());
		String userpwd = CryptoUtils.encryptSHA256(new String (mrequest.getParameter("userpwd")));
		Member member = new Member();
		member.setMemberId(mrequest.getParameter("userid"));
		member.setMemberPwd(userpwd);
		member.setMemberName(mrequest.getParameter("username"));
		member.setMemberEmail(mrequest.getParameter("useremail"));
		member.setMemberBirthday(mrequest.getParameter("userbirth"));
		member.setQuestion(mrequest.getParameter("question"));
		member.setAnswer(mrequest.getParameter("answer"));
		member.setMemberOriginalImg(mrequest.getFilesystemName("upfile"));
		
		String originFileName = member.getMemberOriginalImg();
		if(originFileName != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
			//이름 바꾸기 처리
			//새로운 파일명 만들기 : "년월일시분초.확장자"
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String renameFileName = sdf.format(
					new java.sql.Date(System.currentTimeMillis()))
					+ "." + originFileName.substring(originFileName.lastIndexOf(".") + 1);
			
			//파일명 바꾸려면 File 객체의 renameTo() 사용함
			File originFile = new File(savePath + "\\" + originFileName);
			File renameFile = new File(savePath + "\\" + renameFileName);
			
			//파일 이름바꾸기 실행
			//이름바꾸기 실패할 경우에는 직접 바꾸기함
			//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
			//원본을 삭제함
			if(!originFile.renameTo(renameFile)) {
				//파일이름바꾸기 실패했다면
				int read = -1;
				byte[] buf = new byte[1024];
				//한번에 읽을 배열 크기 지정
				
				//원본을 읽기 위한 파일스트림 생성
				FileInputStream fin = new FileInputStream(originFile);
				//읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
				FileOutputStream fio = new FileOutputStream(renameFile);
				
				//원본 읽어서 복사본에 기록 처리
				while((read = fin.read(buf,0,buf.length)) != -1) {
					fio.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
				}
				//스트림 반납
				fin.close();
				fio.close();
				originFile.delete();//원본 파일 삭제
			}
			member.setMemberRenameImg(renameFileName);
		}else {
			member.setMemberOriginalImg("default_user.png");
			member.setMemberRenameImg("default_user.png");
		}
		int result = new MemberService().joinMember(member);
		
		response.setContentType("text/html; charset=utf-8");
		
		if(result > 0) {
			response.sendRedirect("/classKing/main.jsp");
		}else {
			view = request.getRequestDispatcher("views/member/memberError.jsp");
			request.setAttribute("message", "회원가입실패");
			view.forward(request, response);
		}
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
