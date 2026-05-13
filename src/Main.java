import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import view.Login;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Tema Nimbus para mejor apariencia
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar el tema Nimbus. Se usará el tema por defecto.");
            }
            new Login().setVisible(true);
        });
    }
}
