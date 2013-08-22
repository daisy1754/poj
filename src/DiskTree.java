import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 1760
 **/
public class DiskTree {
    private static int numberOfPaths;
    private static List<Folder> topLevelFolders = new ArrayList<Folder>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfPaths = scanner.nextInt();

        // skip newline after integer
        scanner.nextLine();

        for (int i = 0; i < numberOfPaths; i++) {
            addPath(scanner.nextLine());
        }
        Collections.sort(topLevelFolders);
        for (Folder folder: topLevelFolders) {
            System.out.print(folder);
        }
    }

    private static void addPath(String path) {
        String[] dirs = path.split("\\\\");
        List<Folder> folders = topLevelFolders;
        for (int depth = 0; depth < dirs.length; depth++) {
            Folder folder = getFolderByName(folders, dirs[depth]);
            if (folder == null) {
                folder = new Folder(dirs[depth], depth);
                folders.add(folder);
            }
            folders = folder.children;
        }
    }

    private static Folder getFolderByName(List<Folder> folders, String name) {
        for (Folder f: folders) {
            if (f.name.equals(name)) {
                return f;
            }
        }
        return null;
    }

    private static class Folder implements Comparable<Folder> {
        private final String name;
        private final int depth;
        private List<Folder> children = new ArrayList<Folder>();

        public Folder(String name, int depth) {
            this.name = name;
            this.depth = depth;
        }

        @Override
        public int compareTo(Folder comparisonTarget) {
            return name.compareTo(comparisonTarget.name);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                builder.append(' ');
            }
            builder.append(name).append(System.getProperty("line.separator"));
            Collections.sort(children);
            for (Folder child: children) {
                builder.append(child.toString());
            }
            return builder.toString();
        }
    }
}
