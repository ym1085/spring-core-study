package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() {
        // IOC Container 생성 -> bean 생성 -> bean 의존 관계 주입 -> 초기화 콜백 호출 -> 빈 사용 -> 소멸 콜백 메서드 -> App 종료
        uuid = UUID.randomUUID().toString(); // UUID 생성 -> unique 한 값을 받아옴
        System.out.println("[" + uuid + "] request scope bean created:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean closed:" + this);
    }
}
