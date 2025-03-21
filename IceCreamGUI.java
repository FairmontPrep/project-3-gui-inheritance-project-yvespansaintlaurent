import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Abstract parent class: Base component
abstract class IceCreamComponent extends JPanel {
    private BufferedImage backgroundImage;
    protected String backgroundPath; // Store the chosen background path
    protected String componentDescription; // Description of the component
    protected String backgroundChangeMessage; // Message about background change

    public IceCreamComponent(String bgPath) {
        this.backgroundPath = bgPath;
        this.backgroundChangeMessage = "Background changed to: " + bgPath; // Store message
        loadImage();
    }

    protected abstract void loadImage();

    protected void setBackgroundImage(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the background change message
        g.setColor(Color.WHITE); // Ensure text is visible
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Bigger font
        g.drawString(backgroundChangeMessage, 10, 30); // Position message at the top
    }

    public String getComponentDescription() {
        return componentDescription;
    }
}

// Child class: Adds bowl
class IceCreamBowl extends IceCreamComponent {
    private BufferedImage bowlImage;
    private boolean showBowl;

    public IceCreamBowl(String bgPath) {
        super(bgPath);
        this.componentDescription = "Ice Cream Bowl";
    }

    @Override
    protected void loadImage() {
        setBackgroundImage(backgroundPath);
        showBowl = backgroundPath.equals("background2.png");

        try {
            bowlImage = ImageIO.read(new File("waffle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showBowl && bowlImage != null) {
            g.drawImage(bowlImage, 10, 70, 220, 220, this); // Moved downward
        }
    }
}

// Grandchild class: Adds ice cream
class IceCreamWithScoop extends IceCreamBowl {
    private BufferedImage iceCreamImage;

    public IceCreamWithScoop(String bgPath) {
        super(bgPath);
        this.componentDescription = "Ice Cream Scoop";
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            iceCreamImage = ImageIO.read(new File("vanilla.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iceCreamImage != null) {
            g.drawImage(iceCreamImage, 10, 90, 220, 220, this); // Moved downward
        }
    }
}

// Great-grandchild class: Adds syrup
class IceCreamWithSyrup extends IceCreamWithScoop {
    private BufferedImage syrupImage;

    public IceCreamWithSyrup(String bgPath) {
        super(bgPath);
        this.componentDescription = "Ice Cream with Syrup";
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            syrupImage = ImageIO.read(new File("chocolate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (syrupImage != null) {
            g.drawImage(syrupImage, 10, 90, 220, 220, this); // Moved downward
        }
    }
}

// Main class to display GUI
public class IceCreamGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ice Cream Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(264, 320); // Increased height to accommodate text

            // Randomly choose a background
            String[] backgrounds = {"background1.png", "background2.png"};
            String selectedBackground = backgrounds[(int) (Math.random() * 2)];

            // Create the ice cream component
            IceCreamComponent iceCream = new IceCreamWithSyrup(selectedBackground);

            // Display component description in the console
            System.out.println("Added Component: " + iceCream.getComponentDescription());

            frame.add(iceCream);
            frame.setVisible(true);
        });
    }
}
