package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardFileDownServlet
 */
@WebServlet("/bfdown")
public class BoardFileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFileDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �Խñ� ÷������ �ٿ�ε� ó���� ��Ʈ�ѷ�
	
		
		//������Ʈ ���� ������ ����� ������ ������� ����
		String readFolder = request.getSession().getServletContext().getRealPath("/upload/board_upload");
		String originalFileName = request.getParameter("ofile");
		String renameFileName = request.getParameter("rfile");
		
		// Ŭ���̾�Ʈ�� ������ ��� ��Ʈ�� ����
		ServletOutputStream downOut = response.getOutputStream();
		
		File downFile = new File(readFolder +"/"+ renameFileName);
		
		response.setContentType("text/plain; charset=utf-8");
		//�ѱ����ϸ� ���ڵ�
		response.addHeader("Content-Disposition", "attachment; filename=\"" 
				+ new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
		response.setContentLengthLong((int)downFile.length());
		
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(downFile));
		int read = -1;
		while((read = bin.read()) != -1) {
			downOut.write(read);
			downOut.flush();
		}
		downOut.close();
		bin.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
