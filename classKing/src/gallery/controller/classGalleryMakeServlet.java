package gallery.controller;

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
import javax.websocket.Session;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.CryptoUtils;
import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class classGalleryMakeServlet
 */
@WebServlet("/cGMake")
public class classGalleryMakeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int result4;
    public static int classNo;
    public static String memberId;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryMakeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int maxSize = 1024 * 1024 * 10;
		RequestDispatcher view = null;
		
		// enctype="multipart/form-data" 로 전송되었는지 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
		// enctype 설정이 되지 않았다면
		view = request.getRequestDispatcher("views/gallery/classGalleryError.html");
		request.setAttribute("message", "form 태그 enctype 속성이 설정되지 않았습니다.");
		view.forward(request, response);
		}
		// 해당 웹 컨테이너(was:톰캣)에서 구동중인 웹 어플리케이션의
		// root경로(content directory)를 알아내는 작업
		String root = request.getSession().getServletContext().getRealPath("/");
		// 업로드되는 파일이 저장될 폴더명과 루트 경로 연결
		String savePath = root + "/upload/gal_upload";
		// cos.jar 라이브러리를 사용할 경우, MultipartRequest 객체 생성
		// 객체 생성과 동시에 자동 파일 업로드됨
		// request 객체는 MultiRequest 객체로 변환됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "utf-8",
				new DefaultFileRenamePolicy());
		/*int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(mrequest.getParameter("page"));
		}	*/
		classNo =Integer.parseInt(mrequest.getParameter("classNo"));
		memberId = mrequest.getParameter("memberId");
		/*int currentPage = 1;		
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}*/
		 Gallery gallery = new Gallery();
		
		//gallery.setGalNo(galNo);
		gallery.setMemberId(mrequest.getParameter("memberId"));
		gallery.setGalTitle(mrequest.getParameter("galTitle"));
		gallery.setGalNote(mrequest.getParameter("galNote"));	
		gallery.setGalOriginalImg(mrequest.getFilesystemName("galfilename"));
		if(mrequest.getFilesystemName("galfilename1") != null) {
			gallery.setGalOriginalImg2(mrequest.getFilesystemName("galfilename1"));
		}
		if(mrequest.getFilesystemName("galfilename2") != null) {
			gallery.setGalOriginalImg3(mrequest.getFilesystemName("galfilename2"));
		}
		
		String galOriginalImg = gallery.getGalOriginalImg();
		/*이미지 업로드 1*/
		if(galOriginalImg != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
			//이름 바꾸기 처리
			//새로운 파일명 만들기 : "년월일시분초.확장자"
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String galRenameImg = sdf.format(
					new java.sql.Date(System.currentTimeMillis()))
					+ "." + galOriginalImg.substring(galOriginalImg.lastIndexOf(".") + 1);
			
			//파일명 바꾸려면 File 객체의 renameTo() 사용함
			File originFile = new File(savePath + "\\" + galOriginalImg);
			File renameFile = new File(savePath + "\\" + galRenameImg);
			
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
			gallery.setGalRenameImg(galRenameImg);
		}
	
		
		/*이미지 업로드 2*/
		String galOriginalImg2 = gallery.getGalOriginalImg2();
		if(galOriginalImg2 != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
			//이름 바꾸기 처리
			//새로운 파일명 만들기 : "년월일시분초.확장자"
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String galRenameImg2 = sdf.format(
					new java.sql.Date(System.currentTimeMillis()))+"2"
					+ "." + galOriginalImg2.substring(galOriginalImg2.lastIndexOf(".") + 1);
			
			//파일명 바꾸려면 File 객체의 renameTo() 사용함
			File originFile2 = new File(savePath + "\\" + galOriginalImg2);
			File renameFile2 = new File(savePath + "\\" + galRenameImg2);
			
			//파일 이름바꾸기 실행
			//이름바꾸기 실패할 경우에는 직접 바꾸기함
			//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
			//원본을 삭제함
			if(!originFile2.renameTo(renameFile2)) {
				//파일이름바꾸기 실패했다면
				int read = -1;
				byte[] buf = new byte[1024];
				//한번에 읽을 배열 크기 지정
				
				//원본을 읽기 위한 파일스트림 생성
				FileInputStream fin2 = new FileInputStream(originFile2);
				//읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
				FileOutputStream fio2 = new FileOutputStream(renameFile2);
				
				//원본 읽어서 복사본에 기록 처리
				while((read = fin2.read(buf,0,buf.length)) != -1) {
					fio2.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
				}
				//스트림 반납
				fin2.close();
				fio2.close();
				originFile2.delete();//원본 파일 삭제
			}
			gallery.setGalRenameImg2(galRenameImg2);
		}/*이미지 업로드 2 끝*/
		
		
		/*이미지 업로드 3*/
		String galOriginalImg3 = gallery.getGalOriginalImg3();

		if(galOriginalImg3 != null) {
			//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
			//이름 바꾸기 처리
			//새로운 파일명 만들기 : "년월일시분초.확장자"
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String galRenameImg3 = sdf.format(
					new java.sql.Date(System.currentTimeMillis()))+"3"
					+ "." + galOriginalImg3.substring(galOriginalImg3.lastIndexOf(".") + 1);
			
			//파일명 바꾸려면 File 객체의 renameTo() 사용함
			File originFile3 = new File(savePath + "\\" + galOriginalImg3);
			File renameFile3 = new File(savePath + "\\" + galRenameImg3);
			
			//파일 이름바꾸기 실행
			//이름바꾸기 실패할 경우에는 직접 바꾸기함
			//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
			//원본을 삭제함
			if(!originFile3.renameTo(renameFile3)) {
				//파일이름바꾸기 실패했다면
				int read = -1;
				byte[] buf = new byte[1024];
				//한번에 읽을 배열 크기 지정
				
				//원본을 읽기 위한 파일스트림 생성
				FileInputStream fin3 = new FileInputStream(originFile3);
				//읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
				FileOutputStream fio3 = new FileOutputStream(renameFile3);
				
				//원본 읽어서 복사본에 기록 처리
				while((read = fin3.read(buf,0,buf.length)) != -1) {
					fio3.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
				}
				//스트림 반납
				fin3.close();
				fio3.close();
				originFile3.delete();//원본 파일 삭제
			}
			gallery.setGalRenameImg3(galRenameImg3);
		}/*이미지 업로드 3 끝*/
		
		
		
		result4 = new GalleryService().classGalleryMake(gallery,classNo);
		
		response.setContentType("text/html; charset=utf-8");
		if(result4 > 0) {			
				response.sendRedirect("/classKing/cGallery2?classNo="+classNo);
		}else {
			response.sendRedirect("views/gallery/classGalleryError.html");
			
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
