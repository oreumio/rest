package com.oreumio.james.rest.send;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Date;

/**
 * <pre>
 * 커스텀 마임  메세지
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMyMimeMessage extends MimeMessage {

	/**
	 * 수신된 날짜
	 */
	private Date receivedDate;

	/**
	 * Constructor.
	 * @param session 메일 세션
	 * @param is 스트림
	 * @throws javax.mail.MessagingException
	 */
	public EmlMyMimeMessage(Session session, InputStream is)
			throws MessagingException {
		super(session, is);
	}

	/* (non-Javadoc)
	 * @see javax.mail.internet.MimeMessage#getReceivedDate()
	 */
	@Override
	public Date getReceivedDate() throws MessagingException {
		return this.receivedDate;
	}

	/**
	 * 수신된 날짜 셋팅
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
}
