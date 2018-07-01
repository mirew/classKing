package alarm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alarm.model.service.AlarmService;
import apply.controller.ApplyUpdateServlet;
import apply.controller.ClassJoinApply;
import apply.model.service.ApplyService;
import apply.model.vo.Apply;
import board.controller.BoardInsertServlet;
import board.model.vo.Board;
import classes.model.vo.Classes;
import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;
import gallery.controller.classGalleryMakeServlet;
import notice.controller.NoticeInsertServlet;
import notice.model.service.NoticeService;
import qna.controller.AnswerInserServlet;
import qna.controller.QnaAskServlet;
import qna.model.vo.Qna;

/**
 * Servlet implementation class alarmServlet
 */
@WebServlet("/sse")
public class alarmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public alarmServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/event-stream;charset=UTF-8");

		response.setHeader("Cache-Control", "no-cache");

		response.setHeader("Connection", "keep-alive");

		String id = "";
		PrintWriter out = null;

		if (request.getParameter("userid") != null) {

			id = request.getParameter("userid");

			if (NoticeInsertServlet.result2 == 1 && !(id.equals("admin"))) {

				out = response.getWriter();
				out.write("data: " + "새 공지글이 등록되었습니다." + "\n\n");

				out.flush();
				NoticeInsertServlet.result2 = 0;
			}

			if (BoardInsertServlet.result3 == 1) {

				Classes checkClass = new AlarmService().checkClass(id, BoardInsertServlet.classNo);
				if (checkClass != null && !(id.equals(BoardInsertServlet.writer))) {

					out = response.getWriter();
					out.write("data: " + checkClass.getClassTitle() + "에 새 게시글이 등록되었습니다." + "\n\n");
					out.flush();
					BoardInsertServlet.result3 = 0;
				}
			}

			if (classGalleryMakeServlet.result4 == 1) {

				Classes checkClass = new AlarmService().checkClass(classGalleryMakeServlet.memberId,
						classGalleryMakeServlet.classNo);

				if (checkClass != null && !(id.equals(classGalleryMakeServlet.memberId))) {

					out = response.getWriter();
					out.write("data: " + checkClass.getClassTitle() + "에 새 사진이 등록되었습니다." + "\n\n");
					out.flush();
				}
				classGalleryMakeServlet.result4 = 0;
			}

			if (AnswerInserServlet.result5 == 1 && !(id.equals("admin"))) {

				Qna checkq = new AlarmService().checkQna(id, AnswerInserServlet.qnaNum);
				if (checkq != null) {
					out = response.getWriter();
					out.write("data: " + '"' + checkq.getQnaTitle() + '"' + " 질문에 답변이 등록되었습니다." + "\n\n");
					out.flush();

				}
				AnswerInserServlet.result5 = 0;
			}
			if (ApplyUpdateServlet.result6 == 1) {

				ClassesMember checkClass = new ClassMemberService().checkJoinClass(id, ApplyUpdateServlet.classNo);

				if (checkClass != null && (id.equals(ApplyUpdateServlet.memberId))) {
					out = response.getWriter();
					out.write("data: " + checkClass.getClassTitle() + "에 가입되셨습니다" + "\n\n");
					out.flush();
				}
				ApplyUpdateServlet.result6 = 0;
			}
			if (ClassJoinApply.result7 == 1) {
				Apply apply = new ApplyService().alarmapply(ClassJoinApply.classNo);

				if (apply.getMemberId().equals(id)) {
					out = response.getWriter();
					out.write("data: " + apply.getClassTitle() + "에" + " " + ClassJoinApply.applyId + "님이 가입신청을 하셨습니다."
							+ "\n\n");
					out.flush();

				}
				ClassJoinApply.result7 = 0;
			}

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
