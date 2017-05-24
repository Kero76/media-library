package fr.nicolasgille.medialibrary.utils;

/**
 * An enumeration to represent the format available for a book.
 * It composed by :
 * <ul>
 *     <li><code>CLASSICAL</code> : Classical format when the book can be bought on the market.</li>
 *     <li><code>POCKET</code> : A re-edition of the book in little format.</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public enum BookFormat {
    CLASSICAL("Classical"),
    POCKET("Pocket");

    /**
     * Name stored in database.
     *
     * @since 1.0
     */
    private String name;

    /**
     * Constructor of the Enum for instantiate value of attribute <code>name</code>.
     *
     * @param name
     *  Name stored in database.
     * @since 1.0
     * @version 1.0
     */
    private BookFormat(String name) {
        this.name = name;
    }

    /**
     * Return the name.
     *
     * @return
     *  Return the name of the element.
     * @since 1.0
     * @version 1.0
     */
    public String getName() {
        return name;
    }
}
