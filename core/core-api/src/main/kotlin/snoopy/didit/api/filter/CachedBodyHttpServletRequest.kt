package snoopy.didit.api.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

/**
 * ELK 로깅을 위해 filter단에서 stream을 사용해서 읽으면 controller단에서 읽을 수 없음(stream은 한번 읽으면 다시 읽을 수 없음)
 * 여기서는 두번읽게 가능 하도록 설정
 */
class CachedBodyHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedBody: ByteArray = request.inputStream.readAllBytes()

    override fun getInputStream(): ServletInputStream {
        val byteArrayInputStream = ByteArrayInputStream(cachedBody)
        return object : ServletInputStream() {
            override fun isFinished(): Boolean = byteArrayInputStream.available() == 0

            override fun isReady(): Boolean = true

            override fun setReadListener(readListener: ReadListener?) {
            }

            override fun read(): Int = byteArrayInputStream.read()
        }
    }

    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }
}
