import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/* Class ProfileImages
 * Purpose is to contain profile pictures to be used in a Contact profile picture and displayed within a MakeContactPopUp
 * Author: Sandor Santos
 * PProfileImages contains:
 * ImageIcons accessable from methods
 */
public final class ProfileImages {

    private static final File[] files = {
        new File("ProfilePictures/DefaultPFP.jpg"),
        new File("ProfilePictures/PetePFP.jpg"),
        new File("ProfilePictures/CuteGirlPFP.jpg"),
        new File("ProfilePictures/HaloGuyPFP.jpg"),
        new File("ProfilePictures/GumballPFP.jpg"),
        new File("ProfilePictures/quackPFP.jpg"),
        new File("ProfilePictures/genericGuyPFP.jpg")
    };

    ///////////// Method to be used when implimenting a dymanic amount of profile pictures /////////////

    public static ImageIcon getImage(JLabel parent, int i) throws IOException {
        if(i >= files.length) {
            i %= files.length;
        }
        return new ImageIcon(new ImageIcon(files[i].getCanonicalPath()).getImage().getScaledInstance(parent.getWidth(), parent.getHeight(), Image.SCALE_DEFAULT));
    }
}
