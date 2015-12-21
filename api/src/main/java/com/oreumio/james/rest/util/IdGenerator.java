package com.oreumio.james.rest.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

/**
 * @author panasoon
 *
 */
@Component
public class IdGenerator {

	static final Logger LOGGER = LoggerFactory.getLogger(IdGenerator.class);

	private static String distSystemYn;
	private static int distSystemNo;

	private static final long HOST_ID = getHostId();
	private static long lastTime = 0;
	private static long clockSequence = 0;

	/**
	 * 분산환경시스템 여부
	 * @param distSystemYn Y:분산, N:단독서버
	 */
	@Value("${distributed.system.yn}")
	public void setDistSystemYn(String distSystemYn) {
		IdGenerator.distSystemYn = distSystemYn;
	}

	/**
	 * 분산환경시스템 호기정보
	 * @param distSystemNo 호기정보 (0 ~ 999)
	 */
	@Value("${distributed.system.no}")
	public void setDistSystemNo(int distSystemNo) {
		IdGenerator.distSystemNo = distSystemNo;
	}

	/**
	 * prefix 문자열을 붙인 고유키를 반환합니다.
	 * <p>prefix 문자는 가급적 1Byte로 사용하기 바랍니다.<br>
	 * 최종 생성되는 고유키가 30Bytes 이내인지 확인하시기 바랍니다.</p>
	 *
	 * @param prefix prefix 문자열
	 * @return 고유키
	 */
	public static String generateUniqueId(String prefix) {
        return prefix + generateUniqueId();
    }

	/**
	 * 시간적인 시퀀스를 보장할 수 있는 고유키를 반환합니다.
	 * <p>분산시스템 환경 설정에 따라 15Bytes 또는 18Bytes 키를 반환합니다.</p>
     * <pre>
     * application.properties 파일
     *     distributed.system.yn=N : 15Bytes ID
     *     distributed.system.yn=Y : 18Bytes ID
     *         (15Bytes ID + 호기정보 3Bytes)
     * </pre>
	 * @return 고유키
	 */
	public static String generateUniqueId() {
		// jhbang 2015.07.23 - Id 생성 알고리즘 교체
		/*
        UUID idOne = UUID.randomUUID();
        String str=""+idOne;        
        int uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return str;
        */
		UUID		uuid	= generateIdFromTimestamp(System.currentTimeMillis());
		String		id		= uuid.toString();
		String[]	idArr	= id.split("-");

		int			lastSeq	= Integer.parseInt(idArr[2], 16) + Integer.parseInt(idArr[3], 16);
		String		seqStr	= Integer.toHexString(lastSeq);
		seqStr	= seqStr.substring(seqStr.length() - 3);

		id	= idArr[0] + idArr[1] + StringUtils.leftPad(seqStr, 3, "0")
				 + ("Y".equals(distSystemYn) ? StringUtils.leftPad("" + distSystemNo, 3, "0") : "");

		return id;
    }

	public static String generateKey18char(){
		try {
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			return System.currentTimeMillis() + StringUtils.leftPad(String.valueOf(Math.abs(prng.nextInt(99999))),5,'0');
		} catch (NoSuchAlgorithmException e) {
			return System.currentTimeMillis() + StringUtils.leftPad(String.valueOf((int)(Math.random() * 99999)),5,'0');
		}
	}

	/**
	 * 시간적인 시퀀스를 보장할 수 있는 UUID를 반환합니다.
	 * @return UUID
	 */
	public static final UUID generateUUID() {
		return generateIdFromTimestamp(System.currentTimeMillis());
	}

	private synchronized static final UUID generateIdFromTimestamp(long currentTimeMillis) {
		long time = 0;

		//synchronized (IdGenerator.class) {
			if (currentTimeMillis > lastTime) {
				lastTime		= currentTimeMillis;
				clockSequence	= 0;
			} else {
				++clockSequence;
			}
		//}

		// low Time
		time = currentTimeMillis << 32;

		// mid Time
		time |= ((currentTimeMillis & 0xFFFF00000000L) >> 16);

		// hi Time
		time |= 0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);

		long lsb = (HOST_ID >>> 48) + clockSequence;
		lsb	= (lsb << 48) | (HOST_ID & 0x0000FFFFFFFFFFFFL);

		LOGGER.debug("[IdGen] ms = " + currentTimeMillis + ", cs = " + clockSequence + ", lsb = " + lsb);

		return new UUID(time, lsb);
	}

	private static final long getHostId() {
		long	macAddressAsLong	= 0;

		try {
			Random		random	= new Random();
			InetAddress address = InetAddress.getLocalHost();

			Enumeration<NetworkInterface>	enis	= NetworkInterface.getNetworkInterfaces();
			while (enis.hasMoreElements()) {
				NetworkInterface	sni	= (NetworkInterface) enis.nextElement();

				Enumeration<InetAddress>	eia	= sni.getInetAddresses();
				while (eia.hasMoreElements()) {
					InetAddress sia	= (InetAddress) eia.nextElement();
					if (!sia.isLoopbackAddress() && !sia.isLinkLocalAddress() && !sia.isSiteLocalAddress()) {
						address	= sia;
					}
				}
			}

			NetworkInterface ni = NetworkInterface.getByInetAddress(address);

			if (ni != null) {
				byte[] mac = ni.getHardwareAddress();
				if (null != mac) {
					random.nextBytes(mac); // we don't really want to reveal the actual MAC address

					// Converts array of unsigned bytes to an long
					if (mac != null) {
						for (int i = 0; i < mac.length; i++) {
							macAddressAsLong <<= 8;
							macAddressAsLong ^= (long) mac[i] & 0xFF;
						}
					}
				} else {
					macAddressAsLong	= random.nextLong();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return macAddressAsLong;
	}
}
