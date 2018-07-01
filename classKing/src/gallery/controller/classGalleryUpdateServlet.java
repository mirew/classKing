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

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.CryptoUtils;
import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;

/**
 * Servlet implementation class classGalleryUpdateServlet
 */
@WebServlet("/cGUpdate")
public class classGalleryUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		 int maxSize = 1024 * 1024 * 10;
		 String null2= "null";
		response.setContentType("text/html; charset=utf-8");
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
	      	int classking =Integer.parseInt(mrequest.getParameter("classking"));
	      	int currentPage = 1;
			if(request.getParameter("page") != null) {
				currentPage = Integer.parseInt(mrequest.getParameter("page"));
			}	
			int classNo =Integer.parseInt(mrequest.getParameter("classNo"));    
			Gallery gallery = new Gallery();
			int galNo=Integer.parseInt(mrequest.getParameter("galNo"));
			gallery.setGalNo(galNo);
			gallery.setGalTitle(mrequest.getParameter("galTitle"));
			gallery.setGalNote(mrequest.getParameter("galNote"));
			String imgname[] = mrequest.getParameterValues("imgname");

			
			if(mrequest.getFilesystemName("galfilename0") != null) {

				gallery.setGalOriginalImg(mrequest.getFilesystemName("galfilename0"));
			}
			/*2*/
				if(mrequest.getFilesystemName("galfilename1") != null) {
					gallery.setGalOriginalImg2(mrequest.getFilesystemName("galfilename1"));
				}
				/*3*/
				if(mrequest.getFilesystemName("galfilename2") != null) {
					gallery.setGalOriginalImg3(mrequest.getFilesystemName("galfilename2"));
				}
			
			/*이미지 1*/
			
			String galOriginalImg0 = gallery.getGalOriginalImg();
	      if(mrequest.getFilesystemName("galfilename0") != null) {
	         //첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
	         //이름 바꾸기 처리
	         //새로운 파일명 만들기 : "년월일시분초.확장자"
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	         String galRenameImg0 = sdf.format(
	               new java.sql.Date(System.currentTimeMillis()))
	               + "." + galOriginalImg0.substring(galOriginalImg0.lastIndexOf(".") + 1);
	         
	         //파일명 바꾸려면 File 객체의 renameTo() 사용함
	         File originFile0 = new File(savePath + "\\" + galOriginalImg0);
	         File renameFile0 = new File(savePath + "\\" + galRenameImg0);
	         
	         //파일 이름바꾸기 실행
	         //이름바꾸기 실패할 경우에는 직접 바꾸기함
	         //직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
	         //원본을 삭제함
	         if(!originFile0.renameTo(renameFile0)) {
	            //파일이름바꾸기 실패했다면
	            int read = -1;
	            byte[] buf = new byte[1024];
	            //한번에 읽을 배열 크기 지정
	            
	            //원본을 읽기 위한 파일스트림 생성
	            FileInputStream fin0 = new FileInputStream(originFile0);
	            //읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
	            FileOutputStream fio0 = new FileOutputStream(renameFile0);
	            
	            //원본 읽어서 복사본에 기록 처리
	            while((read = fin0.read(buf,0,buf.length)) != -1) {
	               fio0.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
	            }
	            //스트림 반납
	            fin0.close();
	            fio0.close();
	            originFile0.delete();//원본 파일 삭제
	         }
	         gallery.setGalRenameImg(galRenameImg0);
	      }else if(mrequest.getParameter("renameImg0") != null){
	    	  
	    	  gallery.setGalRenameImg(mrequest.getParameter("renameImg0"));
	    	  gallery.setGalOriginalImg(mrequest.getParameter("origalImg0"));
	      }
	      
	      /*수정 이미지 업로드 2*/
			String galOriginalImg1 = gallery.getGalOriginalImg2();
			if(mrequest.getFilesystemName("galfilename1") != null) {
				//첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
				//이름 바꾸기 처리
				//새로운 파일명 만들기 : "년월일시분초.확장자"
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String galRenameImg1 = sdf.format(
						new java.sql.Date(System.currentTimeMillis()))+"2"
						+ "." + galOriginalImg1.substring(galOriginalImg1.lastIndexOf(".") + 1);
				
				//파일명 바꾸려면 File 객체의 renameTo() 사용함
				File originFile1 = new File(savePath + "\\" + galOriginalImg1);
				File renameFile1 = new File(savePath + "\\" + galRenameImg1);
				
				//파일 이름바꾸기 실행
				//이름바꾸기 실패할 경우에는 직접 바꾸기함
				//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
				//원본을 삭제함
				if(!originFile1.renameTo(renameFile1)) {
					//파일이름바꾸기 실패했다면
					int read = -1;
					byte[] buf = new byte[1024];
					//한번에 읽을 배열 크기 지정
					
					//원본을 읽기 위한 파일스트림 생성
					FileInputStream fin1 = new FileInputStream(originFile1);
					//읽은 내용 기록할 복사본 파일 출력용 파일스트림 생성
					FileOutputStream fio1 = new FileOutputStream(renameFile1);
					
					//원본 읽어서 복사본에 기록 처리
					while((read = fin1.read(buf,0,buf.length)) != -1) {
						fio1.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
					}
					//스트림 반납
					fin1.close();
					fio1.close();
					originFile1.delete();//원본 파일 삭제
				}
				gallery.setGalRenameImg2(galRenameImg1);
			}else  if(mrequest.getParameter("renameImg1") !=null){
		    	  
		    	  gallery.setGalRenameImg2(mrequest.getParameter("renameImg1"));
		    	  gallery.setGalOriginalImg2(mrequest.getParameter("origalImg1"));
		      }/*이미지 업로드 2 끝*/
			
			/* 수정 이미지 업로드 3*/
			String galOriginalImg3 = gallery.getGalOriginalImg3();
			if(mrequest.getFilesystemName("galfilename2") != null) {
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
			}else if(mrequest.getParameter("renameImg2") != null){
		    	  gallery.setGalRenameImg3(mrequest.getParameter("renameImg2"));
		    	  gallery.setGalOriginalImg3(mrequest.getParameter("origalImg2"));
		      }/*이미지 업로드 3 끝*/
			
	      //
	      int result = new GalleryService().classGalleryDetailUpdate(gallery);
	      
	      response.setContentType("text/html; charset=utf-8");
	      
		if(result > 0) {
			/*view =  request.getRequestDispatcher("cGDetail");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("classNo", classNo);
			view.forward(request, response);*/
			response.sendRedirect("/classKing/cGDetail?DgalNo="+galNo+"&page="+currentPage+"&classNo="+classNo+"&classking="+classking);
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
