package com.example.fanmon_be.domain.meeting.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ByteToUUIDConverter {
    public static UUID byteArrayToUUID(byte[] byteArray) {
        // 바이트 배열은 16바이트이어야 한다 (UUID는 128비트이므로)
        if (byteArray.length != 16) {
            throw new IllegalArgumentException("Invalid byte array size for UUID");
        }

        // ByteBuffer를 사용하여 바이트 배열을 long 값 두 개로 변환
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();

        // 두 개의 long 값을 사용하여 UUID 생성
        return new UUID(mostSigBits, leastSigBits);
    }
}
