import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Comparator;

public class FileNavigator extends JFrame {

    private JTextField currentDirField;
    private JList<String> fileList;
    private JLabel fileInfoLabel;
    private ButtonGroup rootsGroup;
    private File currentDirectory;
    private DefaultListModel<String> listModel;
    private File[] currentContent;

    public FileNavigator() {
        super("File Navigator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Górny panel z bieżącą ścieżką
        currentDirField = new JTextField();
        currentDirField.setEditable(false);
        add(currentDirField, BorderLayout.NORTH);

        // Centralna lista plików
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);

        // Dolna etykieta z informacjami o pliku
        fileInfoLabel = new JLabel(" ");
        add(fileInfoLabel, BorderLayout.SOUTH);

        // Prawy panel z korzeniami systemu plików
        JPanel rootsPanel = new JPanel();
        rootsPanel.setLayout(new BoxLayout(rootsPanel, BoxLayout.Y_AXIS));
        rootsGroup = new ButtonGroup();

        File[] roots = File.listRoots();
        for (File root : roots) {
            JRadioButton rootButton = new JRadioButton(root.getAbsolutePath());
            rootButton.addActionListener(e -> {
                changeDirectory(root);
            });
            rootsGroup.add(rootButton);
            rootsPanel.add(rootButton);
        }
        add(rootsPanel, BorderLayout.EAST);

        // Obsługa zdarzeń dla listy
        fileList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && fileList.getSelectedIndex() != -1) {
                    showFileInfo(fileList.getSelectedIndex());
                }
            }
        });

        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && fileList.getSelectedIndex() != -1) {
                    // podwójne kliknięcie
                    int index = fileList.getSelectedIndex();
                    File selected = currentContent[index];
                    if (selected.isDirectory()) {
                        changeDirectory(selected);
                    }
                }
            }
        });

        // Ustawienie katalogu startowego na bieżący katalog
        File startDir = new File(System.getProperty("user.dir"));
        changeDirectory(startDir);

        setVisible(true);
    }

    /**
     * Zmienia bieżący katalog i aktualizuje wyświetlaną listę oraz ścieżkę.
     */
    private void changeDirectory(File dir) {
        if (dir != null && dir.isDirectory()) {
            currentDirectory = dir;
            currentDirField.setText(currentDirectory.getAbsolutePath());
            updateFileList();
            fileInfoLabel.setText(" ");
        }
    }

    /**
     * Aktualizuje zawartość listy plików i katalogów w bieżącym katalogu.
     * Katalogi są wyświetlane jako [nazwa], a pliki normalnie.
     * Najpierw katalogi w kolejności alfabetycznej, potem pliki.
     */
    private void updateFileList() {
        File[] files = currentDirectory.listFiles();
        if (files == null) {
            files = new File[0];
        }

        // Podział na katalogi i pliki
        ArrayList<File> dirs = new ArrayList<>();
        ArrayList<File> fls = new ArrayList<>();

        for (File f : files) {
            if (f.isDirectory()) {
                dirs.add(f);
            } else {
                fls.add(f);
            }
        }

        // Sortowanie
        dirs.sort(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
        fls.sort(Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));

        // Połączone wyniki
        ArrayList<File> combined = new ArrayList<>(dirs);
        combined.addAll(fls);

        currentContent = combined.toArray(new File[0]);

        listModel.clear();
        for (File f : currentContent) {
            if (f.isDirectory()) {
                listModel.addElement("[" + f.getName() + "]");
            } else {
                listModel.addElement(f.getName());
            }
        }
    }

    /**
     * Wyświetla informacje o wybranym pliku w dolnej etykiecie.
     */
    private void showFileInfo(int index) {
        if (index < 0 || index >= currentContent.length) return;
        File f = currentContent[index];

        String name = f.getName();
        if (name.isEmpty()) {
            name = f.getAbsolutePath();
        }
        long size = f.length();
        long lastMod = f.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(lastMod);

        String type = f.isDirectory() ? "Katalog" : "Plik";
        fileInfoLabel.setText(type + ": " + name + " | Rozmiar: " + size + " bajtów | Ostatnia modyfikacja: " + dateStr);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileNavigator::new);
    }
}
