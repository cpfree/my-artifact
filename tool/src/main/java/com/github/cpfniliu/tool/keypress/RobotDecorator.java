package com.github.cpfniliu.tool.keypress;

import com.github.cpfniliu.common.ext.hub.SimpleCode;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * <b>Description : </b>
 * 参考自: https://vimsky.com/examples/detail/java-method-java.awt.event.KeyEvent.getExtendedKeyCodeForChar.html
 *
 * @author CPF
 * Date: 2020/8/6 14:07
 */
@Slf4j
public class RobotDecorator {

    @Getter
    private Robot robot;

    private CharsetEncoder charsetEncoder = Charset.forName("GBK").newEncoder();

    @Getter
    private AsynchronousProcessor<Character> asynchronousProcessor = new AsynchronousProcessor<>(ch -> {
        SimpleCode.ignoreException(() -> RobotUtils.keyPressWithString(robot, ch, charsetEncoder, -1));
        return true;
    }, null, 10, true);

    public RobotDecorator(Robot robot) {
        this.robot = robot;
    }

    public void print(String s) {
        for (char aChar : s.toCharArray()) {
            asynchronousProcessor.add(aChar);
        }
    }

    public void print(char key) {
        asynchronousProcessor.add(key);
    }

}
