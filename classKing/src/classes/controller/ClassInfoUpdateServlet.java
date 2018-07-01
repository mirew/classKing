package classes.controller;

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

import classes.model.service.ClassesService;
import classes.model.vo.Classes;
import classesmember.model.service.ClassMemberService;

/**
 * Servlet implementation class ClassInfoUpdateServlet
 */
@WebServlet("/cupdate")
public class ClassInfoUpdateServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassInfoUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 클래스 정보 수정 컨트롤러
      request.setCharacterEncoding("UTF-8");

      int maxSize = 1024 * 1024 * 10;
      RequestDispatcher view = null;      
      String root = request.getSession().getServletContext().getRealPath("/");
      
      String savePath = root + "/upload/class_upload";
      String renameimg=null;
      String originimg=null;
      
      MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "utf-8",
            new DefaultFileRenamePolicy());
      Classes classes = new Classes();
      classes.setClassNo(Integer.parseInt(mrequest.getParameter("classNo")));
      classes.setClassTitle(mrequest.getParameter("className"));
      classes.setClassSubTitle(mrequest.getParameter("classNotice"));
      classes.setClassOriginalImg(mrequest.getFilesystemName("upfile"));
      classes.setOpen(mrequest.getParameter("ex_rds"));      
      
      String originFileName = classes.getClassOriginalImg();
      
      if(originFileName != null) {
         //첨부된 파일이 있을 경우, 폴더에 기록된 해당 파일의
         //이름 바꾸기 처리
         //새로운 파일명 만들기 : "년월일시분초.확장자"
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
         String renameFileName = sdf.format(
               new java.sql.Date(System.currentTimeMillis()))
               + "." + originFileName.substring(originFileName.lastIndexOf(".") + 1);         
         
         File originFile = new File(savePath + "\\" + originFileName);
         File renameFile = new File(savePath + "\\" + renameFileName);
                  
         if(!originFile.renameTo(renameFile)) {
            //파일이름바꾸기 실패했다면
            int read = -1;
            byte[] buf = new byte[1024];
            
            FileInputStream fin = new FileInputStream(originFile);
            FileOutputStream fio = new FileOutputStream(renameFile);
            
            
            while((read = fin.read(buf,0,buf.length)) != -1) {
               fio.write(buf, 0, read);//read는 읽은 바이트수를 리턴받는다.
            }
            //스트림 반납
            fin.close();
            fio.close();
            originFile.delete();//원본 파일 삭제
         }
         classes.setClassRenameImg(renameFileName);
      }else{
         classes.setClassOriginalImg(request.getParameter("originimg"));
         classes.setClassRenameImg(request.getParameter("renameimg"));
      }
      
      int result = new ClassesService().classUpdate(classes);
      
      response.setContentType("text/html; charset=utf-8");
      
      if(result > 0) {
         
         response.sendRedirect("/classKing/views/class/classHome.jsp?classNo="+classes.getClassNo());
      }else {
         view = request.getRequestDispatcher("views/member/member123.jsp");
         request.setAttribute("message", "클래스수정실패");
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