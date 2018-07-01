package qna.controller;

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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import qna.model.service.QnaService;
import qna.model.vo.Qna;

/**
 * Servlet implementation class QnaAskServlet
 */
@WebServlet("/qnaask")
public class QnaAskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaAskServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int maxSize = 1024 * 1024 * 10;

		String root = request.getSession().getServletContext().getRealPath("/");

		String savePath = root + "upload/qna_upload";

		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "utf-8",
				new DefaultFileRenamePolicy());

		Qna qna = new Qna();

		qna.setQnaTitle(mrequest.getParameter("subject"));
		qna.setQnaNote(mrequest.getParameter("content"));
		qna.setQnaOriginalFile(mrequest.getFilesystemName("filename"));
		qna.setQnaWriter(mrequest.getParameter("qwriter"));

		String qnaOriginFileName = qna.getQnaOriginalFile();
		if (qnaOriginFileName != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
			String qnaRenameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
					+ qnaOriginFileName.substring(qnaOriginFileName.lastIndexOf(".") + 1);

			File qnaOriginFile = new File(savePath + "\\" + qnaOriginFileName);
			File qnaRenameFile = new File(savePath + "\\" + qnaRenameFileName);

			if (!qnaOriginFile.renameTo(qnaRenameFile)) {

				int read = -1;
				byte[] buf = new byte[1024];

				FileInputStream fi = new FileInputStream(qnaOriginFile);
				FileOutputStream fo = new FileOutputStream(qnaRenameFile);

				while ((read = fi.read(buf, 0, buf.length)) != -1) {
					fo.write(buf, 0, read);

				}
				fi.close();
				fo.close();
				qnaOriginFile.delete();
			}
			qna.setQnaRenameFile(qnaRenameFileName);
		}

		int result = new QnaService().insertQuestion(qna);

		response.setContentType("text/html; charset=utf-8");

		if (result > 0) {
			response.sendRedirect("/classKing/qlist");
		} else {
			RequestDispatcher view = null;
			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 등록 실패");
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
