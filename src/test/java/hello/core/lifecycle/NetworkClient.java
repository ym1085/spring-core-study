package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    //Network connect URL
    private String url;

    //기본 생성자
    public NetworkClient() {
        System.out.println("생서자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }

    //외부에서 셋팅이 가능하도록 setter 생성
    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call = " + url + ", message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    //의존관계 주입이 끝나면 호출
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    //
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.disconnect");
        disconnect();
    }
}
