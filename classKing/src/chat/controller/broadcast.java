package chat.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import chat.model.service.ChatService;
import chat.model.vo.Chat;

//@ServerEndpoint는 클라이언트에서 서버로 접속 할 주소로 지정합니다.
@ServerEndpoint("/broadcasting")
public class broadcast {

	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
	private static Map<Session,String> map = new HashMap<Session,String>();
	private static int classNo = 0;
	//@OnMessage에서는 클라이언트로 부터 메시지가 도착했을때 처리입니다.
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		synchronized (clients) { // Iterate over the connected sessions
			// and broadcast the received message
			if(message.substring(0,1).equals("|")) {
				String message2[] = message.split("\\|");
				map.put(session, message2[1]);
				classNo = Integer.parseInt(message2[2]);
				for (Session client : clients) {
					client.getBasicRemote().sendText(message);
				}
			}else if(message.substring(0,1).equals("!")){
				for (Session client : clients) {
					client.getBasicRemote().sendText(message);
				}
			}else {
				String message2[] = message.split("\\|");
				String memberId = message2[0];
				String message3 = message2[1];
				int classNo = Integer.parseInt(message2[2]);
				Chat chat = new Chat();
				chat.setMemberId(memberId);
				chat.setChatNote(message3);
				chat.setClassNo(classNo);
				
				if(!message3.equals("")) {
					int result = new ChatService().chatInsert(chat);
				}
				for (Session client : clients) {
					if (!client.equals(session)) {
						client.getBasicRemote().sendText(message);
					}
				}
			}
		}
	}
	//@OnOpen은 클라이언트에서 서버로 접속할 때의 처리입니다.
	@OnOpen
	public void onOpen(Session session) { // Add session to the connected
											// sessions set
		for (Session client : clients) {
			try {
				session.getBasicRemote().sendText("|"+map.get(client)+"|" + classNo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clients.add(session);
	}
	@OnClose
	public void onClose(Session session) {
		// Remove session from the connected sessions set
		clients.remove(session);
	}
}