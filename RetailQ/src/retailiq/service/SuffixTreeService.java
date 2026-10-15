package retailiq.service;

import java.util.LinkedHashMap;
import java.util.Map;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class SuffixTreeService {

    static class Node {

        String label;

        Map<Character, Node> children =
                new LinkedHashMap<>();

        Node(String label) {
            this.label = label;
        }
    }

    private static void insert(
            Node root,
            String suffix) {

        Node current = root;
        int position = 0;

        while (position < suffix.length()) {

            char first = suffix.charAt(position);

            Node child = current.children.get(first);

            if (child == null) {

                current.children.put(
                        first,
                        new Node(suffix.substring(position))
                );

                return;
            }

            String label = child.label;

            int common = 0;

            while (common < label.length()
                    && position + common < suffix.length()
                    && label.charAt(common)
                    == suffix.charAt(position + common)) {

                common++;
            }

            if (common == label.length()) {

                position += common;
                current = child;

            } else {

                Node split =
                        new Node(label.substring(0, common));

                current.children.put(first, split);

                child.label =
                        label.substring(common);

                split.children.put(
                        child.label.charAt(0),
                        child
                );

                if (position + common < suffix.length()) {

                    Node newLeaf =
                            new Node(
                                suffix.substring(
                                    position + common
                                )
                            );

                    split.children.put(
                            newLeaf.label.charAt(0),
                            newLeaf
                    );
                }

                return;
            }
        }
    }

    private static void printTree(
            Node node,
            String prefix) {

        for (Node child : node.children.values()) {

            System.out.println(
                    prefix + "|-- " + child.label
            );

            printTree(
                    child,
                    prefix + "    "
            );
        }
    }

    public static void performSuffixTree() {

        Product product = chooseProduct();

        if (product == null)
            return;

        String text =
                product.getDescription().toLowerCase();

        Node root = new Node("");

        for (int i = 0; i < text.length(); i++) {

            insert(
                    root,
                    text.substring(i)
            );
        }

        System.out.println();
        System.out.println("========== SUFFIX TREE ==========");
        System.out.println("Product: " + product.getName());
        System.out.println("Text: " + text);
        System.out.println();

        printTree(root, "");
    }

    private static Product chooseProduct() {

        System.out.println("\nAvailable Products:");

        for (Product product : DataStore.products) {

            System.out.println(
                    product.getId()
                    + " - "
                    + product.getName()
            );
        }

        int id =
                MainInput.readInt(
                        "Enter product ID: "
                );

        for (Product product : DataStore.products) {

            if (product.getId() == id)
                return product;
        }

        System.out.println("Product not found.");

        return null;
    }
}