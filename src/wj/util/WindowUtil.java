package wj.util;

import javax.swing.*;
import java.awt.*;

public class WindowUtil {
    public void setWindowCenter(JFrame jFrame) {
        int windowWidth = 800;
        int windowHeight = 800;

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        jFrame.setBounds((width - windowWidth) / 2, (height - windowHeight) / 2, windowWidth, windowHeight);
    }

    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        System.out.println(width);
        System.out.println(height);

        double dpi = tk.getScreenResolution();
        double widthValue = screenSize.width / dpi;
        double heightValue = screenSize.height / dpi;

        System.out.println(widthValue);
        System.out.println(heightValue);

        double len = Math.sqrt(widthValue * widthValue + heightValue * heightValue);
        System.out.println(len);
    }
}
