package hello.core.singleton;

public class SingletonService {

    /* 01. static 영역에 객체를 딱 1개만 생성 해둔다 */
    private static final SingletonService instance = new SingletonService();

    /* 02. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회 허용 */
    public static SingletonService getInstance() {
        return instance;
    }

    /* 03. 생성자를 private으로 선언하여 외부에서 new 키워드 사용한 객체 생성을 막는다 */
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
