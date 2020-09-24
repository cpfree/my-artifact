package cn.cpf.test.keypress.man;

import com.github.cpfniliu.tool.keypress.RobotUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class MainTest {

    private static CharsetEncoder charsetEncoder = Charset.forName("GBK").newEncoder();

    public static void main(String[] args) throws AWTException, CharacterCodingException {
//        final Robot robot = new Robot();
//        robot.delay(20);
//        RobotUtils.keyPressWithString(robot, '\n', charsetEncoder, 100);
//        RobotUtils.keyPressWithString(robot, '\n', charsetEncoder, 100);
//        RobotUtils.keyPressWithString(robot, '\n', charsetEncoder, 100);
//        RobotUtils.keyPressWithString(robot, '\n', charsetEncoder, 100);
        char c = '　';
        System.out.println(KeyEvent.getExtendedKeyCodeForChar(c));
        RobotUtils.keyPressWithString(new Robot(), '　', Charset.forName("GBK").newEncoder(), 0);
    }

}
