import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;


/**
 * Created by Administrator on 2016/1/25.
 */
public class Test {

    public static void main(String[] args){
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
        System.out.println(token.toString());

        subject.login(token);

        subject.isAuthenticated();
        subject.logout();
        System.out.println(token);
    }
}
