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
 * Servlet implementation class QnaModifyServlet
 */
@WebServlet("/qmodify")
public class QnaModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaModifyServlet() {
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

		RequestDispatcher view = null;

		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "/upload/qna_upload";

		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "utf-8",
				new DefaultFileRenamePolicy());
		int currentPage = (Integer.parseInt(mrequest.getParameter("page")));
		Qna q = new Qna();
		q.setQnaNo(Integer.parseInt(mrequest.getParameter("qnanum")));
		q.setQnaTitle(mrequest.getParameter("subject"));
		q.setQnaNote(mrequest.getParameter("content"));
		q.setQnaOriginalFile(mrequest.getFilesystemName("filename"));

		String upfile = mrequest.getFilesystemName("upfile");
		if (upfile != null) {
			q.setQnaOriginalFile(upfile);

			String originFileName = q.getQnaOriginalFile();
			if (originFileName != null) {

				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
				String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + ","
						+ originFileName.substring(originFileName.lastIndexOf(",") + 1);

				File originFile = new File(savePath + "\\" + originFileName);
				File renameFile = new File(savePath + "\\" + renameFileName);

				if (!originFile.renameTo(renameFile)) {
					int read = -1;
					byte[] buf = new byte[1024];

					FileInputStream fin = new FileInputStream(originFile);
					FileOutputStream fout = new FileOutputStream(renameFile);

					while ((read = fin.read(buf, 0, buf.length)) != -1) {
						fout.write(buf, 0, read);
					}

					fin.close();
					fout.close();
					originFile.delete();

				}
				q.setQnaRenameFile(renameFileName);
			}
		}
		int result = new QnaService().updateAsk(q);

		response.setContentType("text/html; charset=utf-8");
		if (result > 0) {
			response.sendRedirect("/classKing/qdetail?qnanum=" + q.getQnaNo() + "&page=" + currentPage);
		} else {
			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 수정 실패");
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
