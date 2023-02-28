package hello.core.lifecycle;

public class NetworkClient {

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
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
